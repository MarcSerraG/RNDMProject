package com.rndm.rndmproject.REST;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

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