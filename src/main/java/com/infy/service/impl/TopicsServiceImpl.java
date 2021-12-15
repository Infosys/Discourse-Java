/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infy.avroschema.TextClassificationRequest;
import com.infy.config.Constants;
import com.infy.domain.Notification;
import com.infy.domain.Topics;
import com.infy.domain.UserActions;
import com.infy.domain.Users;
import com.infy.domain.enumeration.ArcheType;
import com.infy.domain.enumeration.NotificationStatus;
import com.infy.domain.enumeration.NotificationType;
import com.infy.domain.enumeration.PostStatus;
import com.infy.domain.enumeration.TextClassificationType;
import com.infy.domain.enumeration.UserActionType;
import com.infy.repository.PostsRepository;
import com.infy.repository.TopicsRepository;
import com.infy.repository.UserActionsRepository;
import com.infy.repository.UsersRepository;
import com.infy.security.SecurityUtils;
import com.infy.service.ElasticSearchService;
import com.infy.service.NotificationService;
import com.infy.service.TextClassificationProducer;
import com.infy.service.TopicsService;
import com.infy.service.dto.TopicsDTO;
import com.infy.service.mapper.PostResponseMapper;
import com.infy.service.mapper.TopicResponseMapper;
import com.infy.service.mapper.TopicsMapper;
import com.infy.service.model.TopicElasticSearchDocument;
import com.infy.service.model.TopicOrPostResponse;
import com.infy.service.model.TopicResponse;
import com.infy.service.model.UpdateTopicRequest;
import com.infy.service.model.UpdateTopicResponse;
import com.infy.service.model.UpdatedTopic;
import com.infy.web.rest.errors.BadRequestAlertException;

/**
 * Service Implementation for managing {@link Topics}.
 */
@Service
@Transactional
public class TopicsServiceImpl implements TopicsService {

	private final Logger log = LoggerFactory.getLogger(TopicsServiceImpl.class);

	private final TopicsRepository topicsRepository;

	private final TopicsMapper topicsMapper;

	private final TopicResponseMapper topicResponseMapper;

	private final PostsRepository postsRepository;

	private final PostResponseMapper postResponseMapper;

	private final UsersRepository usersRepository;

	private final UserActionsRepository userActionsRepository;

	private final TextClassificationProducer textClassificationProducer;

	private final NotificationService notificationService;

	private final ElasticSearchService elasticSearchService;

	public TopicsServiceImpl(TopicsRepository topicsRepository, TopicsMapper topicsMapper,
			TopicResponseMapper topicResponseMapper, PostsRepository postsRepository,
			PostResponseMapper postResponseMapper, UsersRepository usersRepository,
			UserActionsRepository userActionsRepository, TextClassificationProducer textClassificationProducer,
			NotificationService notificationService, ElasticSearchService elasticSearchService) {
		this.topicsRepository = topicsRepository;
		this.topicsMapper = topicsMapper;
		this.topicResponseMapper = topicResponseMapper;
		this.postsRepository = postsRepository;
		this.postResponseMapper = postResponseMapper;
		this.usersRepository = usersRepository;
		this.userActionsRepository = userActionsRepository;
		this.textClassificationProducer = textClassificationProducer;
		this.notificationService = notificationService;
		this.elasticSearchService = elasticSearchService;
	}

