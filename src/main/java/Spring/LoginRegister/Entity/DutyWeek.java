package Spring.LoginRegister.Entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DutyWeek {
    private LocalDate weekStart;
    private LocalDate weekEnd;
    private List<DutyDate> dutyDates;
    private List<Teacher> dailyTeachers;

    public DutyWeek(LocalDate weekStart, LocalDate weekEnd) {
        this.weekStart = weekStart;
        this.weekEnd = weekEnd;
        this.dutyDates = new ArrayList<>();
    }

    public LocalDate getWeekStart() {
        return weekStart;
    }

    public void setWeekStart(LocalDate weekStart) {
        this.weekStart = weekStart;
    }

    public LocalDate getWeekEnd() {
        return weekEnd;
    }

    public List<DutyDate> getDutyDates() {
        return dutyDates;
    }

    public void addDutyDate(DutyDate dutyDate) {
        dutyDates.add(dutyDate);
    }

    public List<Teacher> getDailyTeachers() {
        return dailyTeachers;
    }
}
