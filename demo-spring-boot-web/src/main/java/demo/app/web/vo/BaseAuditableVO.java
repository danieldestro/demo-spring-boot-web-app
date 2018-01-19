package demo.app.web.vo;

import java.io.Serializable;

/**
 * Base class for audited objects (VO, TO, DTO).
 */
public abstract class BaseAuditableVO<T extends Serializable> extends BaseVO<T> implements Serializable {

    private static final long serialVersionUID = -4831870239469171501L;

    private EntityAuditVO     audit;

    public EntityAuditVO getAudit() {
        return audit;
    }

    public void setAudit(EntityAuditVO audit) {
        this.audit = audit;
    }
}
