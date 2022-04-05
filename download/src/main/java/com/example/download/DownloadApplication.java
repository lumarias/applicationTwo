package com.example.download;

import com.amazonaws.services.sqs.AmazonSQS;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import java.io.*;

@SpringBootApplication
public class DownloadApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(DownloadApplication.class, args);


		ReadFile archivo = new ReadFile();

		String bucket = "newbucketlarias";
		String key = "ejemplo.csv";

		Region region = Region.US_WEST_2;
		S3Client s3 = S3Client.builder().region(region).build();

		GetObjectRequest request = GetObjectRequest.builder()
				.bucket(bucket)
				.key(key)
				.build();

		ResponseInputStream<GetObjectResponse> inputStream = s3.getObject(request);

		String fileName = new File(key).getName();
		BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(key));

		byte[] buffer = new byte[4096];
		int bytesRead = -1;

		while ((bytesRead = inputStream.read(buffer)) != -1) {
			outputStream.write(buffer, 0, bytesRead);
		}

		inputStream.close();
		outputStream.close();

		archivo.read();

	}


}
