package in.malonus.mocktail.metadata.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import in.malonus.mocktail.metadata.MocktailContainer;

public class UniqueIdGeneratorTest {

    UniqueIdGenerator uniqueIdGenerator = MocktailContainer.getInstance().getUniqueIdGenerator();

    @Test
    public void shouldGenerateUniqueIdUsingHashCode() throws Exception {
        int uniqueId = uniqueIdGenerator.getUniqueId("sandy", "ganesh", 12, 23.0);
        assertThat(uniqueId, is(1642607558));

        uniqueId = uniqueIdGenerator.getUniqueId("ganesh", "sandy", 12, 23.0);
        assertThat(uniqueId, is(-1575575532));

        uniqueId = uniqueIdGenerator.getUniqueId(Arrays.asList("sandy", "ganesh", 12, 23.0));
        assertThat(uniqueId, is(1332060453));

        Object[] array = { "ganesh", 12, 23.0 };
        Object[] array2 = { "ganesh", 12, 23.0 };
        assertThat(uniqueIdGenerator.getUniqueId("sandy", array), is(uniqueIdGenerator.getUniqueId("sandy", array2)));
    }

    @Test
    public void shouldGenerateUniqueIdForObject() throws Exception {
        DtoSample dtoSample = new DtoSample();
        dtoSample.setName("mocktail");
        dtoSample.addKeyValuePair("key", new DtoSample());
        DtoSample dtoToAdd = new DtoSample();
        dtoToAdd.setName("mocktailSample");

        dtoSample.addKeyValuePair("key2", dtoToAdd);

        dtoSample.appendStrToList("first name");
        dtoSample.appendStrToList("last name");

        dtoSample.appendStr("address");
        dtoSample.appendStr("zipCode");

        int uniqueId = uniqueIdGenerator.getUniqueId("objectIdTest", dtoSample);

        assertThat(uniqueId, is(-2136598509));
    }

    private class DtoSample {
        private String name;
        private StringBuilder builder;
        private List<String> list;
        private Map<String, DtoSample> map = new HashMap<String, DtoSample>();

        public String getName() {
            return name;
        }

        public DtoSample() {
            list = new ArrayList<String>();
            map = new HashMap<String, DtoSample>();
            builder = new StringBuilder();
        }

        public void setName(String name) {
            this.name = name;
        }

        public void appendStr(String str) {
            builder.append(str);
        }

        public void appendStrToList(String str) {
            list.add(str);
        }

        public void addKeyValuePair(String key, DtoSample value) {
            map.put(key, value);
        }
    }
}
