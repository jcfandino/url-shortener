package com.xuan.urlshortener.resources;

import java.net.URI;
import java.util.Optional;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xuan.urlshortener.domain.KeyEncoder;
import com.xuan.urlshortener.domain.ShortenedUrl;
import com.xuan.urlshortener.exceptions.InvalidUrl;
import com.xuan.urlshortener.repository.ShortenedUrlRepository;
import com.xuan.urlshortener.views.MainView;

import io.dropwizard.views.View;

@Path("/")
public class UrlResource {

    private final static Logger LOG = LoggerFactory.getLogger(UrlResource.class);

    private final String shortDomain;

    private final ShortenedUrlRepository urlRepository;

    private final KeyEncoder keyEncoder;

    public UrlResource(String aShortDomain, ShortenedUrlRepository aUrlRepository, KeyEncoder encoder) {
        shortDomain = aShortDomain;
        urlRepository = aUrlRepository;
        keyEncoder = encoder;
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public View main() {
        return new MainView();
    }

    @GET
    @Path("/{key}")
    public Response redirect(@PathParam("key") String key) {
        LOG.debug("Requesting URL key {}", key);
        Long id = keyEncoder.decode(key);
        Optional<ShortenedUrl> urlFound = Optional.ofNullable(urlRepository.findById(id));
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
            String key = keyEncoder.encode(id);
            String shortUrl = String.format("http://%s/%s", shortDomain, key);
            return Response.created(URI.create(shortUrl)).build();
        } catch (InvalidUrl e) {
            LOG.warn("Invalid URL: {}. Rejecting.", longUrl, e);
            return Response.status(Status.BAD_REQUEST).entity("Invalid URL").build();
        }
    }
}
