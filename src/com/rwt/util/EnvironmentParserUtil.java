package com.rwt.util;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.rwt.beans.Environment;
import com.rwt.constant.Constants;

public class EnvironmentParserUtil {

	public static void main(String[] args) throws Exception {
		System.out.println(getEnvironment("IIB_AIT2"));
	}

	public static Environment getEnvironment(String environmentName) throws Exception {
		InputStream ins = null;
		try {
			ins = EnvironmentParserUtil.class.getClassLoader().getResourceAsStream(Constants.ENVIRONMENT_CONFIG_FILE);
			if (null == ins) {
				System.out.println("input stream is null");
				throw new RuntimeException("Seems like could not able to locate ENVIRONMENT_CONFIG_FILE "
						+ Constants.ENVIRONMENT_CONFIG_FILE + " in classpath please make sure file exist in classpath, "
						+ EnvironmentParserUtil.class.getClassLoader().getResource(Constants.ENVIRONMENT_CONFIG_FILE));
			}

		} catch (Exception e) {
			System.out.println("Exception while loading  ENVIRONMENT_CONFIG_FILE using "
					+ EnvironmentParserUtil.class.getClassLoader());
			throw e;
		}
		return getEnvironment(ins, environmentName);
	}

	public static Environment getEnvironment(InputStream ins, String environmentName) throws Exception {
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		Environment env = null;
		try {
			builder = builderFactory.newDocumentBuilder();
			Document document = builder.parse(ins);
			NodeList nl = document.getElementsByTagName("session");

			env = new Environment();
			env.setEnvironmentName(environmentName);

			for (int i = 0; i < nl.getLength(); i++) {
				Element e = (Element) nl.item(i);
				// System.out.println(e.getAttribute("id"));
				if (e.getAttribute("id").equals(environmentName)) {
					Element connection = (Element) e.getParentNode();
					env.setUserID(connection.getAttribute("username"));
					env.setPassword(connection.getAttribute("password"));
					NodeList factoryChildren = connection.getParentNode().getChildNodes();
					for (int j = 0; j < factoryChildren.getLength(); j++) {
						if (factoryChildren.item(j).getNodeName().equals("provider")) {
							NodeList providerChildren = factoryChildren.item(j).getChildNodes();
							for (int k = 0; k < providerChildren.getLength(); k++) {
								if (providerChildren.item(k).getNodeName().equals("properties")) {
									NodeList propertyList = providerChildren.item(k).getChildNodes();
									for (int l = 0; l < propertyList.getLength(); l++) {
										if (propertyList.item(l).getNodeName().equals("property")) {
											Element property = (Element) propertyList.item(l);
											String value = property.getAttribute("value");
											switch (property.getAttribute("name")) {
											case "channel":
												env.setChannel(value);
												break;
											case "hostName":
												env.setHostName(value);
												break;
											case "port":
												env.setPort(Integer.parseInt(value));
												break;
											case "queueManager":
												env.setQueueManager(value);
												break;
											}
										}
									}
								}
							}
						}
					}
				}
			}

		} catch (SAXException | IOException | ParserConfigurationException e) {
			System.out.println("Exception while getting the detail of " + environmentName);
			throw e;
		}

		return env;
	}
}
