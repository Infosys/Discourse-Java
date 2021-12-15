/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.model;

public class TopicResponse {

	private TopicOrPostResponse topic;
	private TopicOrPostResponse posts;

	public TopicOrPostResponse getTopic() {
		return topic;
	}

	public void setTopic(TopicOrPostResponse topic) {
		this.topic = topic;
	}

	public TopicOrPostResponse getPosts() {
		return posts;
	}

	public void setPosts(TopicOrPostResponse posts) {
		this.posts = posts;
	}

}
