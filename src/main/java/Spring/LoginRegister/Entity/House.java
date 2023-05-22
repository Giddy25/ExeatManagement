package Spring.LoginRegister.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table

@Entity

public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int HouseID;
    @Column(unique = true)
    private String HouseName;
    @OneToMany(mappedBy = "house" )


    private  List<Master> masters=new ArrayList<>();

    @OneToMany(mappedBy = "house" , cascade = CascadeType.ALL, fetch = FetchType.LAZY)

    private List<Student> students = new ArrayList<>();


}
