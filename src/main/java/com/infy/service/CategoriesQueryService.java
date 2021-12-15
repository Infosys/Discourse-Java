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

import com.infy.domain.Categories;
import com.infy.domain.*; // for static metamodels
import com.infy.repository.CategoriesRepository;
import com.infy.service.dto.CategoriesCriteria;
import com.infy.service.dto.CategoriesDTO;
import com.infy.service.mapper.CategoriesMapper;

/**
 * Service for executing complex queries for {@link Categories} entities in the database.
 * The main input is a {@link CategoriesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CategoriesDTO} or a {@link Page} of {@link CategoriesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CategoriesQueryService extends QueryService<Categories> {

    private final Logger log = LoggerFactory.getLogger(CategoriesQueryService.class);

    private final CategoriesRepository categoriesRepository;

    private final CategoriesMapper categoriesMapper;

    public CategoriesQueryService(CategoriesRepository categoriesRepository, CategoriesMapper categoriesMapper) {
        this.categoriesRepository = categoriesRepository;
        this.categoriesMapper = categoriesMapper;
    }

    /**
     * Return a {@link List} of {@link CategoriesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CategoriesDTO> findByCriteria(CategoriesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Categories> specification = createSpecification(criteria);
        return categoriesMapper.toDto(categoriesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CategoriesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CategoriesDTO> findByCriteria(CategoriesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Categories> specification = createSpecification(criteria);
        return categoriesRepository.findAll(specification, page)
            .map(categoriesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CategoriesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Categories> specification = createSpecification(criteria);
        return categoriesRepository.count(specification);
    }

    /**
     * Function to convert {@link CategoriesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Categories> createSpecification(CategoriesCriteria criteria) {
        Specification<Categories> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Categories_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Categories_.name));
            }
            if (criteria.getColor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getColor(), Categories_.color));
            }
            if (criteria.getTopicId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTopicId(), Categories_.topicId));
            }
            if (criteria.getTopicCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTopicCount(), Categories_.topicCount));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserId(), Categories_.userId));
            }
            if (criteria.getTopicsYear() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTopicsYear(), Categories_.topicsYear));
            }
            if (criteria.getTopicsMonth() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTopicsMonth(), Categories_.topicsMonth));
            }
            if (criteria.getTopicsWeek() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTopicsWeek(), Categories_.topicsWeek));
            }
            if (criteria.getSlug() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSlug(), Categories_.slug));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Categories_.description));
            }
            if (criteria.getTextColor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTextColor(), Categories_.textColor));
            }
            if (criteria.getReadRestricted() != null) {
                specification = specification.and(buildSpecification(criteria.getReadRestricted(), Categories_.readRestricted));
            }
            if (criteria.getAutoCloseHours() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAutoCloseHours(), Categories_.autoCloseHours));
            }
            if (criteria.getPostCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPostCount(), Categories_.postCount));
            }
            if (criteria.getLatestPostId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLatestPostId(), Categories_.latestPostId));
            }
            if (criteria.getLatestTopicId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLatestTopicId(), Categories_.latestTopicId));
            }
            if (criteria.getPosition() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPosition(), Categories_.position));
            }
            if (criteria.getParentCategoryId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getParentCategoryId(), Categories_.parentCategoryId));
            }
            if (criteria.getPostsYear() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPostsYear(), Categories_.postsYear));
            }
            if (criteria.getPostsMonth() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPostsMonth(), Categories_.postsMonth));
            }
            if (criteria.getPostsWeek() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPostsWeek(), Categories_.postsWeek));
            }
            if (criteria.getEmailIn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmailIn(), Categories_.emailIn));
            }
            if (criteria.getEmailInAllowStrangers() != null) {
                specification = specification.and(buildSpecification(criteria.getEmailInAllowStrangers(), Categories_.emailInAllowStrangers));
            }
            if (criteria.getTopicsDay() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTopicsDay(), Categories_.topicsDay));
            }
            if (criteria.getPostsDay() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPostsDay(), Categories_.postsDay));
            }
            if (criteria.getAllowBadges() != null) {
                specification = specification.and(buildSpecification(criteria.getAllowBadges(), Categories_.allowBadges));
            }
            if (criteria.getNameLower() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNameLower(), Categories_.nameLower));
            }
            if (criteria.getAutoCloseBasedOnLastPost() != null) {
                specification = specification.and(buildSpecification(criteria.getAutoCloseBasedOnLastPost(), Categories_.autoCloseBasedOnLastPost));
            }
            if (criteria.getTopicTemplate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTopicTemplate(), Categories_.topicTemplate));
            }
            if (criteria.getContainsMessages() != null) {
                specification = specification.and(buildSpecification(criteria.getContainsMessages(), Categories_.containsMessages));
            }
            if (criteria.getSortOrder() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSortOrder(), Categories_.sortOrder));
            }
            if (criteria.getSortAscending() != null) {
                specification = specification.and(buildSpecification(criteria.getSortAscending(), Categories_.sortAscending));
            }
            if (criteria.getUploadedLogoId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUploadedLogoId(), Categories_.uploadedLogoId));
            }
            if (criteria.getUploadedBackgroundId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUploadedBackgroundId(), Categories_.uploadedBackgroundId));
            }
            if (criteria.getTopicFeaturedLinkAllowed() != null) {
                specification = specification.and(buildSpecification(criteria.getTopicFeaturedLinkAllowed(), Categories_.topicFeaturedLinkAllowed));
            }
            if (criteria.getAllTopicsWiki() != null) {
                specification = specification.and(buildSpecification(criteria.getAllTopicsWiki(), Categories_.allTopicsWiki));
            }
            if (criteria.getShowSubcategoryList() != null) {
                specification = specification.and(buildSpecification(criteria.getShowSubcategoryList(), Categories_.showSubcategoryList));
            }
            if (criteria.getNumFeaturedTopics() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumFeaturedTopics(), Categories_.numFeaturedTopics));
            }
            if (criteria.getDefaultView() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDefaultView(), Categories_.defaultView));
            }
            if (criteria.getSubcategoryListStyle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSubcategoryListStyle(), Categories_.subcategoryListStyle));
            }
            if (criteria.getDefaultTopPeriod() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDefaultTopPeriod(), Categories_.defaultTopPeriod));
            }
            if (criteria.getMailinglistMirror() != null) {
                specification = specification.and(buildSpecification(criteria.getMailinglistMirror(), Categories_.mailinglistMirror));
            }
            if (criteria.getMinimumRequiredTags() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMinimumRequiredTags(), Categories_.minimumRequiredTags));
            }
            if (criteria.getNavigateToFirstPostAfterRead() != null) {
                specification = specification.and(buildSpecification(criteria.getNavigateToFirstPostAfterRead(), Categories_.navigateToFirstPostAfterRead));
            }
            if (criteria.getSearchPriority() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSearchPriority(), Categories_.searchPriority));
            }
            if (criteria.getAllowGlobalTags() != null) {
                specification = specification.and(buildSpecification(criteria.getAllowGlobalTags(), Categories_.allowGlobalTags));
            }
            if (criteria.getReviewableByGroupId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReviewableByGroupId(), Categories_.reviewableByGroupId));
            }
            if (criteria.getRequiredTagGroupId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRequiredTagGroupId(), Categories_.requiredTagGroupId));
            }
            if (criteria.getMinTagsFromRequiredGroup() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMinTagsFromRequiredGroup(), Categories_.minTagsFromRequiredGroup));
            }
            if (criteria.getReadOnlyBanner() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReadOnlyBanner(), Categories_.readOnlyBanner));
            }
            if (criteria.getDefaultListFilter() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDefaultListFilter(), Categories_.defaultListFilter));
            }
            if (criteria.getAllowUnlimitedOwnerEditsOnFirstPost() != null) {
                specification = specification.and(buildSpecification(criteria.getAllowUnlimitedOwnerEditsOnFirstPost(), Categories_.allowUnlimitedOwnerEditsOnFirstPost));
            }
        }
        return specification;
    }
}
