package com.ourmenu.backend.domain.menu.application;

import com.ourmenu.backend.domain.menu.util.FileUtil;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

@Slf4j
@RequiredArgsConstructor
@Component
public class AwsS3Service {

    private final S3AsyncClient s3AsyncClient;

    @Value("${spring.cloud.aws.credentials.bucket}")
    private String bucketName;

    public CompletableFuture<PutObjectResponse> uploadLocalFileAsync(MultipartFile multipartFile) {

        try {
            String name = FileUtil.buildFileName(multipartFile.getOriginalFilename());
            PutObjectRequest objectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(name)
                    .build();
            AsyncRequestBody asyncRequestBody = AsyncRequestBody.fromBytes(multipartFile.getBytes());

            CompletableFuture<PutObjectResponse> response = s3AsyncClient.putObject(objectRequest, asyncRequestBody);

            return response.whenComplete((resp, ex) -> {
                if (ex != null) {
                    throw new RuntimeException("이미지 업로드중 문제가 발생하였습니다", ex);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}