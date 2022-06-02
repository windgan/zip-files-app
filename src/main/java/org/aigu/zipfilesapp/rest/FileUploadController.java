package org.aigu.zipfilesapp.rest;

import org.aigu.zipfilesapp.service.ZipService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletResponse;

@Controller
public class FileUploadController {

    private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    private final ZipService zipService;

    @Autowired
    public FileUploadController(ZipService zipService) {
        this.zipService = zipService;
    }


    @GetMapping("/")
    public String showUploadForm(Model model) {
        return "uploadForm";
    }

    @PostMapping(value = "/upload", produces = "application/zip")
    public ResponseEntity<StreamingResponseBody> zipFiles(HttpServletResponse response,
                                                          @RequestParam("file") MultipartFile[] files) {
        logger.info("Received {} files to be zipped", files.length);
        StreamingResponseBody streamResponseBody = out -> zipService.zipFiles(files, out);

        response.setContentType("application/zip");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=example.zip");
        response.addHeader(HttpHeaders.PRAGMA, "no-cache");
        response.addHeader(HttpHeaders.EXPIRES, "0");

        logger.info("Files successfully zipped");
        return ResponseEntity.ok(streamResponseBody);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception exc) {
        return ResponseEntity.internalServerError().build();
    }
}
