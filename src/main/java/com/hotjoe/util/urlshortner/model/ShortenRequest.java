package com.hotjoe.util.urlshortner.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * The incoming request to shorten a url.  Taken partially from
 * <a href="https://dev.bitly.com/api-reference/">the Bit.ly docs</a> for <pre>/v4/shorten</pre>
 *
 */
public class ShortenRequest {

    @JsonProperty("group_guid")
    @JsonInclude(NON_NULL)
    private String groupGuid;

    @JsonInclude(NON_NULL)
    private String domain;

    @JsonProperty("long_url")
    private String longUrl;

    public String getGroupGuid() {
        return groupGuid;
    }

    public void setGroupGuid(String groupGuid) {
        this.groupGuid = groupGuid;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    @Override
    public String toString() {
        return "ShortenRequest{" +
                "groupGuid='" + groupGuid + '\'' +
                ", domain='" + domain + '\'' +
                ", longUrl='" + longUrl + '\'' +
                '}';
    }
}
