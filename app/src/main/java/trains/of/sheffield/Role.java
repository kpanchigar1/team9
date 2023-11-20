package trains.of.sheffield;
public enum Role {
    CUSTOMER(2), STAFF(1), MANAGER(0);

    private int roleID;

    Role(int roleID) {
        this.roleID = roleID;
    }

    public int getRoleID() {
        return roleID;
    }

    public static Role getRole(int roleID) {
        for (Role role : Role.values()) {
            if (role.getRoleID() == roleID) {
                return role;
            }
        }
        return null;
    }
}
