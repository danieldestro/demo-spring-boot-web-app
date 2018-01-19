package demo.app.web.vo;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * VO that represents an UserRole record.
 */
public class UserAccountVO extends BaseAuditableLockingVO<Integer> {

    private static final long serialVersionUID = -2988432962320340655L;

    private String            employeeNumber;
    private String            emailAddress;
    private boolean           enabled;
    private boolean           expired;
    private boolean           locked;

    private List<UserRoleVO>  roles;

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public List<UserRoleVO> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRoleVO> roles) {
        this.roles = roles;
    }

    @Override
    protected void extendToString(ToStringBuilder builder) {
        builder.append("employeeNumber", employeeNumber);
        builder.append("emailAddress", emailAddress);
        builder.append("enabled", enabled);
        builder.append("expired", expired);
        builder.append("locked", locked);
    }

}
