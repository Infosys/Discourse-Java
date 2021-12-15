/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import java.time.Instant;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.infy.avroschema.TextClassificationRequest;
import com.infy.config.Constants;
import com.infy.domain.Categories;
import com.infy.domain.Notification;
import com.infy.domain.PostReplies;
import com.infy.domain.Posts;
import com.infy.domain.Topics;
import com.infy.domain.UserActions;
import com.infy.domain.UserStats;
import com.infy.domain.Users;
import com.infy.domain.enumeration.ArcheType;
import com.infy.domain.enumeration.NotificationStatus;
import com.infy.domain.enumeration.NotificationType;
import com.infy.domain.enumeration.PostStatus;
import com.infy.domain.enumeration.PostType;
import com.infy.domain.enumeration.TextClassificationType;
import com.infy.domain.enumeration.UserActionType;
import com.infy.repository.CategoriesRepository;
import com.infy.repository.PostRepliesRepository;
import com.infy.repository.PostsRepository;
import com.infy.repository.TopicsRepository;
import com.infy.repository.UserActionsRepository;
import com.infy.repository.UserStatsRepository;
import com.infy.repository.UsersRepository;
import com.infy.security.SecurityUtils;
import com.infy.service.CreateMessageService;
import com.infy.service.ElasticSearchService;
import com.infy.service.NotificationService;
import com.infy.service.TextClassificationProducer;
import com.infy.service.mapper.PostResponseMapper;
import com.infy.service.mapper.TopicResponseMapper;
import com.infy.service.model.CreateMessageRequest;
import com.infy.service.model.TopicElasticSearchDocument;
import com.infy.service.model.TopicOrPostResponse;
import com.infy.web.rest.errors.BadRequestAlertException;

@Service
@Transactional(isolation = Isolation.REPEATABLE_READ)
public class CreateMessageServiceImpl implements CreateMessageService {

	private final Logger log = LoggerFactory.getLogger(CreateMessageServiceImpl.class);

	private final PostsRepository postsRepository;
	private final TopicsRepository topicsRepository;
	private final CategoriesRepository categoriesRepository;
	private final TopicResponseMapper topicResponseMapper;
	private final PostResponseMapper postResponseMapper;
	private final PostRepliesRepository postRepliesRepository;
	private final UsersRepository usersRepository;
	private final UserStatsRepository userStatsRepository;
	private final UserActionsRepository userActionsRepository;
	private final TextClassificationProducer textClassificationProducer;
	private final ElasticSearchService elasticSearchService;
	private final NotificationService notificationService;

	public CreateMessageServiceImpl(PostsRepository postsRepository, TopicsRepository topicsRepository,
			CategoriesRepository categoriesRepository, TopicResponseMapper topicResponseMapper,
			PostResponseMapper postResponseMapper, PostRepliesRepository postRepliesRepository,
			UsersRepository usersRepository, UserStatsRepository userStatsRepository,
			UserActionsRepository userActionsRepository, TextClassificationProducer textClassificationProducer,
			ElasticSearchService elasticSearchService, NotificationService notificationService) {
		this.postsRepository = postsRepository;
		this.topicsRepository = topicsRepository;
		this.categoriesRepository = categoriesRepository;
		this.topicResponseMapper = topicResponseMapper;
		this.postResponseMapper = postResponseMapper;
		this.postRepliesRepository = postRepliesRepository;
		this.usersRepository = usersRepository;
		this.userStatsRepository = userStatsRepository;
		this.userActionsRepository = userActionsRepository;
		this.textClassificationProducer = textClassificationProducer;
		this.elasticSearchService = elasticSearchService;
		this.notificationService = notificationService;
	}

