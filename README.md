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
