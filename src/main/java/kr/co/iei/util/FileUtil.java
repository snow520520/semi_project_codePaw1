package kr.co.iei.util;

import java.io.File;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUtil {

	public String upload(String savepath, MultipartFile file) {
		String filename = file.getOriginalFilename();
		String onlyFilename = filename.substring(0,filename.lastIndexOf("."));
		String extention = filename.substring(filename.lastIndexOf("."));
		String filepath = null;
		int count = 0;
		while(true) {
			if(count == 0) {
				filepath = onlyFilename+extention;
			}else {
				filepath = onlyFilename+"_"+count+extention;
			}
			
			File checkFile = new File(savepath+filepath);
			if(!checkFile.exists()) {
				break;
			}
			count++;
		}
		return null;
	}
	
	
}
