package demo.app.core.domain;

public interface LockingEntity {

    Integer getLockVersion();

    void setLockVersion(Integer lockVersion);
}
