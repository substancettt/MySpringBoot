package com.wellshang.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.wellshang.data.config.ApplicationConfig;

@SpringBootApplication
public class MyApplication2 {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(ApplicationConfig.class, args);

        try {
            BufferedReader br = new BufferedReader(new FileReader("test.txt"));
            String line = null;
            List s = new ArrayList<String>();
            while ((line = br.readLine()) != null) {
                s.add(line);
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