	@Override
	public TopicOrPostResponse createMessage(CreateMessageRequest createMessageRequest) {
		log.debug("Request to create message : {} ", createMessageRequest);

		Optional<String> userIdOptional = SecurityUtils.getCurrentUserLoginUserId();
		if (userIdOptional.isEmpty()) {
			throw new BadRequestAlertException("Invalid UserId", "Users", "Invalid UserId");
		}

		Optional<Users> usersOptional = usersRepository.findByUserId(userIdOptional.get());
		if (usersOptional.isEmpty()) {
			throw new BadRequestAlertException("User is not Onboarded", "Users", "User is not Onboarded");
		}

		Optional<UserStats> userStatsOptional = userStatsRepository.findByUserId(userIdOptional.get());
		if (userStatsOptional.isEmpty()) {
			throw new BadRequestAlertException("User is not Onboarded", "Users", "User is not Onboarded");
		}

		if (createMessageRequest.getArchetype().equals(ArcheType.PRIVATE_MESSAGE)) {
			if (createMessageRequest.getTopicId() == null) {
				return createPrivateTopic(createMessageRequest, usersOptional.get(), userStatsOptional.get());
			} else {
				return createPrivatePost(createMessageRequest, usersOptional.get(), userStatsOptional.get());
			}
		} else {
			if (createMessageRequest.getTopicId() == null) {
				return createTopic(createMessageRequest, usersOptional.get(), userStatsOptional.get());
			} else {
				return createPost(createMessageRequest, usersOptional.get(), userStatsOptional.get());
			}
		}
	}

	@Override
	public TopicOrPostResponse createTopic(CreateMessageRequest createMessageRequest, Users users,
			UserStats userStats) {
		log.debug("Request to create new topic");
		Topics topics = new Topics();

		Optional<Categories> categoriesOptional = categoriesRepository.findById(createMessageRequest.getCategory());
		if (categoriesOptional.isEmpty()) {
			throw new BadRequestAlertException("Invalid Category Id", "Categories", "Invalid Category Id");
		}

		topics.setTitle(createMessageRequest.getTitle());
		topics.setFancyTitle(createMessageRequest.getTitle());
		topics.setSubtype(createMessageRequest.getTags());
		topics.setViews(1);
		topics.setUserId(users.getUserId());
		topics.setReplyCount(0);
		topics.setHighestPostNumber(1000);
		topics.setLikeCount(0);
		topics.setIncomingLinkCount(0);
		topics.setCategoryId(createMessageRequest.getCategory());
		topics.setVisible(true);
		topics.setModeratorPostsCount(0);
		topics.setClosed(false);
		topics.setArchived(false);
		topics.setBumpedAt(Instant.now());
		topics.setHasSummary(false);
		if (createMessageRequest.getArchetype() == null) {
			topics.setArchetype(ArcheType.REGULAR.name());
		} else {
			topics.setArchetype(createMessageRequest.getArchetype().name());
		}
		topics.setNotifyModeratorsCount(0);
		topics.setSpamCount(0);
		topics.setPercentRank(0.0);
		topics.setPinnedGlobally(false);
		topics.setHighestStaffPostNumber(500);
		topics.setReviewableScore(0.0);
		topics.setSlowModeSeconds(0);
		topics.setPostsCount(1);
		topics.setLastPostedAt(Instant.now());
		topics.setLastPostUserId(users.getUserId());

		topics = topicsRepository.save(topics);
		produceTextClassificationTopics(topics);

		Posts posts = new Posts();
		posts.setRaw(createMessageRequest.getRaw());
		posts.setUserId(users.getUserId());
		posts.setPostNumber(topics.getPostsCount());
		posts.setTopicId(topics.getId());
		posts.setPostStatus(PostStatus.ACTIVE.getValue());
		posts.setCooked("default cooked");
		posts.setReplyCount(0);
		posts.setQuoteCount(0);
		posts.setOffTopicCount(0);
		posts.setLikeCount(0);
		posts.setIncomingLinkCount(0);
		posts.setBookmarkCount(0);
		posts.setReads(0);
		posts.setPostType(PostType.NORMAL.getValue());
		posts.setHidden(false);
		posts.setNotifyModeratorsCount(0);
		posts.setSpamCount(0);
		posts.setIllegalCount(0);
		posts.setInappropriateCount(0);
		posts.setLastVersionAt(Instant.now());
		posts.setUserDeleted(false);
		posts.setNotifyUserCount(0);
		posts.setLikeScore(0);
		posts.setVersion(1);
		posts.setCookMethod(0);
		posts.setWiki(true);
		posts.setSelfEdits(0);
		posts.setReplyQuoted(false);
		posts.setViaEmail(false);
		posts.setPublicVersion(1);

		posts = postsRepository.save(posts);
		produceTextClassificationPosts(posts);

		Categories categories = categoriesOptional.get();
		categories.setLatestTopicId(topics.getId());
		categories.setTopicCount(categories.getTopicCount() + 1);
		categories.setLatestPostId(posts.getId());
		categories.setPostCount(categories.getPostCount() + 1);
		categoriesRepository.save(categories);

		userStats.setPostCount(userStats.getPostCount() + 1);
		userStats.setTopicCount(userStats.getTopicCount() + 1);
		userStats.setTopicsEntered(userStats.getTopicsEntered() + 1);
		userStats.setTimeRead(userStats.getTimeRead() + 1);
		userStats.setPostsReadCount(userStats.getPostsReadCount() + 1);
		userStatsRepository.save(userStats);

		TopicElasticSearchDocument topicElasticSearchDocument = new TopicElasticSearchDocument();
		topicElasticSearchDocument.setId(topics.getId());
		topicElasticSearchDocument.setPostId(posts.getId());
		topicElasticSearchDocument.setTitle(topics.getTitle());
		topicElasticSearchDocument.setRaw(posts.getRaw());
		topicElasticSearchDocument.setTags(topics.getSubtype());
		topicElasticSearchDocument.setVisible(true);
		elasticSearchService.addTopicElasticSearchDocument(topicElasticSearchDocument);

		TopicOrPostResponse topicOrPostResponse = topicResponseMapper.toDto(topics);
		topicOrPostResponse.setUsername(users.getUsername());
		topicOrPostResponse.setAdmin(users.isAdmin());
		topicOrPostResponse.setTrustLevel(users.getTrustLevel());
		topicOrPostResponse.setUserTitle(users.getName());
		topicOrPostResponse.setCurrentUserLike(false);
		return topicOrPostResponse;
	}

