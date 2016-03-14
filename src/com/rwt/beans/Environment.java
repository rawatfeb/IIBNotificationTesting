package com.rwt.beans;

import static com.rwt.constant.Constants.USER_ID;
import static com.rwt.constant.Constants.PASSWORD;;

public class Environment {

	private String hostName;
	private int port;
	private String channel;
	private String queueManager;
	private String environmentName;
	private String userID; 
	private String password;

	public String getUserID() {
		if (null == userID) {
			userID = USER_ID;
		}
		return userID;
	}

	
	public String getPassword() {
		if (null == password) {
			password = PASSWORD;
		}
		return password;
	}


	public int getPort() {
		return port;
	}


	public void setPort(int port) {
		this.port = port;
	}


	public String getChannel() {
		return channel;
	}


	public void setChannel(String channel) {
		this.channel = channel;
	}


	public String getQueueManager() {
		return queueManager;
	}


	public void setQueueManager(String queueManager) {
		this.queueManager = queueManager;
	}


	public String getEnvironmentName() {
		return environmentName;
	}


	public void setEnvironmentName(String environmentName) {
		this.environmentName = environmentName;
	}


	public void setUserID(String userID) {
		this.userID = userID;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getHostName() {
		return hostName;
	}


	public void setHostName(String hostName) {
		this.hostName = hostName;
	}


	@Override
	public String toString() {
		return "Environment [hostName=" + hostName + ", port=" + port + ", channel=" + channel + ", queueManager="
				+ queueManager + ", environmentName=" + environmentName + ", userID=" + userID + ", password="
				+ password + "]";
	}
}
