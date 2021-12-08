package netcracker.buildings.threads;

import netcracker.buildings.Floor;
import netcracker.buildings.Space;

public class Repairer extends Thread {
    private final Floor floor;

    public Repairer(Floor floor) {
        this.floor = floor;
    }

    @Override
    public void run() {
        try {
            int i = 0;
            for (Space space : floor) {
                if (!isInterrupted()) {
                    System.out.println("Repairing space number " + i++ + " with total area " + space.getArea() + " square");
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
