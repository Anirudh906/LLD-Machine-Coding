package com.systemdesign.urlshortener.service;

public interface UrlService {
    String shortenUrl(String url);
    String redirectToOriginalUrl(String shortUrl);
}
