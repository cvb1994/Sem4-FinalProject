package com.personal.utils;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.google.api.gax.paging.Page;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.Identity;
import com.google.cloud.Policy;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageClass;
import com.google.cloud.storage.StorageOptions;
import com.google.cloud.storage.StorageRoles;
import com.personal.common.FolderTypeEnum;

@Component
public class CloudStorageUtils {
	
	public Bucket findBucket(String bucketName) {
		Storage storage = StorageOptions.getDefaultInstance().getService();
	    Page<Bucket> buckets = storage.list();

	    for (Bucket bucket : buckets.iterateAll()) {
	      if(bucket.getName().equals(bucketName)) {
	    	  return bucket;
	      }
	    }
	    
	    StorageClass storageClass = StorageClass.COLDLINE;
	    String location = "ASIA-SOUTHEAST1";
	    Bucket bucket = storage.create(BucketInfo.newBuilder(bucketName)
	                    .setStorageClass(storageClass)
	                    .setLocation(location)
	                    .build());
	    
	    Policy originalPolicy = storage.getIamPolicy(bucketName);
	    storage.setIamPolicy(bucketName, originalPolicy.toBuilder()
			            .addIdentity(StorageRoles.objectViewer(), Identity.allUsers()) // All users can view
			            .build());
	    
		return bucket;
	}
	
	public String uploadObject(MultipartFile fileUpload, String fileName) {
//		Resource sourceFile = new ClassPathResource("finalproject-338303-035a753549c0.json");
//		Credentials credentials = null;
//		try {
//			InputStream inputStream = sourceFile.getInputStream();
//			credentials = GoogleCredentials.fromStream(inputStream);
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		
//		Storage storage = StorageOptions.newBuilder().setCredentials(credentials)
//				  .setProjectId("finalproject-338303").build().getService();
		Storage storage = StorageOptions.getDefaultInstance().getService();
		Bucket bucket = findBucket(FolderTypeEnum.AUDIO_FOLDER.name);
		
	    BlobId blobId = BlobId.of(bucket.getName(), fileName);
	    BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
	    String url = null;
	    try {
			url = storage.create(blobInfo, new ByteArrayInputStream(fileUpload.getBytes())).getMediaLink();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return url;
	}

}
