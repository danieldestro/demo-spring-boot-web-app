package demo.app.web.config;

import java.util.Arrays;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import demo.app.core.util.MessageUtils;

@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private MessageUtils messageUtils;

    @ResponseBody
    @ResponseStatus(code = HttpStatus.PRECONDITION_FAILED) // 412
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public List<ObjectError> handle(EmptyResultDataAccessException exception) {
        String msg = treatSpecificException(exception);
        return Arrays.asList(new ObjectError("EmptyResultDataAccessException", msg));
    }

    @ResponseBody
    @ResponseStatus(code = HttpStatus.CONFLICT) // 409
    @ExceptionHandler(DataIntegrityViolationException.class)
    public List<ObjectError> handleDataIntegrityViolation(DataIntegrityViolationException exception) {

        Throwable exCause = exception.getCause();

        if (exCause != null && exCause instanceof ConstraintViolationException) {
            String msg = treatSpecificException((ConstraintViolationException) exCause);
            return Arrays.asList(new ObjectError("ConstraintViolationException", msg));
        }

        return Arrays.asList(new ObjectError("DataIntegrityViolationException", exception.getMessage()));
    }

    private String treatSpecificException(ConstraintViolationException exception) {
        String sqlError = exception.getSQLException().getMessage();

        String key = null;
        if (sqlError.contains("column does not allow nulls")) {
            // cannot insert/update null to a not null field
            key = "err.data.column.not.null";

        } else if (sqlError.contains("Violation of UNIQUE KEY constraint")) {
            // cannot insert/update duplicate value in a unique key field
            key = "err.data.unique.key.violation";

        } else if (sqlError.contains("DELETE statement conflicted with the REFERENCE constraint")) {
            // cannot delete a record because of referential integrity
            key = "err.data.referential.integrity.constraint";

        } else {
            key = "err." + exception.getClass().getName();
        }

        return messageUtils.getMessage(key);
    }

    private String treatSpecificException(EmptyResultDataAccessException exception) {
        String errMsg = exception.getMessage();

        String key = null;
        if (errMsg.startsWith("No class") && errMsg.contains("rows")) {
            key = "err.data.not.found";

        } else {
            key = "err." + exception.getClass().getName();
        }

        return messageUtils.getMessage(key);
    }
}
