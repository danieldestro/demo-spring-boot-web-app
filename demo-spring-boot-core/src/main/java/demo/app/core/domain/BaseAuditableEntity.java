package demo.app.core.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Spring Data's support for auditing is used to have those fields populated automatically.
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseAuditableEntity<T extends Serializable> extends BaseEntity<T> {

    private static final long serialVersionUID = 3579558320878560992L;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ENT_AUDT_ID")
    private EntityAudit       audit;

    public EntityAudit getAudit() {
        return audit;
    }

    public void setAudit(EntityAudit audit) {
        this.audit = audit;
    }

}
