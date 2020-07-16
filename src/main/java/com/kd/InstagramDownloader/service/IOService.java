package com.kd.InstagramDownloader.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;

@Service
public class IOService {

    public IOService() {
    }

    public ResponseEntity<BufferedOutputStream> downloadImg(String imgUrl) {
        try {
            System.out.println(imgUrl);
            String filename = "Instagram.jpg";
            File file = new File(filename);
            URL url = new URL(imgUrl);
            InputStream in = new BufferedInputStream(url.openStream());

            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(filename));

            int k;
            while((k = in.read()) != -1) {
                out.write(k);
            }

            in.close();
            out.close();

            HttpHeaders headers = new HttpHeaders();

            headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");

            ResponseEntity<BufferedOutputStream> responseEntity =
                    ResponseEntity.ok().headers(headers).contentLength(
                            file.length()).contentType(MediaType.IMAGE_JPEG).body(out);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
