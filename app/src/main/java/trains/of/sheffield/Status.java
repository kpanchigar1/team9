package trains.of.sheffield;
public enum Status {
    PENDING (0), CONFIRMED (1), FULFILLED (2);

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

    private int getStatusID() {
        return statusID;
    }

}
