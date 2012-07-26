package org.mocktail.aspectpoc;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Service;
 
@Service
public class Demo {
 
    @Inject
    private String text;
 
    private final Integer integerOne;
 
    private Integer integerTwo;
 
    @Inject
    @Named("integerOne")
    public Demo(final Integer integerOne) {
        this.integerOne = integerOne;
    }
 
    @Inject
    @Named("integerTwo")
    public void setintegerTwo(final Integer integerTwo) {
        this.integerTwo = integerTwo;
    }
 
    @Override
    public String toString() {
        return "Demo [integerOne=" + integerOne + ", integerTwo=" + integerTwo
                + ", text=" + text + "]";
    }
}

