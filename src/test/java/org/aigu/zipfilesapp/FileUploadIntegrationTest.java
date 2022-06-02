package org.aigu.zipfilesapp;

import org.aigu.zipfilesapp.service.ZipService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MimeType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FileUploadIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private ZipService zipService;

    @LocalServerPort
    private int port;

    @Test
    public void shouldUploadFile() {
        ClassPathResource resource = new ClassPathResource("testupload.txt", getClass());

        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("file", resource);
        ResponseEntity<byte[]> response = this.restTemplate.postForEntity("/upload", map, byte[].class);

        assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        assertThat(response.getHeaders().getContentType().equalsTypeAndSubtype(MimeType.valueOf("application/zip")));
        then(zipService).should().zipFiles(any(MultipartFile[].class), any(OutputStream.class));
    }
}
