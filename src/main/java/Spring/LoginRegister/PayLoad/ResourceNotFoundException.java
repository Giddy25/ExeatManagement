package Spring.LoginRegister.PayLoad;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResourceNotFoundException extends RuntimeException {

    private String message;
    private Object[] objects;


    public ResourceNotFoundException(String student, String id, int studentID) {
    }
}
