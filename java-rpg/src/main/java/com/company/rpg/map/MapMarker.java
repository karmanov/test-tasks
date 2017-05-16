package com.company.rpg.map;

import com.company.rpg.map.locations.Location;

/**
 * Contains chars with what {@link Location location}
 * can be printed on the map
 *
 * @author Dmitriy Kamranov
 * @since 1.0
 */
public enum MapMarker {

    PLAYER("P"), EMPTY("0"), MONSTER("M"), CLOSED("*");

    private final String marker;

    MapMarker(String marker) {
        this.marker = marker;
    }

    public String getMarker() {
        return marker;
    }
}
