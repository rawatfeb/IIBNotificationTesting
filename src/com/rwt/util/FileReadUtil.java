package com.rwt.util;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileReadUtil {

	public static void main(String[] args) throws Exception {
		String message_dir = "D:/Rawat/SOA/Test Cases/V3 IIB Notification BW Testing/Notifications/V3";
		File[] listOfMessages = listMessages(message_dir);
		int count = 0;
		String message = readFile(listOfMessages[count].getAbsolutePath());
		System.out.println(message);
	}

	public static String readFile2(String path) throws IOException {
		Charset encoding = Charset.forName("UTF-8");
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);
		byte[] data = new byte[(int) file.length()]; // Files.readAllBytes(Paths.get(path));
		fis.read(data);
		fis.close();
		return new String(data, encoding);
	}

	public static String readFile(String path) throws IOException {
		Charset encoding = Charset.forName("UTF-8");
		return new String(Files.readAllBytes(Paths.get(path)), encoding);
	}

	public static File[] listMessages(String message_dir) {
		File messageDir = new File(message_dir);
		File[] listOfMessages = messageDir.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				return pathname.getAbsolutePath().endsWith(".xml") || pathname.getAbsolutePath().endsWith(".txt");
			}
		});
		return listOfMessages;
	}
}
