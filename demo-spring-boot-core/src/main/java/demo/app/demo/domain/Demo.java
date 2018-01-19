package demo.app.demo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import demo.app.core.domain.BaseEntity;

@Entity
@Table(name = "DEMO")
@SequenceGenerator(sequenceName = "SEQ_DEMO_ID", name = "SEQ_DEMO_ID", allocationSize = 1)
public class Demo extends BaseEntity<Integer> {

    private static final long serialVersionUID = -4370457766267756866L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "SEQ_DEMO_ID")
    @Column(name = "DEMO_ID")
    private Integer           id;

    @Column(name = "DEMO_NM")
    private String            name;

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

    @Override
    public String toString() {
        return "Demo [id=" + id + ", name=" + name + "]";
    }

}
