package com.xebia.smok.util;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;

import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.internal.verification.Times;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RecordigsRepositoryTest {

	@Mock
	OutputStream outputStream;

	@SuppressWarnings("unchecked")
	@Test
	public void shouldRecordObj() throws Exception {
		RecordigsRepository.SERIALIZER_RECORDINGS_REPOSITORY.marshall(
				Arrays.asList("sandy", "ganesh", 12, 23.0), outputStream);
		verify(outputStream, new Times(14)).write((byte[]) any(), anyInt(),
				anyInt());
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void shouldReadObj() throws Exception {
		List recordedList = (List) RecordigsRepository.SERIALIZER_RECORDINGS_REPOSITORY
				.unmarshall(new ClasspathResourceLoader()
						.getResourceStream("com/xebia/smok/util/recorded_list.ser"));

		assertThat(recordedList.size(), is(4));

	}
}
