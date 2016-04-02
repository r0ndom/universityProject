package net.github.rtc.app.model.entity.activity;



public enum ActivityAction {

    SAVED, UPDATED, REMOVED;
    @Override
    public String toString() {
        String name = "";
        switch (ordinal()) {
            case 0:
                name = "Saved";
                break;
            case 1:
                name = "Updated";
                break;
            case 2:
                name = "Removed";
                break;
            default:
                name = "Incorrect action";
                break;
        }
        return name;
    }
}
