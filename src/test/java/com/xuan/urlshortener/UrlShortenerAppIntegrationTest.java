package com.xuan.urlshortener;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.jetty.http.HttpStatus;
import org.glassfish.jersey.client.ClientProperties;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import io.dropwizard.testing.ConfigOverride;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;

/**
 * Integration test for the whole app.
 */
public class UrlShortenerAppIntegrationTest {

    private static final String CONFIG_PATH = ResourceHelpers.resourceFilePath("config-test.yml");

    private Client client;

    @ClassRule
    public static final DropwizardAppRule<UrlShortenerConfiguration> RULE = new DropwizardAppRule<>(
            UrlShortenerApp.class, CONFIG_PATH, ConfigOverride.config("database.url", "jdbc:h2:" + createTempFile()));

    @BeforeClass
    public static void migrateDb() throws Exception {
        RULE.getApplication().run("db", "migrate", CONFIG_PATH);
    }

    @Before
    public void setup() {
        client = ClientBuilder.newClient();
    }

    @After
    public void tearDown() throws Exception {
        client.close();
    }

    @Test
    public void testMainView() {
        Response response = client.target(getBaseUrl()).request().get();
        assertThat(response.getStatus(), is(HttpStatus.OK_200));
        assertThat(response.readEntity(String.class), containsString("<title>URL Shortener</title>"));
    }

    @Test
    public void testRequestUnknownKey() {
        Response response = client.target(getBaseUrl() + "/ABC123").request().get();
        assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND_404));
    }

    @Test
    public void testPostLongUrlAndRedirectToShortUrl() {
        String longUrl = "http://google.com/";
        Entity<String> content = Entity.entity(longUrl, MediaType.TEXT_PLAIN);
        // POST the url and check response
        Response postResponse = client.target(getBaseUrl()).request().post(content);
        assertThat(postResponse.getStatus(), is(HttpStatus.CREATED_201));
        String shortUrl = replacePortInUrl(postResponse.getLocation().toString());
        assertThat(shortUrl, containsString(getBaseUrl()));

        // GET the short URL to check where it redirects to
        Response getResponse = client
                .target(shortUrl)
                    .property(ClientProperties.FOLLOW_REDIRECTS, false)
                    .request()
                    .get();
        assertThat(getResponse.getStatus(), is(HttpStatus.TEMPORARY_REDIRECT_307));
        assertThat(getResponse.getLocation().toString(), is(longUrl));
    }

    @Test
    public void testPostIllegalUrl() {
        String longUrl = "google.com"; // Missing protocol
        Entity<String> content = Entity.entity(longUrl, MediaType.TEXT_PLAIN);
        Response postResponse = client.target(getBaseUrl()).request().post(content);
        assertThat(postResponse.getStatus(), is(HttpStatus.BAD_REQUEST_400));
    }

    private String getBaseUrl() {
        return "http://localhost:" + RULE.getLocalPort();
    }

    private String replacePortInUrl(String url) {
        return url.replace("PORT", String.valueOf(RULE.getLocalPort()));
    }

    private static String createTempFile() {
        try {
            return File.createTempFile("urlshortener-test", null).getAbsolutePath();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
