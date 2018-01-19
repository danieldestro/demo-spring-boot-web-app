package demo.app.core.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Base class for all JPA entities, with some commonly used methods implemented.
 */
@MappedSuperclass
public abstract class BaseEntity<T extends Serializable> implements BaseIdentifiable<T> {

    private static final long serialVersionUID = -3276999349287674832L;

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if ((o == null) || !this.getClass().equals(o.getClass())) {
            return false;
        }

        BaseEntity<?> other = (BaseEntity<?>) o;

        if (getId() == null) {
            if (other.getId() != null) {
                return false;
            }
        } else if (!getId().equals(other.getId())) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this).append("id", getId());
        extendToString(builder);
        return builder.toString();
    }

    /**
     * Apply required extensions to toString()
     * <p>
     * Code Example: <code>builder.append("variableName", variableName);</code>
     * </p>
     * 
     * @param builder
     *            {@link ToStringBuilder} to write the string output to
     */
    protected void extendToString(ToStringBuilder builder) {
    }
}
