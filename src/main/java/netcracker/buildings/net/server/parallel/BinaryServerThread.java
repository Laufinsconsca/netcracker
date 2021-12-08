package netcracker.buildings.net.server.parallel;

import netcracker.buildings.Building;
import netcracker.buildings.Buildings;
import netcracker.buildings.factory.BuildingFactory;
import netcracker.buildings.factory.impl.DwellingFactory;
import netcracker.buildings.factory.impl.HotelFactory;
import netcracker.buildings.factory.impl.OfficeFactory;
import netcracker.buildings.net.server.sequential.BinaryServer;
import netcracker.exceptions.BuildingUnderArrestException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class BinaryServerThread extends Thread {
    private static final int[] COSTS_OF_AREA_UNIT = new int[]{1000, 1500, 2000};
    private static final BuildingFactory[] FACTORIES = new BuildingFactory[]{new DwellingFactory(), new OfficeFactory(), new HotelFactory()};
    private final int number;
    private final DataInputStream in;
    private final DataOutputStream out;

    public BinaryServerThread(Socket socket, int number) throws IOException {
        this.number = number;
        out = new DataOutputStream(socket.getOutputStream());
        in = new DataInputStream(socket.getInputStream());
        start();
    }

    @Override
    public void run() {
        try {
            int n = in.readInt();
            for (int i = 0; i < n; i++) {
                int type = in.readInt();
                int costOfAreaUnit = COSTS_OF_AREA_UNIT[type - 1];
                Buildings.setBuildingFactory(FACTORIES[type - 1]);
                Building building = Buildings.inputBuilding(in);
                double cost;
                try {
                    cost = BinaryServer.getCost(building, costOfAreaUnit);
                    System.out.println("Принято здание " + building + ", рассчитанная стоимость - " + cost + "$");
                } catch (BuildingUnderArrestException e) {
                    cost = -1;
                    System.out.println("Принято здание " + building + ", здание находится под арестом");
                }
                out.writeDouble(cost);
                out.flush();
            }
            System.out.println("Расчёт для клиента " + number + " окончен");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
