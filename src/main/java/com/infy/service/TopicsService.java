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

import com.infy.service.dto.TopicsDTO;
import com.infy.service.model.TopicOrPostResponse;
import com.infy.service.model.TopicResponse;
import com.infy.service.model.UpdateTopicRequest;
import com.infy.service.model.UpdateTopicResponse;

/**
 * Service Interface for managing {@link com.infy.domain.Topics}.
 */
public interface TopicsService {

	/**
	 * Save a topics.
	 *
	 * @param topicsDTO the entity to save.
	 * @return the persisted entity.
	 */
	TopicsDTO save(TopicsDTO topicsDTO);

	/**
	 * Get all the topics.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	Page<TopicsDTO> findAll(Pageable pageable);

	/**
	 * Get the "id" topics.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	Optional<TopicsDTO> findOne(Long id);

	/**
	 * Delete the "id" topics.
	 *
	 * @param id the id of the entity.
	 */
	void delete(Long id);

	UpdateTopicResponse updateTopics(Long id, UpdateTopicRequest updateTopicRequest);

	Optional<TopicResponse> findByIdAndResponse(Long id);

	Page<TopicOrPostResponse> findByCategoryIdAndResponse(Long categoryId, Pageable pageable);

	Page<TopicOrPostResponse> findAllAndResponse(Pageable pageable);

	Page<TopicOrPostResponse> findByCurrentLoginUser(Pageable pageable);

	void hideTopic(Long id);

	void unHideTopic(Long id);

	void hideTopicUsingClassifier(Long id);

	Page<TopicOrPostResponse> findAllHiddenTopicOfCurrentUser(Pageable pageable);

	Page<TopicOrPostResponse> findAllPrivateTopicOfCurrentUser(Pageable pageable);

	Page<TopicOrPostResponse> findAllPrivateTopics(Pageable pageable);
}
