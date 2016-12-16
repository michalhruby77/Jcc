package com.leoni.data.dto;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cigi1001 on 27. 11. 2016.
 */
public class RelaisZoneDTO {

    Map<String, String> positionMap;
    String nazov;

    public RelaisZoneDTO(String nazov) {
        positionMap = new HashMap<>();
        this.nazov = nazov;
    }

    public Map<String, String> getPositionMap() {
        return positionMap;
    }

    public void setPositionMap(Map<String, String> positionMap) {
        this.positionMap = positionMap;
    }

    public String getNazov() {
        return nazov;
    }

    public void setNazov(String nazov) {
        this.nazov = nazov;
    }
}
