package demo.app.core.data;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecordNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 5127446479749624919L;

    public RecordNotFoundException(Object record) {
        super(String.format("Record not found: %s", String.valueOf(record)));
    }
}
