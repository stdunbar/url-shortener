package com.hotjoe.util.urlshortner;

import com.hotjoe.persistence.dao.ShortUrlDAO;
import com.hotjoe.persistence.model.ShortUrl;
import com.hotjoe.util.urlshortner.model.ShortenRequest;
import com.hotjoe.util.urlshortner.model.ShortenResponse;
import io.quarkus.narayana.jta.QuarkusTransaction;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

/**
 * A simple URL shortening service.  The inputs and outputs are taken from the bit.ly API.  Most fields
 * are not used in this implementation but are maintained for future compatibility if you ever wanted
 * to move to bit.ly.
 *
 */
@Path("/v1")
public class UrlShortnerService {
    private static final Logger logger = LoggerFactory.getLogger( UrlShortnerService.class );

    @Inject
    ShortUrlDAO shortUrlDAO;

    @ConfigProperty(name="base.domain.name")
    String baseDomainName;

    @ConfigProperty(name="click.count.limit", defaultValue = "0")
    Integer clickCountLimit;

    @Path("/shorten")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response shorten(ShortenRequest shortenRequest ) {

        logger.info("shorten request is " + shortenRequest );
        //
        // make sure the incoming URL is valid
        //
        try {
            URI uri = new URI( shortenRequest.getLongUrl() );
            if( (uri.getScheme() == null) || !uri.getScheme().toLowerCase().startsWith("http"))
                throw new URISyntaxException( shortenRequest.getLongUrl(), "this service only accepts http based urls");
        }
        catch( URISyntaxException use ) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("the given uri is not valid - " + use.getMessage())
                    .type(MediaType.TEXT_PLAIN_TYPE)
                    .build();
        }

        //
        // can't use @Transactional on this method as we want to re-read what the database generated
        // for the encrypted id.  so manage transactions manually
        //
        QuarkusTransaction.begin();
        ShortUrl shortUrl = new ShortUrl();
        OffsetDateTime nowInUTC = OffsetDateTime.now(ZoneOffset.UTC);
        shortUrl.setExpirationDate(nowInUTC.plusDays(5));
        shortUrl.setIssueDate(nowInUTC);
        shortUrl.setOriginalUrl(shortenRequest.getLongUrl());
        if( clickCountLimit != null )
        shortUrl.setClickLimit(3);
        shortUrl.setClickCount(0);

        shortUrlDAO.save(shortUrl);
        QuarkusTransaction.commit();

        //
        // now read back the saved value.  the primary key will already be saved in the object
        // so use it for the query
        //
        QuarkusTransaction.begin();
        ShortUrl reReadShortUrl = shortUrlDAO.findById(shortUrl.getShortUrlId());
        QuarkusTransaction.commit();

        ShortenResponse shortenResponse = new ShortenResponse();
        shortenResponse.setLongUrl(shortenRequest.getLongUrl());
        shortenResponse.setCreatedAt(reReadShortUrl.getIssueDate().toString());
        shortenResponse.setId(Integer.toString(reReadShortUrl.getShortUrlEncryptedId()));
        shortenResponse.setLink(baseDomainName + "/" + Base62.idToShortURL(reReadShortUrl.getShortUrlEncryptedId()));

        return Response.ok().entity(shortenResponse).build();
    }
}
