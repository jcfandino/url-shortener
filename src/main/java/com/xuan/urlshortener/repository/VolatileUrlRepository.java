package com.xuan.urlshortener.repository;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import com.xuan.urlshortener.domain.ShortenedUrl;

/**
 * In memory implementation of {@link ShortenedUrlRepository}.
 *
 * @author xuan
 *
 */
public class VolatileUrlRepository implements ShortenedUrlRepository {

    private final Map<Long, ShortenedUrl> urls = new ConcurrentHashMap<>();

    @Override
    public Optional<ShortenedUrl> findById(Long id) {
        return Optional.ofNullable(urls.get(id));
    }

    @Override
    public Long addUrl(ShortenedUrl shortenedUrl) {
        Long id = Instant.now().toEpochMilli();
        String longUrl = shortenedUrl.getLongUrl();
        urls.put(id, new ShortenedUrl(longUrl));
        return id;
    }

}
