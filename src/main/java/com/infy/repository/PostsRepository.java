/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.repository;

import java.time.Instant;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.infy.domain.Posts;

/**
 * Spring Data repository for the Posts entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PostsRepository extends JpaRepository<Posts, Long>, JpaSpecificationExecutor<Posts> {

	Optional<Posts> findByIdAndPostStatus(Long id, Integer postStatus);

	Optional<Posts> findByIdAndPostStatusAndPostType(Long id, Integer postStatus, Integer postType);

	Page<Posts> findByTopicIdAndPostStatus(Long topicId, Integer postStatus, Pageable pageable);

	Page<Posts> findByTopicIdAndPostStatusAndPostType(Long topicId, Integer postStatus, Integer postType,
			Pageable pageable);

	Optional<Posts> findByTopicIdAndPostNumberAndPostStatus(Long topicId, Integer postNumber, Integer postStatus);

	Optional<Posts> findByTopicIdAndPostNumberAndPostStatusAndPostType(Long topicId, Integer postNumber,
			Integer postStatus, Integer postType);

	Page<Posts> findByTopicIdAndHiddenAndPostStatus(Long topicId, Boolean hidden, Integer postStatus,
			Pageable pageable);

	Page<Posts> findByHiddenAndPostStatusAndPostType(Boolean hidden, Integer postStatus, Integer postType,
			Pageable pageable);

	Page<Posts> findByHiddenAndPostStatus(Boolean hidden, Integer postStatus, Pageable pageable);

	Optional<Posts> findByIdAndHiddenAndPostStatus(Long id, Boolean hidden, Integer postStatus);

	Optional<Posts> findByIdAndHiddenAndPostStatusAndPostType(Long id, Boolean hidden, Integer postStatus,
			Integer postType);

	Page<Posts> findByUserIdAndPostStatus(String userId, Integer postStatus, Pageable pageable);

	Page<Posts> findByUserIdAndPostStatusAndPostType(String userId, Integer postStatus, Integer postType,
			Pageable pageable);

	Page<Posts> findByPostStatusAndPostType(Integer postStatus, Integer postType, Pageable pageable);

	Page<Posts> findByPostStatus(Integer postStatus, Pageable pageable);

	@Modifying
	@Query(value = "UPDATE Posts p SET p.hidden=true,p.hiddenAt=?2 WHERE p.topicId=?1 AND p.postStatus=1 AND p.hidden=false")
	void hideAllPostsByTopicId(Long id, Instant date);

	@Modifying
	@Query(value = "UPDATE Posts p SET p.hidden=false WHERE p.topicId=?1 AND p.postStatus=1")
	void unHideAllPostsByTopicId(Long id);

	Page<Posts> findByHiddenAndUserIdAndPostStatus(Boolean hidden, String userId, Integer postStatus,
			Pageable pageable);

	Page<Posts> findByHiddenAndUserIdAndPostStatusAndPostType(Boolean hidden, String userId, Integer postStatus,Integer postType,
			Pageable pageable);
}
