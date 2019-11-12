package com.rndm.rndmproject;

import com.rndm.rndmproject.Controller.WeatherREST;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class RndmProjectApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(RndmProjectApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();}

    @Override
    public void run(String... args) throws Exception {

        RestTemplate rest = new RestTemplate();

        WeatherREST weather = rest.getForObject(
                "http://api.openweathermap.org/data/2.5/weather?q=London,uk&APPID=71d74a92f4a94cdec1174ca88377b4cf", WeatherREST.class);
        System.out.println(weather.toString());

    }
}
