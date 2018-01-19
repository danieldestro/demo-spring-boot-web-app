package demo.app.core.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 * Spring Data's support for JPA optimistic locking on all (updatable) entities.
 */
@MappedSuperclass
public abstract class BaseLockingEntity<T extends Serializable> extends BaseEntity<T> implements LockingEntity {

    private static final long serialVersionUID = 6748264477483357743L;

    @Version
    @Column(name = "LCK_VERS_NR")
    private Integer           lockVersion;

    @Override
    public Integer getLockVersion() {
        return lockVersion;
    }

    @Override
    public void setLockVersion(Integer lockVersion) {
        this.lockVersion = lockVersion;
    }
}
