package com.gvstave.mistergift.service.geoip;

public class GeolocationResult {

    private String country;

    private String region;

    private String city;

    public GeolocationResult(String country, String region, String city) {
        this.country = country;
        this.region = region;
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public String getRegion() {
        return region;
    }

    public String getCity() {
        return city;
    }
}
