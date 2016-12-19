package com.xuan.urlshortener;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Optional;

import javax.ws.rs.core.Response;

import org.easymock.EasyMockRunner;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.xuan.urlshortener.domain.ShortenedUrl;
import com.xuan.urlshortener.repository.ShortenedUrlRepository;
import com.xuan.urlshortener.resources.UrlResource;

@RunWith(EasyMockRunner.class)
public class UrlResourceTest {

    private static final String SHORT_DOMAIN = "test.com";

    private ShortenedUrlRepository repository = createMock(ShortenedUrlRepository.class);

    private UrlResource resource = new UrlResource(SHORT_DOMAIN, repository);

    @Test
    public void testRedirect() {
        String longUrl = "http://example.com/test";
        expect(repository.findById(1000L)).andReturn(Optional.of(new ShortenedUrl(longUrl)));
        replay(repository);

        Response response = resource.redirect("1000");

        verify(repository);
        assertThat(response.getStatus(), is(HttpStatus.TEMPORARY_REDIRECT_307));
        assertThat(response.getLocation().toString(), is(longUrl));
    }

    @Test
    public void testCreateInvalidUrl() {
        String invalidUrl = "example.com/invalid-no-protocol";
        Response response = resource.create(invalidUrl);
        assertThat(response.getStatus(), is(HttpStatus.BAD_REQUEST_400));
    }

    @Test
    public void testCreate() {
        String longUrl = "http://long.com/123";
        expect(repository.addUrl(anyObject(ShortenedUrl.class))).andReturn(1000L);
        replay(repository);

        Response response = resource.create(longUrl);

        verify(repository);
        assertThat(response.getStatus(), is(HttpStatus.CREATED_201));
        assertThat(response.getLocation().toString(), is("http://" + SHORT_DOMAIN + "/1000"));
    }

}
