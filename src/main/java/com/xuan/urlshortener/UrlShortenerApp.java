package com.xuan.urlshortener;

import com.xuan.urlshortener.repository.ShortenedUrlRepository;
import com.xuan.urlshortener.repository.VolatileUrlRepository;
import com.xuan.urlshortener.resources.UrlResource;

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
        // TODO Not Enabling persistence yet
        // bootstrap.addBundle(new DBIExceptionsBundle());
    }

    @Override
    public void run(UrlShortenerConfiguration configuration, Environment environment) {
        // TODO Not Enabling persistence yet
        // DBIFactory factory = new DBIFactory();
        // DBI jdbi = factory.build(environment,
        // configuration.getDataSourceFactory(), "postgresql");
        // UrlRepository urlRepository = jdbi.onDemand(UrlRepository.class);
        // Using simple implementation
        ShortenedUrlRepository urlRepository = new VolatileUrlRepository();
        UrlResource urlResource = new UrlResource(configuration.getShortDomain(), urlRepository);
        environment.jersey().register(urlResource);
    }

}