	@Override
	public TopicOrPostResponse createPost(CreateMessageRequest createMessageRequest, Users users, UserStats userStats) {
		log.debug("Request to create new Post");

		Posts posts = new Posts();
		posts.setRaw(createMessageRequest.getRaw());
		posts.setUserId(users.getUserId());

		Optional<Topics> topicsOptional = topicsRepository.findById(createMessageRequest.getTopicId());
		if (topicsOptional.isEmpty()) {
			throw new BadRequestAlertException("Invalid topic id", "Post", "Invalid topic id");
		}

		Topics topics = topicsOptional.get();
		topics.setPostsCount(topics.getPostsCount() + 1);
		topics.setLastPostedAt(Instant.now());
		topics.setLastPostUserId(users.getUserId());

		posts.setTopicId(topics.getId());

		if (createMessageRequest.getReplyToPost() != null) {
			Optional<Posts> postsOptional = postsRepository.findById(createMessageRequest.getReplyToPost());
			if (postsOptional.isEmpty()) {
				throw new BadRequestAlertException("Invalid reply to post id", "Post", "Invalid reply to post id");
			}
			Posts replyToPosts = postsOptional.get();

			produceNotification(replyToPosts, replyToPosts.getUserId(), users.getUsername(), true);

			posts.setReplyToPostNumber(replyToPosts.getId());
			posts.setReplyToUserId(replyToPosts.getUserId());

			replyToPosts.setReplyCount(replyToPosts.getReplyCount() + 1);
			postsRepository.save(replyToPosts);

			topics.setReplyCount(topics.getReplyCount() + 1);
		} else {
			produceNotification(posts, topics.getUserId(), users.getUsername(), false);
		}

		topics = topicsRepository.save(topics);

		posts.setPostNumber(topics.getPostsCount());
		posts.setTopicId(topics.getId());
		posts.setPostStatus(PostStatus.ACTIVE.getValue());
		posts.setCooked("default cooked");
		posts.setReplyCount(0);
		posts.setQuoteCount(0);
		posts.setOffTopicCount(0);
		posts.setLikeCount(0);
		posts.setIncomingLinkCount(0);
		posts.setBookmarkCount(0);
		posts.setReads(0);
		posts.setPostType(PostType.NORMAL.getValue());
		posts.setHidden(false);
		posts.setNotifyModeratorsCount(0);
		posts.setSpamCount(0);
		posts.setIllegalCount(0);
		posts.setInappropriateCount(0);
		posts.setLastVersionAt(Instant.now());
		posts.setUserDeleted(false);
		posts.setNotifyUserCount(0);
		posts.setLikeScore(0);
		posts.setVersion(1);
		posts.setCookMethod(0);
		posts.setWiki(true);
		posts.setSelfEdits(0);
		posts.setReplyQuoted(false);
		posts.setViaEmail(false);
		posts.setPublicVersion(1);

		posts = postsRepository.save(posts);
		produceTextClassificationPosts(posts);

		TopicOrPostResponse topicOrPostResponse = postResponseMapper.toDto(posts);

		Optional<Categories> cateOptional = categoriesRepository.findById(topics.getCategoryId());
		if (cateOptional.isPresent()) {
			Categories categories = cateOptional.get();
			categories.setLatestPostId(posts.getId());
			categories.setPostCount(categories.getPostCount() + 1);
			categoriesRepository.save(categories);
		}

		if (createMessageRequest.getReplyToPost() != null) {
			PostReplies postReplies = new PostReplies();
			postReplies.setPostId(createMessageRequest.getReplyToPost());
			postReplies.setReplyPostId(posts.getId());
			postRepliesRepository.save(postReplies);

			UserActions userActions = new UserActions();
			userActions.setUserId(users.getUserId());
			userActions.setActionType(UserActionType.REPLY.getValue());
			userActions.setActingUserId(users.getUserId());
			userActions.setTargetPostId(posts.getId());
			userActions.setTargetPostId(posts.getTopicId());
			userActions.setTargetUserId(users.getUserId());
			userActionsRepository.save(userActions);
		}

		userStats.setTopicsEntered(userStats.getTopicsEntered() + 1);
		userStats.setPostsReadCount(userStats.getPostsReadCount() + 1);
		userStats.setPostCount(userStats.getPostCount());
		userStatsRepository.save(userStats);

		topicOrPostResponse.setUsername(users.getUsername());
		topicOrPostResponse.setAdmin(users.isAdmin());
		topicOrPostResponse.setTrustLevel(users.getTrustLevel());
		topicOrPostResponse.setUserTitle(users.getName());
		topicOrPostResponse.setCurrentUserLike(false);

		return topicOrPostResponse;
	}

