package com.xuan.urlshortener.domain;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class KeyEncoderTest {

    private KeyEncoder encoder = new KeyEncoder();

    @Test
    public void testEncodeZero() {
        String encoded = encoder.encode(0L);
        assertThat(encoded, is("A"));
    }

    @Test
    public void testDecodeA() {
        Long decoded = encoder.decode("A");
        assertThat(decoded, is(0L));
    }

    @Test
    public void testEncode1000000000() {
        String encoded = encoder.encode(10000000000L);
        assertThat(encoded, is("ky8u4K"));
    }

    @Test
    public void testDecodeABC() {
        Long decoded = encoder.decode("ABC");
        assertThat(decoded, is(7750L));
    }

    @Test
    public void printMany() {
        for (Long i = 0L; i < 5000L; i++) {
            String key = encoder.encode(i);
            assertThat(encoder.decode(key), is(i));
        }
    }
}
