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

    public int getNumberOfLayers() {
        return listLayer.size();
    }

    public void addDependency(Integer layerIndexSource, Integer layerIndexTarget, boolean movementEqualOrInverse) {

        LockLayer layerSource = this.listLayer.get(layerIndexSource);

        LockLayerDependency dependency = new LockLayerDependency(layerIndexTarget, movementEqualOrInverse);
        layerSource.getListLockLayerDependency().add(dependency);
    }

    public void movePosition(Movement movement) {

        Integer layerIndex = movement.getLayerIndex();
        boolean upOrDown = movement.isUpOrDown();

        if (!isMovePositionPossible(movement)) {
            throw new RuntimeException("Not possible move in layer: " + layerIndex + " | upOrDown = " + upOrDown);
        }

        LockLayer layer = this.listLayer.get(layerIndex);
        layer.movePosition(upOrDown);

        for (LockLayerDependency dependency : layer.getListLockLayerDependency()) {
            boolean upOrDownDependency = getUpOrDownOfDependency(upOrDown, dependency);
            LockLayer layerTarget = getLayerOfDependency(dependency);

            layerTarget.movePosition(upOrDownDependency);
        }
    }

    private boolean getUpOrDownOfDependency(boolean upOrDown, LockLayerDependency dependency) {

        boolean movementEqualOrInverse = dependency.isMovementEqualOrInverse();
        return upOrDown ^ !movementEqualOrInverse;
    }

    private LockLayer getLayerOfDependency(LockLayerDependency dependency) {

        int layerIndexTarget = dependency.getLayerIndexTarget();
        return this.listLayer.get(layerIndexTarget);
    }

    public boolean isMovePositionPossible(Movement movement) {

        Integer layerIndex = movement.getLayerIndex();
        boolean upOrDown = movement.isUpOrDown();

        LockLayer layer = this.listLayer.get(layerIndex);

        if (!layer.isMovePositionPossible(upOrDown)) {
            return false;
        }

        for (LockLayerDependency dependency : layer.getListLockLayerDependency()) {
            boolean upOrDownDependency = getUpOrDownOfDependency(upOrDown, dependency);
            LockLayer layerTarget = getLayerOfDependency(dependency);

            if (!layerTarget.isMovePositionPossible(upOrDownDependency)) {
                return false;
            }
        }

        return true;
    }

    public boolean isOpen() {

        for (LockLayer layer : this.listLayer) {
            if (!layer.isOpen()) {
                return false;
            }
        }

        return true;
    }

    public int getNumberOfLayersOpen() {

        int numberOpen = 0;

        for (LockLayer layer : this.listLayer) {
            if (layer.isOpen()) {
                numberOpen++;
            }
        }

        return numberOpen;
    }

    public Lock buildClone() {
        List<LockLayer> listLayer = this.listLayer.stream().map(LockLayer::buildClone).toList();
        return new Lock(listLayer);
    }
}