	@Override
	public TopicOrPostResponse createPrivateTopic(CreateMessageRequest createMessageRequest, Users users,
			UserStats userStats) {
		log.debug("Request to create new private topic");
		Topics topics = new Topics();

		Optional<Categories> categoriesOptional = categoriesRepository.findById(createMessageRequest.getCategory());
		if (categoriesOptional.isEmpty()) {
			throw new BadRequestAlertException("Invalid Category Id", "Categories", "Invalid Category Id");
		}

		topics.setTitle(createMessageRequest.getTitle());
		topics.setFancyTitle(createMessageRequest.getTitle());
		topics.setSubtype(createMessageRequest.getTags());
		topics.setViews(1);
		topics.setUserId(users.getUserId());
		topics.setReplyCount(0);
		topics.setHighestPostNumber(1000);
		topics.setLikeCount(0);
		topics.setIncomingLinkCount(0);
		topics.setCategoryId(createMessageRequest.getCategory());
		topics.setVisible(true);
		topics.setModeratorPostsCount(0);
		topics.setClosed(false);
		topics.setArchived(false);
		topics.setBumpedAt(Instant.now());
		topics.setHasSummary(false);
		topics.setArchetype(ArcheType.PRIVATE_MESSAGE.name());
		topics.setNotifyModeratorsCount(0);
		topics.setSpamCount(0);
		topics.setPercentRank(0.0);
		topics.setPinnedGlobally(false);
		topics.setHighestStaffPostNumber(500);
		topics.setReviewableScore(0.0);
		topics.setSlowModeSeconds(0);
		topics.setPostsCount(1);
		topics.setLastPostedAt(Instant.now());
		topics.setLastPostUserId(users.getUserId());

		topics = topicsRepository.save(topics);

		Posts posts = new Posts();
		posts.setRaw(createMessageRequest.getRaw());
		posts.setUserId(users.getUserId());
		posts.setPostNumber(topics.getPostsCount());
		posts.setTopicId(topics.getId());
		posts.setPostStatus(PostStatus.ACTIVE.getValue());
		posts.setCooked("default cooked");
		posts.setReplyCount(0);
		posts.setQuoteCount(0);
		posts.setOffTopicCount(0);
		posts.setLikeCount(0);
		posts.setIncomingLinkCount(0);
		posts.setBookmarkCount(0);
		posts.setReads(0);
		posts.setPostType(PostType.PRIVATE_MESSAGE.getValue());
		posts.setHidden(false);
		posts.setNotifyModeratorsCount(0);
		posts.setSpamCount(0);
		posts.setIllegalCount(0);
		posts.setInappropriateCount(0);
		posts.setLastVersionAt(Instant.now());
		posts.setUserDeleted(false);
		posts.setNotifyUserCount(0);
		posts.setLikeScore(0);
		posts.setVersion(1);
		posts.setCookMethod(0);
		posts.setWiki(true);
		posts.setSelfEdits(0);
		posts.setReplyQuoted(false);
		posts.setViaEmail(false);
		posts.setPublicVersion(1);

		posts = postsRepository.save(posts);

		Categories categories = categoriesOptional.get();
		categories.setLatestTopicId(topics.getId());
		categories.setTopicCount(categories.getTopicCount() + 1);
		categories.setLatestPostId(posts.getId());
		categories.setPostCount(categories.getPostCount() + 1);
		categoriesRepository.save(categories);

		userStats.setPostCount(userStats.getPostCount() + 1);
		userStats.setTopicCount(userStats.getTopicCount() + 1);
		userStats.setTopicsEntered(userStats.getTopicsEntered() + 1);
		userStats.setTimeRead(userStats.getTimeRead() + 1);
		userStats.setPostsReadCount(userStats.getPostsReadCount() + 1);
		userStatsRepository.save(userStats);

		TopicOrPostResponse topicOrPostResponse = topicResponseMapper.toDto(topics);
		topicOrPostResponse.setUsername(users.getUsername());
		topicOrPostResponse.setAdmin(users.isAdmin());
		topicOrPostResponse.setTrustLevel(users.getTrustLevel());
		topicOrPostResponse.setUserTitle(users.getName());
		topicOrPostResponse.setCurrentUserLike(false);
		return topicOrPostResponse;
	}

