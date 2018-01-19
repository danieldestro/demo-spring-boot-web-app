package demo.app.web.vo;

import java.io.Serializable;
import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;

import demo.app.core.domain.BaseIdentifiable;

/**
 * Base class for all transfer objects (VO, TO, DTO).
 */
public abstract class BaseVO<T extends Serializable> implements BaseIdentifiable<T> {

    private static final long serialVersionUID = -4831870239469171501L;

    private T                 id;

    public void setId(T id) {
        this.id = id;
    }

    @Override
    public T getId() {
        return this.id;
    }

    @Override
    public boolean equals(final Object other) {

        if (this == other) {
            return true;
        }

        if (other == null) {
            return false;
        }

        if (!getClass().equals(other.getClass())) {
            return false;
        }

        BaseVO<?> castOther = (BaseVO<?>) other;
        return Objects.equals(id, castOther.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this).append("id", getId());
        extendToString(builder);
        return builder.toString();
    }

    /**
     * Apply required extensions to toString() method.
     * <p>
     * Code Example: <code>builder.append("variableName", variableName);</code>
     * </p>
     * 
     * @param builder
     */
    protected void extendToString(ToStringBuilder builder) {
        // Default implementation does not extend
    }
}
