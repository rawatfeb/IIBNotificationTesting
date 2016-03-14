package com.rwt.iib.testing_framework;

import static com.rwt.constant.Constants.SAMPLE_NOTIFICATION_DIRECTORY;
import static com.rwt.constant.Constants.TEST_CASES_DIRECTORY;
import static com.rwt.constant.Constants.NotificationsType.BILLING;
import static com.rwt.constant.Constants.NotificationsType.COLLAPSE;
import static com.rwt.constant.Constants.NotificationsType.GROUPING;
import static com.rwt.constant.Constants.NotificationsType.PERSON;
import static com.rwt.constant.Constants.NotificationsType.POLICY;
import static com.rwt.constant.Constants.NotificationsType.UNMERGE;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import com.rwt.beans.Environment;
import com.rwt.beans.IdentificationObject;
import com.rwt.constant.Constants.NotificationsType;
import com.rwt.mapping_reader.CSVReader;
import com.rwt.mapping_reader.PublishNotificationMappingReader;
import com.rwt.util.EnvironmentParserUtil;
import com.rwt.util.Utils;

public class IIBTest {

	private String envNameInHermesConfig = "IIB_AIT2";
	boolean DEBUG;
	String cwd;
	String testCasesDir;
	String sampleNotificatificationsDir;
	private NotificationsType notificationType;
	private String testCaseName;

	public static void main(String[] args) throws Exception {
		// java IIBTest -e:IIB_AIT2
		// java IIBTest -e:IIB_AIT2 -v -n:Person
		// -t:Person_BW_Attributes_TestCase3.csv

		IIBTest iIBTest = new IIBTest();
		// iIBTest.setDEBUG(true);
		iIBTest.parseArguments(args);
		iIBTest.init();
		iIBTest.execute();

	}

