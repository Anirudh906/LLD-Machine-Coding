package com.systemdesign.urlshortener.controllers;

import com.systemdesign.urlshortener.dto.UrlRequest;
import com.systemdesign.urlshortener.dto.UrlResponse;
import com.systemdesign.urlshortener.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/api/v1/url")
public class UrlController {
    
    @Autowired
    private UrlService urlService;
    
    @PostMapping("/shorten")
    public ResponseEntity<UrlResponse> shortenUrl(@RequestBody UrlRequest request) {
        try {
            String shortUrl = urlService.shortenUrl(request.getUrl());
            UrlResponse response = new UrlResponse(request.getUrl(), shortUrl);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/{shortUrl}")
    public RedirectView redirectToOriginalUrl(@PathVariable String shortUrl) {
        String originalUrl = urlService.redirectToOriginalUrl(shortUrl);
        return new RedirectView(originalUrl);
    }
}
