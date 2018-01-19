package demo.app.web.vo;

import java.io.Serializable;

/**
 * Base class for locking transfer objects (VO, TO, DTO).
 */
public abstract class BaseAuditableLockingVO<T extends Serializable> extends BaseAuditableVO<T> {

    private static final long serialVersionUID = -4831870239469171501L;

    private Integer           lockVersion;

    public Integer getLockVersion() {
        return lockVersion;
    }

    public void setLockVersion(Integer lockVersion) {
        this.lockVersion = lockVersion;
    }
}
