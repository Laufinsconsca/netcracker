package netcracker.buildings.threads;

import netcracker.buildings.Floor;
import netcracker.buildings.Space;

public class SequentialRepairer implements Runnable {
    private final Floor floor;
    private final RepairCleanSemaphore repairCleanSemaphore;

    public SequentialRepairer(Floor floor, RepairCleanSemaphore repairCleanSemaphore) {
        this.floor = floor;
        this.repairCleanSemaphore = repairCleanSemaphore;
    }

    @Override
    public void run() {
        try {
            int i = 0;
            for (Space space : floor) {
                if (!Thread.currentThread().isInterrupted()) {
                    repairCleanSemaphore.getRepairSemaphore().acquire();
                    System.out.println("Repairing space number " + i++ + " with total area " + space.getArea() + " square");
                    repairCleanSemaphore.getCleanSemaphore().release();
                } else {
                    throw new InterruptedException();
                }
            }
            System.out.println("All spaces have been repaired");
        } catch (InterruptedException e) {
            System.out.println("Repairer is interrupted");
        }
    }
}
