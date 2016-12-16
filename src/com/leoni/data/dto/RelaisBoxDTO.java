package com.leoni.data.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cigi1001 on 27. 11. 2016.
 */
public class RelaisBoxDTO {
    List<RelaisZoneDTO> relaisZoneDTOList;

    public RelaisBoxDTO() {
        this.relaisZoneDTOList = new ArrayList<>();
    }

    public List<RelaisZoneDTO> getRelaisZoneDTOList() {
        return relaisZoneDTOList;
    }

    public void setRelaisZoneDTOList(List<RelaisZoneDTO> relaisZoneDTOList) {
        this.relaisZoneDTOList = relaisZoneDTOList;
    }
}
