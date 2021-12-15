/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.PostActionTypesService;
import com.infy.domain.PostActionTypes;
import com.infy.repository.PostActionTypesRepository;
import com.infy.service.dto.PostActionTypesDTO;
import com.infy.service.mapper.CreatePostActionRequestTypeMapper;
import com.infy.service.mapper.PostActionTypeResponseMapper;
import com.infy.service.mapper.PostActionTypesMapper;
import com.infy.service.model.CreatePostActionTypeRequest;
import com.infy.service.model.PostActionTypeResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PostActionTypes}.
 */
@Service
@Transactional
public class PostActionTypesServiceImpl implements PostActionTypesService {

	private final Logger log = LoggerFactory.getLogger(PostActionTypesServiceImpl.class);

	private final PostActionTypesRepository postActionTypesRepository;

	private final PostActionTypesMapper postActionTypesMapper;
	private final PostActionTypeResponseMapper postActionTypeResponseMapper;
	private final CreatePostActionRequestTypeMapper createPostActionRequestTypeMapper;

	public PostActionTypesServiceImpl(PostActionTypesRepository postActionTypesRepository,
			PostActionTypesMapper postActionTypesMapper, PostActionTypeResponseMapper postActionTypeResponseMapper,
			CreatePostActionRequestTypeMapper createPostActionRequestTypeMapper) {
		this.postActionTypesRepository = postActionTypesRepository;
		this.postActionTypesMapper = postActionTypesMapper;
		this.postActionTypeResponseMapper = postActionTypeResponseMapper;
		this.createPostActionRequestTypeMapper = createPostActionRequestTypeMapper;
	}

	@Override
	public PostActionTypesDTO save(PostActionTypesDTO postActionTypesDTO) {
		log.debug("Request to save PostActionTypes : {}", postActionTypesDTO);
		PostActionTypes postActionTypes = postActionTypesMapper.toEntity(postActionTypesDTO);
		postActionTypes = postActionTypesRepository.save(postActionTypes);
		return postActionTypesMapper.toDto(postActionTypes);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<PostActionTypesDTO> findAll(Pageable pageable) {
		log.debug("Request to get all PostActionTypes");
		return postActionTypesRepository.findAll(pageable).map(postActionTypesMapper::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<PostActionTypesDTO> findOne(Long id) {
		log.debug("Request to get PostActionTypes : {}", id);
		return postActionTypesRepository.findById(id).map(postActionTypesMapper::toDto);
	}

	@Override
	public void delete(Long id) {
		log.debug("Request to delete PostActionTypes : {}", id);
		postActionTypesRepository.deleteById(id);
	}

	@Override
	public PostActionTypeResponse saveAndResponse(CreatePostActionTypeRequest createPostActionTypeRequest) {
		log.debug("Request to save PostActionTypes : {} ", createPostActionTypeRequest);
		PostActionTypes postActionTypes = createPostActionRequestTypeMapper.toEntity(createPostActionTypeRequest);
		postActionTypes = postActionTypesRepository.save(postActionTypes);
		return postActionTypeResponseMapper.toDto(postActionTypes);
	}
}
