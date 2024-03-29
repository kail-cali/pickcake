package co.pickcake.mapapi.service;

import co.pickcake.common.entity.Address;

import java.net.URI;

public interface BaseUriBuilder {
    public URI builderUrlByAddress(Address address);

    public URI builderUrlByKeyWord(Double longitude, Double latitude, Double radius, String keyword);

}
