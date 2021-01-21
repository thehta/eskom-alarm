package za.net.ether.mokse;

public class TimeStage {
    private final String hour;
    private final int stage;

    public TimeStage(int hour, int stage) {
        this.hour = String.format("%02d:00", hour * 2);
        this.stage = stage;
    }

    public String getHour() {
        return hour;
    }

    public int getStage() {
        return stage;
    }
}
