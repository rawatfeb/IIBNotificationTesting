package com.rwt.beans;

import static com.rwt.constant.Constants.ECN;
import static com.rwt.constant.Constants.MDM_PUBLISH_BILLING_ECN_VALUE_XPATH;
import static com.rwt.constant.Constants.MDM_PUBLISH_BILLING_ECN_XPATH;
import static com.rwt.constant.Constants.MDM_PUBLISH_COLLAPSE_ECN_VALUE_XPATH;
import static com.rwt.constant.Constants.MDM_PUBLISH_COLLAPSE_ECN_XPATH;
import static com.rwt.constant.Constants.MDM_PUBLISH_CUSTOMER_ECN_VALUE_XPATH;
import static com.rwt.constant.Constants.MDM_PUBLISH_GROUPING_ECN_VALUE_XPATH;
import static com.rwt.constant.Constants.MDM_PUBLISH_GROUPING_ECN_XPATH;
import static com.rwt.constant.Constants.MDM_PUBLISH_POLICY_NUMBER_XPATH;
import static com.rwt.constant.Constants.MDM_PUBLISH_UNMERGE_ECN_VALUE_XPATH;
import static com.rwt.constant.Constants.MDM_PUBLISH_UNMERGE_ECN_XPATH;

import java.util.LinkedList;
import java.util.List;

import com.rwt.constant.Constants.NotificationBobj;

public class IdentificationObject {
	private NotificationBobj notificationType;
	private String identificationNumber;
	private String mdmPublishXPath;
	private String mdmNotificationXPath;
	private String value;

	List<String> xPaths = new LinkedList<String>();
	List<String> expectedValues = new LinkedList<String>();

	public NotificationBobj getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(NotificationBobj notificationType) {
		this.notificationType = notificationType;
		switch (notificationType) {
		case POLICY_NOTIFICATION:
			xPaths.add(MDM_PUBLISH_POLICY_NUMBER_XPATH);
			xPaths.add(mdmPublishXPath);
			expectedValues.add(identificationNumber);
			expectedValues.add(value);
			break;
		case PERSON_NOTIFICATION:
			xPaths.add(MDM_PUBLISH_CUSTOMER_ECN_VALUE_XPATH);
			xPaths.add(MDM_PUBLISH_COLLAPSE_ECN_XPATH);
			xPaths.add(mdmPublishXPath);
			expectedValues.add(identificationNumber);
			expectedValues.add(ECN);
			expectedValues.add(value);
			break;
		case COLLAPSE_NOTIFICATION:
			xPaths.add(MDM_PUBLISH_COLLAPSE_ECN_VALUE_XPATH);
			xPaths.add(MDM_PUBLISH_COLLAPSE_ECN_XPATH);
			xPaths.add(mdmPublishXPath);
			expectedValues.add(identificationNumber);
			expectedValues.add(ECN);
			expectedValues.add(value);
			break;
		case GROUPING_NOTIFICATION:
			xPaths.add(MDM_PUBLISH_GROUPING_ECN_VALUE_XPATH);
			xPaths.add(MDM_PUBLISH_GROUPING_ECN_XPATH);
			xPaths.add(mdmPublishXPath);
			expectedValues.add(identificationNumber);
			expectedValues.add(ECN);
			expectedValues.add(value);
			break;
		case BILLING_NOTIFICATION:
			xPaths.add(MDM_PUBLISH_BILLING_ECN_VALUE_XPATH);
			xPaths.add(MDM_PUBLISH_BILLING_ECN_XPATH);
			xPaths.add(mdmPublishXPath);
			expectedValues.add(identificationNumber);
			expectedValues.add(ECN);
			expectedValues.add(value);
			break;
		case UNMERGE_NOTIFICATION:
			xPaths.add(MDM_PUBLISH_UNMERGE_ECN_VALUE_XPATH);
			xPaths.add(MDM_PUBLISH_UNMERGE_ECN_XPATH);
			xPaths.add(mdmPublishXPath);
			expectedValues.add(identificationNumber);
			expectedValues.add(ECN);
			expectedValues.add(value);
			break;
		}
	}

	public String getIdentificationNumber() {
		return identificationNumber;
	}

	public void setIdentificationNumber(String identificationNumber) {
		this.identificationNumber = identificationNumber;
	}

	public String getMdmPublishXPath() {
		return mdmPublishXPath;
	}

	public void setMdmPublishXPath(String mdmPublishXPath) {
		this.mdmPublishXPath = mdmPublishXPath;
	}

	public String getMdmNotificationXPath() {
		return mdmNotificationXPath;
	}

	public void setMdmNotificationXPath(String mdmNotificationXPath) {
		this.mdmNotificationXPath = mdmNotificationXPath;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "IdentificationObject [notificationType=" + notificationType.toString() + ", identificationNumber="
				+ identificationNumber + ", mdmPublishXPath=" + mdmPublishXPath + ", mdmNotificationXPath="
				+ mdmNotificationXPath + ", value=" + value + "]";
	}

	public List<String> getxPaths() {
		return xPaths;
	}

	public void setxPaths(List<String> xPaths) {
		this.xPaths = xPaths;
	}

	public List<String> getExpectedValues() {
		return expectedValues;
	}

	public void setExpectedValues(List<String> expectedValues) {
		this.expectedValues = expectedValues;
	}

}
