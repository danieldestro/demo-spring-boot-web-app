package demo.app.core.domain;

import java.io.Serializable;

public interface BaseIdentifiable<T extends Serializable> extends Serializable {

    /**
     * Gets the primary key of the entity.
     *
     * @return the id
     */
    T getId();

    /**
     * Sets the primary key of the entity.
     * 
     * @param id
     *            id or primary key
     */
    void setId(T id);
}
