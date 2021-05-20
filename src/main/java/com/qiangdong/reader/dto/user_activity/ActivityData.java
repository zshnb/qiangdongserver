package com.qiangdong.reader.dto.user_activity;

import java.io.Serializable;

public class ActivityData implements Serializable {
	private CreateActivity createActivity = new CreateActivity();

	private AgreeActivity agreeActivity = new AgreeActivity();

	private AgainstActivity againstActivity = new AgainstActivity();

	private ShareActivity shareActivity = new ShareActivity();

	public CreateActivity getCreateActivity() {
		return createActivity;
	}

	public void setCreateActivity(CreateActivity createActivity) {
		this.createActivity = createActivity;
	}

	public AgreeActivity getAgreeActivity() {
		return agreeActivity;
	}

	public void setAgreeActivity(AgreeActivity agreeActivity) {
		this.agreeActivity = agreeActivity;
	}

	public AgainstActivity getAgainstActivity() {
		return againstActivity;
	}

	public void setAgainstActivity(AgainstActivity againstActivity) {
		this.againstActivity = againstActivity;
	}

	public ShareActivity getShareActivity() {
		return shareActivity;
	}

	public void setShareActivity(ShareActivity shareActivity) {
		this.shareActivity = shareActivity;
	}
}
