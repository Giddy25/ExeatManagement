package Spring.LoginRegister.Entity;

import jakarta.persistence.*;



@Entity
@Table(name = "teachers")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    // Add any other relevant properties

    // Constructors, getters, and setters


    public Teacher() {
    }

    public Teacher(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
