package si.f5.luna3419.lib.date;

public interface Time {
    int getHour();
    int getMinute();
    int getSecond();

    Time clone();
}
