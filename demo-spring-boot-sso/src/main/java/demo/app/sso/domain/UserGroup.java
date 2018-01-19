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
@Table(name = "USER_GRP")
@SequenceGenerator(sequenceName = "SEQ_USER_GRP_ID", name = "SEQ_USER_GRP_ID", allocationSize = 1)
public class UserGroup extends BaseAuditableLockingEntity<Integer> {

    private static final long serialVersionUID = -4370457766267756866L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "SEQ_USER_GRP_ID")
    @Column(name = "USER_GRP_ID")
    private Integer           id;

    @Column(name = "USER_GRP_NM")
    private String            name;

    @Column(name = "USER_GRP_DN")
    private String            description;

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
