package com.xuan.urlshortener;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import com.xuan.urlshortener.domain.ShortenedUrl;

public class ShortenedUrlMapper implements ResultSetMapper<ShortenedUrl> {

    @Override
    public ShortenedUrl map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new ShortenedUrl(r.getString("long_url"));
    }

}
