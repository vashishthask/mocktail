package org.springframework.samples.petclinic;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Models a {@link Vet Vet's} specialty (for example, dentistry).
 * 
 * @author Juergen Hoeller
 */
public class Specialty extends NamedEntity{
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
