/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.UserStatsService;
import com.infy.domain.UserStats;
import com.infy.repository.UserStatsRepository;
import com.infy.security.SecurityUtils;
import com.infy.service.dto.UserStatsDTO;
import com.infy.service.mapper.UserStatsMapper;
import com.infy.service.mapper.UserStatsReponseMapper;
import com.infy.service.model.UserStatsResponse;
import com.infy.web.rest.errors.BadRequestAlertException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UserStats}.
 */
@Service
@Transactional
public class UserStatsServiceImpl implements UserStatsService {

	private final Logger log = LoggerFactory.getLogger(UserStatsServiceImpl.class);

	private final UserStatsRepository userStatsRepository;

	private final UserStatsMapper userStatsMapper;

	private final UserStatsReponseMapper userStatsReponseMapper;

	public UserStatsServiceImpl(UserStatsRepository userStatsRepository, UserStatsMapper userStatsMapper,
			UserStatsReponseMapper userStatsReponseMapper) {
		this.userStatsRepository = userStatsRepository;
		this.userStatsMapper = userStatsMapper;
		this.userStatsReponseMapper = userStatsReponseMapper;
	}

	@Override
	public UserStatsDTO save(UserStatsDTO userStatsDTO) {
		log.debug("Request to save UserStats : {}", userStatsDTO);
		UserStats userStats = userStatsMapper.toEntity(userStatsDTO);
		userStats = userStatsRepository.save(userStats);
		return userStatsMapper.toDto(userStats);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<UserStatsDTO> findAll(Pageable pageable) {
		log.debug("Request to get all UserStats");
		return userStatsRepository.findAll(pageable).map(userStatsMapper::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<UserStatsDTO> findOne(Long id) {
		log.debug("Request to get UserStats : {}", id);
		return userStatsRepository.findById(id).map(userStatsMapper::toDto);
	}

	@Override
	public void delete(Long id) {
		log.debug("Request to delete UserStats : {}", id);
		userStatsRepository.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<UserStatsDTO> findByUserId(String userId) {
		log.debug("Request to get UserStats by userId : {} ", userId);
		return userStatsRepository.findByUserId(userId).map(userStatsMapper::toDto);
	}

	@Override
	public Optional<UserStatsResponse> findByCurrentLoginUser() {
		log.debug("Request to get current login users UserStats");

		Optional<String> userIdOptional = SecurityUtils.getCurrentUserLoginUserId();
		if (userIdOptional.isEmpty()) {
			throw new BadRequestAlertException("Invalid user id", "Users", "Invalid user id");
		}

		return userStatsRepository.findByUserId(userIdOptional.get()).map(userStatsReponseMapper::toDto);
	}
}
