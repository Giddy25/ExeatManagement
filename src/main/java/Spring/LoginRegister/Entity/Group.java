package Spring.LoginRegister.Entity;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Group {
    private int weekNumber;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<Teacher> teachers;

    public Group(int weekNumber, LocalDate startDate, LocalDate endDate) {
        this.weekNumber = weekNumber;
        this.startDate = startDate;
        this.endDate = endDate;
        this.teachers = teachers;
    }

    public int getWeekNumber() {
        return weekNumber;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setWeekNumber(int weekNumber) {
        this.weekNumber = weekNumber;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }
//    @Override
//    public String toString() {
//        SimpleDateFormat formatter = new SimpleDateFormat("d MM YYYY");
//        return String.format("%s", endDate.format(formatter));
//    }
}
