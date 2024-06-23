package org.example.invoice.service.domain.extractor.amazon;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Slf4j
@Component
public class AmazonS3Utils {

    public void uploadFile(String folderName, String fileName, byte[] file, String bucketName) {
        log.info("Uploading a file to Amazon S3, folder name: {}, file name: {}, bucket name: {}", folderName, fileName, bucketName);
        try (S3Client client = S3Client.builder().build()) {
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(folderName + "/" + fileName)
                    .build();

            client.putObject(request, RequestBody.fromBytes(file));
        }
    }

    public void deleteFile(String folderName, String fileName, String bucketName) {
        log.info("Deleting a file from Amazon S3, folder name: {}, file name: {}, bucket name: {}", folderName, fileName, bucketName);
        try (S3Client client = S3Client.builder().build()) {
            DeleteObjectRequest request = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(folderName + "/" + fileName)
                    .build();

            client.deleteObject(request);
        }
    }
}
