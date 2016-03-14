package com.rwt.test;

import java.nio.file.Paths;

public class Test {
	public static void main(String[] args) throws Exception {
		getCurrentWorkingDirectory();
	}

	public static void getCurrentWorkingDirectory() throws Exception {
		System.out.println(System.getProperty("user.dir"));
		System.out.println(new java.io.File(".").getCanonicalPath());
		System.out.println(Paths.get("").toAbsolutePath().toString());
		System.out.println(Paths.get(".").toAbsolutePath().normalize().toString());
	}
}
