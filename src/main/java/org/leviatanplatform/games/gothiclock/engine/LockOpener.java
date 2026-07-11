package org.leviatanplatform.games.gothiclock.engine;

import org.leviatanplatform.games.gothiclock.engine.domain.Lock;
import org.leviatanplatform.games.gothiclock.engine.domain.LockAndMovements;
import org.leviatanplatform.games.gothiclock.engine.domain.Movement;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

            listLockAndMovements = purgeRepeated(listLockAndMovements);

            int[] arrayNumberOfOpenLayers = buildArrayNumberOfOpenLayers(numberOfLayers, listLockAndMovements);
            printReportOfIteration(arrayNumberOfOpenLayers, listLockAndMovements);

            listLockAndMovements = purgeLeastAdvanced(arrayNumberOfOpenLayers, listLockAndMovements);
        }
    }

    private static List<LockAndMovements> purgeRepeated(List<LockAndMovements> listLockAndMovements) {

        List<LockAndMovements> listLockAndMovementsFiltered = new ArrayList<>();
        Set<String> setLockPrints = new HashSet<>();

        for (LockAndMovements lockAndMovements : listLockAndMovements) {

            boolean added = setLockPrints.add(lockAndMovements.toString());

            if (added) {
                listLockAndMovementsFiltered.add(lockAndMovements);
            }
        }

        return listLockAndMovementsFiltered;
    }

    private static List<LockAndMovements> purgeLeastAdvanced(int[] arrayNumberOfOpenLayers, List<LockAndMovements> listLockAndMovements) {
        int maxIndexNonZero = getMaxIndexNonZero(arrayNumberOfOpenLayers);
        int minAllowedOpened = maxIndexNonZero - 1;
        return listLockAndMovements.stream().filter(lm -> lm.getLock().getNumberOfLayersOpen() >= minAllowedOpened).toList();
    }

    private static int getMaxIndexNonZero(int[] arrayNumberOfOpenLayers) {

        for (int i = arrayNumberOfOpenLayers.length - 1; i >= 0; i--) {
            if (arrayNumberOfOpenLayers[i] != 0) {
                return i;
            }
        }

        return 0;
    }

    private static void printReportOfIteration(int[] arrayNumberOfOpenLayers, List<LockAndMovements> listLockAndMovements) {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < arrayNumberOfOpenLayers.length; i++) {
            int num = arrayNumberOfOpenLayers[i];
            sb.append("(").append(i).append(": ").append(num).append(") ");
        }

        System.out.println("listLockAndMovements size = " + listLockAndMovements.size() + " | OpenLayers = " + sb.toString());
    }

    private static int[] buildArrayNumberOfOpenLayers(int numberOfLayers, List<LockAndMovements> listLockAndMovements) {

        int[] arrayNumberOfOpenLayers = new int[numberOfLayers + 1];

        for (LockAndMovements lockAndMovements : listLockAndMovements) {
            int numberLayersOpen = lockAndMovements.getLock().getNumberOfLayersOpen();
            arrayNumberOfOpenLayers[numberLayersOpen]++;
        }
        return arrayNumberOfOpenLayers;
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
