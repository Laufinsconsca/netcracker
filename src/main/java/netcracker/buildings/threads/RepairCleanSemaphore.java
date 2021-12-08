package netcracker.buildings.threads;

import java.util.concurrent.Semaphore;

public class RepairCleanSemaphore {
    private final Semaphore repairSemaphore;
    private final Semaphore cleanSemaphore;

    public RepairCleanSemaphore() {
        repairSemaphore = new Semaphore(1);
        cleanSemaphore = new Semaphore(0);
    }

    public Semaphore getRepairSemaphore() {
        return repairSemaphore;
    }

    public Semaphore getCleanSemaphore() {
        return cleanSemaphore;
    }
}
