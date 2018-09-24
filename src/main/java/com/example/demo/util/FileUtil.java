package com.example.demo.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtil {

	
	public static String creatFile(String path, long fatherid, String content){
		FileWriter fw = null;
		
		String name = "sealPage_" + fatherid + ".txt";
		File file = new File(path + name);
		file.delete();
		try {
			file.createNewFile();
			fw = new FileWriter(file);
			
			fw.write(content);
			fw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return file.getName();
	}
	
}
