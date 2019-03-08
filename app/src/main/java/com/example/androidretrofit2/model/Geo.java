
package com.example.androidretrofit2.model;


import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class Geo {

    private String lat;
    private String lng;

    @Builder
    public Geo(String lat, String lng) {
        this.lat = lat;
        this.lng = lng;
    }
}
