package com.untildawn.Enums.MapConsts;

import java.util.Map;

public enum MapSizes {
    MAP_ROWS(100),
    MAP_COLS(100),
    FARM_ROWS(30),
    FARM_COLS(30),
    ;

    private final int size;
    MapSizes(int size) {
        this.size = size;
    }

    public int getSize() {
        return this.size;
    }
}
