/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infy.domain.Categories;
import com.infy.repository.CategoriesRepository;
import com.infy.security.SecurityUtils;
import com.infy.service.CategoriesService;
import com.infy.service.dto.CategoriesDTO;
import com.infy.service.mapper.CategoriesMapper;
import com.infy.service.mapper.CategoriesResponseMapper;
import com.infy.service.model.CategoryList;
import com.infy.service.model.CategoryResponse;
import com.infy.service.model.CreateCategoryRequest;
import com.infy.service.model.UpdateCategoryResponse;
import com.infy.web.rest.errors.BadRequestAlertException;

/**
 * Service Implementation for managing {@link Categories}.
 */
@Service
@Transactional
public class CategoriesServiceImpl implements CategoriesService {

	private final Logger log = LoggerFactory.getLogger(CategoriesServiceImpl.class);

	private final CategoriesRepository categoriesRepository;

	private final CategoriesMapper categoriesMapper;

	private final CategoriesResponseMapper categoriesResponseMapper;

	public CategoriesServiceImpl(CategoriesRepository categoriesRepository, CategoriesMapper categoriesMapper,
			CategoriesResponseMapper categoriesResponseMapper) {
		this.categoriesRepository = categoriesRepository;
		this.categoriesMapper = categoriesMapper;
		this.categoriesResponseMapper = categoriesResponseMapper;
	}

	@Override
	public CategoriesDTO save(CategoriesDTO categoriesDTO) {
		log.debug("Request to save Categories : {}", categoriesDTO);
		Categories categories = categoriesMapper.toEntity(categoriesDTO);
		categories = categoriesRepository.save(categories);
		return categoriesMapper.toDto(categories);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<CategoriesDTO> findAll(Pageable pageable) {
		log.debug("Request to get all Categories");
		return categoriesRepository.findAll(pageable).map(categoriesMapper::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<CategoriesDTO> findOne(Long id) {
		log.debug("Request to get Categories : {}", id);
		return categoriesRepository.findById(id).map(categoriesMapper::toDto);
	}

	@Override
	public void delete(Long id) {
		log.debug("Request to delete Categories : {}", id);
		categoriesRepository.deleteById(id);
	}

	@Override
	public CategoryResponse saveAndResponse(CreateCategoryRequest createCategoryRequest) {
		log.debug("Request to save Category : {} ", createCategoryRequest);

		Categories categories = new Categories();
		categories.setName(createCategoryRequest.getName());
		categories.setColor(createCategoryRequest.getColor());
		categories.setTextColor(createCategoryRequest.getTextColor());
		categories.setDescription(createCategoryRequest.getDescription());
		categories.setTopicCount(0);

		Optional<String> userId = SecurityUtils.getCurrentUserLoginUserId();
		if (userId.isEmpty()) {
			throw new BadRequestAlertException("User id is null", "Categories", "User id is null");
		}

		categories.setUserId(userId.get());
		categories.setSlug(createCategoryRequest.getName());
		categories.setReadRestricted(false);
		categories.setPostCount(0);
		categories.setAllowBadges(true);
		categories.setAllTopicsWiki(true);
		categories.setMailinglistMirror(true);
		categories.setMinimumRequiredTags(0);
		categories.setNavigateToFirstPostAfterRead(true);
		categories.setAllowGlobalTags(true);
		categories.setMinTagsFromRequiredGroup(0);
		categories.setAllowUnlimitedOwnerEditsOnFirstPost(true);

		categories = categoriesRepository.save(categories);
		return categoriesResponseMapper.toDto(categories);
	}

	@Override
	public CategoryList getAllCategories(Pageable pageable) {
		log.debug("Request to get Categories List");
		CategoryList categoryList = new CategoryList();
		categoryList.setCanCreateTopic(true);
		categoryList.setCanCreateCategory(true);
		categoryList.setDraftSequence(0);
		List<CategoryResponse> categories = categoriesRepository.findAll(pageable).stream()
				.map(categoriesResponseMapper::toDto).collect(Collectors.toList());

		categoryList.setCategories(categories);
		return categoryList;
	}

	@Override
	public UpdateCategoryResponse updateCategory(Long id, CreateCategoryRequest createCategoryRequest) {
		log.debug("Request to update Category : {} ", createCategoryRequest);

		Optional<Categories> categoryOptional = categoriesRepository.findById(id);
		if (categoryOptional.isEmpty()) {
			throw new BadRequestAlertException("Invalid Id", "Categories", "Invalid Id");
		}

		Categories categories = categoryOptional.get();
		categories.setName(createCategoryRequest.getName());
		categories.setColor(createCategoryRequest.getColor());
		categories.setTextColor(createCategoryRequest.getTextColor());
		categories.setDescription(createCategoryRequest.getDescription());
		categories.setSlug(createCategoryRequest.getName());

		categories = categoriesRepository.save(categories);

		CategoryResponse categoryResponse = categoriesResponseMapper.toDto(categories);
		UpdateCategoryResponse updateCategoryResponse = new UpdateCategoryResponse();
		updateCategoryResponse.setSuccess("Success");
		updateCategoryResponse.setCategory(categoryResponse);

		return updateCategoryResponse;
	}
}
