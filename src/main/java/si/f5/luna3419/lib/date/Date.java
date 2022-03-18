package si.f5.luna3419.lib.date;

public interface Date {
    int getYear();
    int getMonth();
    int getDay();

    Date addDays(int days);

    Date clone();
}
