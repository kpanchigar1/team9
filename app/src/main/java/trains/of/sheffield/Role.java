package trains.of.sheffield;
public enum Role {
    CUSTOMER(2), STAFF(1), MANAGER(0);

    private int roleID;

    Role(int roleID) {
        this.roleID = roleID;
    }
}
