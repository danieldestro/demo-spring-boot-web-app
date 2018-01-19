package demo.app.sso.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import demo.app.sso.domain.base.BaseAuditableLockingEntity;

@Entity
@Table(name = "USER_ROLE")
@SequenceGenerator(sequenceName = "SEQ_USER_ROLE_ID", name = "SEQ_USER_ROLE_ID", allocationSize = 1)
public class UserRole extends BaseAuditableLockingEntity<Integer> {

    private static final long serialVersionUID = -4370457766267756866L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "SEQ_USER_ROLE_ID")
    @Column(name = "USER_ROLE_ID")
    private Integer           id;

    @Column(name = "USER_ROLE_NM")
    private String            name;

    @Column(name = "USER_ROLE_DN")
    private String            description;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinTable(name = "USER_ROLE_PRMSN", joinColumns = @JoinColumn(name = "USER_ROLE_ID", referencedColumnName = "USER_ROLE_ID"), inverseJoinColumns = @JoinColumn(name = "PRMSN_ID", referencedColumnName = "PRMSN_ID"))
    private Set<Permission>   permissions;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

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

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

}
