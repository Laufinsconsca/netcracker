package netcracker.exceptions;

import java.io.Serializable;

public class BuildingUnderArrestException extends RuntimeException implements Serializable {
    public BuildingUnderArrestException() {
        super();
    }

    public BuildingUnderArrestException(String message) {
        super(message);
    }
}
