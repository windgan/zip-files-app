package org.aigu.zipfilesapp.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;

public interface ZipService {

	void zipFiles(MultipartFile[] files, OutputStream outputStream);
}
