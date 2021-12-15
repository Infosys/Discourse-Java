/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infy.domain.PostActions;
import com.infy.domain.Posts;
import com.infy.domain.Topics;
import com.infy.domain.UserActions;
import com.infy.domain.UserStats;
import com.infy.domain.enumeration.PostStatus;
import com.infy.domain.enumeration.UserActionType;
import com.infy.repository.PostActionsRepository;
import com.infy.repository.PostsRepository;
import com.infy.repository.TopicsRepository;
import com.infy.repository.UserActionsRepository;
import com.infy.repository.UserStatsRepository;
import com.infy.security.SecurityUtils;
import com.infy.service.PostActionsService;
import com.infy.service.dto.PostActionsDTO;
import com.infy.service.mapper.PostActionsMapper;
import com.infy.service.mapper.PostResponseMapper;
import com.infy.service.model.CreatePostActionRequest;
import com.infy.service.model.TopicOrPostResponse;
import com.infy.web.rest.errors.BadRequestAlertException;

/**
 * Service Implementation for managing {@link PostActions}.
 */
@Service
@Transactional
public class PostActionsServiceImpl implements PostActionsService {

	private final Logger log = LoggerFactory.getLogger(PostActionsServiceImpl.class);

	private final PostActionsRepository postActionsRepository;

	private final PostActionsMapper postActionsMapper;

	private final PostsRepository postsRepository;

	private final PostResponseMapper postResponseMapper;

	private final UserActionsRepository userActionsRepository;

	private final UserStatsRepository userStatsRepository;

	private final TopicsRepository topicsRepository;

	public PostActionsServiceImpl(PostActionsRepository postActionsRepository, PostActionsMapper postActionsMapper,
			PostsRepository postsRepository, PostResponseMapper postResponseMapper,
			UserActionsRepository userActionsRepository, UserStatsRepository userStatsRepository,TopicsRepository topicsRepository) {
		this.postActionsRepository = postActionsRepository;
		this.postActionsMapper = postActionsMapper;
		this.postsRepository = postsRepository;
		this.postResponseMapper = postResponseMapper;
		this.userActionsRepository = userActionsRepository;
		this.userStatsRepository = userStatsRepository;
		this.topicsRepository = topicsRepository;
	}

