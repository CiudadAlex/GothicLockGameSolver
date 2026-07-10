package org.leviatanplatform.games.gothiclock.engine.domain;

public class LockLayer {

    private final int positionToOpen;
    private final int numberOfPositions;
    private int position;

    public LockLayer(int positionToOpen, int numberOfPositions, int position) {
        this.positionToOpen = positionToOpen;
        this.numberOfPositions = numberOfPositions;
        this.position = position;
    }

    public void movePosition(boolean upOrDown) {

    }

    private boolean checkIfFinalPositionIsPossible(int newPosition) {
        return newPosition >= 0 && newPosition < numberOfPositions;
    }
}
