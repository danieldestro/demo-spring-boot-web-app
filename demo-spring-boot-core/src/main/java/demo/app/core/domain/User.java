package demo.app.core.domain;

/**
 * Interface that is related to an user.
 */
public interface User {

    Integer getId();

    void setId(Integer id);

    String getUsername();

    String getEmailAddress();

    void setEmailAddress(String emailAddress);

    String getEmployeeNumber();

    void setEmployeeNumber(String employeeNumber);
}
