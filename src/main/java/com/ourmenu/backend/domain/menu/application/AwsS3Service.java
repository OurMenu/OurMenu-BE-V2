package com.ourmenu.backend.domain.menu.application;

import com.ourmenu.backend.domain.menu.util.FileUtil;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Utilities;
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
    public String uploadLocalFileAsync(MultipartFile multipartFile) {

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
                        throw new RuntimeException("이미지 업로드중 문제가 발생하였습니다", ex);
                    })
                    .join();

        } catch (IOException e) {
            throw new RuntimeException("메뉴판 이미지 저장 중 문제가 발생하였습니다");
        }
    }

    private String getS3Url(String fileName) {
        S3Utilities s3Utilities = s3AsyncClient.utilities();
        return s3Utilities.getUrl(GetUrlRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build()).toExternalForm();
    }
}