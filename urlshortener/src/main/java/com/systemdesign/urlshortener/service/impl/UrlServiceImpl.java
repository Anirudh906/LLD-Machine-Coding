package com.systemdesign.urlshortener.service.impl;

import com.systemdesign.urlshortener.model.UrlEntity;
import com.systemdesign.urlshortener.repository.UrlRepository;
import com.systemdesign.urlshortener.service.UrlService;
import com.systemdesign.urlshortener.util.Base62Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UrlServiceImpl implements UrlService {
    
    @Autowired
    private UrlRepository urlRepository;
    
    @Override
    public String shortenUrl(String originalUrl) {
        // Check if URL already exists in database
        Optional<UrlEntity> existingUrl = urlRepository.findByOriginalUrl(originalUrl);
        if (existingUrl.isPresent()) {
            return existingUrl.get().getShortUrl();
        }
        
        // Generate short URL using SHA-256 + Base62 encoding
        String shortUrl = Base62Encoder.generateShortUrl(originalUrl);
        
        // Handle collision - if short URL already exists, append a counter
        String finalShortUrl = shortUrl;
        int counter = 0;
        while (urlRepository.findByShortUrl(finalShortUrl).isPresent()) {
            counter++;
            finalShortUrl = Base62Encoder.generateShortUrl(originalUrl + counter);
        }
        
        // Save to database
        UrlEntity urlEntity = new UrlEntity(originalUrl, finalShortUrl);
        urlRepository.save(urlEntity);
        
        return finalShortUrl;
    }

    @Override
    public String redirectToOriginalUrl(String shortUrl) {
        Optional<UrlEntity> urlEntity = urlRepository.findByShortUrl(shortUrl);
        
        if (urlEntity.isEmpty()) {
            throw new RuntimeException("Short URL not found: " + shortUrl);
        }
        
        // Check if URL has expired
        if (urlEntity.get().getExpiresAt().isBefore(java.time.LocalDateTime.now())) {
            throw new RuntimeException("Short URL has expired: " + shortUrl);
        }
        
        return urlEntity.get().getOriginalUrl();
    }
}
