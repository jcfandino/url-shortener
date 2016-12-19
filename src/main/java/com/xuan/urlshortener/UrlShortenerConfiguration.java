package com.xuan.urlshortener;

import io.dropwizard.Configuration;

public class UrlShortenerConfiguration extends Configuration {

    private String shortDomain;

    public String getShortDomain() {
        return shortDomain;
    }

    public void setShortDomain(String aShortDomain) {
        shortDomain = aShortDomain;
    }
}
