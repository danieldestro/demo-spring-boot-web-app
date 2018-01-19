package demo.app.core.data;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 8260772006048009316L;

    public BusinessException(String message) {
        super(message);
    }
}
