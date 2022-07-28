package com.hotjoe.util.urlshortner.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Part of the outgoing response to shorten a url.  Taken directly from
 * <a href="https://dev.bitly.com/api-reference/">the Bit.ly docs</a> for <pre>/v4/shorten</pre>
 *
 */
public class Deeplinks {
    private String guid;
    private String bitlink;

    @JsonProperty("app_uri_path")
    private String appUrlPath;

    @JsonProperty("install_url")
    private String installUrl;

    @JsonProperty("app_guid")
    private String appGuid;
    private String os;

    @JsonProperty("install_type")
    private String installType;

    private String created;
    private String modified;
    private String brandGuid;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getBitlink() {
        return bitlink;
    }

    public void setBitlink(String bitlink) {
        this.bitlink = bitlink;
    }

    public String getAppUrlPath() {
        return appUrlPath;
    }

    public void setAppUrlPath(String appUrlPath) {
        this.appUrlPath = appUrlPath;
    }

    public String getInstallUrl() {
        return installUrl;
    }

    public void setInstallUrl(String installUrl) {
        this.installUrl = installUrl;
    }

    public String getAppGuid() {
        return appGuid;
    }

    public void setAppGuid(String appGuid) {
        this.appGuid = appGuid;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getInstallType() {
        return installType;
    }

    public void setInstallType(String installType) {
        this.installType = installType;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getBrandGuid() {
        return brandGuid;
    }

    public void setBrandGuid(String brandGuid) {
        this.brandGuid = brandGuid;
    }
}
