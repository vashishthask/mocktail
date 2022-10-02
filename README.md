# Mocktail

[Mocktail](https://github.com/vashishthask/mocktail/) is a tool to cache the response of potentially any Java method call on the disk. This becomes tremendously useful when you want to mock a method call, but still want it to respond with real-world response, e.g. for complex database call, for a restful service call. After the first call it makes to the method implementation, from next call onwards, method provides the response from its cache.

This can be termed as [VCR](https://github.com/vcr/vcr) implementation in Java. VCR records your test suite's HTTP interactions and replays them during future test runs for fast, deterministic, accurate tests. Mocktail does the record and playback for any method. HTTP record and playback is one of the record and playback it does.

Currently mocktail examples have been implemented for
* record and play for jdbc call, ORM implementation
* Restful service implementation with Springboot

# Use Cases
* On-Line test services are not freely available. We have to book ahead and pay for each test session, which is not practical if we want to run tests all the time.
* Simulating the scenario with a test data available on the server

# Mocktail available on Maven Central
Here's the required dependency
```xml
<dependency>
  <groupId>in.malonus.mocktail</groupId>
  <artifactId>mocktail-core</artifactId>
  <version>1.0.1</version>
</dependency>
```
and required maven plugin
```xml
<plugin>
  <artifactId>mocktail-maven-plugin</artifactId>
  <groupId>in.malonus.mocktail</groupId>
  <version>1.0.1</version>
</plugin>
```
# Steps in Setting up Mocktail
Mocktail is made of following components
* Maven plugin - mocktail-maven-plugin in association with aspectj-maven-plugin
* A Java lib - mocktail-core
* aspectjrt lib
* aspectj-maven-plugin plugin

## Maven Pom Changes to Include Mocktail
Include mocktail-core lib

```xml
<properties>
    <org.aspectj.version>1.9.7</org.aspectj.version>
</properties>

<dependency>
  <groupId>in.malonus.mocktail</groupId>
  <artifactId>mocktail-core</artifactId>
  <version>1.0.1</version>
</dependency>
<dependency>
  <groupId>org.aspectj</groupId>
  <artifactId>aspectjrt</artifactId>
  <version>${org.aspectj.version}</version>
  <scope>runtime</scope>
</dependency>
```


Include mocktail-maven-plugin and aspectj-maven-plugin

```xml
<plugin>
  <artifactId>mocktail-maven-plugin</artifactId>
  <groupId>in.malonus.mocktail</groupId>
  <version>1.0.1</version>
  <configuration>
    <mode>recording_new</mode>
  </configuration>
  <executions>
    <execution>
      <id>mocktail</id>
      <phase>validate</phase>
      <goals>
        <goal>mocktail</goal>
      </goals>
    </execution>
  </executions>
</plugin>
<plugin>
  <groupId>org.codehaus.mojo</groupId>
  <artifactId>aspectj-maven-plugin</artifactId>
  <version>1.14.0</version>
  <executions>
    <execution>
      <goals>
        <goal>compile</goal> 
        <goal>test-compile</goal>
      </goals>
      <configuration>
        <source>1.8</source>
        <target>1.8</target>
        <complianceLevel>1.8</complianceLevel>
        <aspectDirectory>target/generated/aspects</aspectDirectory>
      </configuration>
    </execution>
  </executions>
  <dependencies>
    <dependency>
      <groupId>org.aspectj</groupId>
      <artifactId>aspectjtools</artifactId>
      <version>${org.aspectj.version}</version>
    </dependency>
  </dependencies>
</plugin>
```

In mocktail-maven-plugin configuration, highlighted <mode/> information is important. 

The mode "recording" is used for "recording" and "playback" which means, if recording for a Java method mentioned in the configuration mocktail.xml is available on the disk, it'll just run the playback from the available recording. If recording is not available, it'll create a new recording and will save that to the disk.

The mode "recording_new" is used when we want to clean all existing recordings and want to add a new recording. 

For the next test run though, it will be important to use mode "recording", otherwise it'll again clean the existing recordings and will not use them for the playback.
## Setting up mocktail.xml for Letting Mocktail Know Which Methods Responses to Record and Playback

You can create a mocktail.xml in the src/test/resources folder where you mention the details of the class methods for which the response needs to be cached. An example of mocktail.xml is as follows:

```xml
<mocktails>
  <mocktail>
    <className>UserDao</className>
    <classPackageName>in.malonus.mocktail.mock.jdbc.user</classPackageName>
    <methods>
      <string>get</string>
      <string>save</string>
      <string>delete</string>
      <string>update</string>
    </methods>
  </mocktail>
</mocktails>
```

With the above mocktail.xml Mocktail gets to know which methods to consider for response caching. In the above case the methods get(), save(), delete() and update() are in in.malonus.mocktail.mock.jdbc.user.UserDao class.

In the plugin section above, the aspectj-maven-plugin creates the AOP aspects based on the configuration available in mocktail.xml under the target/generated folder. For the given methods in mocktail.xml, around advice gets applied.


Through mocktail.xml we get to know which methods to consider for response caching but we still need to mention when to cache their responses. That's what we mention in a test method.

```java
@Test
public void testSomething(){
    MethodMocktail methodMocktail = new MethodMocktail(this);
    // search with recording mode
    UserDetail userDetail = userDao.get(1L);
    assertNotNull(userDetail);
    assertThat(10, is(userDetail.getAge()));
}
```

The declaration "MethodMocktail methodMocktail = new MethodMocktail(this);" provides a test method context to Mocktail. 

It also tells the Mocktail that the method calls from this test method to any of the methods mentioned in mocktail.xml (e.g. userDao.get()) should be considered for record and playback in Mocktail. 

That's how the "when to cache" question gets answered. In above method, the call to userDao.get(1L) gets considered for response caching on the disk. In the next test run or in the next userDao.get(1L) call within the same test method, Mocktail provides the response from the disk.

Any recorded response gets saved under src/test/resources/recorded. 

If test class is "in.malonus.mocktail.jdbc.UserDaoTest" and test method name is "testSomething()", the recording path for any recording will be "src/test/resources/recorded/in/malonus/mocktail/jdbc/UserDaoTest/testSomething". Under this path the recordings will get saved with the cached method name, e.g. 
```bash
src
└── test
    └── resources
        └── recordings
            └── in
                └── malonus
                    └── mocktail
                        └── jdbc
                            └── user
                                └── UserDaoTest
                                    └── testGetUser
                                        └── get_-630771270
```
# Why Mocktail?
Most of the software applications use interfacing points, e.g. a database, XML datasource, or a restful service. Such interfacing points pose challenges while creating repeatable automated tests. The challenges are as follows:
## Tests Become Flaky
Tests become flaky as they depend on the connection itself. If a database or a restful service is down, the tests dependent on them fail. 

Tests that sometimes work and sometimes do not, break the trust of a team. Sometimes just because of that people stop paying attention to the test suite and may not fix them. After some time such tests become unmaintainable and people stop using them. As a result, the entire investment in creating such tests goes in a drain.
## Creating Realistic Test Data Takes Time
Now you may say that for the database scenario mentioned above, we can use an in-memory database such as H2 or hsqldb for tests which removes the dependency on the DB I/O operations anymore. 

That's a welcome change and helps a lot in creating faster and repeatable tests. However still, test data creation in itself may take a good amount of time in doing just that for any real-world application. 

It would be of help, if someone or some tool could help us in creating the test data quickly
## Creating Realistic Mocked Web/Restful Service Response Takes Time
Connecting to a service and getting the resultant response consumes time while executing an automated test. 

This response time depends on the connectivity of the service, the time that service takes in fetching the data and applying related business rules. 

In an automated test suite, such interactions consume the most amount of time. A test suite containing similar tests may take a significant amount of time (sometimes hours) in execution and developers may hesitate running them frequently.

In most cases, instead of running end-to-end tests only, it's worthwhile to test a component in isolation and mock the responses of external service interactions. 

We can mock a restful service or a Java method easily through a mocking framework. But again it would be more helpful if such a mock response could be more realistic. 

Such a realistic dataset can be created as well but that takes time. It would be of help if such manual work could be automated as well through some tool.

# What is Mocktail?
What if instead of connecting to a real-world interface (a datasource or a service) in all repeated test runs for getting a realistic test dataset, a tool helps in providing the same or similar dataset from the disk. Mocktail does just that.

This becomes helpful in many different aspects:
* Picking a dataset from the disk is quicker than getting it through a network call after relevant business rules processing. This helps in reducing the test run time. That in turn helps in providing quicker feedback to a developer from such test runs.
* Tests will no longer be flaky and will have repeatability.
* Test data continues to be realistic and not half baked.
* The time taken in creating the test data gets reduced which in turn helps in creating tests quicker.
* It becomes useful in those scenarios as well when we want to create a broken test case around a defect discovered in any specific environment.

Mocktail is a tool which is used in conjunction with JUnit, Cucumber or other sorts of automated tests. 

It helps in saving the method responses on the disk which encapsulate the interactions with interfaces such as databases and restful services etc. 

In the subsequent method calls it returns those saved responses without executing the method. 

# How Mocktail Works?

![foo](http://www.agilebuddha.com/wp-content/uploads/2022/09/Mocktail_-Introducing-a-Java-Based-Record-and-Playback-Tool-for-Test-Automation-Purposes.png "title") 

It's done through applying an AOP (Aspect Oriented Programming) proxy dynamically on Java methods . 

This proxy checks if a response exists on the disk for the inputs received. If the proxy finds the response, it doesn't execute the method anymore. Instead the proxy returns the saved response from the disk.

If the proxy doesn't find the response for a specific input on the disk, it executes the method call, saves the response on the disk and returns the response to the caller.

For creating dynamic proxy on identified Java methods, AOP Aspect gets generated at compile time through aspectj-maven-plugin. A configuration file mocktail.xml helps in deciding which Java method to apply proxy on. The configuration files looks something like this:

```xml
<mocktails>
  <mocktail>
    <className>UserDao</className>
    <classPackageName>in.malonus.mocktail.mock.jdbc.user</classPackageName>
    <methods>
      <string>get</string>
    </methods>
  </mocktail>
</mocktails>
```

In above configuration, Mocktail is to be applied on the get() method of in.malonus.mocktail.mock.jdbc.user.UserDao class.

In AOP, join-points help in deciding when to apply a proxy (AOP advice) on a method. 
Mocktail gets to know about a joinpoint if an instance of MocktailMethod exists in a test method, e.g.:

```java
@Test
public void testSomething(){
    MethodMocktail methodMocktail = new MethodMocktail(this);
    // search with recording mode
    UserDetail userDetail = userDao.get(1L);
    assertNotNull(userDetail);
    assertThat(10, is(userDetail.getAge()));
}
```

In the above test method Mocktail gets applied to the userDao.get() method (defined in mocktail.xml file) as testSomething() method contains an instance of MethodMocktail. 

So for the first ever testSomething() call, the get() method will actually get executed and will interact with a DB. 

The response of this call then gets saved on the disk under the src/test/resources/recording folder. 

For any subsequent get() method call (either within the same test method or any rerun of the test), the proxy gets the response from the disk for the input "1L" instead of getting it by executing the actual get() method and in turn calling the DB.

