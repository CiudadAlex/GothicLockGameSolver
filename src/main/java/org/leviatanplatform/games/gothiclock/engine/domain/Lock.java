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

    public Lock(List<LockLayer> listLayer) {
        this.listLayer = listLayer;
    }

    public void addDependency(Integer layerIndexSource, Integer layerIndexTarget, boolean movementEqualOrInverse) {

        LockLayer layerSource = this.listLayer.get(layerIndexSource);

        LockLayerDependency dependency = new LockLayerDependency(layerIndexTarget, movementEqualOrInverse);
        layerSource.getListLockLayerDependency().add(dependency);
    }

    public void movePosition(Integer layerIndex, boolean upOrDown) {
        LockLayer layer = this.listLayer.get(layerIndex);
        layer.movePosition(upOrDown);
    }

    public boolean isMovePositionPossible(Integer layerIndex, boolean upOrDown) {

        LockLayer layer = this.listLayer.get(layerIndex);

        if (!layer.isMovePositionPossible(upOrDown)) {
            return false;
        }

        for (LockLayerDependency dependency : layer.getListLockLayerDependency()) {
            int layerIndexTarget = dependency.getLayerIndexTarget();
            boolean movementEqualOrInverse = dependency.isMovementEqualOrInverse();

            LockLayer layerTarget = this.listLayer.get(layerIndexTarget);

            if (!layerTarget.isMovePositionPossible(upOrDown ^ !movementEqualOrInverse)) {
                return false;
            }
        }

        return true;
    }

    public boolean isOpen() {

        for (LockLayer layer : listLayer) {
            if (!layer.isOpen()) {
                return false;
            }
        }

        return true;
    }

    public Lock buildClone() {
        List<LockLayer> listLayer = this.listLayer.stream().map(LockLayer::buildClone).toList();
        return new Lock(listLayer);
    }

    // FIXME evaluate dependencies in movement
}
