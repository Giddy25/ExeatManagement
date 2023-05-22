package Spring.LoginRegister.Entity;



import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DutyDate {

private LocalDate date;
            private List<Teacher> dailyTeachers;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public DutyDate() {
            this.dailyTeachers = new ArrayList<>();
        }

        public DutyDate(LocalDate date) {
            this();
            this.date = date;
        }

        // Getter and Setter methods

        public List<Teacher> getDailyTeachers() {
            return dailyTeachers;
        }

        public void setDailyTeachers(List<Teacher> dailyTeachers) {
            this.dailyTeachers = dailyTeachers;
        }
}
