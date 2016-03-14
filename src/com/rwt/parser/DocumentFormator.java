package com.rwt.parser;

import static com.rwt.constant.Constants.ECN;
import static com.rwt.constant.Constants.INDENTIFICATION_NUMBER;
import static com.rwt.constant.Constants.INDENTIFICATION_VALUE;
import static com.rwt.constant.Constants.POLICY_NUMBER;
import static com.rwt.constant.Constants.PUB_BACKOUT_QUEUE;
import static com.rwt.constant.Constants.TCRM_PARTY_IDENTIFICATION;
import static com.rwt.constant.Constants.NotificationBobj.PERSON_NOTIFICATION;
import static com.rwt.constant.Constants.NotificationBobj.POLICY_NOTIFICATION;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.rwt.beans.IdentificationObject;
import com.rwt.constant.Constants.NotificationBobj;

public class DocumentFormator {

	private String path;
	private Document document;
	private NotificationBobj notificationType;

	public DocumentFormator() {
		super();
		loadDocument();
		setNotificationType();
	}

	public DocumentFormator(String path) {
		super();
		this.path = path;
		loadDocument();
		setNotificationType();
	}

	public DocumentFormator(byte[] bytesArray) {
		super();
		loadDocumentFromByteArray(bytesArray);
		setNotificationType();
	}

	private void setNotificationType() {
		// need enhancement, probably a different approach to get notification
		// type from the document
		NodeList elements = document.getElementsByTagName(PERSON_NOTIFICATION.toString());
		if (elements.getLength() != 0) {
			notificationType = PERSON_NOTIFICATION;
		} else {
			elements = document.getElementsByTagName(POLICY_NOTIFICATION.toString());
			if (elements.getLength() != 0) {
				notificationType = POLICY_NOTIFICATION;
			}
		}

	}

	public static void main(String[] args) throws Exception {
		// String path="D:/Rawat/SOA/Test Cases/V3 IIB Notification BW
		// Testing/Notifications/V3/PolicyNotification_AutoPolicy_Agent_type_Test.xml";
		// path = "D:/Rawat/SOA/Test Cases/V3 IIB Notification BW
		// Testing/Notifications/ID_414d51204d514e54414954333842202055d16f5620005c10.txt";
		DocumentFormator messageFormator = new DocumentFormator();
		System.out.println(messageFormator.getIdentificationNumber());
		messageFormator.setIdentificationNumber("Hello");
		System.out.println(messageFormator.getIdentificationNumber());

		String XPath = "//PersonNotificationBObj2/TCRMAdminContEquivBObj/TestElement";
		messageFormator.setValue(XPath, "Paksh");

		System.out.println(messageFormator.getDocumentAsString());
	}

	private void loadDocument() {
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		try {
			builder = builderFactory.newDocumentBuilder();
			document = builder.parse(new FileInputStream(path));
		} catch (SAXException | IOException | ParserConfigurationException e) {
		}
	}

	private void loadDocumentFromByteArray(byte[] byteArray) {
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		try {
			builder = builderFactory.newDocumentBuilder();
			InputStream inputStream = new ByteArrayInputStream(byteArray);
			document = builder.parse(inputStream);
		} catch (SAXException | IOException | ParserConfigurationException e) {
		}
	}

	public String getCharacterDataFromElement(Element e) {
		Node child = e.getFirstChild();
		if (child instanceof CharacterData) {
			CharacterData cd = (CharacterData) child;
			return cd.getData();
		}
		return "";
	}

	// PersonNotificationBObj/TCRMAdminContEquivBObj/AdminPartyId
	public void setValue(String XPath, String value) {
		String[] pth = XPath.split("/");
		String parent = "";
		for (String string : pth) {
			if (!string.trim().isEmpty()) {
				// System.out.println(string);
				NodeList nl = document.getElementsByTagName(string);
				if (nl.getLength() == 0) {
					Node node = document.createElement(string);
					NodeList pn = document.getElementsByTagName(parent); // verifying
																			// only
																			// one
																			// level
																			// up
					if (pn.getLength() != 0) {
						pn.item(0).appendChild(node);
					} else {
						document.getDocumentElement().appendChild(node);
					}
				}
			}
			parent = string;
		}

		// need to enhance here
		if (null != value && !value.isEmpty()) {
			int leafElementIndex = pth.length - 1;
			NodeList leafNodes = document.getElementsByTagName(pth[leafElementIndex]);
			leafNodes.item(0).setTextContent(value);
		}
	}

