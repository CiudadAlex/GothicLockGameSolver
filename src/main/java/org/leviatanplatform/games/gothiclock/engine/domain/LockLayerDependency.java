package org.leviatanplatform.games.gothiclock.engine.domain;

public class LockLayerDependency {

    private final LockLayer lockLayer;
    private final boolean movementEqualOrInverse;

    public LockLayerDependency(LockLayer lockLayer, boolean movementEqualOrInverse) {
        this.lockLayer = lockLayer;
        this.movementEqualOrInverse = movementEqualOrInverse;
    }

    public LockLayer getLockLayer() {
        return lockLayer;
    }

    public boolean isMovementEqualOrInverse() {
        return movementEqualOrInverse;
    }
}
