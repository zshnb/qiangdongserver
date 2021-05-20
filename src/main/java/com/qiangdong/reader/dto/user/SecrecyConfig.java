package com.qiangdong.reader.dto.user;

import com.qiangdong.reader.enums.user.SecrecyConfigUserPermissionTypeEnum;

public class SecrecyConfig {
	private boolean enableAddressBookRecommend = false;
	private boolean enableSearchByMobile = false;
	private SecrecyConfigUserPermissionTypeEnum watchMyFollow = SecrecyConfigUserPermissionTypeEnum.ALL;
	private SecrecyConfigUserPermissionTypeEnum watchMyFans = SecrecyConfigUserPermissionTypeEnum.ALL;

	public boolean isEnableAddressBookRecommend() {
		return enableAddressBookRecommend;
	}

	public void setEnableAddressBookRecommend(boolean enableAddressBookRecommend) {
		this.enableAddressBookRecommend = enableAddressBookRecommend;
	}

	public boolean isEnableSearchByMobile() {
		return enableSearchByMobile;
	}

	public void setEnableSearchByMobile(boolean enableSearchByMobile) {
		this.enableSearchByMobile = enableSearchByMobile;
	}

	public SecrecyConfigUserPermissionTypeEnum getWatchMyFollow() {
		return watchMyFollow;
	}

	public void setWatchMyFollow(SecrecyConfigUserPermissionTypeEnum watchMyFollow) {
		this.watchMyFollow = watchMyFollow;
	}

	public SecrecyConfigUserPermissionTypeEnum getWatchMyFans() {
		return watchMyFans;
	}

	public void setWatchMyFans(SecrecyConfigUserPermissionTypeEnum watchMyFans) {
		this.watchMyFans = watchMyFans;
	}
}
