package com.xuan.urlshortener.repository;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import com.xuan.urlshortener.ShortenedUrlMapper;
import com.xuan.urlshortener.domain.ShortenedUrl;

/**
 * A Repository to save Shortened URLs.
 *
 * @author xuan
 */
@RegisterMapper(ShortenedUrlMapper.class)
public interface ShortenedUrlRepository {

    /**
     * @return the ShortenedUrl, null if not found. <br>
     *         Note: I tried to use java.util.Optional but the Dropwizard
     *         mapping didn't work as supposed.
     *
     * @param id
     *            the numeric id of the ShortenedUrl to find.
     *
     */
    @SqlQuery("select long_url from shortened_url where id = :id")
    ShortenedUrl findById(@Bind("id") Long id);

    /**
     * Insert a new ShortenedUrl to the repository and generate the ID.
     *
     * @param shortenedUrl
     *            the ShortenedUrl to add.
     * @return the newly created ID for the url.
     */
    @SqlUpdate("insert into shortened_url(long_url) values (:longUrl)")
    @GetGeneratedKeys
    Long addUrl(@BindBean ShortenedUrl shortenedUrl);
}
