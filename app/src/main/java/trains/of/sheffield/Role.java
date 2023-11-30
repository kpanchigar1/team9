package trains.of.sheffield;
/**
 * Role enum is used to store the role of a user
 */
public enum Role {
    CUSTOMER(2), STAFF(1), MANAGER(0);

    private int roleID;

    /**
     * Constructor to create a role
     * @param roleID The role ID
     */
    Role(int roleID) {
        this.roleID = roleID;
    }

    public int getRoleID() {
        return roleID;
    }

    /**
     * Method to get the role from the role ID
     * @param roleID The role ID
     * @return The role
     */
    public static Role getRole(int roleID) {
        for (Role role : Role.values()) {
            if (role.getRoleID() == roleID) {
                return role;
            }
        }
        return null;
    }
}
