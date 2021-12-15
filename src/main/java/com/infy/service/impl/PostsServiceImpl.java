/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infy.avroschema.TextClassificationRequest;
import com.infy.config.Constants;
import com.infy.domain.Notification;
import com.infy.domain.PostReplies;
import com.infy.domain.Posts;
import com.infy.domain.Topics;
import com.infy.domain.UserActions;
import com.infy.domain.Users;
import com.infy.domain.enumeration.ArcheType;
import com.infy.domain.enumeration.NotificationStatus;
import com.infy.domain.enumeration.NotificationType;
import com.infy.domain.enumeration.PostStatus;
import com.infy.domain.enumeration.PostType;
import com.infy.domain.enumeration.TextClassificationType;
import com.infy.domain.enumeration.UserActionType;
import com.infy.repository.PostRepliesRepository;
import com.infy.repository.PostsRepository;
import com.infy.repository.TopicsRepository;
import com.infy.repository.UserActionsRepository;
import com.infy.repository.UsersRepository;
import com.infy.security.SecurityUtils;
import com.infy.service.ElasticSearchService;
import com.infy.service.NotificationService;
import com.infy.service.PostsService;
import com.infy.service.TextClassificationProducer;
import com.infy.service.dto.PostsDTO;
import com.infy.service.mapper.PostResponseMapper;
import com.infy.service.mapper.PostsMapper;
import com.infy.service.model.TopicElasticSearchDocument;
import com.infy.service.model.TopicOrPostResponse;
import com.infy.service.model.UpdatePostRequest;
import com.infy.service.model.UpdatePostResponse;
import com.infy.web.rest.errors.BadRequestAlertException;

/**
 * Service Implementation for managing {@link Posts}.
 */
@Service
@Transactional
public class PostsServiceImpl implements PostsService {

	private final Logger log = LoggerFactory.getLogger(PostsServiceImpl.class);

	private final PostsRepository postsRepository;

	private final PostsMapper postsMapper;

	private final PostResponseMapper postResponseMapper;

	private final PostRepliesRepository postRepliesRepository;

	private final UsersRepository usersRepository;

	private final TopicsRepository topicsRepository;

	private final UserActionsRepository userActionsRepository;

	private final TextClassificationProducer textClassificationProducer;

	private final NotificationService notificationService;

	private final ElasticSearchService elasticSearchService;

	public PostsServiceImpl(PostsRepository postsRepository, PostsMapper postsMapper,
			PostResponseMapper postResponseMapper, PostRepliesRepository postRepliesRepository,
			UsersRepository usersRepository, TopicsRepository topicsRepository,
			UserActionsRepository userActionsRepository, TextClassificationProducer textClassificationProducer,
			NotificationService notificationService, ElasticSearchService elasticSearchService) {
		this.postsRepository = postsRepository;
		this.postsMapper = postsMapper;
		this.postResponseMapper = postResponseMapper;
		this.postRepliesRepository = postRepliesRepository;
		this.usersRepository = usersRepository;
		this.topicsRepository = topicsRepository;
		this.userActionsRepository = userActionsRepository;
		this.textClassificationProducer = textClassificationProducer;
		this.notificationService = notificationService;
		this.elasticSearchService = elasticSearchService;
	}

