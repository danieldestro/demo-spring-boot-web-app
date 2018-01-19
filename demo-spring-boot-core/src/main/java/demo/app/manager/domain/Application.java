package demo.app.manager.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

import demo.app.core.domain.BaseEntity;

@Entity
@Table(name = "APP")
@SequenceGenerator(sequenceName = "SEQ_APP_ID", name = "SEQ_APP_ID", allocationSize = 1)
public class Application extends BaseEntity<Integer> {

    private static final long serialVersionUID = -4370457766267756866L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "SEQ_APP_ID")
    @Column(name = "APP_ID")
    private Integer           id;

    @Column(name = "APP_NM")
    private String            name;

    @Column(name = "APP_DN")
    private String            description;

    @Column(name = "APP_URL")
    private String            url;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    protected void extendToString(ToStringBuilder builder) {
        builder.append("name", getName());
        builder.append("description", getDescription());
        builder.append("url", getUrl());
    }
}
