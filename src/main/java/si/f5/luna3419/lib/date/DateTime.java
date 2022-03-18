package si.f5.luna3419.lib.date;

import java.util.Calendar;

public record DateTime(
    int year, 
    int month, 
    int day, 
    int hour, 
    int minute, 
    int second
) implements Date, Time {

    @Override
    public int getHour() {
        return hour;
    }

    @Override
    public int getMinute() {
        return minute;
    }

    @Override
    public int getSecond() {
        return second;
    }

    @Override
    public int getYear() {
        return year;
    }

    @Override
    public int getMonth() {
        return month;
    }

    @Override
    public int getDay() {
        return day;
    }

    @Override
    public Date addDays(int days) {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DATE, day);

        calendar.add(Calendar.DATE, days);

        return new DateTime(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), hour, minute, second);
    }

    
    @Override
    public String toString() {
        if (this instanceof Date) {
            return year + "-" + month + "-" + day;
        } else {
            return hour + ":" + minute + ":" + second;
        }
    }

    @Override
    public DateTime clone() {
        return new DateTime(year, month, day, hour, minute, second);
    }
}
