package org.leviatanplatform.games.gothiclock;

import org.leviatanplatform.games.gothiclock.engine.LockOpener;
import org.leviatanplatform.games.gothiclock.engine.domain.Lock;
import org.leviatanplatform.games.gothiclock.engine.domain.Movement;
import org.leviatanplatform.games.gothiclock.examples.LockGenerator;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        Lock lock = LockGenerator.generateDifficultLock();
        List<Movement> listMovements = LockOpener.open(lock);

        System.out.println("=======================================");

        for (Movement movement : listMovements) {
            System.out.println(movement.toString());
        }

        System.out.println("=======================================");

        Lock lockCloned = lock.buildClone();
        System.out.println(lockCloned);

        for (Movement movement : listMovements) {
            lockCloned.movePosition(movement);
            System.out.println(lockCloned);
        }
    }
}
