package org.leviatanplatform.games.gothiclock.engine;

import org.leviatanplatform.games.gothiclock.engine.domain.Lock;
import org.leviatanplatform.games.gothiclock.engine.domain.Movement;

import java.util.List;

public class LockOpener {

    public static List<Movement> open(Lock lock) {

        int numberOfLayers = lock.getNumberOfLayers();

        for (int layerIndex = 0; layerIndex < numberOfLayers; layerIndex++) {
            Lock lockClone = lock.buildClone();
            lockClone.isMovePositionPossible(layerIndex, true);
        }


        // FIXME finish
        return null;
    }
}
