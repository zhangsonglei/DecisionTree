package com.decisiontree.id3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * 
 * Description: 
 * @author: Sonly
 * Company: HUST 
 * @date: 2017年5月1日上午10:18:19
 */
public class FileOperater {
	
	/**
	 * 
	 * @title: readFile
	 * Description: read data set file
	 * @return ArrayList<ArrayList<String>>
	 * @throws
	 */
	public static ArrayList<ArrayList<String>> readFile(String path) {
		String enCode = "utf8"; //file encoding
		ArrayList<ArrayList<String>> dataSet = new ArrayList<>();
		
		try {
			File file = new File(path);
			if(file.isFile() && file.exists()) {
				InputStreamReader reader = new InputStreamReader(new FileInputStream(file), enCode);
				BufferedReader bufferedReader = new BufferedReader(reader);
				String line = null;
				String[] strings = null; 
				while(null != (line = bufferedReader.readLine())) {
					strings = line.split("\\t");
					ArrayList<String> list = new ArrayList<>();
					
					for(String str : strings)
						list.add(str);
					dataSet.add(list);
				}
				
				reader.close();
			}else {
				System.out.println("Fail to find the file:" + path);
			}
		} catch (Exception e) {
			System.out.println("Error in reading file");
			e.printStackTrace();
		}
		
		return dataSet; 
	}

}
