package in.malonus.mocktail.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DiskObjectRepositoryTest {

	private static final String SERIALIZED_LIST_PATH = "src/test/resources/in/malonus/mocktail";
    @Mock
	OutputStream outputStream;
	ObjectRepository objectRepository = new DiskObjectRepository();

	@Test
	public void testObjectAlreadyExists() throws Exception {
		String locationDirectory = getAbsolutePath(SERIALIZED_LIST_PATH);
		boolean objectAlreadyExist = objectRepository.objectAlreadyExist("recorded_list.ser", locationDirectory);
		assertThat(objectAlreadyExist, is(true));
	}

	@Test
	public void shouldSaveObject() throws Exception {
		String locationDirectory = getAbsolutePath(SERIALIZED_LIST_PATH);
		File file = new File(locationDirectory, "saved_object");
		assertThat(file.exists(), is(false));
		objectRepository.saveObject(Arrays.asList("sandy", "ganesh", 12, 23.0), "saved_object", locationDirectory);
		file = new File(locationDirectory, "saved_object");
		assertThat(file.exists(), is(true));
		assertThat("Unable to delete file", true, is(file.delete()));
	}

	@Test
	public void shouldGetObject() throws Exception {
		String locationDirectory = getAbsolutePath(SERIALIZED_LIST_PATH);
		@SuppressWarnings("rawtypes")
        List recordedList = (List) objectRepository.getObject("recorded_list.ser", locationDirectory);
		assertThat(recordedList.size(), is(4));
		assertThat((String) recordedList.get(0), is("sandy"));
		assertThat((String) recordedList.get(1), is("ganesh"));
		assertThat((Integer) recordedList.get(2), is(12));
		assertThat((Double) recordedList.get(3), is(23.0));
	}

	private String getAbsolutePath(String path) throws IOException {
		File file = new File(".");
		StringBuffer absolutePath = new StringBuffer(file.getCanonicalPath());
		absolutePath.append(File.separator).append(path);
		return absolutePath.toString();
	}
}
