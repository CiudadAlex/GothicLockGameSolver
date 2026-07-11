package org.leviatanplatform.games.gothiclock.engine;

import org.leviatanplatform.games.gothiclock.engine.domain.Lock;
import org.leviatanplatform.games.gothiclock.engine.domain.LockAndMovements;
import org.leviatanplatform.games.gothiclock.engine.domain.Movement;

import java.util.ArrayList;
import java.util.List;

public class LockOpener {

    public static List<Movement> open(Lock lock) {

        int numberOfLayers = lock.getNumberOfLayers();
        List<Movement> allMovements = getAllPossibleMovements(numberOfLayers);
        List<LockAndMovements> listLockAndMovements = List.of(new LockAndMovements(lock, List.of()));

        while (true) {
            listLockAndMovements = iteration(listLockAndMovements, allMovements);

            for (LockAndMovements lockAndMovements : listLockAndMovements) {
                if (lockAndMovements.isOpen()) {
                    return lockAndMovements.getListMovements();
                }
            }

            printReportOfIteration(numberOfLayers, listLockAndMovements);
        }
    }

    private static void printReportOfIteration(int numberOfLayers, List<LockAndMovements> listLockAndMovements) {

        int[] arrayNumberOfOpenLayers = new int[numberOfLayers];

        for (LockAndMovements lockAndMovements : listLockAndMovements) {
            int numberLayersOpen = lockAndMovements.getLock().getNumberOfLayersOpen();
            arrayNumberOfOpenLayers[numberLayersOpen]++;
        }

        System.out.println("listLockAndMovements size = " + listLockAndMovements.size() + " | arrayNumberOfOpenLayers = " + arrayNumberOfOpenLayers);
    }

    private static List<LockAndMovements> iteration(List<LockAndMovements> listLockAndMovements, List<Movement> allMovements) {

        List<LockAndMovements> listLockAndMovementsNextIteration = new ArrayList<>();

        for (LockAndMovements lockAndMovements : listLockAndMovements) {
            List<LockAndMovements> list = iteration(lockAndMovements, allMovements);
            listLockAndMovementsNextIteration.addAll(list);
        }

        return listLockAndMovementsNextIteration;
    }

    private static List<LockAndMovements> iteration(LockAndMovements lockAndMovements, List<Movement> allMovements) {

        List<LockAndMovements> listLockAndMovements = new ArrayList<>();
        Lock lock = lockAndMovements.getLock();
        List<Movement> listPreviousMovements = lockAndMovements.getListMovements();

        for (Movement movement : allMovements) {

            if (lock.isMovePositionPossible(movement)) {
                LockAndMovements newLockAndMovements = getLockAndMovements(lock, listPreviousMovements, movement);
                listLockAndMovements.add(newLockAndMovements);
            }
        }

        return listLockAndMovements;
    }

    private static LockAndMovements getLockAndMovements(Lock lock, List<Movement> listPreviousMovements, Movement movement) {

        Lock lockClone = lock.buildClone();
        lockClone.movePosition(movement);
        List<Movement> listMovements = new ArrayList<>(listPreviousMovements);
        listMovements.add(movement);
        return new LockAndMovements(lockClone, listMovements);
    }

    private static List<Movement> getAllPossibleMovements(int numberOfLayers) {

        List<Movement> allMovements = new ArrayList<>();

        for (int layerIndex = 0; layerIndex < numberOfLayers; layerIndex++) {
            allMovements.add(new Movement(layerIndex, true));
            allMovements.add(new Movement(layerIndex, false));
        }

        return allMovements;
    }
}
