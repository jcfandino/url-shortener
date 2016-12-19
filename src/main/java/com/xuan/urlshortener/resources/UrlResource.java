package com.xuan.urlshortener.resources;

import java.net.URI;
import java.util.Optional;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xuan.urlshortener.domain.ShortenedUrl;
import com.xuan.urlshortener.exceptions.InvalidUrl;
import com.xuan.urlshortener.repository.ShortenedUrlRepository;

@Path("/")
public class UrlResource {

    private final static Logger LOG = LoggerFactory.getLogger(UrlResource.class);

    private final String shortDomain;

    private final ShortenedUrlRepository urlRepository;

    public UrlResource(String aShortDomain, ShortenedUrlRepository aUrlRepository) {
        shortDomain = aShortDomain;
        urlRepository = aUrlRepository;
    }

    @GET
    @Path("/{key}")
    public Response redirect(@PathParam("key") String key) {
        LOG.debug("Requesting URL key {}", key);
        // First implementation, only numbers are supported
        Long id = Long.parseLong(key);
        Optional<ShortenedUrl> urlFound = urlRepository.findById(id);
        return urlFound
                .map(ShortenedUrl::getLongUrl)
                    .map(URI::create)
                    .map(uri -> Response.temporaryRedirect(uri).build())
                    .orElse(Response.status(Status.NOT_FOUND).build());
    }

    @POST
    public Response create(String longUrl) {
        try {
            LOG.debug("Creating new short URL for {}", longUrl);
            Long id = urlRepository.addUrl(new ShortenedUrl(longUrl));
            String key = Long.toString(id);
            String shortUrl = String.format("http://%s/%s", shortDomain, key);
            return Response.created(URI.create(shortUrl)).build();
        } catch (InvalidUrl e) {
            LOG.warn("Invalid URL: {}. Rejecting.", longUrl, e);
            return Response.status(Status.BAD_REQUEST).entity("Invalid URL").build();
        }
    }
}
