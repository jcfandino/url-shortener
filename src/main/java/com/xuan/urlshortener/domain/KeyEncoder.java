package com.xuan.urlshortener.domain;

/**
 * Simple key encoder. Encodes and decodes numbers in base 62 using the alphabet
 * {@value #ALPHABET}.
 *
 * @author xuan
 */
public class KeyEncoder {

    private final static String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    /**
     * @param number
     *            the number to encode.
     * @return the string representation of the encoded number.
     */
    public String encode(Long number) {
        StringBuilder sb = new StringBuilder();
        do {
            int modulus = (int) (number % ALPHABET.length());
            sb.append(ALPHABET.charAt(modulus));
            number = number / ALPHABET.length();
        } while (number > 0);
        return sb.toString();
    }

    /**
     * @param string
     *            the string to decode.
     * @return the decoded number.
     */
    public Long decode(String string) {
        Long number = 0L;
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            number += ALPHABET.indexOf(c) * basePower(i);
        }
        return number;
    }

    private Long basePower(Integer i) {
        Long result = (long) 1L;
        for (int j = 0; j < i; j++) {
            result = result * ALPHABET.length();
        }
        return result;
    }

}
