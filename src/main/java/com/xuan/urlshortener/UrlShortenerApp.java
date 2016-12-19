package com.xuan.urlshortener;

import org.skife.jdbi.v2.DBI;

import com.xuan.urlshortener.domain.KeyEncoder;
import com.xuan.urlshortener.repository.ShortenedUrlRepository;
import com.xuan.urlshortener.resources.UrlResource;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.jdbi.bundles.DBIExceptionsBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;

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
        bootstrap.addBundle(new AssetsBundle("/assets/", "/static/"));
        bootstrap.addBundle(new ViewBundle<UrlShortenerConfiguration>());
        bootstrap.addBundle(new DBIExceptionsBundle());
        bootstrap.addBundle(new MigrationsBundle<UrlShortenerConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(UrlShortenerConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        });
    }

    @Override
    public void run(UrlShortenerConfiguration configuration, Environment environment) {
        DBIFactory factory = new DBIFactory();
        DBI jdbi = factory.build(environment, configuration.getDataSourceFactory(), "postgresql");
        ShortenedUrlRepository urlRepository = jdbi.onDemand(ShortenedUrlRepository.class);
        UrlResource urlResource = new UrlResource(configuration.getShortDomain(), urlRepository, new KeyEncoder());
        environment.jersey().register(urlResource);
    }

}
