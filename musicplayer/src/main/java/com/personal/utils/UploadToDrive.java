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
            if(fileType.equals(FileTypeEnum.GENRE_IMAGE.name)) {
            	targetFolder = FolderTypeEnum.GENRE_IMAGE_FOLDER.name;
            } else if(fileType.equals(FileTypeEnum.ARTIST_IMAGE.name)){
            	targetFolder = FolderTypeEnum.ARTIST_IMAGE_FOLDER.name;
            } else if(fileType.equals(FileTypeEnum.ALBUM_IMAGE.name)){
            	targetFolder = FolderTypeEnum.ALBUM_IMAGE_FOLDER.name;
            } else if(fileType.equals(FileTypeEnum.SONG_IMAGE.name)){
            	targetFolder = FolderTypeEnum.SONG_IMAGE_FOLDER.name;
            } else if(fileType.equals(FileTypeEnum.USER_IMAGE.name)){
            	targetFolder = FolderTypeEnum.USER_IMAGE_FOLDER.name;
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
        
        if(fileType.equals(FileTypeEnum.GENRE_IMAGE.name)) {
        	fileMetadata.setName(FolderTypeEnum.GENRE_IMAGE_FOLDER.name);
        } else if(fileType.equals(FileTypeEnum.ARTIST_IMAGE.name)){
        	fileMetadata.setName(FolderTypeEnum.ARTIST_IMAGE_FOLDER.name);
        } else if(fileType.equals(FileTypeEnum.ALBUM_IMAGE.name)){
        	fileMetadata.setName(FolderTypeEnum.ALBUM_IMAGE_FOLDER.name);
        } else if(fileType.equals(FileTypeEnum.SONG_IMAGE.name)){
        	fileMetadata.setName(FolderTypeEnum.SONG_IMAGE_FOLDER.name);
        } else if(fileType.equals(FileTypeEnum.USER_IMAGE.name)){
        	fileMetadata.setName(FolderTypeEnum.USER_IMAGE_FOLDER.name);
        }

        fileMetadata.setMimeType("application/vnd.google-apps.folder");

        File file = driveManager.getService().files().create(fileMetadata).setFields("id").execute();
        folderId = file.getId();
        return folderId;
    }

    public String uploadImageFile(MultipartFile fileUpload, String fileType, String fileName){
        try {
        	String folderId = getFolder(fileType);
            if (fileUpload != null) {
                File file = new File();
                file.setParents(Collections.singletonList(folderId));
                file.setName(fileName);
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
    	String genreFolderId = "1caoxL3s_6YzuQD4drj-SPxiTN3ZBnmlJ";
    	String artistFolderId = "1onQIU34Gi1Guq0pn3yMDAqiO77t5oBV-";
    	String albumFolderId = "1_DJzxp-RGRBqvdXdL6gfvoQ-5MwbWW5r";
    	String songFolderId = "1TcHCabvT6X2qttbqs-ddN4PvHfzzsCW7";
    	
    	String fileId = "1Z24WnT1aCNDEki89cKTdqw-kepmHt7gD";
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
    		    .setEmailAddress("aptech.sem4.group2@gmail.com");
    	driveManager.getService().permissions().create(genreFolderId, userPermission)
        .setFields("id")
        .queue(batch, callback);
    	batch.execute();
    }
}