	private void init() throws IOException {
		try {
			cwd = Paths.get(".").toAbsolutePath().normalize().toString();
			if (DEBUG) {
				System.out.println(
						cwd + " is the path where " + TEST_CASES_DIRECTORY + " and " + SAMPLE_NOTIFICATION_DIRECTORY
								+ " directories would get created if not already present for further use");
			}

			testCasesDir = cwd + File.separator + TEST_CASES_DIRECTORY;

			if (!Paths.get(testCasesDir).toFile().exists()) {
				Paths.get(testCasesDir).toFile().mkdirs();
			} else if (Paths.get(testCasesDir).toFile().isFile()) {
				throw new RuntimeException(
						testCasesDir + " is a file. Please Delete it if not required and create the directory");
			}

			if (!Paths.get(testCasesDir + File.separator + PERSON).toFile().exists()) {
				Paths.get(testCasesDir + File.separator + PERSON).toFile().mkdirs();
			}
			if (!Paths.get(testCasesDir + File.separator + POLICY).toFile().exists()) {
				Paths.get(testCasesDir + File.separator + POLICY).toFile().mkdirs();
			}
			if (!Paths.get(testCasesDir + File.separator + BILLING).toFile().exists()) {
				Paths.get(testCasesDir + File.separator + BILLING).toFile().mkdirs();
			}
			if (!Paths.get(testCasesDir + File.separator + GROUPING).toFile().exists()) {
				Paths.get(testCasesDir + File.separator + GROUPING).toFile().mkdirs();
			}
			if (!Paths.get(testCasesDir + File.separator + COLLAPSE).toFile().exists()) {
				Paths.get(testCasesDir + File.separator + COLLAPSE).toFile().mkdirs();
			}
			if (!Paths.get(testCasesDir + File.separator + UNMERGE).toFile().exists()) {
				Paths.get(testCasesDir + File.separator + UNMERGE).toFile().mkdirs();
			}

			sampleNotificatificationsDir = cwd + File.separator + SAMPLE_NOTIFICATION_DIRECTORY;
			if (!Paths.get(sampleNotificatificationsDir).toFile().exists()) {
				Paths.get(sampleNotificatificationsDir).toFile().mkdirs();
			} else if (Paths.get(sampleNotificatificationsDir).toFile().isFile()) {
				throw new RuntimeException(
						testCasesDir + " is a file. Please Delete it if not required and create the directory");
			}

			if (!Paths.get(sampleNotificatificationsDir + File.separator + PERSON).toFile().exists()) {
				Paths.get(sampleNotificatificationsDir + File.separator + PERSON).toFile().mkdirs();
			}
			if (!Paths.get(sampleNotificatificationsDir + File.separator + POLICY).toFile().exists()) {
				Paths.get(sampleNotificatificationsDir + File.separator + POLICY).toFile().mkdirs();
			}
			if (!Paths.get(sampleNotificatificationsDir + File.separator + BILLING).toFile().exists()) {
				Paths.get(sampleNotificatificationsDir + File.separator + BILLING).toFile().mkdirs();
			}
			if (!Paths.get(sampleNotificatificationsDir + File.separator + GROUPING).toFile().exists()) {
				Paths.get(sampleNotificatificationsDir + File.separator + GROUPING).toFile().mkdirs();
			}
			if (!Paths.get(sampleNotificatificationsDir + File.separator + COLLAPSE).toFile().exists()) {
				Paths.get(sampleNotificatificationsDir + File.separator + COLLAPSE).toFile().mkdirs();
			}
			if (!Paths.get(sampleNotificatificationsDir + File.separator + UNMERGE).toFile().exists()) {
				Paths.get(sampleNotificatificationsDir + File.separator + UNMERGE).toFile().mkdirs();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void parseArguments(String[] args) {
		for (String arg : args) {
			String[] tokens = arg.split(":");
			switch (tokens[0]) {
			case "-e":
				this.envNameInHermesConfig = tokens[1];
				break;
			case "-n":
				try {
					this.notificationType = NotificationsType.valueOf(tokens[1].toUpperCase());
				} catch (IllegalArgumentException e) {
					System.out.println(
							"Please make sure you have supplied correct NotificationsType. Available NotificationsType are as follows:"
									+ Arrays.asList(NotificationsType.values()));
					throw e;
				}
				break;

			case "-t":
				this.testCaseName = tokens[1];
				break;

			case "-v":
				this.DEBUG = true;
				break;
			}
		}
	}

	private void execute() throws Exception {
		Environment env = EnvironmentParserUtil.getEnvironment(envNameInHermesConfig);
		if (null != notificationType) {
			execute0(env, notificationType);
		} else {

			for (NotificationsType notificationType : NotificationsType.values()) {
				execute0(env, notificationType);
			}
		}
	}

	public void TestMatch() throws Exception {
		String envNameInHermesConfig = "IIB_AIT2";
		Environment env = EnvironmentParserUtil.getEnvironment(envNameInHermesConfig);
		MQGateway mQConnectionUtil = null;
		try {
			mQConnectionUtil = new MQGateway(env);
			mQConnectionUtil.setDEBUG(DEBUG);
			// mQConnectionUtil.putMessageToNotificationQueue(pathOfSampleNotification,
			// identificationObjectList);
			Thread.sleep(500);
			List<IdentificationObject> identificationObjectList = Utils.createSampleIdentificationObjectList2();
			mQConnectionUtil.readMessageIdentificationNo(identificationObjectList);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mQConnectionUtil.closeQManager();
		}

	}

	private void execute0(Environment env, NotificationsType notificationType) throws Exception {
		String notificationTestCaseDir = getTestCasesDir() + File.separator + notificationType;
		PublishNotificationMappingReader publishNotificationMappingReader = new CSVReader();
		if (null != testCaseName && !testCaseName.isEmpty()) {
			notificationTestCaseDir = notificationTestCaseDir + File.separator + testCaseName;
		}
		List<IdentificationObject> identificationObjectList = publishNotificationMappingReader
				.read(notificationTestCaseDir);
		String pathOfSampleNotification = getSampleNotificatificationsDir() + File.separator + notificationType;
		System.out.println("Notifications Directory=" + pathOfSampleNotification);
		if (!identificationObjectList.isEmpty()) {
			execute00(env, pathOfSampleNotification, identificationObjectList);
		}
	}

	private void execute00(Environment env, String pathOfSampleNotification,
			List<IdentificationObject> identificationObjectList) throws Exception {
		MQGateway mQGateway = null;
		try {
			mQGateway = new MQGateway(env);
			mQGateway.setDEBUG(DEBUG);
			mQGateway.putMessageToNotificationQueue(pathOfSampleNotification, identificationObjectList);
			Thread.sleep(500);
			mQGateway.readMessageIdentificationNo(identificationObjectList);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mQGateway.closeQManager();
		}
	}

	public boolean isDEBUG() {
		return DEBUG;
	}

	public void setDEBUG(boolean dEBUG) {
		DEBUG = dEBUG;
	}

	public String getCwd() {
		return cwd;
	}

	public void setCwd(String cwd) {
		this.cwd = cwd;
	}

	public String getTestCasesDir() {
		return testCasesDir;
	}

	public void setTestCasesDir(String testCasesDir) {
		this.testCasesDir = testCasesDir;
	}

	public String getSampleNotificatificationsDir() {
		return sampleNotificatificationsDir;
	}

	public void setSampleNotificatificationsDir(String sampleNotificatificationsDir) {
		this.sampleNotificatificationsDir = sampleNotificatificationsDir;
	}

}
