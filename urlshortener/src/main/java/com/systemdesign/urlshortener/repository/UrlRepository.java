package com.systemdesign.urlshortener.repository;

import com.systemdesign.urlshortener.model.UrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<UrlEntity, Long> {
    Optional<UrlEntity> findByShortUrl(String shortUrl);
    Optional<UrlEntity> findByOriginalUrl(String originalUrl);
}
