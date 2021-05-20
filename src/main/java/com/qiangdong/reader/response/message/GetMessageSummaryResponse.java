package com.qiangdong.reader.response.message;

public class GetMessageSummaryResponse {
	private int systemMessageCount = 0;
	private int agreeActivityMessageCount = 0;
	private int mentionMessageCount = 0;
	private int followMessageCount = 0;

	public int getSystemMessageCount() {
		return systemMessageCount;
	}

	public void setSystemMessageCount(int systemMessageCount) {
		this.systemMessageCount = systemMessageCount;
	}

	public int getAgreeActivityMessageCount() {
		return agreeActivityMessageCount;
	}

	public void setAgreeActivityMessageCount(int agreeActivityMessageCount) {
		this.agreeActivityMessageCount = agreeActivityMessageCount;
	}

	public int getMentionMessageCount() {
		return mentionMessageCount;
	}

	public void setMentionMessageCount(int mentionMessageCount) {
		this.mentionMessageCount = mentionMessageCount;
	}

	public int getFollowMessageCount() {
		return followMessageCount;
	}

	public void setFollowMessageCount(int followMessageCount) {
		this.followMessageCount = followMessageCount;
	}
}
