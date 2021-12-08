package netcracker.buildings.threads;

import netcracker.buildings.Floor;
import netcracker.buildings.Space;

public class Cleaner extends Thread {
    private final Floor floor;

    public Cleaner(Floor floor) {
        this.floor = floor;
    }

    @Override
    public void run() {
        try {
            int i = 0;
            for (Space space : floor) {
                if (!isInterrupted()) {
                    System.out.println("Cleaning room number " + i++ + " with total area " + space.getArea() + " square");
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