	@Override
	public PostsDTO save(PostsDTO postsDTO) {
		log.debug("Request to save Posts : {}", postsDTO);
		Posts posts = postsMapper.toEntity(postsDTO);
		posts = postsRepository.save(posts);
		return postsMapper.toDto(posts);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<PostsDTO> findAll(Pageable pageable) {
		log.debug("Request to get all Posts");
		return postsRepository.findAll(pageable).map(postsMapper::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<PostsDTO> findOne(Long id) {
		log.debug("Request to get Posts : {}", id);
		return postsRepository.findById(id).map(postsMapper::toDto);
	}

	@Override
	public void delete(Long id) {
		log.debug("Request to delete Posts : {}", id);
		postsRepository.deleteById(id);
	}

	@Override
	public UpdatePostResponse updatePosts(Long id, UpdatePostRequest updatePostRequest) {
		log.debug("Request to update post : {}", updatePostRequest);

		Optional<String> userIdOptional = SecurityUtils.getCurrentUserLoginUserId();
		if (userIdOptional.isEmpty()) {
			throw new BadRequestAlertException("Invalid user id", "Users", "Invalid user id");
		}

		Optional<Users> usersOptional = usersRepository.findByUserId(userIdOptional.get());
		if (usersOptional.isEmpty()) {
			throw new BadRequestAlertException("User is not Onboarded", "Users", "User is not Onboarded");
		}
		Users users = usersOptional.get();

		Optional<Posts> postsOptional = postsRepository.findByIdAndPostStatus(id, PostStatus.ACTIVE.getValue());
		if (postsOptional.isPresent()) {
			Posts posts = postsOptional.get();

			if (!(posts.getUserId().equals(userIdOptional.get()) || users.isAdmin())) {
				throw new BadRequestAlertException("You Have not created this post and You are not admin", "Posts",
						"You Have not created this post and You are not admin");
			}

			if (updatePostRequest.getUpdatedPost() != null) {
				posts.setRaw(updatePostRequest.getUpdatedPost().getRaw());
				posts.setEditReason(updatePostRequest.getUpdatedPost().getEditReason());
				posts.setLastEditorId(userIdOptional.get());
				posts.setLastVersionAt(Instant.now());
				posts.setPublicVersion(posts.getPublicVersion() + 1);
				posts.setLastVersionAt(Instant.now());
				posts.setVersion(posts.getVersion() + 1);

				posts = postsRepository.save(posts);

				if (!(posts.getPostType() == PostType.PRIVATE_MESSAGE.getValue())) {
					produceTextClassification(posts);
					if (posts.getPostNumber() == 1) {
						TopicElasticSearchDocument topicElasticSearchDocument = new TopicElasticSearchDocument();
						topicElasticSearchDocument.setId(posts.getTopicId());
						topicElasticSearchDocument.setRaw(posts.getRaw());
						elasticSearchService.addTopicElasticSearchDocument(topicElasticSearchDocument);
					}
				}

				if (users.isAdmin()) {
					createNotification(posts.getUserId(), posts.getTopicId(), posts.getId(), Constants.POST_EDIT_TITLE,
							Constants.POST_CREATE_TEXT, NotificationType.EDITED);
				}

				UpdatePostResponse updatePostResponse = new UpdatePostResponse();
				TopicOrPostResponse topicOrPostResponse = postResponseMapper.toDto(posts);
				topicOrPostResponse.setUsername(users.getUsername());
				topicOrPostResponse.setAdmin(users.isAdmin());
				topicOrPostResponse.setTrustLevel(users.getTrustLevel());
				topicOrPostResponse.setUserTitle(users.getName());
				updatePostResponse.setPost(topicOrPostResponse);

				return updatePostResponse;
			} else {
				throw new BadRequestAlertException("updated request post is null", "Post",
						"update post request post is null");
			}
		} else {
			throw new BadRequestAlertException("Invalid Id", "Posts", "Invalid Id");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<TopicOrPostResponse> findByIdAndResponse(Long id) {
		log.debug("Request to get Post : {}", id);

		Optional<String> userIdOptional = SecurityUtils.getCurrentUserLoginUserId();
		if (userIdOptional.isEmpty()) {
			throw new BadRequestAlertException("Invalid user id", "Users", "Invalid user id");
		}

		Optional<Users> loginUsersOptional = usersRepository.findByUserId(userIdOptional.get());
		if (loginUsersOptional.isEmpty()) {
			throw new BadRequestAlertException("User is not Onboarded", "Users", "User is not Onboarded");
		}
		Users loginUsers = loginUsersOptional.get();

		Optional<TopicOrPostResponse> postOptional;
		if (loginUsers.isAdmin() || loginUsers.isModerator()) {
			postOptional = postsRepository.findByIdAndPostStatus(id, PostStatus.ACTIVE.getValue())
					.map(postResponseMapper::toDto);
		} else {
			postOptional = postsRepository.findByIdAndHiddenAndPostStatus(id, false, PostStatus.ACTIVE.getValue())
					.map(postResponseMapper::toDto);
		}

		if (postOptional.isPresent()) {
			TopicOrPostResponse topicOrPostResponse = postOptional.get();

			if (topicOrPostResponse.getPostType() == PostType.PRIVATE_MESSAGE.getValue()) {
				if (!(loginUsers.isAdmin() || loginUsers.isModerator())) {
					if (!loginUsers.getUserId().equals(topicOrPostResponse.getUserId())) {
						return Optional.ofNullable(null);
					}
				}
			}
			topicOrPostResponse.setCurrentUserLike(false);

			List<UserActions> userActions = userActionsRepository.findByUserIdAndActionTypeAndTargetPostId(
					loginUsers.getUserId(), UserActionType.LIKE.getValue(), topicOrPostResponse.getId());
			if (!userActions.isEmpty())
				topicOrPostResponse.setCurrentUserLike(true);

			Optional<Users> usersOptional = usersRepository.findByUserId(topicOrPostResponse.getUserId());
			if (usersOptional.isPresent()) {
				Users users = usersOptional.get();
				topicOrPostResponse.setUsername(users.getUsername());
				topicOrPostResponse.setAdmin(users.isAdmin());
				topicOrPostResponse.setTrustLevel(users.getTrustLevel());
				topicOrPostResponse.setUserTitle(users.getName());
			}

			return Optional.of(topicOrPostResponse);
		}
		return Optional.ofNullable(null);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<TopicOrPostResponse> findByTopicIdAndResponse(Long topicId, Pageable pageable) {
		log.debug("Request to get posts using topic id : {}", topicId);

		Optional<String> userIdOptional = SecurityUtils.getCurrentUserLoginUserId();
		if (userIdOptional.isEmpty()) {
			throw new BadRequestAlertException("Invalid user id", "Users", "Invalid user id");
		}

		Optional<Users> loginUsersOptional = usersRepository.findByUserId(userIdOptional.get());
		if (loginUsersOptional.isEmpty()) {
			throw new BadRequestAlertException("User is not Onboarded", "Users", "User is not Onboarded");
		}
		Users loginUsers = loginUsersOptional.get();

		Page<TopicOrPostResponse> postPage;
		if (loginUsers.isAdmin() || loginUsers.isModerator()) {
			postPage = postsRepository.findByTopicIdAndPostStatus(topicId, PostStatus.ACTIVE.getValue(), pageable)
					.map(postResponseMapper::toDto);
		} else {
			Optional<Topics> topicOptional = topicsRepository.findById(topicId);

			if (topicOptional.isPresent()) {
				Topics topic = topicOptional.get();
				if (topic.getArchetype().equals(ArcheType.PRIVATE_MESSAGE.name())) {
					if (!loginUsers.getUserId().equals(topic.getUserId())) {
						throw new BadRequestAlertException("Private Topic", "Topic", "Private Topic");
					}
				}
			}
			postPage = postsRepository
					.findByTopicIdAndHiddenAndPostStatus(topicId, false, PostStatus.ACTIVE.getValue(), pageable)
					.map(postResponseMapper::toDto);
		}

		postPage.getContent().forEach(post -> {
			post.setCurrentUserLike(false);

			List<UserActions> userActions = userActionsRepository.findByUserIdAndActionTypeAndTargetPostId(
					loginUsers.getUserId(), UserActionType.LIKE.getValue(), post.getId());
			if (!userActions.isEmpty())
				post.setCurrentUserLike(true);

			Optional<Users> usersOptional = usersRepository.findByUserId(post.getUserId());
			if (usersOptional.isPresent()) {
				Users users = usersOptional.get();
				post.setUsername(users.getUsername());
				post.setAdmin(users.isAdmin());
				post.setTrustLevel(users.getTrustLevel());
				post.setUserTitle(users.getName());
			}
		});
		return postPage;
	}

	@Override
	@Transactional(readOnly = true)
	public Page<TopicOrPostResponse> findAllAndResponse(Pageable pageable) {
		log.debug("Request to get all Posts");

		Optional<String> userIdOptional = SecurityUtils.getCurrentUserLoginUserId();
		if (userIdOptional.isEmpty()) {
			throw new BadRequestAlertException("Invalid user id", "Users", "Invalid user id");
		}

		Optional<Users> loginUsersOptional = usersRepository.findByUserId(userIdOptional.get());
		if (loginUsersOptional.isEmpty()) {
			throw new BadRequestAlertException("User is not Onboarded", "Users", "User is not Onboarded");
		}
		Users loginUsers = loginUsersOptional.get();

		Page<TopicOrPostResponse> postPage;
		if (loginUsers.isAdmin() || loginUsers.isModerator()) {
			postPage = postsRepository.findByPostStatus(PostStatus.ACTIVE.getValue(), pageable)
					.map(postResponseMapper::toDto);
		} else {
			postPage = postsRepository.findByHiddenAndPostStatusAndPostType(false, PostStatus.ACTIVE.getValue(),
					PostType.NORMAL.getValue(), pageable).map(postResponseMapper::toDto);
		}

		postPage.getContent().forEach(post -> {
			post.setCurrentUserLike(false);

			List<UserActions> userActions = userActionsRepository.findByUserIdAndActionTypeAndTargetPostId(
					loginUsers.getUserId(), UserActionType.LIKE.getValue(), post.getId());
			if (!userActions.isEmpty())
				post.setCurrentUserLike(true);

			Optional<Users> usersOptional = usersRepository.findByUserId(post.getUserId());
			if (usersOptional.isPresent()) {
				Users users = usersOptional.get();
				post.setUsername(users.getUsername());
				post.setAdmin(users.isAdmin());
				post.setTrustLevel(users.getTrustLevel());
				post.setUserTitle(users.getName());
			}
		});
		return postPage;
	}

	@Override
	@Transactional(readOnly = true)
	public Page<TopicOrPostResponse> findAllReplyPosts(Long id, Pageable pageable) {
		log.debug("Request to get reply posts using id : {} ", id);

		Page<PostReplies> postRepliesPage = postRepliesRepository.findByPostId(id, pageable);

		Optional<String> userIdOptional = SecurityUtils.getCurrentUserLoginUserId();
		if (userIdOptional.isEmpty()) {
			throw new BadRequestAlertException("Invalid user id", "Users", "Invalid user id");
		}

		Optional<Users> loginUsersOptional = usersRepository.findByUserId(userIdOptional.get());
		if (loginUsersOptional.isEmpty()) {
			throw new BadRequestAlertException("User is not Onboarded", "Users", "User is not Onboarded");
		}
		Users loginUsers = loginUsersOptional.get();
		List<TopicOrPostResponse> postsList = new ArrayList<>();

		postRepliesPage.getContent().forEach(postReplies -> {
			Optional<TopicOrPostResponse> postOptional;
			if (loginUsers.isAdmin() || loginUsers.isModerator()) {
				postOptional = postsRepository
						.findByIdAndPostStatus(postReplies.getReplyPostId(), PostStatus.ACTIVE.getValue())
						.map(postResponseMapper::toDto);
			} else {
				postOptional = postsRepository.findByIdAndHiddenAndPostStatus(postReplies.getReplyPostId(), false,
						PostStatus.ACTIVE.getValue()).map(postResponseMapper::toDto);
			}

			if (postOptional.isPresent()) {
				TopicOrPostResponse post = postOptional.get();
				post.setCurrentUserLike(false);

				List<UserActions> userActions = userActionsRepository.findByUserIdAndActionTypeAndTargetPostId(
						loginUsers.getUserId(), UserActionType.LIKE.getValue(), post.getId());
				if (!userActions.isEmpty())
					post.setCurrentUserLike(true);

				Optional<Users> usersOptional = usersRepository.findByUserId(post.getUserId());
				if (usersOptional.isPresent()) {
					Users users = usersOptional.get();
					post.setUsername(users.getUsername());
					post.setAdmin(users.isAdmin());
					post.setTrustLevel(users.getTrustLevel());
					post.setUserTitle(users.getName());
				}
				postsList.add(post);
			}
		});

		return new PageImpl<>(postsList, postRepliesPage.getPageable(), postRepliesPage.getTotalElements());
	}

	@Override
	public void hidePost(Long id) {
		log.debug("Request to hide post by id : {} ", id);

		Optional<String> userIdOptional = SecurityUtils.getCurrentUserLoginUserId();
		if (userIdOptional.isEmpty()) {
			throw new BadRequestAlertException("Invalid user id", "Users", "Invalid user id");
		}

		Optional<Posts> postsOptional = postsRepository.findByIdAndPostStatus(id, PostStatus.ACTIVE.getValue());
		if (postsOptional.isEmpty()) {
			throw new BadRequestAlertException("Invalid post id", "Posts", "Invalid post id");
		}

		Posts posts = postsOptional.get();

		if (posts.isHidden()) {
			throw new BadRequestAlertException("Already Hidden Posts", "Posts", "Already Hidden Posts");
		}
		if (posts.getPostNumber() == 1) {
			throw new BadRequestAlertException("You can not hide primary post of topic hide topic", "Posts",
					"You can not hide primary post of topic hide topic");
		}

		if (posts.getReplyToPostNumber() != null) {
			Optional<Posts> replyPostOptional = postsRepository.findByIdAndPostStatus(posts.getReplyToPostNumber(),
					PostStatus.ACTIVE.getValue());
			if (replyPostOptional.isPresent()) {
				Posts replyPost = replyPostOptional.get();
				replyPost.setReplyCount(replyPost.getReplyCount() - 1);
				postsRepository.save(replyPost);
			}
		}
		topicsRepository.decreasePostAndReplyCountById(posts.getTopicId());

		posts.setHidden(true);
		posts.setHiddenAt(Instant.now());
		posts.setLockedById(userIdOptional.get());
		postsRepository.save(posts);

		createNotification(posts.getUserId(), posts.getTopicId(), posts.getId(), Constants.POST_HIDE_TITLE,
				Constants.POST_HIDE_TEXT, NotificationType.HIDDEN);

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
//			Optional<Posts> postsOptional = postsRepository.findByIdAndPostStatus(id, PostStatus.ACTIVE.getValue());
//			if (postsOptional.isEmpty()) {
//				throw new BadRequestAlertException("Invalid post id", "Posts", "Invalid post id");
//			}
//
//			Posts posts = postsOptional.get();
//
//			if (posts.isHidden()) {
//				throw new BadRequestAlertException("Already Hidden Posts", "Posts", "Already Hidden Posts");
//			}
//			if (posts.getPostNumber() == 1) {
//				throw new BadRequestAlertException("You can not hide primary post of topic hide topic", "Posts",
//						"You can not hide primary post of topic hide topic");
//			}
//
//			if (posts.getReplyToPostNumber() != null) {
//				Optional<Posts> replyPostOptional = postsRepository.findByIdAndPostStatus(posts.getReplyToPostNumber(),
//						PostStatus.ACTIVE.getValue());
//				if (replyPostOptional.isPresent()) {
//					Posts replyPost = replyPostOptional.get();
//					replyPost.setReplyCount(replyPost.getReplyCount() - 1);
//					postsRepository.save(replyPost);
//				}
//			}
//			topicsRepository.decreasePostAndReplyCountById(posts.getTopicId());
//
//			posts.setHidden(true);
//			posts.setHiddenAt(Instant.now());
//			posts.setLockedById(users.getUserId());
//			postsRepository.save(posts);
//
//			createNotification(posts.getUserId(), posts.getTopicId(), posts.getId(), Constants.POST_HIDE_TITLE,
//					Constants.POST_HIDE_TEXT, NotificationType.HIDDEN);
//
//		} else {
//			throw new BadRequestAlertException("Only Admin or Moderator Hide Post", "Posts",
//					"Only Admin or Moderator Hide Post");
//		}
	}

	@Override
	public void unHidePost(Long id) {
		log.debug("Request to unHide post by id : {} ", id);

		Optional<Posts> postsOptional = postsRepository.findByIdAndPostStatus(id, PostStatus.ACTIVE.getValue());
		if (postsOptional.isEmpty()) {
			throw new BadRequestAlertException("Invalid post id", "Posts", "Invalid post id");
		}

		Posts posts = postsOptional.get();

		if (!posts.isHidden()) {
			throw new BadRequestAlertException("Already unHidden Posts", "Posts", "Already unHidden Posts");
		}

		if (posts.getPostNumber() == 1) {
			throw new BadRequestAlertException("You can not unhide primary post you can unhide topic", "Posts",
					"You can not unhide primary post you can unhide topic");
		}

		if (posts.getReplyToPostNumber() != null) {
			Optional<Posts> replyPostOptional = postsRepository.findByIdAndPostStatus(posts.getReplyToPostNumber(),
					PostStatus.ACTIVE.getValue());
			if (replyPostOptional.isPresent()) {
				Posts replyPost = replyPostOptional.get();
				replyPost.setReplyCount(replyPost.getReplyCount() + 1);
				postsRepository.save(replyPost);
			}
		}
		topicsRepository.increasePostCountAndReplyCountById(posts.getTopicId());

		posts.setHidden(false);
		postsRepository.save(posts);

		createNotification(posts.getUserId(), posts.getTopicId(), posts.getId(), Constants.POST_UNHIDE_TITLE,
				Constants.POST_UNHIDE_TEXT, NotificationType.UNHIDE);

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
//			Optional<Posts> postsOptional = postsRepository.findByIdAndPostStatus(id, PostStatus.ACTIVE.getValue());
//			if (postsOptional.isEmpty()) {
//				throw new BadRequestAlertException("Invalid post id", "Posts", "Invalid post id");
//			}
//
//			Posts posts = postsOptional.get();
//
//			if (!posts.isHidden()) {
//				throw new BadRequestAlertException("Already unHidden Posts", "Posts", "Already unHidden Posts");
//			}
//
//			if (posts.getPostNumber() == 1) {
//				throw new BadRequestAlertException("You can not unhide primary post you can unhide topic", "Posts",
//						"You can not unhide primary post you can unhide topic");
//			}
//
//			if (posts.getReplyToPostNumber() != null) {
//				Optional<Posts> replyPostOptional = postsRepository.findByIdAndPostStatus(posts.getReplyToPostNumber(),
//						PostStatus.ACTIVE.getValue());
//				if (replyPostOptional.isPresent()) {
//					Posts replyPost = replyPostOptional.get();
//					replyPost.setReplyCount(replyPost.getReplyCount() + 1);
//					postsRepository.save(replyPost);
//				}
//			}
//			topicsRepository.increasePostCountAndReplyCountById(posts.getTopicId());
//
//			posts.setHidden(false);
//			postsRepository.save(posts);
//
//			createNotification(posts.getUserId(), posts.getTopicId(), posts.getId(), Constants.POST_UNHIDE_TITLE,
//					Constants.POST_UNHIDE_TEXT, NotificationType.UNHIDE);
//
//		} else {
//			throw new BadRequestAlertException("Only Admin or Moderator unHide Post", "Posts",
//					"Only Admin or Moderator unHide Post");
//		}
	}

	@Override
	@Transactional(readOnly = true)
	public Page<TopicOrPostResponse> findByCurrentLoginUser(Pageable pageable) {
		log.debug("Request to get all posts page of current login user");

		Optional<String> userIdOptional = SecurityUtils.getCurrentUserLoginUserId();
		if (userIdOptional.isEmpty()) {
			throw new BadRequestAlertException("Invalid user id", "Users", "Invalid user id");
		}

		Optional<Users> usersOptional = usersRepository.findByUserId(userIdOptional.get());
		if (usersOptional.isEmpty()) {
			throw new BadRequestAlertException("User is not Onboarded", "Users", "User is not Onboarded");
		}
		Users users = usersOptional.get();

		Page<TopicOrPostResponse> postPage = postsRepository
				.findByUserIdAndPostStatus(userIdOptional.get(), PostStatus.ACTIVE.getValue(), pageable)
				.map(postResponseMapper::toDto);

		postPage.getContent().forEach(post -> {
			post.setCurrentUserLike(false);

			List<UserActions> userActions = userActionsRepository.findByUserIdAndActionTypeAndTargetPostId(
					users.getUserId(), UserActionType.LIKE.getValue(), post.getId());
			if (!userActions.isEmpty())
				post.setCurrentUserLike(true);

			Optional<Topics> topicOptional = topicsRepository.findById(post.getTopicId());
			if (topicOptional.isPresent()) {
				post.setTopicSlug(topicOptional.get().getTitle());
				post.setCategoryId(topicOptional.get().getCategoryId());
			}

			post.setUsername(users.getUsername());
			post.setAdmin(users.isAdmin());
			post.setTrustLevel(users.getTrustLevel());
			post.setUserTitle(users.getName());
		});
		return postPage;
	}

	@Override
	@Transactional(readOnly = true)
	public Page<TopicOrPostResponse> findAllReplyPostOfCurrentUser(Pageable pageable) {
		log.debug("Request to get All Reply posts of current user");

		Optional<String> userIdOptional = SecurityUtils.getCurrentUserLoginUserId();
		if (userIdOptional.isEmpty()) {
			throw new BadRequestAlertException("Invalid user id", "Users", "Invalid user id");
		}

		List<TopicOrPostResponse> posts = new ArrayList<>();
		Page<UserActions> userActionsPage = userActionsRepository.findByUserIdAndActionType(userIdOptional.get(),
				UserActionType.REPLY.getValue(), pageable);

		userActionsPage.getContent().forEach(userAction -> {

			Optional<TopicOrPostResponse> postOptional = postsRepository
					.findByIdAndPostStatus(userAction.getTargetPostId(), PostStatus.ACTIVE.getValue())
					.map(postResponseMapper::toDto);
			if (postOptional.isPresent()) {

				TopicOrPostResponse post = postOptional.get();
				Optional<Topics> topicOptional = topicsRepository.findById(post.getTopicId());
				if (topicOptional.isPresent()) {
					post.setTopicSlug(topicOptional.get().getTitle());
					post.setCategoryId(topicOptional.get().getCategoryId());
				}

				Optional<Users> usersOptional = usersRepository.findByUserId(post.getUserId());
				if (usersOptional.isPresent()) {
					Users users = usersOptional.get();
					post.setUsername(users.getUsername());
					post.setAdmin(users.isAdmin());
					post.setTrustLevel(users.getTrustLevel());
					post.setUserTitle(users.getName());
				}
				posts.add(post);
			}

		});

		return new PageImpl<>(posts, userActionsPage.getPageable(), userActionsPage.getTotalElements());
	}

	@Override
	@Transactional(readOnly = true)
	public Page<TopicOrPostResponse> findAllLikedPostOfCurrentUser(Pageable pageable) {
		log.debug("Request to get All Liked posts of current user");

		Optional<String> userIdOptional = SecurityUtils.getCurrentUserLoginUserId();
		if (userIdOptional.isEmpty()) {
			throw new BadRequestAlertException("Invalid user id", "Users", "Invalid user id");
		}

		List<TopicOrPostResponse> posts = new ArrayList<>();
		Page<UserActions> userActionsPage = userActionsRepository.findByUserIdAndActionType(userIdOptional.get(),
				UserActionType.LIKE.getValue(), pageable);

		userActionsPage.getContent().forEach(userAction -> {

			Optional<TopicOrPostResponse> postOptional = postsRepository
					.findByIdAndPostStatus(userAction.getTargetPostId(), PostStatus.ACTIVE.getValue())
					.map(postResponseMapper::toDto);
			if (postOptional.isPresent()) {

				TopicOrPostResponse post = postOptional.get();
				Optional<Topics> topicOptional = topicsRepository.findById(post.getTopicId());
				if (topicOptional.isPresent()) {
					post.setTopicSlug(topicOptional.get().getTitle());
					post.setCategoryId(topicOptional.get().getCategoryId());
				}

				Optional<Users> usersOptional = usersRepository.findByUserId(post.getUserId());
				if (usersOptional.isPresent()) {
					Users users = usersOptional.get();
					post.setUsername(users.getUsername());
					post.setAdmin(users.isAdmin());
					post.setTrustLevel(users.getTrustLevel());
					post.setUserTitle(users.getName());
				}
				post.setCurrentUserLike(true);
				posts.add(post);
			}
		});

		return new PageImpl<>(posts, userActionsPage.getPageable(), userActionsPage.getTotalElements());
	}

	@Override
	public void safeDeletePost(Long id) {
		log.debug("Request to delete Posts : {} ", id);

		Optional<String> userIdOptional = SecurityUtils.getCurrentUserLoginUserId();
		if (userIdOptional.isEmpty()) {
			throw new BadRequestAlertException("Invalid Userid", "Users", "Invalid Userid");
		}

		Optional<Users> userOptional = usersRepository.findByUserId(userIdOptional.get());
		if (userOptional.isEmpty()) {
			throw new BadRequestAlertException("User is not Onboarded", "Users", "User is not Onboarded");
		}
		Users user = userOptional.get();

		Optional<Posts> postOptional = postsRepository.findById(id);
		if (postOptional.isEmpty()) {
			throw new BadRequestAlertException("Invalid post id", "Posts", "Invalid post id");
		}
		Posts post = postOptional.get();

		if (post.getPostStatus() == PostStatus.INACTIVE.getValue()) {
			throw new BadRequestAlertException("Posts is Already Deleted", "Posts", "Posts is Already Deleted");
		}

		if (!(post.getUserId().equals(user.getUserId()) || user.isAdmin())) {
			throw new BadRequestAlertException("You Have not created this post and You are not admin", "Posts",
					"You Have not created this post and You are not admin");
		}

		if (post.getPostNumber() == 1) {
			throw new BadRequestAlertException("You can not delete primary post of topic you can hide topic", "Posts",
					"You can not delete primary post of topic you can hide topic");
		}

		if (!post.isHidden()) {
			Optional<Topics> topicOptional = topicsRepository.findById(post.getTopicId());
			if (topicOptional.isPresent()) {
				Topics topic = topicOptional.get();
				topic.setPostsCount(topic.getPostsCount() - 1);
				if (post.getReplyToPostNumber() != null) {
					topic.setReplyCount(topic.getReplyCount() - 1);
				}
				topicsRepository.save(topic);
			}

			if (post.getReplyToPostNumber() != null) {
				Optional<Posts> replyPostOptional = postsRepository.findById(post.getReplyToPostNumber());
				if (replyPostOptional.isPresent()) {
					Posts replyPost = replyPostOptional.get();
					replyPost.setReplyCount(replyPost.getReplyCount() - 1);
					postsRepository.save(replyPost);
				}
			}
		}

		if (user.isAdmin()) {
			createNotification(post.getUserId(), post.getTopicId(), post.getId(), Constants.POST_DELETE_TITLE,
					Constants.POST_DELETE_TEXT, NotificationType.DELETED);
		}

		post.setPostStatus(PostStatus.INACTIVE.getValue());
		post.setDeletedAt(Instant.now());
		post.setDeletedById(user.getUserId());

		postsRepository.save(post);
	}

	private void produceTextClassification(Posts posts) {
		TextClassificationRequest textClassificationRequest = new TextClassificationRequest();
		textClassificationRequest.setId(posts.getId());
		textClassificationRequest.setType(TextClassificationType.POST.toString());
		textClassificationRequest.setContent(posts.getRaw());

		textClassificationProducer.produceTextClassification(textClassificationRequest);
	}

	@Override
	public void setPostsAsHidden(Long id) {
		log.debug("Request to set posts as Hidden using Text Classifier using id : {} ", id);

		Optional<Posts> postsOptional = postsRepository.findByIdAndPostStatus(id, PostStatus.ACTIVE.getValue());
		if (postsOptional.isPresent()) {
			Posts posts = postsOptional.get();

			if (!posts.isHidden()) {

				if (posts.getReplyToPostNumber() != null) {
					Optional<Posts> replyPostOptional = postsRepository
							.findByIdAndPostStatus(posts.getReplyToPostNumber(), PostStatus.ACTIVE.getValue());
					if (replyPostOptional.isPresent()) {
						Posts replyPost = replyPostOptional.get();
						replyPost.setReplyCount(replyPost.getReplyCount() - 1);
						postsRepository.save(replyPost);
					}
				}
				topicsRepository.decreasePostAndReplyCountById(posts.getTopicId());

				posts.setHidden(true);
				posts.setHiddenAt(Instant.now());
				postsRepository.save(posts);

				createNotification(posts.getUserId(), posts.getTopicId(), posts.getId(), Constants.POST_HIDE_TITLE,
						Constants.POST_HIDE_TEXT, NotificationType.HIDDEN);
			}
		}

	}

	@Override
	@Transactional(readOnly = true)
	public Page<TopicOrPostResponse> findAllHiddenPostOfCurrentUser(Pageable pageable) {
		log.debug("Request to get All Hidden posts of current user");

		Optional<String> userIdOptional = SecurityUtils.getCurrentUserLoginUserId();
		if (userIdOptional.isEmpty()) {
			throw new BadRequestAlertException("Invalid user id", "Users", "Invalid user id");
		}

		Page<TopicOrPostResponse> postsPage = postsRepository
				.findByHiddenAndUserIdAndPostStatus(true, userIdOptional.get(), PostStatus.ACTIVE.getValue(), pageable)
				.map(postResponseMapper::toDto);

		postsPage.getContent().forEach(post -> {
			Optional<Topics> topicOptional = topicsRepository.findById(post.getTopicId());
			if (topicOptional.isPresent()) {
				post.setTopicSlug(topicOptional.get().getTitle());
				post.setCategoryId(topicOptional.get().getCategoryId());
			}
		});
		return postsPage;
	}

	@Override
	@Transactional(readOnly = true)
	public Page<TopicOrPostResponse> findAllPrivatePostOfCurrentUser(Pageable pageable) {
		log.debug("Request to get All Hidden posts of current user");

		Optional<String> userIdOptional = SecurityUtils.getCurrentUserLoginUserId();
		if (userIdOptional.isEmpty()) {
			throw new BadRequestAlertException("Invalid user id", "Users", "Invalid user id");
		}

		Page<TopicOrPostResponse> postsPage = postsRepository
				.findByHiddenAndUserIdAndPostStatusAndPostType(false, userIdOptional.get(), PostStatus.ACTIVE.getValue(),PostType.PRIVATE_MESSAGE.getValue(), pageable)
				.map(postResponseMapper::toDto);

		postsPage.getContent().forEach(post -> {
			Optional<Topics> topicOptional = topicsRepository.findById(post.getTopicId());
			if (topicOptional.isPresent()) {
				post.setTopicSlug(topicOptional.get().getTitle());
				post.setCategoryId(topicOptional.get().getCategoryId());
			}
		});
		return postsPage;
	}

	private void createNotification(String userId, Long topicId, Long postId, String title, String text,
			NotificationType notificationType) {

		Notification notification = new Notification();
		notification.setUserId(userId);
		notification.setTopicId(topicId);
		notification.setPostId(postId);
		notification.setTitle(title);
		notification.setText(text);
		notification.setNotificationType(notificationType);
		notification.setNotificationStatus(NotificationStatus.CREATED);

		notificationService.createNotification(notification);
	}

}
