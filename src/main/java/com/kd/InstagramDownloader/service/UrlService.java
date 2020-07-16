package com.kd.InstagramDownloader.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UrlService {
    public List<String> getURL(String url, boolean choice) {
        String JSON = readUrl(url + "?__a=1");
        if (choice) { return findVideo(JSON); }
        else { return findImg(JSON); }
    }

    private String readUrl(String URL) {
        String out = "";
        try {
            java.net.URL oracle = new URL(URL);
            URLConnection yc = oracle.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    yc.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                out += inputLine;
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out;
    }

    private List<String> findImg(String JSON) {
        List<String> out = new LinkedList<>();
        if (JSON.contains("\"display_url\":\"")) {
            String[] urlsArr = JSON.split("\"display_url\":\"");
            for (int i = 1; i < urlsArr.length; i++) {
                out.add(urlsArr[i].split("\",\"")[0]);
            }
        }
        return out.stream().distinct().collect(Collectors.toList());
    }

    private List<String> findVideo(String JSON) {
        List<String> out = new LinkedList<>();
        if (JSON.contains("\"video_url\":")) {
            String[] urlsArr = JSON.split("\"video_url\":");
            for (int i = 1; i < urlsArr.length; i++) {
                out.add(urlsArr[i].substring(1, urlsArr[i].indexOf("\",")));
            }
        }
        return out.stream().distinct().collect(Collectors.toList());
    }
}
