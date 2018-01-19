package demo.app.sso.domain.base;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

import demo.app.core.domain.BaseEntity;
import demo.app.sso.domain.EntityAudit;

/**
 * Spring Data's support for auditing is used to have those fields populated automatically.
 */
@MappedSuperclass
public abstract class BaseAuditableEntity<T extends Serializable> extends BaseEntity<T> {

    private static final long serialVersionUID = 3579558320878560992L;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ENT_AUDT_ID")
    private EntityAudit       audit            = new EntityAudit();

    public EntityAudit getAudit() {
        return audit;
    }

    public void setAudit(EntityAudit audit) {
        this.audit = audit;
    }
}
