/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.repository;

import com.infy.domain.Topics;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the Topics entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TopicsRepository extends JpaRepository<Topics, Long>, JpaSpecificationExecutor<Topics> {

	Page<Topics> findByCategoryId(Long categoryId, Pageable pageable);

	Page<Topics> findByCategoryIdAndArchetype(Long categoryId, String archetype, Pageable pageable);

	Page<Topics> findByCategoryIdAndVisible(Long categoryId, Boolean visible, Pageable pageable);

	Page<Topics> findByCategoryIdAndVisibleAndArchetype(Long categoryId, Boolean visible, String archetype,
			Pageable pageable);

	Page<Topics> findByUserIdAndVisible(String userId, Boolean visible, Pageable pageable);

	Optional<Topics> findByIdAndVisible(Long id, Boolean visible);

	Page<Topics> findByVisible(Boolean visible, Pageable pageable);

	Page<Topics> findByVisibleAndArchetype(Boolean visible, String archetype, Pageable pageable);

	@Modifying
	@Query(value = "UPDATE Topics t SET t.postsCount = t.postsCount - 1 , t.replyCount = t.replyCount - 1 WHERE t.id=?1")
	void decreasePostAndReplyCountById(Long id);

	@Modifying
	@Query(value = "UPDATE Topics t SET t.postsCount = t.postsCount + 1 , t.replyCount = t.replyCount + 1 WHERE t.id=?1")
	void increasePostCountAndReplyCountById(Long id);

	Page<Topics> findByUserIdAndVisibleAndArchetype(String userId, Boolean visible, String archetype,
			Pageable pageable);

	Page<Topics> findByArchetype(String archetype, Pageable pageable);
}
