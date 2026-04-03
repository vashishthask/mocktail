# Mocktail 🍹

> Run integration tests 10× faster — no Docker, no hand-written stubs, no drifting fixtures.

Mocktail records the real response of **any Java method call** to disk on the first run, then replays it instantly on every run after. Think of it as VCR for your Java code — not just HTTP, but any method: JPA repositories, AWS SDKs, Kafka consumers, legacy DAOs, gRPC clients — all of it.

---

## The Problem

You have integration tests that call a database, an external SDK, or an internal service. Your choices today feel like a trade-off you can't win:

| Approach | The hidden cost |
|---|---|
| Testcontainers | 10–30s Docker startup per test suite. Breaks in airgapped CI. |
| WireMock | Only intercepts HTTP. Useless for JPA, SDKs, message buses. |
| Mockito stubs | Hand-written. Never match real data. Drift silently. |
| H2 in-memory | SQL dialect differences cause false passes and false failures. |

Mocktail is none of those. It captures the *real* response once, checks it into source control, and replays it from disk in milliseconds forever.

---

## Quick Start

### 1. Add the dependency

```xml
<dependency>
    <groupId>in.malonus.mocktail</groupId>
    <artifactId>mocktail-core</artifactId>
    <version>1.0.3</version>
    <scope>test</scope>
</dependency>
```

You also need the AspectJ compiler plugin to enable method interception:

```xml
<plugin>
    <groupId>org.codehaus.mojo</groupId>
    <artifactId>aspectj-maven-plugin</artifactId>
    <version>1.14.0</version>
    <configuration>
        <complianceLevel>11</complianceLevel>
        <aspectLibraries>
            <aspectLibrary>
                <groupId>in.malonus.mocktail</groupId>
                <artifactId>mocktail-core</artifactId>
            </aspectLibrary>
        </aspectLibraries>
    </configuration>
    <executions>
        <execution>
            <goals><goal>compile</goal><goal>test-compile</goal></goals>
        </execution>
    </executions>
</plugin>
```

### 2. Configure which methods to intercept

Create `src/test/resources/mocktail.xml`:

```xml
<mocktail mode="recording">
    <mocktailMethod>
        <classQualifiedName>com.example.UserRepository</classQualifiedName>
        <methodName>findByEmail</methodName>
    </mocktailMethod>
</mocktail>
```

**Modes:**
- `recording` — Record the response once; replay on every subsequent call (recommended for CI)
- `recording_new` — Always re-record, overwriting what's on disk (use to refresh fixtures)

### 3. Wrap your test

```java
@Test
void shouldReturnUserByEmail() throws Exception {
    try (MethodMocktail m = new MethodMocktail(this)) {
        User user = userRepository.findByEmail("jane@example.com");
        assertThat(user.getName()).isEqualTo("Jane Doe");
    }
}
```

That's it. On the first run Mocktail calls the real `findByEmail`, saves the response to disk under `src/test/resources/mocktail/`, and every run after that skips the database entirely.

---

## How It Works
