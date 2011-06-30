package com.xebia.smok.repository;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.internal.verification.Times;
import org.mockito.runners.MockitoJUnitRunner;

import com.xebia.smok.SmokContainer;

@RunWith(MockitoJUnitRunner.class)
public class ObjectRepositoryTest {

	@Mock
	OutputStream outputStream;
	ObjectRepository objectRepository = SmokContainer.getObjectRepository();

	@SuppressWarnings("unchecked")
	@Test
	public void shouldRecordObj() throws Exception {
		objectRepository.saveObject(
				Arrays.asList("sandy", "ganesh", 12, 23.0), outputStream);
		verify(outputStream, new Times(14)).write((byte[]) any(), anyInt(),
				anyInt());
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void shouldReadObj() throws Exception {
		List recordedList = (List) objectRepository
				.getObject(new ClasspathResourceLoader()
						.getResourceStream("com/xebia/smok/util/recorded_list.ser"));

		assertThat(recordedList.size(), is(4));

	}

	@Test
	public void testObjectAlreadyExists() throws Exception {
		String locationDirectory = getAbsolutePath("src", "test", "resources",
				"com", "xebia", "smok", "util");
		boolean objectAlreadyExist = objectRepository.objectAlreadyExist("recorded_list.ser", locationDirectory);
		assertThat(objectAlreadyExist, is(true));
	}

	@Test
	public void shouldSaveObject() throws Exception {
		String locationDirectory = getAbsolutePath("src", "test", "resources",
				"com", "xebia", "smok", "util");
		File file = new File(locationDirectory, "saved_object");
		assertThat(file.exists(), is(false));
		objectRepository.saveObject(
				Arrays.asList("sandy", "ganesh", 12, 23.0), "saved_object",
				locationDirectory);
		file = new File(locationDirectory, "saved_object");
		assertThat(file.exists(), is(true));
		assertThat("Unable to delete file", true, is(file.delete()));
	}

	@Test
	public void shouldGetObject() throws Exception {
		String locationDirectory = getAbsolutePath("src", "test", "resources",
				"com", "xebia", "smok", "util");
		List recordedList = (List) objectRepository.getObject("recorded_list.ser", locationDirectory);
		assertThat(recordedList.size(), is(4));
		assertThat((String) recordedList.get(0), is("sandy"));
		assertThat((String) recordedList.get(1), is("ganesh"));
		assertThat((Integer) recordedList.get(2), is(12));
		assertThat((Double) recordedList.get(3), is(23.0));
	}

	private String getAbsolutePath(String... path) throws IOException {
		File file = new File(".");
		StringBuffer absolutePath = new StringBuffer(file.getCanonicalPath());
		for (String pathElement : path) {
			absolutePath.append(File.separator).append(pathElement);
		}
		return absolutePath.toString();
	}
}
