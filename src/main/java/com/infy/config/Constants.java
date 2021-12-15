/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.config;

/**
 * Application constants.
 */
public final class Constants {

	// Regex for acceptable logins
	public static final String LOGIN_REGEX = "^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$";

	public static final String SYSTEM_ACCOUNT = "system";
	public static final String DEFAULT_LANGUAGE = "en";
	public static final String ANONYMOUS_USER = "anonymoususer";

	public static final String POST_HIDE_TITLE = "Your Post was Hidden By Moderator";
	public static final String POST_HIDE_TEXT = "Post was Hidden Because it violated our policies";

	public static final String POST_UNHIDE_TITLE = "Your Post was UnHide By Moderator";
	public static final String POST_UNHIDE_TEXT = "";

	public static final String REPLY_POST_TITLE = " replied to your Post";
	public static final String REPLY_POST_TEXT = "";

	public static final String POST_CREATE_TITLE = " created a Post in your Topic";
	public static final String POST_CREATE_TEXT = "";

	public static final String POST_EDIT_TITLE = "Your Post was Edited By Moderator";
	public static final String POST_EDIT_TEXT = "";

	public static final String POST_DELETE_TITLE = "Your Post was Deleted By Moderator";
	public static final String POST_DELETE_TEXT = "Post was Deleted Because it violated our policies";

	public static final String TOPIC_HIDE_TITLE = "Your Topic was Hidden";
	public static final String TOPIC_HIDE_TEXT = "Topic was Hidden Because it violated our policies";

	public static final String TOPIC_UNHIDE_TITLE = "Your Topic was Unhidden By Moderator";
	public static final String TOPIC_UNHIDE_TEXT = "";

	public static final String TOPIC_EDIT_TITLE = "Your Topic was Edited By Moderator";
	public static final String TOPIC_EDIT_TEXT = "";

	private Constants() {
	}
}
