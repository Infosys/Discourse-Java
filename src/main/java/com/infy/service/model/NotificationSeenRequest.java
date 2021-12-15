/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.model;

import java.util.ArrayList;
import java.util.List;

public class NotificationSeenRequest {

	private List<Long> notificationIds = new ArrayList<>();

	public List<Long> getNotificationIds() {
		return notificationIds;
	}

	public void setNotificationIds(List<Long> notificationIds) {
		this.notificationIds = notificationIds;
	}

	@Override
	public String toString() {
		return "NotificationSeenRequest [notificationIds=" + notificationIds + "]";
	}

}