	@Override
	public TopicOrPostResponse createPrivatePost(CreateMessageRequest createMessageRequest, Users users,
			UserStats userStats) {
		log.debug("Request to create new private Post");

		Posts posts = new Posts();
		posts.setRaw(createMessageRequest.getRaw());
		posts.setUserId(users.getUserId());

		Optional<Topics> topicsOptional = topicsRepository.findById(createMessageRequest.getTopicId());
		if (topicsOptional.isEmpty()) {
			throw new BadRequestAlertException("Invalid topic id", "Post", "Invalid topic id");
		}

		Topics topics = topicsOptional.get();
		topics.setPostsCount(topics.getPostsCount() + 1);
		topics.setLastPostedAt(Instant.now());
		topics.setLastPostUserId(users.getUserId());

		if (!(users.isAdmin() || users.isModerator())) {
			if (!topics.getUserId().equals(users.getUserId())) {
				throw new BadRequestAlertException("Only Admin/Moderator or Topic Owner Can create private post",
						"Post", "Invalid Access of User");
			}
		}

		posts.setTopicId(topics.getId());

		if (createMessageRequest.getReplyToPost() != null) {
			Optional<Posts> postsOptional = postsRepository.findById(createMessageRequest.getReplyToPost());
			if (postsOptional.isEmpty()) {
				throw new BadRequestAlertException("Invalid reply to post id", "Post", "Invalid reply to post id");
			}
			Posts replyToPosts = postsOptional.get();

			produceNotification(replyToPosts, replyToPosts.getUserId(), users.getUsername(), true);

			posts.setReplyToPostNumber(replyToPosts.getId());
			posts.setReplyToUserId(replyToPosts.getUserId());

			replyToPosts.setReplyCount(replyToPosts.getReplyCount() + 1);
			postsRepository.save(replyToPosts);

			topics.setReplyCount(topics.getReplyCount() + 1);
		} else {
			produceNotification(posts, topics.getUserId(), users.getUsername(), false);
		}

		topics = topicsRepository.save(topics);

		posts.setPostNumber(topics.getPostsCount());
		posts.setTopicId(topics.getId());
		posts.setPostStatus(PostStatus.ACTIVE.getValue());
		posts.setCooked("default cooked");
		posts.setReplyCount(0);
		posts.setQuoteCount(0);
		posts.setOffTopicCount(0);
		posts.setLikeCount(0);
		posts.setIncomingLinkCount(0);
		posts.setBookmarkCount(0);
		posts.setReads(0);
		posts.setPostType(PostType.PRIVATE_MESSAGE.getValue());
		posts.setHidden(false);
		posts.setNotifyModeratorsCount(0);
		posts.setSpamCount(0);
		posts.setIllegalCount(0);
		posts.setInappropriateCount(0);
		posts.setLastVersionAt(Instant.now());
		posts.setUserDeleted(false);
		posts.setNotifyUserCount(0);
		posts.setLikeScore(0);
		posts.setVersion(1);
		posts.setCookMethod(0);
		posts.setWiki(true);
		posts.setSelfEdits(0);
		posts.setReplyQuoted(false);
		posts.setViaEmail(false);
		posts.setPublicVersion(1);

		posts = postsRepository.save(posts);

		TopicOrPostResponse topicOrPostResponse = postResponseMapper.toDto(posts);

		Optional<Categories> cateOptional = categoriesRepository.findById(topics.getCategoryId());
		if (cateOptional.isPresent()) {
			Categories categories = cateOptional.get();
			categories.setLatestPostId(posts.getId());
			categories.setPostCount(categories.getPostCount() + 1);
			categoriesRepository.save(categories);
		}

		if (createMessageRequest.getReplyToPost() != null) {
			PostReplies postReplies = new PostReplies();
			postReplies.setPostId(createMessageRequest.getReplyToPost());
			postReplies.setReplyPostId(posts.getId());
			postRepliesRepository.save(postReplies);

			UserActions userActions = new UserActions();
			userActions.setUserId(users.getUserId());
			userActions.setActionType(UserActionType.REPLY.getValue());
			userActions.setActingUserId(users.getUserId());
			userActions.setTargetPostId(posts.getId());
			userActions.setTargetPostId(posts.getTopicId());
			userActions.setTargetUserId(users.getUserId());
			userActionsRepository.save(userActions);
		}

		userStats.setTopicsEntered(userStats.getTopicsEntered() + 1);
		userStats.setPostsReadCount(userStats.getPostsReadCount() + 1);
		userStats.setPostCount(userStats.getPostCount());
		userStatsRepository.save(userStats);

		topicOrPostResponse.setUsername(users.getUsername());
		topicOrPostResponse.setAdmin(users.isAdmin());
		topicOrPostResponse.setTrustLevel(users.getTrustLevel());
		topicOrPostResponse.setUserTitle(users.getName());
		topicOrPostResponse.setCurrentUserLike(false);

		return topicOrPostResponse;
	}

