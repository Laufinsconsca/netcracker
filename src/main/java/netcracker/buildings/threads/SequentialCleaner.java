package netcracker.buildings.threads;

import netcracker.buildings.Floor;
import netcracker.buildings.Space;

public class SequentialCleaner implements Runnable {
    private final Floor floor;
    private final RepairCleanSemaphore repairCleanSemaphore;

    public SequentialCleaner(Floor floor, RepairCleanSemaphore repairCleanSemaphore) {
        this.floor = floor;
        this.repairCleanSemaphore = repairCleanSemaphore;
    }

    @Override
    public void run() {
        try {
            int i = 0;
            for (Space space : floor) {
                if (!Thread.currentThread().isInterrupted()) {
                    repairCleanSemaphore.getCleanSemaphore().acquire();
                    System.out.println("Cleaning space number " + i++ + " with total area " + space.getArea() + " square");
                    repairCleanSemaphore.getRepairSemaphore().release();
                } else {
                    throw new InterruptedException();
                }
            }
            System.out.println("All spaces have been cleaned");
        } catch (InterruptedException e) {
            System.out.println("Cleaner is interrupted");
        }
    }
}
