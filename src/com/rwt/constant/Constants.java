package com.rwt.constant;

public final class Constants {
	private Constants() {
	}

	public static final String USER_ID = "uswtlug";
	public static final String PASSWORD = "Farmers1";
	public static final String NOTIFICATION_QUEUE = "MDM.NOTIFICATION.XML";
	public static final String SUB_QUEUE = "MDM.SUB.XML";
	public static final String PUB_BACKOUT_QUEUE = "MDM.PUB.BKOUT.XML";
	public static final String NOTIFICATION_BACKOUT_QUEUE = "MDM.NOTIFICATION.BKOUT.XML";
	public static final String SFDC_QUEUE = "MDM.SFDC.XML";

	public static final String POLICY_NUMBER = "PolicyNumber";
	public static final String TCRM_PARTY_IDENTIFICATION = "TCRMPartyIdentificationBObj";
	public static final String INDENTIFICATION_VALUE = "IdentificationValue";
	public static final String INDENTIFICATION_NUMBER = "IdentificationNumber";

	public static final String ECN = "ECN";
	public static final String MDM_PUBLISH_CUSTOMER_ECN_XPATH = "//cus_info:MDMPublish/cus_info:customerNotification/cus_info:header/cus_info:systemReferences/cus:keyType";
	public static final String MDM_PUBLISH_CUSTOMER_ECN_VALUE_XPATH = "//cus_info:MDMPublish/cus_info:customerNotification/cus_info:header/cus_info:systemReferences/cus:keyValue";
	public static final String MDM_PUBLISH_COLLAPSE_ECN_XPATH = "//cus_info:MDMPublish/cus_info:collapseNotification/cus_info:header/cus_info:systemReferences/cus:keyType";
	public static final String MDM_PUBLISH_COLLAPSE_ECN_VALUE_XPATH = "//cus_info:MDMPublish/cus_info:collapseNotification/cus_info:header/cus_info:systemReferences/cus:keyValue";
	public static final String MDM_PUBLISH_UNMERGE_ECN_XPATH = "//cus_info:MDMPublish/cus_info:snapshotNotification/cus_info:header/cus_info:systemReferences/cus:keyType";
	public static final String MDM_PUBLISH_UNMERGE_ECN_VALUE_XPATH = "//cus_info:MDMPublish/cus_info:snapshotNotification/cus_info:header/cus_info:systemReferences/cus:keyValue";
	public static final String MDM_PUBLISH_GROUPING_ECN_XPATH = "//cus_info:MDMPublish/cus_info:groupingNotification/cus_info:header/cus_info:systemReferences/cus:keyType";
	public static final String MDM_PUBLISH_GROUPING_ECN_VALUE_XPATH = "//cus_info:MDMPublish/cus_info:groupingNotification/cus_info:header/cus_info:systemReferences/cus:keyValue";
	public static final String MDM_PUBLISH_BILLING_ECN_XPATH = "//cus_info:MDMPublish/cus_info:billingNotification/cus_info:header/cus_info:systemReferences/cus:keyType";
	public static final String MDM_PUBLISH_BILLING_ECN_VALUE_XPATH = "//cus_info:MDMPublish/cus_info:billingNotification/cus_info:header/cus_info:systemReferences/cus:keyValue";

	public static final String MDM_PUBLISH_POLICY_NUMBER_XPATH = "//cus_info:MDMPublish/cus_info:policyNotification/cus_info:header/cus_info:policyNumber/cus:policyNumber";

	public static final String CUS_INFO = "cus_info";
	public static final String CUS = "cus";
	public static final String CUS_INFO_NAMESPACE = "http://mdm.soa.farmersinsurance.com/CustomerInformationManagement";
	public static final String CUS_NAMESPACE = "http://mdm.soa.farmersinsurance.com/Customer";

	public static final String TEST_CASES_DIRECTORY = "TestCases";
	public static final String SAMPLE_NOTIFICATION_DIRECTORY = "Notifications";

	public static final String RESULT_FILE_NAME = "TestCaseResult.csv";
	public static final String COMMA = ",";

	public static enum NotificationBobj {
		PERSON_NOTIFICATION {
			@Override
			public String toString() {
				return "PersonNotificationBObj";
			}
		},
		POLICY_NOTIFICATION {
			@Override
			public String toString() {
				return "PolicyNotificationBObj";
			}
		},
		BILLING_NOTIFICATION {
			@Override
			public String toString() {
				return "BillingNotificationBObj";
			}
		},
		COLLAPSE_NOTIFICATION {
			@Override
			public String toString() {
				return "CollapseNotificationBObj";
			}
		},
		GROUPING_NOTIFICATION {
			@Override
			public String toString() {
				return "GroupingNotificationBObj";
			}
		},
		UNMERGE_NOTIFICATION {
			@Override
			public String toString() {
				return "UnmergeNotificationBObj";
			}
		}
	}

	public static enum NotificationsType {
		PERSON {
			@Override
			public String toString() {
				return "Person";
			}
		},
		POLICY {
			@Override
			public String toString() {
				return "Policy";
			}
		},
		BILLING {
			@Override
			public String toString() {
				return "Billing";
			}
		},
		COLLAPSE {
			@Override
			public String toString() {
				return "Collapse";
			}
		},
		GROUPING {
			@Override
			public String toString() {
				return "Grouping";
			}
		},
		UNMERGE {
			@Override
			public String toString() {
				return "Unmerge";
			}
		}
	}

	public static final String ENVIRONMENT_CONFIG_FILE = "hermes-config.xml";

}
