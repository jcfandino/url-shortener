package com.xuan.urlshortener;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

public class UrlShortenerConfiguration extends Configuration {

    @NotNull
    private String shortDomain;

    @Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();

    public String getShortDomain() {
        return shortDomain;
    }

    public void setShortDomain(String aShortDomain) {
        shortDomain = aShortDomain;
    }

    @JsonProperty("database")
    public void setDataSourceFactory(DataSourceFactory factory) {
        database = factory;
    }

    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory() {
        return database;
    }
}
