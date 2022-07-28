package com.hotjoe.util.urlshortner.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * The outgoing response to shorten a url.  Taken directly from
 * <a href="https://dev.bitly.com/api-reference/">the Bit.ly docs</a> for <pre>/v4/shorten</pre>
 *
 */

public class ShortenResponse {
    @JsonInclude(NON_NULL)
    private List<String> references;
    private String id;
    private String longUrl;

    private String link;

    @JsonInclude(NON_NULL)
    private Boolean archived;
    @JsonInclude(NON_NULL)
    private String createdAt;
    @JsonInclude(NON_NULL)
    private List<String> customBitLinks;
    @JsonInclude(NON_NULL)
    private List<String> tags;
    @JsonInclude(NON_NULL)
    private List<Deeplinks> deeplinks;

    public List<String> getReferences() {
        return references;
    }

    public void setReferences(List<String> references) {
        this.references = references;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Boolean getArchived() {
        return archived;
    }

    public void setArchived(Boolean archived) {
        this.archived = archived;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public List<String> getCustomBitLinks() {
        return customBitLinks;
    }

    public void setCustomBitLinks(List<String> customBitLinks) {
        this.customBitLinks = customBitLinks;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<Deeplinks> getDeeplinks() {
        return deeplinks;
    }

    public void setDeeplinks(List<Deeplinks> deeplinks) {
        this.deeplinks = deeplinks;
    }
}
