package demo.app.core.service;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

public class SearchFilter implements Serializable {

    private static final long serialVersionUID = 4657676114395426584L;

    private String            filter;

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    /**
     * Indicates whether the filter has no value set or not.
     * 
     * @return
     */
    public boolean isEmpty() {
        return StringUtils.isEmpty(getFilter());
    }
}
