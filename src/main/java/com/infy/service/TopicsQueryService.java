/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// for static metamodels
import com.infy.domain.Topics;
import com.infy.domain.Topics_;
import com.infy.repository.TopicsRepository;
import com.infy.service.dto.TopicsCriteria;
import com.infy.service.dto.TopicsDTO;
import com.infy.service.mapper.TopicsMapper;

import io.github.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Topics} entities in the database.
 * The main input is a {@link TopicsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TopicsDTO} or a {@link Page} of {@link TopicsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TopicsQueryService extends QueryService<Topics> {

    private final Logger log = LoggerFactory.getLogger(TopicsQueryService.class);

    private final TopicsRepository topicsRepository;

    private final TopicsMapper topicsMapper;

    public TopicsQueryService(TopicsRepository topicsRepository, TopicsMapper topicsMapper) {
        this.topicsRepository = topicsRepository;
        this.topicsMapper = topicsMapper;
    }

    /**
     * Return a {@link List} of {@link TopicsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TopicsDTO> findByCriteria(TopicsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Topics> specification = createSpecification(criteria);
        return topicsMapper.toDto(topicsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TopicsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TopicsDTO> findByCriteria(TopicsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Topics> specification = createSpecification(criteria);
        return topicsRepository.findAll(specification, page)
            .map(topicsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TopicsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Topics> specification = createSpecification(criteria);
        return topicsRepository.count(specification);
    }

    /**
     * Function to convert {@link TopicsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Topics> createSpecification(TopicsCriteria criteria) {
        Specification<Topics> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Topics_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), Topics_.title));
            }
            if (criteria.getLastPostedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastPostedAt(), Topics_.lastPostedAt));
            }
            if (criteria.getViews() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getViews(), Topics_.views));
            }
            if (criteria.getPostsCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPostsCount(), Topics_.postsCount));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserId(), Topics_.userId));
            }
            if (criteria.getLastPostUserId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastPostUserId(), Topics_.lastPostUserId));
            }
            if (criteria.getReplyCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReplyCount(), Topics_.replyCount));
            }
            if (criteria.getFeaturedUser1Id() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFeaturedUser1Id(), Topics_.featuredUser1Id));
            }
            if (criteria.getFeaturedUser2Id() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFeaturedUser2Id(), Topics_.featuredUser2Id));
            }
            if (criteria.getFeaturedUser3Id() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFeaturedUser3Id(), Topics_.featuredUser3Id));
            }
            if (criteria.getDeletedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeletedAt(), Topics_.deletedAt));
            }
            if (criteria.getHighestPostNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHighestPostNumber(), Topics_.highestPostNumber));
            }
            if (criteria.getLikeCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLikeCount(), Topics_.likeCount));
            }
            if (criteria.getIncomingLinkCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIncomingLinkCount(), Topics_.incomingLinkCount));
            }
            if (criteria.getCategoryId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCategoryId(), Topics_.categoryId));
            }
            if (criteria.getVisible() != null) {
                specification = specification.and(buildSpecification(criteria.getVisible(), Topics_.visible));
            }
            if (criteria.getModeratorPostsCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModeratorPostsCount(), Topics_.moderatorPostsCount));
            }
            if (criteria.getClosed() != null) {
                specification = specification.and(buildSpecification(criteria.getClosed(), Topics_.closed));
            }
            if (criteria.getArchived() != null) {
                specification = specification.and(buildSpecification(criteria.getArchived(), Topics_.archived));
            }
            if (criteria.getBumpedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBumpedAt(), Topics_.bumpedAt));
            }
            if (criteria.getHasSummary() != null) {
                specification = specification.and(buildSpecification(criteria.getHasSummary(), Topics_.hasSummary));
            }
            if (criteria.getArchetype() != null) {
                specification = specification.and(buildStringSpecification(criteria.getArchetype(), Topics_.archetype));
            }
            if (criteria.getFeaturedUser4Id() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFeaturedUser4Id(), Topics_.featuredUser4Id));
            }
            if (criteria.getNotifyModeratorsCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNotifyModeratorsCount(), Topics_.notifyModeratorsCount));
            }
            if (criteria.getSpamCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSpamCount(), Topics_.spamCount));
            }
            if (criteria.getPinnedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPinnedAt(), Topics_.pinnedAt));
            }
            if (criteria.getScore() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getScore(), Topics_.score));
            }
            if (criteria.getPercentRank() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPercentRank(), Topics_.percentRank));
            }
            if (criteria.getSubtype() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSubtype(), Topics_.subtype));
            }
            if (criteria.getSlug() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSlug(), Topics_.slug));
            }
            if (criteria.getDeletedById() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDeletedById(), Topics_.deletedById));
            }
            if (criteria.getParticipantCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getParticipantCount(), Topics_.participantCount));
            }
            if (criteria.getWordCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWordCount(), Topics_.wordCount));
            }
            if (criteria.getExcerpt() != null) {
                specification = specification.and(buildStringSpecification(criteria.getExcerpt(), Topics_.excerpt));
            }
            if (criteria.getPinnedGlobally() != null) {
                specification = specification.and(buildSpecification(criteria.getPinnedGlobally(), Topics_.pinnedGlobally));
            }
            if (criteria.getPinnedUntil() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPinnedUntil(), Topics_.pinnedUntil));
            }
            if (criteria.getFancyTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFancyTitle(), Topics_.fancyTitle));
            }
            if (criteria.getHighestStaffPostNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHighestStaffPostNumber(), Topics_.highestStaffPostNumber));
            }
            if (criteria.getFeaturedLink() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFeaturedLink(), Topics_.featuredLink));
            }
            if (criteria.getReviewableScore() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReviewableScore(), Topics_.reviewableScore));
            }
            if (criteria.getImageUploadId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getImageUploadId(), Topics_.imageUploadId));
            }
            if (criteria.getSlowModeSeconds() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSlowModeSeconds(), Topics_.slowModeSeconds));
            }
        }
        return specification;
    }
}
