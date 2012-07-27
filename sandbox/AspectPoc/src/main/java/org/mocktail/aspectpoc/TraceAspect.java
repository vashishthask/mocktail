package org.mocktail.aspectpoc;

import javax.inject.Inject;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.junit.Test;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * An example aspect that shows how to write pointcuts for annotated fields, methods and
 * types and how to advice those aspects.
 *
 * <p>
 * <ul>
 *  <li>annotation target set to method</li>
 *
<ul>
    <li>{@link Inject @Inject}, {@link Test @Test}</li>
</ul>
 *  <li>annotation target set to type</li>
 *
<ul>
    <li>{@link Component @Component} or a specialization of it</li>
</ul>
 *      ({@link Service @Service}, {@link Repository @Repository})
 * </ul>
 *
 * @author Espen Berntsen
 * @see Demo
 */
@Aspect
public class TraceAspect {
 
    // Trace injected methods and constructors -->
    @Pointcut("within(@(@org.springframework.stereotype.Component *) *)")
    public void beanAnnotatedWithComponentOrASpecializationOfIt() {}
 
    @Pointcut("execution(@javax.inject.Inject * demo.*.*(..))")
    public void methodAnnotatedWithInjectAndInDemoPackage() {}
 
    @Pointcut("execution(@javax.inject.Inject demo.*.new(Integer)) && args(integer)")
    public void constructorAnnotatedWithInjectAndIndemoPackage(
            Integer integer) {}
 
    // With args(integer).
    @AfterReturning("beanAnnotatedWithComponentOrASpecializationOfIt() &&  " +
            "constructorAnnotatedWithInjectAndIndemoPackage(integer)")
    public void afterReturningFromConstructorInSpringBeanWithIntegerParameter(
            JoinPoint joinPoint, Integer integer) {
        System.out.println("Executed @Injected constructor: "
                + joinPoint.getSignature() + " with the integer: " + integer);
 
    }
 
    // Using joinPoint.getArgs() to show arguments.
    @AfterReturning("beanAnnotatedWithComponentOrASpecializationOfIt() &&  " +
            "methodAnnotatedWithInjectAndInDemoPackage()")
    public void afterReturningFromMethodInSpringBean(JoinPoint joinPoint) {
        System.out.print("Executed the @Injected method: "
                + joinPoint.getSignature() + " with value(s): ");
 
        for (Object object : joinPoint.getArgs()) {
            System.out.print(object);
        }
        System.out.println();
    }
    // Trace injected methods and constructors <--
 
    // Trace get on String fields annotated with @Inject -->
    @Pointcut("get(@javax.inject.Inject java.lang.String demo..*.*)")
    public void getStringFieldAnnotatedWithInjectInTheDemoPackage() {}
 
    @Pointcut("!withincode(@org.junit.Test * demo..*(..))")
    public void notInTestMethod() {}
 
    @Pointcut("getStringFieldAnnotatedWithInjectInTheDemoPackage() && notInTestMethod()")
    public void getStringFieldAnnotatedWithInjectInTheDemoPackageAndNotInTestMethod() {}
 
    @Around("getStringFieldAnnotatedWithInjectInTheDemoPackageAndNotInTestMethod()")
    public String aroundGetStringFieldAnnotatedWithInjectInTheDemoPackageAndNotInTestMethod(
            ProceedingJoinPoint joinPoint) throws Throwable {
 
        String fieldValue = (String) joinPoint.proceed();
        System.out.println("Get on @Inject String field: "
                + joinPoint.getSignature() + " with value: " + fieldValue);
        return fieldValue;
    }
    // Trace get on String fields annotated with @Inject <--
}

