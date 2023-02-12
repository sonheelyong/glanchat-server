package com.green.chat.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import com.green.chat.model.UserFileEntity;
import com.green.chat.service.FileService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/userfile")
public class UserFileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public void fileupload(@AuthenticationPrincipal String user_email, @RequestPart MultipartFile files) throws IOException{
        UserFileEntity file = new UserFileEntity();

        String sourceFileName = files.getOriginalFilename();
        String sourceFileNameExtension = FilenameUtils.getExtension(sourceFileName).toLowerCase();
        FilenameUtils.removeExtension(sourceFileName);

        File destinationFile;
        String destinationFileName;
        String fileUrl = "C:\\Users\\82105\\Desktop\\ws\\chat-react\\reactfront\\public\\resource\\";
        
        
    
        do{
            // 파일 이름 변환
            destinationFileName = RandomStringUtils.randomAlphanumeric(32) + "." + sourceFileNameExtension;
            destinationFile = new File(fileUrl + destinationFileName);
        } while (destinationFile.exists());

        destinationFile.getParentFile().mkdirs();
        files.transferTo(destinationFile);
        
        file.setEmail(user_email);
        file.setFilename(destinationFileName);
        file.setFileOriname(sourceFileName);
        file.setFileUrl(fileUrl);
        fileService.save(file);
    
    }
       
     @GetMapping("/upload/{id}")
     public ResponseEntity<?> getFile(@PathVariable String id) {
       
         Optional<UserFileEntity> file = fileService.findById(id);

         return ResponseEntity.ok(file);
        
     }
    
@GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadAttach(@PathVariable String id) throws MalformedURLException {

        UserFileEntity file = fileService.findById(id).orElse(null);

        UrlResource resource = new UrlResource("file:" + file.getFileUrl());

        String encodedFileName = UriUtils.encode(file.getFileOriname(), StandardCharsets.UTF_8);

        // 파일 다운로드 대화상자가 뜨도록 하는 헤더를 설정해주는 것
        // Content-Disposition 헤더에 attachment; filename="업로드 파일명" 값을 준다.
        String contentDisposition = "attachment; filename=\"" + encodedFileName + "\"";

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,contentDisposition).body(resource);
    }
}
