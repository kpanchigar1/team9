package trains.of.sheffield;

/**
 * Status enum is used to store the status of an order
 */
public enum Status {
    PENDING (0), CONFIRMED (1), FULFILLED (2), BLOCKED (3);

    private final int statusID;

    /**
     * Constructor to create a status
     * @param statusID The status ID
     */
    Status (int statusID) {
        this.statusID = statusID;
    }

    /**
     * Method to get the status from the status ID
     * @param statusID The status ID
     * @return The status
     */
    public static Status getStatus(int statusID) {
        for (Status status : Status.values()) {
            if (status.getStatusID() == statusID) {
                return status;
            }
        }
        return null;
    }

    public int getStatusID() {
        return statusID;
    }

}
