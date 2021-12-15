/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.infy.service.dto.PostsDTO;
import com.infy.service.model.TopicOrPostResponse;
import com.infy.service.model.UpdatePostRequest;
import com.infy.service.model.UpdatePostResponse;

/**
 * Service Interface for managing {@link com.infy.domain.Posts}.
 */
public interface PostsService {

	/**
	 * Save a posts.
	 *
	 * @param postsDTO the entity to save.
	 * @return the persisted entity.
	 */
	PostsDTO save(PostsDTO postsDTO);

	/**
	 * Get all the posts.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	Page<PostsDTO> findAll(Pageable pageable);

	/**
	 * Get the "id" posts.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	Optional<PostsDTO> findOne(Long id);

	/**
	 * Delete the "id" posts.
	 *
	 * @param id the id of the entity.
	 */
	void delete(Long id);

	UpdatePostResponse updatePosts(Long id, UpdatePostRequest updatePostRequest);

	Optional<TopicOrPostResponse> findByIdAndResponse(Long id);

	Page<TopicOrPostResponse> findByTopicIdAndResponse(Long topicId, Pageable pageable);

	Page<TopicOrPostResponse> findAllAndResponse(Pageable pageable);

	Page<TopicOrPostResponse> findAllReplyPosts(Long id, Pageable pageable);

	void hidePost(Long id);

	void unHidePost(Long id);

	Page<TopicOrPostResponse> findByCurrentLoginUser(Pageable pageable);

	Page<TopicOrPostResponse> findAllReplyPostOfCurrentUser(Pageable pageable);

	Page<TopicOrPostResponse> findAllLikedPostOfCurrentUser(Pageable pageable);

	void safeDeletePost(Long id);

	void setPostsAsHidden(Long id);

	Page<TopicOrPostResponse> findAllHiddenPostOfCurrentUser(Pageable pageable);

	Page<TopicOrPostResponse> findAllPrivatePostOfCurrentUser(Pageable pageable);

}
