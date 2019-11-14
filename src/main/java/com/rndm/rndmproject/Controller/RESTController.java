package com.rndm.rndmproject.Controller;

import com.rndm.rndmproject.REST.WeatherREST;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RESTController {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {return builder.build();}

    private WeatherREST weather;
    private RestTemplate rest;

    public RESTController(WeatherREST weather){
        this.weather = weather;
        rest = new RestTemplate();
    }

  public WeatherREST getWeather(){

      weather = rest.getForObject(
              "http://api.openweathermap.org/data/2.5/weather?q=Mataro,es&units=metric&APPID=71d74a92f4a94cdec1174ca88377b4cf",
              WeatherREST.class);

      System.out.println(weather);
        return weather;}
}
