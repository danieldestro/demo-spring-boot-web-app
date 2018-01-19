package demo.app.web.vo;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * VO that represents an UserRole record.
 */
public class UserRoleVO extends BaseAuditableLockingVO<Integer> {

    private static final long  serialVersionUID = -2988432962320340655L;

    private String             name;
    private String             description;
    private List<PermissionVO> permissions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<PermissionVO> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<PermissionVO> permissions) {
        this.permissions = permissions;
    }

    @Override
    protected void extendToString(ToStringBuilder builder) {
        builder.append("name", name);
        builder.append("description", description);
    }

}
