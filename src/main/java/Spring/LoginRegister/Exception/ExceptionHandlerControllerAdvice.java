package Spring.LoginRegister.Exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.net.SocketTimeoutException;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice {
    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException(HttpServletRequest request, IOException ex) {
        String errorURL = request.getRequestURL().toString();
        String errorMethod = request.getMethod();
        String errorMessage = "An error occurred while processing your request. If the error persists, please contact us at <a href='mailto:sidgidtech@gmail.com'>SIDGIDTECH</a> ";

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }
    @ExceptionHandler(SocketTimeoutException.class)
    public ResponseEntity<String> handleSocketTimeoutException(HttpServletRequest request, SocketTimeoutException ex) {
        String errorURL = request.getRequestURL().toString();
        String retryURL = errorURL; // set retry URL to current URL by default
        String errorMessage = "The request took too long to process. Please try again later. ";
        if (request.getMethod().equals("GET")) {
            retryURL = errorURL + "?retry=true"; // append retry parameter to URL
            errorMessage += "You can also <a href='" + retryURL + "'>click here to retry</a> the request.";
        }
        return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body(errorMessage);
    }
}
