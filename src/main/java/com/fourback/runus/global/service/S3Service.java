package com.fourback.runus.global.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.s3.dir}")
    private String dir;


    public String uploadFile(MultipartFile multipartFile) {

        // 폴더명
        String uploadFilePath = getFolderName();
        
        // 파일명
        String originalFileName = multipartFile.getOriginalFilename();
        String uploadFileName = getUuidFileName(Objects.requireNonNull(originalFileName));

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());


        // AWS S3에 저장
        try (InputStream inputStream = multipartFile.getInputStream()) {

            String keyName = uploadFilePath + "/" + uploadFileName; // ex) 구분/년/월/일/파일.확장자

            // S3에 폴더 및 파일 업로드
            amazonS3.putObject(
                    new PutObjectRequest(bucket, keyName, inputStream, objectMetadata));


            // 업로드된 파일의 URL 반환
            return amazonS3.getUrl(bucket, keyName).toString();

        } catch (IOException e) {
            log.error("Filed upload failed", e);
        }

        return "";
    }

    
    // 폴더명 생성 (2024/07/23)
    private String getFolderName() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        String str = sdf.format(date);

        return str.replace("-", "/");
    }

    
    // 파일명 생성 (UUID)
    public String getUuidFileName(String fileName) {
        String ext = fileName.substring(fileName.indexOf(".") + 1);

        return UUID.randomUUID() + "." + ext;
    }


    // 파일을 덮어쓰지 않고 동일한 키로 업로드하면 수정과 동일하게 동작
    public String updateFile(MultipartFile file, String existingFileName) throws IOException {
        String key = dir + "/" + existingFileName;

        // S3에 파일 업로드 (기존 파일 덮어쓰기)
        amazonS3.putObject(new PutObjectRequest(bucket, key, file.getInputStream(), null)
                .withCannedAcl(CannedAccessControlList.PublicRead));

        // 업로드된 파일의 URL 반환
        return amazonS3.getUrl(bucket, key).toString();
    }
}
