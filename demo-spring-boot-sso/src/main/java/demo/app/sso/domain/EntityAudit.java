package demo.app.sso.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import demo.app.core.domain.BaseEntity;

/**
 * Base class for all JPA auditable entities.
 */
@Entity
@Table(name = "ENT_AUDT")
@SequenceGenerator(sequenceName = "SEQ_ENT_AUDT_ID", name = "SEQ_ENT_AUDT_ID", allocationSize = 1)
@EntityListeners(AuditingEntityListener.class)
public class EntityAudit extends BaseEntity<Integer> {

    private static final long serialVersionUID = -476457088362655710L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "SEQ_ENT_AUDT_ID")
    @Column(name = "ENT_AUDT_ID", nullable = false)
    private Integer           id;

    @CreatedDate
    @Column(name = "CRT_TS", nullable = false)
    @Type(type = "org.hibernate.type.LocalDateTimeType")
    private LocalDateTime     createdDate;

    @CreatedBy
    @Column(name = "CRT_USER_EMAIL")
    private String            createdBy;

    @LastModifiedDate
    @Column(name = "LAST_MODF_TS")
    @Type(type = "org.hibernate.type.LocalDateTimeType")
    private LocalDateTime     lastModifiedDate;

    @LastModifiedBy
    @Column(name = "LAST_MODF_USER_EMAIL")
    private String            lastModifiedBy;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

}
