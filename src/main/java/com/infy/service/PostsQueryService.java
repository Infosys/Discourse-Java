/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.infy.domain.Posts;
import com.infy.domain.*; // for static metamodels
import com.infy.repository.PostsRepository;
import com.infy.service.dto.PostsCriteria;
import com.infy.service.dto.PostsDTO;
import com.infy.service.mapper.PostsMapper;

/**
 * Service for executing complex queries for {@link Posts} entities in the database.
 * The main input is a {@link PostsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PostsDTO} or a {@link Page} of {@link PostsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PostsQueryService extends QueryService<Posts> {

    private final Logger log = LoggerFactory.getLogger(PostsQueryService.class);

    private final PostsRepository postsRepository;

    private final PostsMapper postsMapper;

    public PostsQueryService(PostsRepository postsRepository, PostsMapper postsMapper) {
        this.postsRepository = postsRepository;
        this.postsMapper = postsMapper;
    }

    /**
     * Return a {@link List} of {@link PostsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PostsDTO> findByCriteria(PostsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Posts> specification = createSpecification(criteria);
        return postsMapper.toDto(postsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PostsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PostsDTO> findByCriteria(PostsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Posts> specification = createSpecification(criteria);
        return postsRepository.findAll(specification, page)
            .map(postsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PostsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Posts> specification = createSpecification(criteria);
        return postsRepository.count(specification);
    }

    /**
     * Function to convert {@link PostsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Posts> createSpecification(PostsCriteria criteria) {
        Specification<Posts> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Posts_.id));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserId(), Posts_.userId));
            }
            if (criteria.getTopicId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTopicId(), Posts_.topicId));
            }
            if (criteria.getPostNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPostNumber(), Posts_.postNumber));
            }
            if (criteria.getCooked() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCooked(), Posts_.cooked));
            }
            if (criteria.getReplyToPostNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReplyToPostNumber(), Posts_.replyToPostNumber));
            }
            if (criteria.getReplyCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReplyCount(), Posts_.replyCount));
            }
            if (criteria.getQuoteCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuoteCount(), Posts_.quoteCount));
            }
            if (criteria.getDeletedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeletedAt(), Posts_.deletedAt));
            }
            if (criteria.getOffTopicCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOffTopicCount(), Posts_.offTopicCount));
            }
            if (criteria.getLikeCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLikeCount(), Posts_.likeCount));
            }
            if (criteria.getIncomingLinkCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIncomingLinkCount(), Posts_.incomingLinkCount));
            }
            if (criteria.getBookmarkCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBookmarkCount(), Posts_.bookmarkCount));
            }
            if (criteria.getScore() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getScore(), Posts_.score));
            }
            if (criteria.getReads() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReads(), Posts_.reads));
            }
            if (criteria.getPostType() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPostType(), Posts_.postType));
            }
            if (criteria.getSortOrder() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSortOrder(), Posts_.sortOrder));
            }
            if (criteria.getLastEditorId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastEditorId(), Posts_.lastEditorId));
            }
            if (criteria.getHidden() != null) {
                specification = specification.and(buildSpecification(criteria.getHidden(), Posts_.hidden));
            }
            if (criteria.getHiddenReasonId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHiddenReasonId(), Posts_.hiddenReasonId));
            }
            if (criteria.getNotifyModeratorsCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNotifyModeratorsCount(), Posts_.notifyModeratorsCount));
            }
            if (criteria.getSpamCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSpamCount(), Posts_.spamCount));
            }
            if (criteria.getIllegalCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIllegalCount(), Posts_.illegalCount));
            }
            if (criteria.getInappropriateCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInappropriateCount(), Posts_.inappropriateCount));
            }
            if (criteria.getLastVersionAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastVersionAt(), Posts_.lastVersionAt));
            }
            if (criteria.getUserDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getUserDeleted(), Posts_.userDeleted));
            }
            if (criteria.getReplyToUserId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReplyToUserId(), Posts_.replyToUserId));
            }
            if (criteria.getPercentRank() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPercentRank(), Posts_.percentRank));
            }
            if (criteria.getNotifyUserCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNotifyUserCount(), Posts_.notifyUserCount));
            }
            if (criteria.getLikeScore() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLikeScore(), Posts_.likeScore));
            }
            if (criteria.getDeletedById() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDeletedById(), Posts_.deletedById));
            }
            if (criteria.getEditReason() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEditReason(), Posts_.editReason));
            }
            if (criteria.getWordCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWordCount(), Posts_.wordCount));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), Posts_.version));
            }
            if (criteria.getCookMethod() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCookMethod(), Posts_.cookMethod));
            }
            if (criteria.getWiki() != null) {
                specification = specification.and(buildSpecification(criteria.getWiki(), Posts_.wiki));
            }
            if (criteria.getBakedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBakedAt(), Posts_.bakedAt));
            }
            if (criteria.getBakedVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBakedVersion(), Posts_.bakedVersion));
            }
            if (criteria.getHiddenAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHiddenAt(), Posts_.hiddenAt));
            }
            if (criteria.getSelfEdits() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSelfEdits(), Posts_.selfEdits));
            }
            if (criteria.getReplyQuoted() != null) {
                specification = specification.and(buildSpecification(criteria.getReplyQuoted(), Posts_.replyQuoted));
            }
            if (criteria.getViaEmail() != null) {
                specification = specification.and(buildSpecification(criteria.getViaEmail(), Posts_.viaEmail));
            }
            if (criteria.getRawEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRawEmail(), Posts_.rawEmail));
            }
            if (criteria.getPublicVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPublicVersion(), Posts_.publicVersion));
            }
            if (criteria.getActionCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getActionCode(), Posts_.actionCode));
            }
            if (criteria.getLockedById() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLockedById(), Posts_.lockedById));
            }
            if (criteria.getImageUploadId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getImageUploadId(), Posts_.imageUploadId));
            }
            if (criteria.getPollsId() != null) {
                specification = specification.and(buildSpecification(criteria.getPollsId(),
                    root -> root.join(Posts_.polls, JoinType.LEFT).get(Polls_.id)));
            }
        }
        return specification;
    }
}
