package org.leviatanplatform.games.gothiclock.engine.domain;

public class LockLayerDependency {

    private final int layerIndexTarget;
    private final boolean movementEqualOrInverse;

    public LockLayerDependency(int layerIndexTarget, boolean movementEqualOrInverse) {
        this.layerIndexTarget = layerIndexTarget;
        this.movementEqualOrInverse = movementEqualOrInverse;
    }

    public int getLayerIndexTarget() {
        return layerIndexTarget;
    }

    public boolean isMovementEqualOrInverse() {
        return movementEqualOrInverse;
    }
}
