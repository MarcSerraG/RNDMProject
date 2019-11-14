package com.rndm.rndmproject.REST;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Weather {



    private int id;
    private String main;
    private String description;
    private String icon;

    public Weather(){}

    public String getMain() {return main;}
    public void setMain(String main) {this.main = main;}
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}
    public String getIcon() {return icon;}
    public void setIcon(String icon) {this.icon = icon;}

    @Override
    public String toString() {
        return "{id=" + id +
                ", main=" + main +
                ", description=" + description +
                ", icon=" + icon +
                "}";
    }
}
