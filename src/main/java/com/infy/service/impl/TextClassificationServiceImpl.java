/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.TextClassificationService;
import com.infy.domain.TextClassification;
import com.infy.domain.enumeration.TextClassificationType;
import com.infy.repository.TextClassificationRepository;
import com.infy.service.dto.TextClassificationDTO;
import com.infy.service.mapper.TextClassificationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TextClassification}.
 */
@Service
@Transactional
public class TextClassificationServiceImpl implements TextClassificationService {

	private final Logger log = LoggerFactory.getLogger(TextClassificationServiceImpl.class);

	private final TextClassificationRepository textClassificationRepository;

	private final TextClassificationMapper textClassificationMapper;

	public TextClassificationServiceImpl(TextClassificationRepository textClassificationRepository,
			TextClassificationMapper textClassificationMapper) {
		this.textClassificationRepository = textClassificationRepository;
		this.textClassificationMapper = textClassificationMapper;
	}

	@Override
	public TextClassificationDTO save(TextClassificationDTO textClassificationDTO) {
		log.debug("Request to save TextClassification : {}", textClassificationDTO);
		TextClassification textClassification = textClassificationMapper.toEntity(textClassificationDTO);
		textClassification = textClassificationRepository.save(textClassification);
		return textClassificationMapper.toDto(textClassification);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<TextClassificationDTO> findAll(Pageable pageable) {
		log.debug("Request to get all TextClassifications");
		return textClassificationRepository.findAll(pageable).map(textClassificationMapper::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<TextClassificationDTO> findOne(Long id) {
		log.debug("Request to get TextClassification : {}", id);
		return textClassificationRepository.findById(id).map(textClassificationMapper::toDto);
	}

	@Override
	public void delete(Long id) {
		log.debug("Request to delete TextClassification : {}", id);
		textClassificationRepository.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<TextClassificationDTO> findByPostId(Long id) {
		log.debug("Request to get TextClassification By PostId : {}", id);
		return textClassificationRepository.findByContentIdAndType(id, TextClassificationType.POST)
				.map(textClassificationMapper::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<TextClassificationDTO> findByTopicId(Long id) {
		log.debug("Request to get TextClassification By TopicId : {}", id);
		return textClassificationRepository.findByContentIdAndType(id, TextClassificationType.TOPIC)
				.map(textClassificationMapper::toDto);
	}
}
