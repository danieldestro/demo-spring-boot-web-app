package demo.app.web.vo;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Base class for audited objects (VO, TO, DTO).
 */
public class EntityAuditVO implements Serializable {

    private static final long serialVersionUID = -4831870239469171501L;

    private Integer           id;
    private LocalDateTime     createdDate;
    private String            createdBy;
    private LocalDateTime     lastModifiedDate;
    private String            lastModifiedBy;

    public Integer getId() {
        return id;
    }

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
