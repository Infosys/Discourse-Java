/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.WebpushConfig;
import com.infy.domain.Notification;
import com.infy.service.FireBaseMessagingService;

@Service
public class FireBaseMessagingServiceImpl implements FireBaseMessagingService {

	private final Logger log = LoggerFactory.getLogger(FireBaseMessagingServiceImpl.class);

	private final FirebaseMessaging firebaseMessaging;

	public FireBaseMessagingServiceImpl(FirebaseMessaging firebaseMessaging) {
		this.firebaseMessaging = firebaseMessaging;
	}

	@Override
	public String sendNotification(Notification notification, String token) {
		log.debug("Request to push notification : {} ", notification);
		try {
			com.google.firebase.messaging.Notification fireBaseNotification = com.google.firebase.messaging.Notification
					.builder().setTitle(notification.getTitle()).setBody(notification.getText()).build();

			Map<String, String> additionalData = new HashMap<>();
			additionalData.put("topicId", String.valueOf(notification.getTopicId()));
			additionalData.put("postId", String.valueOf(notification.getPostId()));
			additionalData.put("notificationId", String.valueOf(notification.getId()));
			additionalData.put("notificationType", notification.getNotificationType().name());

			Message message = Message.builder().setToken(token).setWebpushConfig(WebpushConfig.builder().build())
					.setNotification(fireBaseNotification).putAllData(additionalData).build();

			return firebaseMessaging.send(message);
		} catch (FirebaseMessagingException e) {
			log.error(e.getMessage());
		}
		return null;
	}

}
