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
}
