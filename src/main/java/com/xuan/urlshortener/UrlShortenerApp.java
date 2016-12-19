package com.xuan.urlshortener;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class UrlShortenerApp extends Application<UrlShortenerConfiguration> {

    public static void main(String[] args) throws Exception {
        new UrlShortenerApp().run(args);
    }

    @Override
    public String getName() {
        return "url-shortener";
    }

    @Override
    public void initialize(Bootstrap<UrlShortenerConfiguration> bootstrap) {
    }

    @Override
    public void run(UrlShortenerConfiguration configuration, Environment environment) {
        UrlResource urlResource = new UrlResource(configuration.getShortDomain());
        environment.jersey().register(urlResource);
    }

}
