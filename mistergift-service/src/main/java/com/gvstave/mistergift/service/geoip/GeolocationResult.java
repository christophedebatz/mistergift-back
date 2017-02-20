package com.gvstave.mistergift.service.geoip;

public class GeolocationResult {

    /** The country name. */
    private String country;

    /** The region name. */
    private String region;

    /** The city name. */
    private String city;

    /**
     * Constructor.
     *
     * @param country The country name.
     * @param region  The region name.
     * @param city    The city name.
     */
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
