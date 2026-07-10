package org.leviatanplatform.games.gothiclock.engine.domain;

import java.util.ArrayList;
import java.util.List;

public class LockLayer {

    private final int positionToOpen;
    private final int numberOfPositions;
    private final List<LockLayerDependency> listLockLayerDependency = new ArrayList<>();
    private int position;

    public LockLayer(int positionToOpen, int numberOfPositions, int position) {
        this.positionToOpen = positionToOpen;
        this.numberOfPositions = numberOfPositions;
        this.position = position;

        throwIfPositionIsNotPossible(position);
    }

    public void movePosition(boolean upOrDown) {

        int newPosition = upOrDown ? this.position + 1 : this.position - 1;
        throwIfPositionIsNotPossible(newPosition);
        this.position = newPosition;
    }

    public boolean isMovePositionPossible(boolean upOrDown) {

        int newPosition = upOrDown ? this.position + 1 : this.position - 1;
        return checkIfPositionIsPossible(newPosition);
    }

    private void throwIfPositionIsNotPossible(int newPosition) {
        if (checkIfPositionIsPossible(newPosition)) {
            throw new RuntimeException("Incorrect position: " + newPosition);
        }
    }

    private boolean checkIfPositionIsPossible(int newPosition) {
        return newPosition >= 0 && newPosition < numberOfPositions;
    }

    public List<LockLayerDependency> getListLockLayerDependency() {
        return listLockLayerDependency;
    }

    public boolean isOpen() {
        return positionToOpen == position;
    }

    public LockLayer buildClone() {
        LockLayer clone = new LockLayer(positionToOpen, numberOfPositions, position);
        clone.getListLockLayerDependency().addAll(listLockLayerDependency);
        return clone;
    }
}
