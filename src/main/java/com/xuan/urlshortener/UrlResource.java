package com.xuan.urlshortener;

import java.net.URI;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/")
public class UrlResource {

    private final static Logger LOG = LoggerFactory.getLogger(UrlResource.class);

    private final String shortDomain;

    public UrlResource(String aShortDomain) {
        shortDomain = aShortDomain;
    }

    @GET
    @Path("/{key}")
    public Response redirect(@PathParam("key") String key) {
        LOG.debug("Requesting URL key {}", key);
        String url = "http://google.com/search?q=" + key;
        return Response.temporaryRedirect(URI.create(url)).build();
    }

    @POST
    public Response create(String longUrl) {
        LOG.debug("Creating new short URL for {}", longUrl);
        String key = "test";
        String shortUrl = String.format("http://%s/%s", shortDomain, key);
        return Response.created(URI.create(shortUrl)).build();
    }
}
