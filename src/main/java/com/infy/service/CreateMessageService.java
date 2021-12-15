/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.domain.UserStats;
import com.infy.domain.Users;
import com.infy.service.model.CreateMessageRequest;
import com.infy.service.model.TopicOrPostResponse;

public interface CreateMessageService {

	TopicOrPostResponse createMessage(CreateMessageRequest createMessageRequest);

	TopicOrPostResponse createTopic(CreateMessageRequest createMessageRequest, Users users, UserStats userStats);

	TopicOrPostResponse createPost(CreateMessageRequest createMessageRequest, Users users, UserStats userStats);

	TopicOrPostResponse createPrivateTopic(CreateMessageRequest createMessageRequest, Users users,
			UserStats userStats);

	TopicOrPostResponse createPrivatePost(CreateMessageRequest createMessageRequest, Users users,
			UserStats userStats);

}
