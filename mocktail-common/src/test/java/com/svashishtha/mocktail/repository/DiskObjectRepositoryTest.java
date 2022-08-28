package com.svashishtha.mocktail.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.internal.verification.Times;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DiskObjectRepositoryTest {

	@Mock
	OutputStream outputStream;
	ObjectRepository objectRepository = new DiskObjectRepository();

	@Test
	public void shouldRecordObj() throws Exception {
		objectRepository.saveObject(Arrays.asList("sandy", "ganesh", "12", "23"), outputStream);
		verify(outputStream, new Times(4)).write((byte[]) any(), anyInt(), anyInt());
	}

	@Test
	public void shouldReadObj() throws Exception {
		List recordedList = (List) objectRepository.getObject(this.getClass().getClassLoader()
				.getResourceAsStream("com/svashishtha/mocktail/util/recorded_list.ser"));

		assertThat(recordedList.size(), is(4));

	}

	@Test
	public void testObjectAlreadyExists() throws Exception {
		String locationDirectory = getAbsolutePath("src", "test", "resources", "com", "svashishtha", "mocktail",
				"util");
		boolean objectAlreadyExist = objectRepository.objectAlreadyExist("recorded_list.ser", locationDirectory);
		assertThat(objectAlreadyExist, is(true));
	}

	@Test
	public void shouldSaveObject() throws Exception {
		String locationDirectory = getAbsolutePath("src", "test", "resources", "com", "svashishtha", "mocktail",
				"util");
		File file = new File(locationDirectory, "saved_object");
		assertThat(file.exists(), is(false));
		objectRepository.saveObject(Arrays.asList("sandy", "ganesh", 12, 23.0), "saved_object", locationDirectory);
		file = new File(locationDirectory, "saved_object");
		assertThat(file.exists(), is(true));
		assertThat("Unable to delete file", true, is(file.delete()));
	}

	@Test
	public void shouldGetObject() throws Exception {
		String locationDirectory = getAbsolutePath("src", "test", "resources", "com", "svashishtha", "mocktail",
				"util");
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
