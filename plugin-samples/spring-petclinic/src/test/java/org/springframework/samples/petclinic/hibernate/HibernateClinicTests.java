package org.springframework.samples.petclinic.hibernate;

import org.junit.Ignore;
import org.springframework.samples.petclinic.AbstractClinicTests;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

/**
 * <p>
 * Integration tests for the {@link HibernateClinic} implementation.
 * </p>
 * <p>
 * "HibernateClinicTests-context.xml" determines the actual beans to test.
 * </p>
 *
 * @author Juergen Hoeller
 * @author Sam Brannen
 */
@ContextConfiguration
@DirtiesContext
@Ignore
public class HibernateClinicTests extends AbstractClinicTests {

}
