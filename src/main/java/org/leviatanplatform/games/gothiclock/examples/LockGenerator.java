package org.leviatanplatform.games.gothiclock.examples;

import org.leviatanplatform.games.gothiclock.engine.domain.Lock;

import java.util.List;

public class LockGenerator {

    public static Lock generateDifficultLock() {

        int positionToOpen = 3;
        int numberOfPositions = 7;
        List<Integer> listInitialPositions = List.of(3, 0, 6, 4, 4, 5);

        Lock lock = new Lock(positionToOpen, numberOfPositions, listInitialPositions);

        lock.addDependency(1, 2, true);
        lock.addDependency(1, 4, false);

        lock.addDependency(2, 3, true);
        lock.addDependency(2, 5, false);

        lock.addDependency(3, 1, false);
        lock.addDependency(3, 4, false);

        lock.addDependency(5, 3, true);

        return lock;
    }
}