	private void produceTextClassificationPosts(Posts posts) {
		TextClassificationRequest textClassificationRequest = new TextClassificationRequest();
		textClassificationRequest.setId(posts.getId());
		textClassificationRequest.setType(TextClassificationType.POST.toString());
		textClassificationRequest.setContent(posts.getRaw());

		textClassificationProducer.produceTextClassification(textClassificationRequest);
	}

	private void produceTextClassificationTopics(Topics topics) {
		TextClassificationRequest textClassificationRequest = new TextClassificationRequest();
		textClassificationRequest.setId(topics.getId());
		textClassificationRequest.setType(TextClassificationType.TOPIC.toString());
		textClassificationRequest.setContent(topics.getTitle());

		textClassificationProducer.produceTextClassification(textClassificationRequest);
	}

	private void produceNotification(Posts posts, String userId, String username, boolean replyPost) {

		Notification notification = new Notification();
		notification.setUserId(userId);
		notification.setPostId(posts.getId());
		notification.setTopicId(posts.getTopicId());
		notification.setNotificationType(NotificationType.REPLIED);

		if (replyPost) {
			notification.setTitle(username + Constants.REPLY_POST_TITLE);
			notification.setText(Constants.REPLY_POST_TEXT);
		} else {
			notification.setTitle(username + Constants.POST_CREATE_TITLE);
			notification.setText(Constants.POST_CREATE_TEXT);
		}
		notification.setNotificationStatus(NotificationStatus.CREATED);
		notificationService.createNotification(notification);
	}

}