	@Override
	public TopicsDTO save(TopicsDTO topicsDTO) {
		log.debug("Request to save Topics : {}", topicsDTO);
		Topics topics = topicsMapper.toEntity(topicsDTO);
		topics = topicsRepository.save(topics);
		return topicsMapper.toDto(topics);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<TopicsDTO> findAll(Pageable pageable) {
		log.debug("Request to get all Topics");
		return topicsRepository.findAll(pageable).map(topicsMapper::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<TopicsDTO> findOne(Long id) {
		log.debug("Request to get Topics : {}", id);
		return topicsRepository.findById(id).map(topicsMapper::toDto);
	}

	@Override
	public void delete(Long id) {
		log.debug("Request to delete Topics : {}", id);
		topicsRepository.deleteById(id);
	}

	@Override
	public UpdateTopicResponse updateTopics(Long id, UpdateTopicRequest updateTopicRequest) {
		log.debug("Request to update topics : {} ", updateTopicRequest);

		Optional<Topics> topicOptional = topicsRepository.findById(id);
		if (topicOptional.isEmpty()) {
			throw new BadRequestAlertException("Invalid topic id", "Topics", "Invalid topic id");
		}

		Optional<String> userIdOptional = SecurityUtils.getCurrentUserLoginUserId();
		if (userIdOptional.isEmpty()) {
			throw new BadRequestAlertException("Invalid user id", "Users", "Invalid user id");
		}

		Optional<Users> usersOptional = usersRepository.findByUserId(userIdOptional.get());
		if (usersOptional.isEmpty()) {
			throw new BadRequestAlertException("User is not Onboarded", "Users", "User is not Onboarded");
		}
		Users users = usersOptional.get();

		Topics topics = topicOptional.get();
		if (!(topics.getUserId().equals(userIdOptional.get()) || users.isAdmin())) {
			throw new BadRequestAlertException("You Have not created this Topics And You are not admin", "Topics",
					"You Have not created this Topics And You are not admin");
		}

		topics.setTitle(updateTopicRequest.getTitle());
		topics.setCategoryId(updateTopicRequest.getCategoryId());
		topics.setSubtype(updateTopicRequest.getTags());

		topics = topicsRepository.save(topics);

		if (users.isAdmin()) {
			createNotification(topics.getUserId(), topics.getId(), null, Constants.TOPIC_EDIT_TITLE,
					Constants.TOPIC_EDIT_TEXT, NotificationType.EDITED);
		}

		if (!topics.getArchetype().equals(ArcheType.PRIVATE_MESSAGE.name())) {
			produceTextClassificaton(topics);
			TopicElasticSearchDocument topicElasticSearchDocument = new TopicElasticSearchDocument();
			topicElasticSearchDocument.setId(topics.getId());
			topicElasticSearchDocument.setTitle(topics.getTitle());
			topicElasticSearchDocument.setTags(topics.getSubtype());
			elasticSearchService.addTopicElasticSearchDocument(topicElasticSearchDocument);
		}

		UpdatedTopic updatedTopic = new UpdatedTopic();
		updatedTopic.setId(topics.getId());
		updatedTopic.setTitle(topics.getTitle());
		updatedTopic.setPostsCount(topics.getPostsCount());
		updatedTopic.setSlug(topics.getSlug());
		updatedTopic.setTags(topics.getSubtype());

		UpdateTopicResponse updateTopicResponse = new UpdateTopicResponse();
		updateTopicResponse.setUpdatedTopic(updatedTopic);

		return updateTopicResponse;
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<TopicResponse> findByIdAndResponse(Long id) {
		log.debug("Request to get Topic by id : {}", id);

		Optional<String> userIdOptional = SecurityUtils.getCurrentUserLoginUserId();
		if (userIdOptional.isEmpty()) {
			throw new BadRequestAlertException("Invalid User id", "Users", "Invalid User id");
		}

		Optional<Users> loginUsersOptional = usersRepository.findByUserId(userIdOptional.get());
		if (loginUsersOptional.isEmpty()) {
			throw new BadRequestAlertException("User is not Onboarded", "Users", "User is not Onboarded");
		}
		Users loginUsers = loginUsersOptional.get();

		Optional<TopicOrPostResponse> topicOptional;
		if (loginUsers.isAdmin() || loginUsers.isModerator()) {
			topicOptional = topicsRepository.findById(id).map(topicResponseMapper::toDto);
		} else {
			topicOptional = topicsRepository.findByIdAndVisible(id, true).map(topicResponseMapper::toDto);
		}

		if (topicOptional.isPresent()) {
			TopicOrPostResponse topic = topicOptional.get();

			if (topic.getArchetype().equals(ArcheType.PRIVATE_MESSAGE.name())) {
				if (!(loginUsers.isAdmin() || loginUsers.isModerator())) {
					if (!loginUsers.getUserId().equals(topic.getUserId())) {
						return Optional.ofNullable(null);
					}
				}
			}

			Optional<Users> usersOptional = usersRepository.findByUserId(topic.getUserId());
			if (usersOptional.isPresent()) {
				Users users = usersOptional.get();
				topic.setUsername(users.getUsername());
				topic.setAdmin(users.isAdmin());
				topic.setTrustLevel(users.getTrustLevel());
				topic.setUserTitle(users.getName());
			}

			Optional<TopicOrPostResponse> postOptional = postsRepository
					.findByTopicIdAndPostNumberAndPostStatus(id, 1, PostStatus.ACTIVE.getValue())
					.map(postResponseMapper::toDto);

			TopicResponse topicResponse = new TopicResponse();
			topicResponse.setTopic(topic);

			if (postOptional.isPresent()) {
				TopicOrPostResponse post = postOptional.get();
				post.setCurrentUserLike(false);

				List<UserActions> userActions = userActionsRepository.findByUserIdAndActionTypeAndTargetPostId(
						userIdOptional.get(), UserActionType.LIKE.getValue(), post.getId());
				if (!userActions.isEmpty())
					post.setCurrentUserLike(true);

				if (usersOptional.isPresent()) {
					Users users = usersOptional.get();
					post.setUsername(users.getUsername());
					post.setAdmin(users.isAdmin());
					post.setTrustLevel(users.getTrustLevel());
					post.setUserTitle(users.getName());
				}
				topicResponse.setPosts(post);
			}
			return Optional.of(topicResponse);
		}
		return Optional.ofNullable(null);
	}

	@Override
	public Page<TopicOrPostResponse> findByCategoryIdAndResponse(Long categoryId, Pageable pageable) {
		log.debug("Request to get topics from categoryId : {}", categoryId);

		Optional<String> userIdOptional = SecurityUtils.getCurrentUserLoginUserId();
		if (userIdOptional.isEmpty()) {
			throw new BadRequestAlertException("Invalid user id", "Users", "Invalid user id");
		}

		Optional<Users> loginUsersOptional = usersRepository.findByUserId(userIdOptional.get());
		if (loginUsersOptional.isEmpty()) {
			throw new BadRequestAlertException("User is not Onboarded", "Users", "User is not Onboarded");
		}
		Users loginUsers = loginUsersOptional.get();

		Page<TopicOrPostResponse> topicPage;
		if (loginUsers.isAdmin() || loginUsers.isModerator()) {
			topicPage = topicsRepository.findByCategoryId(categoryId, pageable).map(topicResponseMapper::toDto);
		} else {
			topicPage = topicsRepository
					.findByCategoryIdAndVisibleAndArchetype(categoryId, true, ArcheType.REGULAR.name(), pageable)
					.map(topicResponseMapper::toDto);
		}

		topicPage.getContent().forEach(topic -> {
			Optional<Users> usersOptional = usersRepository.findByUserId(topic.getUserId());
			if (usersOptional.isPresent()) {
				Users users = usersOptional.get();
				topic.setUsername(users.getUsername());
				topic.setAdmin(users.isAdmin());
				topic.setTrustLevel(users.getTrustLevel());
				topic.setUserTitle(users.getName());
			}
		});
		return topicPage;
	}

	@Override
	@Transactional(readOnly = true)
	public Page<TopicOrPostResponse> findAllAndResponse(Pageable pageable) {
		log.debug("Request to get all Topics");

		Optional<String> userIdOptional = SecurityUtils.getCurrentUserLoginUserId();
		if (userIdOptional.isEmpty()) {
			throw new BadRequestAlertException("Invalid user id", "Users", "Invalid user id");
		}

		Optional<Users> loginUsersOptional = usersRepository.findByUserId(userIdOptional.get());
		if (loginUsersOptional.isEmpty()) {
			throw new BadRequestAlertException("User is not Onboarded", "Users", "User is not Onboarded");
		}
		Users loginUsers = loginUsersOptional.get();

		Page<TopicOrPostResponse> topicPage;
		if (loginUsers.isAdmin() || loginUsers.isModerator()) {
			topicPage = topicsRepository.findAll(pageable).map(topicResponseMapper::toDto);
		} else {
			topicPage = topicsRepository.findByVisibleAndArchetype(true, ArcheType.REGULAR.name(), pageable)
					.map(topicResponseMapper::toDto);
		}

		topicPage.getContent().forEach(topic -> {
			Optional<Users> usersOptional = usersRepository.findByUserId(topic.getUserId());
			if (usersOptional.isPresent()) {
				Users users = usersOptional.get();
				topic.setUsername(users.getUsername());
				topic.setAdmin(users.isAdmin());
				topic.setTrustLevel(users.getTrustLevel());
				topic.setUserTitle(users.getName());
			}
		});
		return topicPage;
	}

	@Override
	@Transactional(readOnly = true)
	public Page<TopicOrPostResponse> findByCurrentLoginUser(Pageable pageable) {
		log.debug("Request to get all topics of current login user");

		Optional<String> userIdOptional = SecurityUtils.getCurrentUserLoginUserId();
		if (userIdOptional.isEmpty()) {
			throw new BadRequestAlertException("Invalid user id", "Users", "Invalid user id");
		}

		Optional<Users> usersOptional = usersRepository.findByUserId(userIdOptional.get());
		if (usersOptional.isEmpty()) {
			throw new BadRequestAlertException("User is not Onboarded", "Users", "User is not Onboarded");
		}
		Users users = usersOptional.get();

		Page<TopicOrPostResponse> topicPage = topicsRepository
				.findByUserIdAndVisible(userIdOptional.get(), true, pageable).map(topicResponseMapper::toDto);

		topicPage.getContent().forEach(topic -> {
			topic.setUsername(users.getUsername());
			topic.setAdmin(users.isAdmin());
			topic.setTrustLevel(users.getTrustLevel());
			topic.setUserTitle(users.getName());
		});
		return topicPage;
	}

	private void produceTextClassificaton(Topics topics) {
		TextClassificationRequest textClassificationRequest = new TextClassificationRequest();
		textClassificationRequest.setId(topics.getId());
		textClassificationRequest.setType(TextClassificationType.TOPIC.toString());
		textClassificationRequest.setContent(topics.getTitle());

		textClassificationProducer.produceTextClassification(textClassificationRequest);
	}

	@Override
	public void hideTopic(Long id) {
		log.debug("Request to Hide Topics : {} ", id);

		Optional<Topics> topicOptional = topicsRepository.findById(id);
		if (topicOptional.isPresent()) {
			Topics topics = topicOptional.get();
			if (!topics.isVisible()) {
				throw new BadRequestAlertException("Topic is already Hidden", "Topics", "Topic is already Hidden");
			}

			topics.setVisible(false);
			topicsRepository.save(topics);
			postsRepository.hideAllPostsByTopicId(id, Instant.now());

			createNotification(topics.getUserId(), topics.getId(), null, Constants.TOPIC_HIDE_TITLE,
					Constants.TOPIC_HIDE_TEXT, NotificationType.HIDDEN);

			if (!topics.getArchetype().equals(ArcheType.PRIVATE_MESSAGE.name())) {
				TopicElasticSearchDocument topicElasticSearchDocument = new TopicElasticSearchDocument();
				topicElasticSearchDocument.setId(topics.getId());
				topicElasticSearchDocument.setVisible(false);
				elasticSearchService.addTopicElasticSearchDocument(topicElasticSearchDocument);
			}
		} else {
			throw new BadRequestAlertException("Invalid topic id", "Topics", "Invalid topic id");
		}

//		Optional<String> userIdOptional = SecurityUtils.getCurrentUserLoginUserId();
//		if (userIdOptional.isEmpty()) {
//			throw new BadRequestAlertException("Invalid user id", "Users", "Invalid user id");
//		}
//
//		Optional<Users> usersOptional = usersRepository.findByUserId(userIdOptional.get());
//		if (usersOptional.isEmpty()) {
//			throw new BadRequestAlertException("User is not Onboarded", "Users", "User is not Onboarded");
//		}
//		Users users = usersOptional.get();
//
//		if (users.isAdmin() || users.isModerator()) {
//
//			Optional<Topics> topicOptional = topicsRepository.findById(id);
//			if (topicOptional.isPresent()) {
//				Topics topics = topicOptional.get();
//				if (!topics.isVisible()) {
//					throw new BadRequestAlertException("Topic is already Hidden", "Topics", "Topic is already Hidden");
//				}
//
//				topics.setVisible(false);
//				topicsRepository.save(topics);
//				postsRepository.hideAllPostsByTopicId(id, Instant.now());
//				createNotification(topics.getUserId(), topics.getId(), null, Constants.TOPIC_HIDE_TITLE,
//						Constants.TOPIC_HIDE_TEXT,NotificationType.HIDDEN);
//
//				TopicElasticSearchDocument topicElasticSearchDocument = new TopicElasticSearchDocument();
//				topicElasticSearchDocument.setId(topics.getId());
//				topicElasticSearchDocument.setVisible(false);
//				elasticSearchService.addTopicElasticSearchDocument(topicElasticSearchDocument);
//			} else {
//				throw new BadRequestAlertException("Invalid topic id", "Topics", "Invalid topic id");
//			}
//		} else {
//			throw new BadRequestAlertException("Only Admin or Moderator Hide Topics", "Topics",
//					"Only Admin or Moderator Hide Topics");
//		}
	}

	@Override
	public void unHideTopic(Long id) {
		log.debug("Request to unHide Topic : {} ", id);

		Optional<Topics> topicOptional = topicsRepository.findById(id);
		if (topicOptional.isPresent()) {
			Topics topics = topicOptional.get();
			if (topics.isVisible()) {
				throw new BadRequestAlertException("Topic is already Visible", "Topics", "Topic is already Visible");
			}

			topics.setVisible(false);
			topicsRepository.save(topics);
			postsRepository.unHideAllPostsByTopicId(id);

			createNotification(topics.getUserId(), topics.getId(), null, Constants.TOPIC_UNHIDE_TITLE,
					Constants.TOPIC_UNHIDE_TEXT, NotificationType.UNHIDE);

			if (!topics.getArchetype().equals(ArcheType.PRIVATE_MESSAGE.name())) {
				TopicElasticSearchDocument topicElasticSearchDocument = new TopicElasticSearchDocument();
				topicElasticSearchDocument.setId(topics.getId());
				topicElasticSearchDocument.setVisible(true);
				elasticSearchService.addTopicElasticSearchDocument(topicElasticSearchDocument);
			}
		} else {
			throw new BadRequestAlertException("Invalid topic id", "Topics", "Invalid topic id");
		}

//		Optional<String> userIdOptional = SecurityUtils.getCurrentUserLoginUserId();
//		if (userIdOptional.isEmpty()) {
//			throw new BadRequestAlertException("Invalid user id", "Users", "Invalid user id");
//		}
//
//		Optional<Users> usersOptional = usersRepository.findByUserId(userIdOptional.get());
//		if (usersOptional.isEmpty()) {
//			throw new BadRequestAlertException("User is not Onboarded", "Users", "User is not Onboarded");
//		}
//		Users users = usersOptional.get();
//
//		if (users.isAdmin() || users.isModerator()) {
//			Optional<Topics> topicOptional = topicsRepository.findById(id);
//			if (topicOptional.isPresent()) {
//				Topics topics = topicOptional.get();
//				if (topics.isVisible()) {
//					throw new BadRequestAlertException("Topic is already Visible", "Topics",
//							"Topic is already Visible");
//				}
//
//				topics.setVisible(false);
//				topicsRepository.save(topics);
//				postsRepository.unHideAllPostsByTopicId(id);
//
//				createNotification(topics.getUserId(), topics.getId(), null, Constants.TOPIC_UNHIDE_TITLE,
//						Constants.TOPIC_UNHIDE_TEXT,NotificationType.UNHIDE);
//
//				TopicElasticSearchDocument topicElasticSearchDocument = new TopicElasticSearchDocument();
//				topicElasticSearchDocument.setId(topics.getId());
//				topicElasticSearchDocument.setVisible(true);
//				elasticSearchService.addTopicElasticSearchDocument(topicElasticSearchDocument);
//			} else {
//				throw new BadRequestAlertException("Invalid topic id", "Topics", "Invalid topic id");
//			}
//		} else {
//			throw new BadRequestAlertException("Only Admin or Moderator Hide Topics", "Topics",
//					"Only Admin or Moderator Hide Topics");
//		}

	}

	@Override
	public void hideTopicUsingClassifier(Long id) {
		log.debug("Request to hide topic using text classifier : {} ", id);

		Optional<Topics> topicOptional = topicsRepository.findByIdAndVisible(id, true);
		if (topicOptional.isPresent()) {
			Topics topics = topicOptional.get();

			topics.setVisible(false);
			topicsRepository.save(topics);
			postsRepository.hideAllPostsByTopicId(id, Instant.now());
			createNotification(topics.getUserId(), topics.getId(), null, Constants.TOPIC_HIDE_TITLE,
					Constants.TOPIC_HIDE_TEXT, NotificationType.HIDDEN);

			TopicElasticSearchDocument topicElasticSearchDocument = new TopicElasticSearchDocument();
			topicElasticSearchDocument.setId(topics.getId());
			topicElasticSearchDocument.setVisible(false);
			elasticSearchService.addTopicElasticSearchDocument(topicElasticSearchDocument);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Page<TopicOrPostResponse> findAllHiddenTopicOfCurrentUser(Pageable pageable) {
		log.debug("Request to get all hidden topics of current login user");

		Optional<String> userIdOptional = SecurityUtils.getCurrentUserLoginUserId();
		if (userIdOptional.isEmpty()) {
			throw new BadRequestAlertException("Invalid user id", "Users", "Invalid user id");
		}

		Optional<Users> usersOptional = usersRepository.findByUserId(userIdOptional.get());
		if (usersOptional.isEmpty()) {
			throw new BadRequestAlertException("User is not Onboarded", "Users", "User is not Onboarded");
		}
		Users users = usersOptional.get();

		Page<TopicOrPostResponse> topicPage = topicsRepository
				.findByUserIdAndVisible(userIdOptional.get(), false, pageable).map(topicResponseMapper::toDto);

		topicPage.getContent().forEach(topic -> {
			topic.setUsername(users.getUsername());
			topic.setAdmin(users.isAdmin());
			topic.setTrustLevel(users.getTrustLevel());
			topic.setUserTitle(users.getName());
		});
		return topicPage;
	}

	@Override
	@Transactional(readOnly = true)
	public Page<TopicOrPostResponse> findAllPrivateTopicOfCurrentUser(Pageable pageable) {
		log.debug("Request to get all Private topics of current login user");

		Optional<String> userIdOptional = SecurityUtils.getCurrentUserLoginUserId();
		if (userIdOptional.isEmpty()) {
			throw new BadRequestAlertException("Invalid user id", "Users", "Invalid user id");
		}

		Optional<Users> usersOptional = usersRepository.findByUserId(userIdOptional.get());
		if (usersOptional.isEmpty()) {
			throw new BadRequestAlertException("User is not Onboarded", "Users", "User is not Onboarded");
		}
		Users users = usersOptional.get();

		Page<TopicOrPostResponse> topicPage = topicsRepository.findByUserIdAndVisibleAndArchetype(userIdOptional.get(),
				true, ArcheType.PRIVATE_MESSAGE.name(), pageable).map(topicResponseMapper::toDto);

		topicPage.getContent().forEach(topic -> {
			topic.setUsername(users.getUsername());
			topic.setAdmin(users.isAdmin());
			topic.setTrustLevel(users.getTrustLevel());
			topic.setUserTitle(users.getName());
		});
		return topicPage;
	}

	@Override
	public Page<TopicOrPostResponse> findAllPrivateTopics(Pageable pageable) {
		log.debug("Request to get all Private Topics");

		Page<TopicOrPostResponse> topicPage = topicsRepository
				.findByArchetype(ArcheType.PRIVATE_MESSAGE.name(), pageable).map(topicResponseMapper::toDto);

		topicPage.getContent().forEach(topic -> {
			Optional<Users> usersOptional = usersRepository.findByUserId(topic.getUserId());
			if (usersOptional.isPresent()) {
				Users users = usersOptional.get();
				topic.setUsername(users.getUsername());
				topic.setAdmin(users.isAdmin());
				topic.setTrustLevel(users.getTrustLevel());
				topic.setUserTitle(users.getName());
			}
		});
		return topicPage;
	}

	private void createNotification(String userId, Long topicId, Long postId, String title, String text,
			NotificationType notificationType) {

		Notification notification = new Notification();
		notification.setUserId(userId);
		notification.setTopicId(topicId);
		notification.setPostId(postId);
		notification.setTitle(title);
		notification.setText(text);
		notification.setNotificationStatus(NotificationStatus.CREATED);
		notification.setNotificationType(notificationType);

		notificationService.createNotification(notification);
	}
}
