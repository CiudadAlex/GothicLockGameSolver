package org.leviatanplatform.games.gothiclock.engine.domain;

public class LockLayer {

    private final int positionToOpen;
    private final int numberOfPositions;
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
}
