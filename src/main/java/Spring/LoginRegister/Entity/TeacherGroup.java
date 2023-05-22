package Spring.LoginRegister.Entity;

import Spring.LoginRegister.Entity.Teacher;

import java.util.List;

public class TeacherGroup {
    private List<Teacher> members;

    public TeacherGroup(List<Teacher> members) {
        this.members = members;
    }
}