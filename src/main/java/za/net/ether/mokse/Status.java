package za.net.ether.mokse;

public class Status {

    private String date;
    private int stage;

    public Status(String date, int stage) {
        this.date = date;
        this.stage = stage;
    }

    public int getStage() {
        return stage;
    }

    public String getTime() {
        return date;
    }
}
