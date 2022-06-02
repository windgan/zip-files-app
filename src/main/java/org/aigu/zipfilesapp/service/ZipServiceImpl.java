package org.aigu.zipfilesapp.service;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class ZipServiceImpl implements ZipService {

    private static final Logger logger = LoggerFactory.getLogger(ZipService.class);

    @Override
    public void zipFiles(MultipartFile[] files, OutputStream outputStream) {
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream)) {
            for (MultipartFile file : files) {
                try (InputStream inputStream = file.getInputStream()) {
                    ZipEntry zipEntry = new ZipEntry(file.getOriginalFilename());
                    zipOutputStream.putNextEntry(zipEntry);
                    IOUtils.copy(inputStream, zipOutputStream);
                }
            }
        } catch (IOException e) {
            logger.error("Exception while reading and streaming data", e);
        }
    }
}