	@Override
	public PostActionsDTO save(PostActionsDTO postActionsDTO) {
		log.debug("Request to save PostActions : {}", postActionsDTO);
		PostActions postActions = postActionsMapper.toEntity(postActionsDTO);
		postActions = postActionsRepository.save(postActions);
		return postActionsMapper.toDto(postActions);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<PostActionsDTO> findAll(Pageable pageable) {
		log.debug("Request to get all PostActions");
		return postActionsRepository.findAll(pageable).map(postActionsMapper::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<PostActionsDTO> findOne(Long id) {
		log.debug("Request to get PostActions : {}", id);
		return postActionsRepository.findById(id).map(postActionsMapper::toDto);
	}

	@Override
	public void delete(Long id) {
		log.debug("Request to delete PostActions : {}", id);
		postActionsRepository.deleteById(id);
	}

	@Override
	public TopicOrPostResponse createPostAction(CreatePostActionRequest createPostActionRequest) {
		log.debug("Request to create post action : {} ", createPostActionRequest);

		Optional<String> userIdOptional = SecurityUtils.getCurrentUserLoginUserId();
		if (userIdOptional.isEmpty()) {
			throw new BadRequestAlertException("Invalid UserId", "Users", "Invalid UserId");
		}

		Optional<UserStats> usersOptional = userStatsRepository.findByUserId(userIdOptional.get());
		if (usersOptional.isEmpty()) {
			throw new BadRequestAlertException("User is not Onboarded", "Users", "User is not Onboarded");
		}
		UserStats userStats = usersOptional.get();

		Optional<Posts> postsOptional = postsRepository.findByIdAndPostStatus(createPostActionRequest.getId(),PostStatus.ACTIVE.getValue());
		if (postsOptional.isEmpty()) {
			throw new BadRequestAlertException("Invalid post id", "PostAction", "Invalid post id");
		}

		List<UserActions> existingUserActions = userActionsRepository.findByUserIdAndActionTypeAndTargetPostId(
				userStats.getUserId(), UserActionType.LIKE.getValue(), createPostActionRequest.getId());
		if (!existingUserActions.isEmpty()) {
			throw new BadRequestAlertException("Already Like Post", "UserAction", "Already Like Post");
		}

		PostActions postActions = new PostActions();
		postActions.setPostId(createPostActionRequest.getId());
		postActions.setPostActionTypeId(createPostActionRequest.getPostActionTypeId());
		postActions.setUserId(userIdOptional.get());
		postActions.setStaffTookAction(true);
		postActions.setTargetsTopic(true);
		postActionsRepository.save(postActions);

		Posts posts = postsOptional.get();
		posts.setLikeCount(posts.getLikeCount() + 1);
		posts.setLikeScore(posts.getLikeScore() + 1);
		posts = postsRepository.save(posts);

		Optional<Topics> topicOptional = topicsRepository.findById(posts.getTopicId());
		if(topicOptional.isPresent()) {
			Topics topic = topicOptional.get();
			topic.setLikeCount(topic.getLikeCount()+1);
			topicsRepository.save(topic);
		}

		TopicOrPostResponse topicOrPostResponse = postResponseMapper.toDto(posts);
		if (userIdOptional.get().equals(posts.getUserId())) {
			topicOrPostResponse.setYours(true);
		} else {
			topicOrPostResponse.setYours(false);
		}

		UserActions userActions = new UserActions();
		userActions.setActingUserId(userStats.getUserId());
		userActions.setActionType(UserActionType.LIKE.getValue());
		userActions.setUserId(userStats.getUserId());
		userActions.setTargetPostId(posts.getId());
		userActions.setTargetTopicId(posts.getTopicId());
		userActions.setTargetUserId(posts.getUserId());
		userActionsRepository.save(userActions);

		userStats.setLikesGiven(userStats.getLikesGiven() + 1);
		userStats.setPostsReadCount(userStats.getPostsReadCount() + 1);
		userStatsRepository.save(userStats);

		Optional<UserStats> userStatsOptional = userStatsRepository.findByUserId(posts.getUserId());
		if (userStatsOptional.isPresent()) {
			UserStats postCreatedUserStats = userStatsOptional.get();
			postCreatedUserStats.setLikesReceived(postCreatedUserStats.getLikesReceived() + 1);
			userStatsRepository.save(postCreatedUserStats);
		}

		topicOrPostResponse.setCurrentUserLike(true);
		return topicOrPostResponse;
	}

	@Override
	public TopicOrPostResponse removePostAction(CreatePostActionRequest createPostActionRequest) {
		log.debug("Request to remove post action : {} ", createPostActionRequest);

		Optional<String> userIdOptional = SecurityUtils.getCurrentUserLoginUserId();
		if (userIdOptional.isEmpty()) {
			throw new BadRequestAlertException("Invalid UserId", "Users", "Invalid UserId");
		}

		Optional<UserStats> usersOptional = userStatsRepository.findByUserId(userIdOptional.get());
		if (usersOptional.isEmpty()) {
			throw new BadRequestAlertException("User is not Onboarded", "Users", "User is not Onboarded");
		}
		UserStats userStats = usersOptional.get();

		Optional<Posts> postsOptional = postsRepository.findByIdAndPostStatus(createPostActionRequest.getId(),PostStatus.ACTIVE.getValue());
		if (postsOptional.isEmpty()) {
			throw new BadRequestAlertException("Invalid post id", "PostAction", "Invalid post id");
		}

		List<UserActions> existingUserActions = userActionsRepository.findByUserIdAndActionTypeAndTargetPostId(
				userStats.getUserId(), UserActionType.LIKE.getValue(), createPostActionRequest.getId());
		if (existingUserActions.isEmpty()) {
			throw new BadRequestAlertException("You did not Like this post", "UserAction",
					"You did not Like this post");
		}

		existingUserActions.forEach(existingUserAction -> {
			userActionsRepository.deleteById(existingUserAction.getId());
		});

		Posts posts = postsOptional.get();
		posts.setLikeCount(posts.getLikeCount() - 1);
		posts.setLikeScore(posts.getLikeScore() - 1);
		posts = postsRepository.save(posts);

		Optional<Topics> topicOptional = topicsRepository.findById(posts.getTopicId());
		if(topicOptional.isPresent()) {
			Topics topic = topicOptional.get();
			topic.setLikeCount(topic.getLikeCount()-1);
			topicsRepository.save(topic);
		}

		TopicOrPostResponse topicOrPostResponse = postResponseMapper.toDto(posts);
		if (userIdOptional.get().equals(posts.getUserId())) {
			topicOrPostResponse.setYours(true);
		} else {
			topicOrPostResponse.setYours(false);
		}

		userStats.setLikesGiven(userStats.getLikesGiven() - 1);
		userStatsRepository.save(userStats);

		Optional<UserStats> userStatsOptional = userStatsRepository.findByUserId(posts.getUserId());
		if (userStatsOptional.isPresent()) {
			UserStats postCreatedUserStats = userStatsOptional.get();
			postCreatedUserStats.setLikesReceived(postCreatedUserStats.getLikesReceived() - 1);
			userStatsRepository.save(postCreatedUserStats);
		}

		topicOrPostResponse.setCurrentUserLike(false);
		return topicOrPostResponse;
	}
}
