package org.mocktail.aspectpoc;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
 
/**
 * Should have been a main method, but I use the
 * {@link Test} annotation to demonstrate withincode designator
 * {@link TraceAspect#beanAnnotatedWithComponentOrASpecializationOfIt()}
 *
 * @author Espen Berntsen
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class DemoTest {
 
    @Inject
    private Demo demo;
 
    @Test
    public void testToString() {
        System.out.println("demo.toString(): " + demo.toString());
    }
}

