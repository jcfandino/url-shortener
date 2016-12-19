package com.xuan.urlshortener.domain;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import com.xuan.urlshortener.exceptions.InvalidUrl;

public class ShortenedUrl {

    private final String longUrl;

    public ShortenedUrl(String aLongUrl) {
        try {
            new URL(aLongUrl).toURI();
        } catch (MalformedURLException | URISyntaxException e) {
            throw new InvalidUrl();
        }
        longUrl = aLongUrl;
    }

    public String getLongUrl() {
        return longUrl;
    }

}
