package org.leviatanplatform.games.gothiclock.engine.domain;

import java.util.List;

public class LockAndMovements {

    private Lock lock;
    private List<Movement> listMovements;

    public LockAndMovements(Lock lock, List<Movement> listMovements) {
        this.lock = lock;
        this.listMovements = listMovements;
    }

    public Lock getLock() {
        return lock;
    }

    public List<Movement> getListMovements() {
        return listMovements;
    }

    public boolean isOpen() {
        return this.lock.isOpen();
    }
}
