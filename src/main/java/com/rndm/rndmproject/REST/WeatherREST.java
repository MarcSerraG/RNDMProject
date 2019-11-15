package com.rndm.rndmproject.REST;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherREST {

    private List<Weather> weather;
    private Main main;
    private Sys sys;
    private String name;

    public WeatherREST() {
    }

    public Main getMain() {
        return main;
    }
    public void setMain(Main main) {
        this.main = main;
    }
    public Sys getSys() {return sys;}
    public void setSys(Sys sys) {this.sys = sys;}
    public List<Weather> getWeather() {return weather;}
    public void setWeather(List<Weather> weather) {this.weather = weather;}
    public String getName(){return name;}
    public void setName(String name){this.name = name;}
    public String getClima(){return weather.get(0).getMain();}
    public float getTemp(){return main.getTemp();}
    public float getTempMin(){return main.getTemp_min();}
    public float getTempMax(){return main.getTemp_max();}
    public String getCountry(){return sys.getCountry();}

    @Override
    public String toString() {
        return "WeatherREST{" +
                "weather:" + weather.get(0) +
                ", main:" + main +
                ", sys:" + sys +
                ", name:" + name +
                '}';
    }
}