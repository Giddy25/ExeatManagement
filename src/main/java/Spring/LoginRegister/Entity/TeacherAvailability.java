package Spring.LoginRegister.Entity;

import java.util.ArrayList;
import java.util.List;

public class TeacherAvailability {

        private Teacher teacher;
        private List<Boolean> available;

        public TeacherAvailability(Teacher teacher, int numWeeks) {
            this.teacher = teacher;
            this.available = new ArrayList<>(numWeeks);
            for (int i = 0; i < numWeeks; i++) {
                available.add(true);
            }
        }

        // Getter and setter for teacher and available list
    }

