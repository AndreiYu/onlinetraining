package com.yushkev.onlinetraining.service;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import javax.servlet.http.Part;

import com.yushkev.onlinetraining.constant.GeneralConstant;
import com.yushkev.onlinetraining.content.RequestContent;
import com.yushkev.onlinetraining.resource.ConfigurationManager;

/* contains only static methods, so no need to create new instances */

public class AvatarImgService {
	
	private AvatarImgService() {
		
	}
		
    /**
     * Creates new filename for saving.
     */
	
	private static String defineFileName(Part part, String entityId) {
		String newFileName = null;
			String fileExtention = getImageExtention(part);
		    newFileName = part.getName().equals(GeneralConstant.AVATAR_USER_IMG) ? 
		    		GeneralConstant.AVATAR_USER_IMG.concat("_" + (entityId)) : 
		    			GeneralConstant.AVATAR_COURSE_IMG.concat("_" + (entityId));
			newFileName =  newFileName.concat(fileExtention);
		
		return newFileName;
  }
    
    public static String getImageExtention(Part part) {
    	String fileExtention = null;
    	if (part != null) {
	        String contentDisposition = part.getHeader("content-disposition");
	        String[] items = contentDisposition.split(";");
	        for (String s : items) {
	            if (s.trim().startsWith("filename")) {
	            	fileExtention = s.substring(s.indexOf("."), s.length() - 1);
	            }
	        }
    	}
		return fileExtention;
    }
    
    
    /**
     * Saves file in the specified directory.
     * @throws IOException 
     */
    public static String uploadImage (RequestContent requestContent, String entityId) throws IOException, UncheckedIOException {
   		
    	String imgDir = ConfigurationManager.getProperty("path.dir.image");
		String fullPath = (String) requestContent.getContextAttribute(GeneralConstant.SERVLET_CONTEXT_ATTR_REAL_PATH) + imgDir + File.separator; //by default returns directory where default noname avatars will be placed
		Map<String, Part> allParts = (Map<String, Part>) requestContent.getAllParts();
    	Part part = allParts.getOrDefault(GeneralConstant.AVATAR_USER_IMG, allParts.get(GeneralConstant.AVATAR_COURSE_IMG));
/*    	if image was loaded*/
    	if (part != null && part.getSize() != 0) {
    		Path filePath = Paths.get(fullPath);
/*	create directories if not created yet  */    		
        	if (!Files.exists(filePath)) {
	        	Files.createDirectories(filePath); 
	        }
    		String partName = defineFileName(part, entityId);
    		fullPath += partName;
/*	delete previously uploaded files regardless of extensions  */
	        Files.list(filePath).filter(p -> p.toString()
				.contains(partName.substring(0, partName.lastIndexOf(".")))).forEach((p) -> {
		    try {
					Files.deleteIfExists(p);
			} catch (IOException e) {
					throw new UncheckedIOException(e);
			}
			});
	        part.write(fullPath); 	
    	}
/*	returns name of filename  to save in DB (if present) or empty String*/
    	String fileNameToSave = fullPath.substring(fullPath.indexOf(imgDir) + imgDir.length() + 1);
    	return fileNameToSave;
    }
    
//    .replace(File.separator, "/")

    
   
//    public static String getFilePath (String realPath, String userLogin) {
//		Path filePath = null;
//    	String userAvatarName = userLogin != null ? GeneralConstant.AVATAR_USER_IMG.concat("_" + (userLogin)) : 
//    		GeneralConstant.AVATAR_USER_IMG.concat("_default");
//
//
//    	String	fileSaveDir = realPath + ConfigurationManager.getProperty("path.dir.image");
//
//    		Path defaultFilePath = Paths.get(fileSaveDir + File.separator + GeneralConstant.AVATAR_USER_IMG.concat("_default") + ".png");
//    		try {
//				filePath = Files.find(Paths.get(fileSaveDir), Integer.MAX_VALUE,
//				       	(path, basicFileAttributes) -> path.toFile().getName()
//				       	.contains(userAvatarName)).findAny().orElse(defaultFilePath);
//			} catch (IOException e) {
//				filePath = defaultFilePath;
//			}
//
//    	return filePath.toString();	
//    }
    
    
}
