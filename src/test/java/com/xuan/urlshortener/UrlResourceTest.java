package com.xuan.urlshortener;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import javax.ws.rs.core.Response;

import org.eclipse.jetty.http.HttpStatus;
import org.junit.Test;

public class UrlResourceTest {

    private static final String SHORT_DOMAIN = "test.com";

    private UrlResource resource = new UrlResource(SHORT_DOMAIN);

    @Test
    public void testRedirect() {
        Response response = resource.redirect("test");
        assertThat(response.getStatus(), is(HttpStatus.TEMPORARY_REDIRECT_307));
    }

    @Test
    public void testCreate() {
        Response response = resource.create("http://long.com/123");
        assertThat(response.getStatus(), is(HttpStatus.CREATED_201));
        assertThat(response.getLocation().toString(), containsString(SHORT_DOMAIN));
    }

}
