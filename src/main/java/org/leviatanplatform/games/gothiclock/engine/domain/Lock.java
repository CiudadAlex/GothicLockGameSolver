package org.leviatanplatform.games.gothiclock.engine.domain;

import java.util.ArrayList;
import java.util.List;

public class Lock {

    private final List<LockLayer> listLayer;

    public Lock(int positionToOpen, int numberOfPositions, List<Integer> listInitialPositions) {

        this.listLayer = new ArrayList<>();

        for (Integer initialPosition : listInitialPositions) {
            LockLayer layer = new LockLayer(positionToOpen, numberOfPositions, initialPosition);
            listLayer.add(layer);
        }
    }

    public void addDependency(Integer layerIndexSource, Integer layerIndexTarget, boolean movementEqualOrInverse) {

        LockLayer layerSource = this.listLayer.get(layerIndexSource);
        LockLayer layerTarget = this.listLayer.get(layerIndexTarget);

        LockLayerDependency dependency = new LockLayerDependency(layerTarget, movementEqualOrInverse);
        layerSource.getListLockLayerDependency().add(dependency);
    }

    public void movePosition(Integer layerIndex, boolean upOrDown) {
        LockLayer layer = this.listLayer.get(layerIndex);
        layer.movePosition(upOrDown);
    }

    public boolean isMovePositionPossible(Integer layerIndex, boolean upOrDown) {
        LockLayer layer = this.listLayer.get(layerIndex);
        return layer.isMovePositionPossible(upOrDown);
    }

    public boolean isOpen() {

        for (LockLayer layer : listLayer) {
            if (!layer.isOpen()) {
                return false;
            }
        }

        return true;
    }
}
