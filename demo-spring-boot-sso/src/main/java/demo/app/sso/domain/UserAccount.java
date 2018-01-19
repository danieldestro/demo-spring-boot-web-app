package demo.app.sso.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import demo.app.core.domain.User;
import demo.app.sso.domain.base.BaseAuditableLockingEntity;

@Entity
@Table(name = "USER_ACCT")
@SequenceGenerator(sequenceName = "SEQ_USER_ACCT_ID", name = "SEQ_USER_ACCT_ID", allocationSize = 1)
public class UserAccount extends BaseAuditableLockingEntity<Integer> implements User, UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "SEQ_USER_ACCT_ID")
    @Column(name = "USER_ACCT_ID")
    private Integer             id;

    @Column(name = "EMP_NR")
    private String              employeeNumber;

    @Column(name = "EMAIL_ADDR")
    private String              emailAddress;

    @Column(name = "ACCT_ENBL")
    private boolean             enabled;

    @Column(name = "ACCT_EXP")
    private boolean             expired;

    @Column(name = "ACCT_LCK")
    private boolean             locked;

    @ManyToMany(cascade = { CascadeType.REFRESH })
    @JoinTable(name = "ACCT_USER_ROLE", joinColumns = @JoinColumn(name = "USER_ACCT_ID"), inverseJoinColumns = @JoinColumn(name = "USER_ROLE_ID"))
    private List<UserRole>      roles;

    @ManyToMany(cascade = { CascadeType.REFRESH })
    @JoinTable(name = "ACCT_USER_GRP", joinColumns = @JoinColumn(name = "USER_ACCT_ID"), inverseJoinColumns = @JoinColumn(name = "USER_GRP_ID"))
    private List<UserGroup>     groups;

    private static final long   serialVersionUID = 6752938074476659028L;

    private static final Logger LOG              = LoggerFactory.getLogger(UserAccount.class);

    private static final String ROLE_PREFIX      = "ROLE_";

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Stream<String> roleStream = roles.stream()
                .map(UserRole::getName)
                .map(roleName -> ROLE_PREFIX + roleName);

        Stream<String> permissionStream = roles.stream()
                .map(UserRole::getPermissions)
                .flatMap(permissions -> permissions.stream())
                .map(Permission::getName);

        List<SimpleGrantedAuthority> authorities = Stream.concat(roleStream, permissionStream)
                .map(permissionName -> new SimpleGrantedAuthority(permissionName))
                .collect(Collectors.toList());

        LOG.info("User {} (id: {}) has the following authorities: {}", emailAddress, id, authorities);
        return authorities;
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return employeeNumber;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !expired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !expired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
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

    @Override
    public String getEmailAddress() {
        return emailAddress;
    }

    @Override
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public String getEmployeeNumber() {
        return employeeNumber;
    }

    @Override
    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public List<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }

    public List<UserGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<UserGroup> groups) {
        this.groups = groups;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean hasRole(String roleName) {
        return Optional.ofNullable(roles).orElse(new ArrayList<>()).stream()
                .map(UserRole::getName).anyMatch(name -> name.equals(roleName));
    }

    public boolean hasAuthority(String authorityName) {
        return Optional.ofNullable(getAuthorities()).orElse(new ArrayList<>()).stream()
                .map(GrantedAuthority::getAuthority).anyMatch(name -> name.equals(authorityName));
    }

}
