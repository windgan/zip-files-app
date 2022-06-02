package org.aigu.zipfilesapp.service;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.ByteArrayOutputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ZipServiceImplTest {

    private ZipServiceImpl service = new ZipServiceImpl();

    @Test
    public void checkSingleFileZipped() {
        MockMultipartFile[] files = {new MockMultipartFile("foo", "foo.txt", MediaType.TEXT_PLAIN_VALUE,
                "Hello, World".getBytes())};
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        service.zipFiles(files, outputStream);
        assertThat(outputStream.size()).isGreaterThan(0);
    }

    @Test
    public void checkTwoFilesZipped() {
        MockMultipartFile[] files = {
                new MockMultipartFile("foo", "foo.txt", MediaType.TEXT_PLAIN_VALUE,
                        "Hello, World".getBytes()),
                new MockMultipartFile("bar", "barbar.txt", MediaType.TEXT_PLAIN_VALUE,
                        "Hello, Bar".getBytes())
        };
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        service.zipFiles(files, outputStream);
        assertThat(outputStream.size()).isGreaterThan(0);
    }

    @Test
    public void checkEmptyFiles() {
        MockMultipartFile[] files = {};
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        service.zipFiles(files, outputStream);
        assertThat(outputStream.size()).isGreaterThan(0);
    }

    @Test
    public void passNullOutputStream() {
        MockMultipartFile[] files = {};
        assertThrows(NullPointerException.class, () -> service.zipFiles(files, null));
    }
}
