package demo.app.manager.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import demo.app.core.domain.BaseEntity;

@Entity
@Table(name = "GBL_CNFG")
@SequenceGenerator(sequenceName = "SEQ_GBL_CNFG_ID", name = "SEQ_GBL_CNFG_ID", allocationSize = 1)
public class GlobalConfiguration extends BaseEntity<Integer> {

    private static final long serialVersionUID = -3472801177743213044L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "SEQ_GBL_CNFG_ID")
    @Column(name = "GBL_CNFG_ID")
    private Integer           id;

    @Column(name = "GBL_CNFG_CNTXT")
    private String            context;

    @Column(name = "GBL_CNFG_NM")
    private String            name;

    @Column(name = "GBL_CNFG_VALU")
    private String            value;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
