pack org.team.models;

public enum priority {
    REGULAR(0), URGENT(1);

    private int value;

    priority(int value) {
        this.value = value;
    }


    public static priority parse(int id) {
        priority priority = null; // Default
        for (priority item : priority.values()) {
            if (item.getValue() == id) {
                priority = item;
                break;
            }
        }
        return priority;
    }

    public static priority parse(priority id) {

        return id;
    }

    public int getValue() {
        return value;
    }
}