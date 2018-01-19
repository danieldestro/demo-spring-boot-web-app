package demo.app.sso.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import demo.app.sso.domain.base.BaseAuditableLockingEntity;

@Entity
@Table(name = "PRMSN")
@SequenceGenerator(sequenceName = "SEQ_PRMSN_ID", name = "SEQ_PRMSN_ID", allocationSize = 1)
public class Permission extends BaseAuditableLockingEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "SEQ_PRMSN_ID")
    @Column(name = "PRMSN_ID")
    private Integer           id;

    @Column(name = "PRMSN_NM")
    private String            name;

    @Column(name = "PRMSN_DN")
    private String            description;

    private static final long serialVersionUID = 483184098778707462L;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
