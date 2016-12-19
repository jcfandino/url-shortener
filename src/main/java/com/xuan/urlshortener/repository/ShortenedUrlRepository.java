package com.xuan.urlshortener.repository;

import java.util.Optional;

import com.xuan.urlshortener.domain.ShortenedUrl;

/**
 * A Repository to save Shortened URLs.
 *
 * @author xuan
 */
public interface ShortenedUrlRepository {

    /**
     * @return Optional of the ShortenedUrl, absent if not found.
     * @param id
     *            the numeric id of the ShortenedUrl to find.
     */
    Optional<ShortenedUrl> findById(Long id);

    /**
     * Insert a new ShortenedUrl to the repository and generate the ID.
     *
     * @param shortenedUrl
     *            the ShortenedUrl to add.
     * @return the newly created ID for the url.
     */
    Long addUrl(ShortenedUrl shortenedUrl);
}
