package demo.app.web.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * VO that represents an Application record.
 */
public class ApplicationVO extends BaseVO<Integer> {

    private static final long serialVersionUID = -2988432962320340655L;

    private String            name;

    private String            description;

    private String            url;

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
        builder.append("name", name);
        builder.append("description", description);
        builder.append("url", url);
    }
}
