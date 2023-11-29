package trains.of.sheffield;

/**
 * Status enum is used to store the status of an order
 */
public enum Status {
    PENDING (0), CONFIRMED (1), FULFILLED (2), BLOCKED (3);

    private final int statusID;

    Status (int statusID) {
        this.statusID = statusID;
    }

    public static Status getStatus(int roleID) {
        for (Status status : Status.values()) {
            if (status.getStatusID() == roleID) {
                return status;
            }
        }
        return null;
    }

    public int getStatusID() {
        return statusID;
    }

}
