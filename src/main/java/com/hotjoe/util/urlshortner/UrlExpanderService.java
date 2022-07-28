package com.hotjoe.util.urlshortner;

import com.hotjoe.persistence.dao.ClickTrackerDAO;
import com.hotjoe.persistence.dao.ShortUrlDAO;
import com.hotjoe.persistence.model.ClickTracker;
import com.hotjoe.persistence.model.ShortUrl;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import io.quarkus.logging.Log;



/**
 * Expands a URL based on the passed in short code.  This is the base62 string from the "encrypted" sequence number
 * stored in the db.
 *
 */
@Path("/")
public class UrlExpanderService {
    @Inject
    ShortUrlDAO shortUrlDAO;

    @Inject
    ClickTrackerDAO clickTrackerDAO;

    @Path("/{shortCode}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response expandShortCode(@PathParam("shortCode") String shortCode, @Context HttpServletRequest request) throws URISyntaxException {

        Log.debug("short code request is \"" + shortCode + "\"" );

        int encryptedId = Base62.shortURLtoID(shortCode);

        ShortUrl shortUrl = shortUrlDAO.findByEncryptedId(encryptedId);

        if( shortUrl == null ) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("the short code " + shortCode + " does not exist")
                    .type(MediaType.TEXT_PLAIN_TYPE)
                    .build();
        }

        if( shortUrl.getExpirationDate().isBefore(OffsetDateTime.now(ZoneOffset.UTC))) {
            return Response
                    .status(Response.Status.GONE)
                    .entity("the short code " + shortCode + " has expired")
                    .type(MediaType.TEXT_PLAIN_TYPE)
                    .build();
        }

        //
        // track clicks even if the click count is surpassed
        //
        shortUrl.setClickCount(shortUrl.getClickCount() + 1);
        shortUrlDAO.update(shortUrl);

        if( shortUrl.getClickLimit() > 0 ) {
            if( shortUrl.getClickCount() > shortUrl.getClickLimit() ) {
                return Response
                        .status(Response.Status.TOO_MANY_REQUESTS)
                        .entity("the short code " + shortCode + " has reached it's click limit")
                        .type(MediaType.TEXT_PLAIN_TYPE)
                        .build();
            }
        }

        ClickTracker clickTracker = new ClickTracker();
        clickTracker.setShortUrl( shortUrl );
        clickTracker.setUserAgent(request.getHeader("User-Agent"));
        clickTracker.setRemoteHostAddr(request.getRemoteAddr());
        clickTracker.setCreateDate(OffsetDateTime.now(ZoneId.of("UTC")));

        clickTrackerDAO.save(clickTracker);

        //
        // send a 302 back to redirect to the original url.  we've validated the url so we "should never" see
        // the exception from the URI constructor
        //
        return Response.status(Response.Status.FOUND).location(new URI(shortUrl.getOriginalUrl())).build();
    }
}
