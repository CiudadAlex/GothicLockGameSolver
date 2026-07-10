package org.leviatanplatform.games.gothiclock.engine.domain;

public class Movement {

    private final Integer layerIndex;
    private final boolean upOrDown;

    public Movement(Integer layerIndex, boolean upOrDown) {
        this.layerIndex = layerIndex;
        this.upOrDown = upOrDown;
    }

    public Integer getLayerIndex() {
        return layerIndex;
    }

    public boolean isUpOrDown() {
        return upOrDown;
    }
}
