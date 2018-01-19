package demo.app.sso.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import demo.app.core.domain.BaseEntity;

@Entity
@Table(name = "USER_LOGN_H")
@SequenceGenerator(sequenceName = "SEQ_USER_LOGN_H_ID", name = "SEQ_USER_LOGN_H_ID", allocationSize = 1)
public class UserLoginHistory extends BaseEntity<Integer> {

    private static final long serialVersionUID = 1882611954346650979L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "SEQ_USER_LOGN_H_ID")
    @Column(name = "USER_LOGN_ID")
    private Integer           id;

    @Column(name = "LOGN_TS")
    @Type(type = "org.hibernate.type.LocalDateTimeType")
    private LocalDateTime     timeLogin;

    @Column(name = "LOGN_STAT_CD")
    private String            status;

    @Column(name = "LOGOUT_TS")
    @Type(type = "org.hibernate.type.LocalDateTimeType")
    private LocalDateTime     timeLogout;

    @OneToOne
    @JoinColumn(name = "USER_ACCT_ID")
    private UserAccount       user;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getTimeLogin() {
        return timeLogin;
    }

    public void setTimeLogin(LocalDateTime timeLogin) {
        this.timeLogin = timeLogin;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getTimeLogout() {
        return timeLogout;
    }

    public void setTimeLogout(LocalDateTime timeLogout) {
        this.timeLogout = timeLogout;
    }

    public UserAccount getUser() {
        return user;
    }

    public void setUser(UserAccount user) {
        this.user = user;
    }
}
