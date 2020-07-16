package com.kd.InstagramDownloader.controller;

import com.kd.InstagramDownloader.service.IOService;
import com.kd.InstagramDownloader.service.UrlService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedOutputStream;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {

    UrlService urlService;
    IOService ioService;

    public RestController(UrlService urlService, IOService ioService) {
        this.urlService = urlService;
        this.ioService = ioService;
    }

    @PostMapping("/image")
    public ResponseEntity<List<String>> getImg(@RequestBody String url) {
        return new ResponseEntity<>(urlService.getURL(url, false), HttpStatus.OK);
    }

    @PostMapping(
            value = "/image/download",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public ResponseEntity<BufferedOutputStream> downloadImg(@RequestBody String url) {
        return ioService.downloadImg(url);
    }

    @PostMapping("/video")
    public ResponseEntity<List<String>> getVideo(@RequestBody String url) {
        return new ResponseEntity<>(urlService.getURL(url, true), HttpStatus.OK);
    }
}
