package com.systemdesign.urlshortener.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Base62Encoder {
    
    private static final String BASE62_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int SHORT_URL_LENGTH = 7;
    
    /**
     * Generates a short URL using SHA-256 hash and Base62 encoding
     * @param originalUrl The original URL to shorten
     * @return A Base62 encoded short URL string
     */
    public static String generateShortUrl(String originalUrl) {
        try {
            // Generate SHA-256 hash
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(originalUrl.getBytes(StandardCharsets.UTF_8));
            
            // Convert hash to a positive number
            long hashValue = 0;
            for (int i = 0; i < Math.min(8, hashBytes.length); i++) {
                hashValue = (hashValue << 8) | (hashBytes[i] & 0xFF);
            }
            hashValue = Math.abs(hashValue);
            
            // Encode to Base62
            return encodeToBase62(hashValue);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }
    
    /**
     * Encodes a number to Base62
     * @param number The number to encode
     * @return Base62 encoded string
     */
    private static String encodeToBase62(long number) {
        if (number == 0) {
            return "0";
        }
        
        StringBuilder sb = new StringBuilder();
        while (number > 0) {
            sb.append(BASE62_CHARS.charAt((int) (number % 62)));
            number /= 62;
        }
        
        String encoded = sb.reverse().toString();
        
        // Ensure minimum length by padding if necessary
        if (encoded.length() < SHORT_URL_LENGTH) {
            // Pad with leading zeros (represented as '0' in base62)
            int padding = SHORT_URL_LENGTH - encoded.length();
            encoded = "0".repeat(padding) + encoded;
        }
        
        // Return only the first SHORT_URL_LENGTH characters
        return encoded.substring(0, Math.min(SHORT_URL_LENGTH, encoded.length()));
    }
}
