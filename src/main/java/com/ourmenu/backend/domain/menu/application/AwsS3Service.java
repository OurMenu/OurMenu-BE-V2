package com.ourmenu.backend.domain.menu.application;

import com.ourmenu.backend.domain.menu.exception.S3DeleteFailureException;
import com.ourmenu.backend.domain.menu.exception.S3UploadFailureException;
import com.ourmenu.backend.domain.menu.util.FileUtil;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Utilities;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Slf4j
@RequiredArgsConstructor
@Component
public class AwsS3Service {

    private final S3AsyncClient s3AsyncClient;

    @Value("${spring.cloud.aws.credentials.bucket}")
    private String bucketName;

    /**
     * 파일 업로드
     *
     * @param multipartFile
     * @return 파일 url
     */
    public String uploadFileAsync(MultipartFile multipartFile) {

        try {
            String name = FileUtil.buildFileName(multipartFile.getOriginalFilename());
            PutObjectRequest objectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(name)
                    .build();
            AsyncRequestBody asyncRequestBody = AsyncRequestBody.fromBytes(multipartFile.getBytes());
            return s3AsyncClient.putObject(objectRequest, asyncRequestBody)
                    .thenApply(resp -> getS3Url(name))
                    .exceptionally(ex -> {
                        throw new S3UploadFailureException();
                    })
                    .join();

        } catch (IOException e) {
            throw new S3UploadFailureException();
        }
    }

    public List<String> uploadFilesAsync(List<MultipartFile> multipartFiles) {
        return multipartFiles.stream().map(this::uploadFileAsync).toList();
    }

    public void deleteFileAsync(String fileUrl) {
        try {
            String s3UrlKey = getS3UrlKey(fileUrl);

            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(s3UrlKey)
                    .build();

            s3AsyncClient.deleteObject(deleteObjectRequest)
                    .exceptionally(ex -> {
                        throw new S3DeleteFailureException();
                    })
                    .join();
        } catch (Exception e) {
            throw new S3DeleteFailureException();
        }
    }


    private String getS3Url(String fileName) {
        S3Utilities s3Utilities = s3AsyncClient.utilities();
        return s3Utilities.getUrl(GetUrlRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build()).toExternalForm();
    }

    private String getS3UrlKey(String s3Url) {
        s3Url = URLDecoder.decode(s3Url, StandardCharsets.UTF_8);
        String[] pathParts = s3Url.split("/", 4);
        return pathParts[3];
    }
}