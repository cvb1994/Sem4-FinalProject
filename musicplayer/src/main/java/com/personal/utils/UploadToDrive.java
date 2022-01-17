package com.personal.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.google.api.client.googleapis.batch.BatchRequest;
import com.google.api.client.googleapis.batch.json.JsonBatchCallback;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.InputStreamContent;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.Permission;
import com.personal.common.FileTypeEnum;
import com.personal.common.FolderTypeEnum;
import com.personal.serviceImp.DriveManager;

@Component
public class UploadToDrive {
	@Autowired
    private DriveManager driveManager;
	
	public String searchFolder(String fileType) throws IOException, GeneralSecurityException {
        String pageToken = null;
        String folderId = null;
        do {
            FileList result = driveManager.getService().files().list().setQ("mimeType = 'application/vnd.google-apps.folder'")
                    .setSpaces("drive").setFields("nextPageToken, files(id, name)").setPageToken(pageToken).execute();
            String targetFolder = null;
            if(fileType.equals(FileTypeEnum.AUDIO.name)) {
            	targetFolder = FolderTypeEnum.AUDIO_FOLDER.name;
            } else {
//            	targetFolder = FolderTypeEnum.IMAGE_FOLDER.name;
            }

            for (File file : result.getFiles()) {
                if (file.getName().equalsIgnoreCase(targetFolder)) {
                    folderId = file.getId();
                    break;
                }
            }
            pageToken = result.getNextPageToken();
        } while (pageToken != null && folderId == null);
        return folderId;
    }

    public String getFolder(String fileType) throws IOException, GeneralSecurityException {
        String folderId = searchFolder(fileType);
        if (folderId != null)
            return folderId;

        File fileMetadata = new File();
        if(fileType.equals(FileTypeEnum.AUDIO.name)) {
        	fileMetadata.setName(FolderTypeEnum.AUDIO_FOLDER.name);
        } else {
//        	fileMetadata.setName(FolderTypeEnum.IMAGE_FOLDER.name);
        }
        fileMetadata.setMimeType("application/vnd.google-apps.folder");

        File file = driveManager.getService().files().create(fileMetadata).setFields("id").execute();
        folderId = file.getId();
        return folderId;
    }

    public String uploadFile(MultipartFile fileUpload, String fileType){
        try {
        	String folderId = getFolder(fileType);
            if (fileUpload != null) {
                File file = new File();
                file.setParents(Collections.singletonList(folderId));
                file.setName(fileUpload.getOriginalFilename());
                File uploadFile = driveManager.getService().files()
                        .create(file,
                                new InputStreamContent(fileUpload.getContentType(),
                                        new ByteArrayInputStream(fileUpload.getBytes())))
                        .setFields("id,webViewLink").execute();
                String fileUploadId = uploadFile.getId();
                String fileUploadUrl = "https://drive.google.com/uc?id=" + fileUploadId;
                return fileUploadUrl;
            }
        } catch (Exception e) {
            System.out.println("error");
        }
        return null;
    }
    
    
    //Cấp quyền truy cập cho tk khác 
    public void grantPermission() throws GeneralSecurityException, IOException {
    	String fileId = "1hm1WZghhYiyJ60wXl8j8IAlHDHR99H4j";
    	JsonBatchCallback<Permission> callback = new JsonBatchCallback<Permission>() {
    		  @Override
    		  public void onFailure(GoogleJsonError e,
    		                        HttpHeaders responseHeaders)
    		      throws IOException {
    		    // Handle error
    		    System.err.println(e.getMessage());
    		  }

    		  @Override
    		  public void onSuccess(Permission permission,
    		                        HttpHeaders responseHeaders)
    		      throws IOException {
    		    System.out.println("Permission ID: " + permission.getId());
    		  }
    		};
    	BatchRequest batch = driveManager.getService().batch();
    	Permission userPermission = new Permission()
    			.setType("user")
    		    .setRole("writer")
    		    .setEmailAddress("service.email.test123@gmail.com");
    	driveManager.getService().permissions().create(fileId, userPermission)
        .setFields("id")
        .queue(batch, callback);
    	batch.execute();
    }
}
