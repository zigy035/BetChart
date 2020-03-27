package com.betchart.util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.net.URL;

public class FileDownload {

    public static void main(String[] args) {

        File file = new File("c:/jj/1gb.zip");

        URL url;
        try {
            url = new URL("http://download.thinkbroadband.com/1GB.zip");
            long start = System.currentTimeMillis();
            System.out.println("Downloading....");
            FileUtils.copyURLToFile(url, file);
            long end = System.currentTimeMillis();
            System.out.println("Completed....in ms : " + (end - start));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