	private String parseNotification(boolean isSetID, String identificationNumber) {
		NodeList tCRMPartyIdentificationBObjs = document.getElementsByTagName(TCRM_PARTY_IDENTIFICATION);
		for (int i = 0; i < tCRMPartyIdentificationBObjs.getLength(); i++) {
			Node tCRMPartyIdentificationBObj = tCRMPartyIdentificationBObjs.item(i);
			NodeList nodes = tCRMPartyIdentificationBObj.getChildNodes();
			for (int j = 0; j < nodes.getLength(); j++) {
				Node node = nodes.item(j);
				if (node instanceof Element) {
					// a child element to process
					Element child = (Element) node;
					// System.out.println(child.getNodeName());
					String value = getCharacterDataFromElement(child);

					if (INDENTIFICATION_VALUE.equals(child.getNodeName()) && ECN.equals(value)) {
						NodeList tCRMPartyIdentificationBObjChildren = tCRMPartyIdentificationBObj.getChildNodes();

						for (int k = 0; k < tCRMPartyIdentificationBObjChildren.getLength(); k++) {
							Node n = tCRMPartyIdentificationBObjChildren.item(k);
							if (INDENTIFICATION_NUMBER.equals(n.getNodeName())) {
								if (isSetID) {
									n.setTextContent(identificationNumber);
								}
								return getCharacterDataFromElement((Element) n);
							}

						}
					}
				}
			}

		}

		return null;
	}

	private String parsePolicyNotification(boolean isSetID, String policyNumber) {
		NodeList policyNumbers = document.getElementsByTagName(POLICY_NUMBER);
		for (int i = 0; i < policyNumbers.getLength(); i++) {
			Node policyNumberNode = policyNumbers.item(i);
			if (policyNumberNode instanceof Element) {
				// a child element to process
				Element child = (Element) policyNumberNode;
				// System.out.println(child.getNodeName());
				if (isSetID) {
					child.setTextContent(policyNumber);
				}
				return getCharacterDataFromElement(child);
			}
		}

		return null;
	}

	public boolean matchNotification(List<IdentificationObject> identificationObjectList) throws Exception {
		boolean found = false;
		Iterator<IdentificationObject> itr = identificationObjectList.iterator();
		while (itr.hasNext()) {
			IdentificationObject identificationObject = itr.next();
			found = identificationObject.getIdentificationNumber().equals(getIdentificationNumber());
			if (found) {
				System.out.println("\n\n\n >>>>>>>>>>Found<<<<<<<<<<<< Notification in " + PUB_BACKOUT_QUEUE
						+ " Queue \n\n\n" + getDocumentAsString());
				System.out.println("\n\n\n Test Failed for =" + identificationObject + "\n\n\n");
				itr.remove();
			}
		}
		return found;
	}

	public String getDocumentAsString() throws TransformerException {
		DOMSource domSource = new DOMSource(document);
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		StringWriter sw = new StringWriter();
		StreamResult sr = new StreamResult(sw);
		transformer.transform(domSource, sr);
		return sw.toString();
	}

	public String getIdentificationNumber() {
		String id = null;
		switch (notificationType) {
		case POLICY_NOTIFICATION:
			id = parsePolicyNotification(false, null);
			break;
		default:
			id = parseNotification(false, null);
		}
		return id;
	}

	public void setIdentificationNumber(String identificationNumber) {
		switch (notificationType) {
		case POLICY_NOTIFICATION:
			parsePolicyNotification(true, identificationNumber);
			break;
		default:
			parseNotification(true, identificationNumber);
		}
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public NotificationBobj getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(NotificationBobj notificationType) {
		this.notificationType = notificationType;
	}
}