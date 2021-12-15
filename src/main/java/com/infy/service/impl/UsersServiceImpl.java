/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.UsersService;
import com.infy.domain.Users;
import com.infy.repository.UsersRepository;
import com.infy.security.SecurityUtils;
import com.infy.service.dto.UsersDTO;
import com.infy.service.mapper.UserResponseMapper;
import com.infy.service.mapper.UsersMapper;
import com.infy.service.model.FireBaseTokenRequest;
import com.infy.service.model.User;
import com.infy.service.model.UsersResponse;
import com.infy.web.rest.errors.BadRequestAlertException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Users}.
 */
@Service
@Transactional
public class UsersServiceImpl implements UsersService {

	private final Logger log = LoggerFactory.getLogger(UsersServiceImpl.class);

	private final UsersRepository usersRepository;

	private final UsersMapper usersMapper;

	private final UserResponseMapper userResponseMapper;

	public UsersServiceImpl(UsersRepository usersRepository, UsersMapper usersMapper,
			UserResponseMapper userResponseMapper) {
		this.usersRepository = usersRepository;
		this.usersMapper = usersMapper;
		this.userResponseMapper = userResponseMapper;
	}

	@Override
	public UsersDTO save(UsersDTO usersDTO) {
		log.debug("Request to save Users : {}", usersDTO);
		Users users = usersMapper.toEntity(usersDTO);
		users = usersRepository.save(users);
		return usersMapper.toDto(users);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<UsersDTO> findAll(Pageable pageable) {
		log.debug("Request to get all Users");
		return usersRepository.findAll(pageable).map(usersMapper::toDto);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<UsersDTO> findOne(Long id) {
		log.debug("Request to get Users : {}", id);
		return usersRepository.findById(id).map(usersMapper::toDto);
	}

	@Override
	public void delete(Long id) {
		log.debug("Request to delete Users : {}", id);
		usersRepository.deleteById(id);
	}

	@Override
	public Optional<UsersResponse> currentLoginUser() {
		log.debug("Request to get current login user");
		Optional<String> userIdOptional = SecurityUtils.getCurrentUserLoginUserId();
		if (userIdOptional.isEmpty()) {
			throw new BadRequestAlertException("Invalid user id", "Users", "Invalid user id");
		}

		Optional<User> userOptional = usersRepository.findByUserId(userIdOptional.get()).map(userResponseMapper::toDto);
		if (userOptional.isPresent()) {
			UsersResponse usersResponse = new UsersResponse();
			usersResponse.setUser(userOptional.get());
			return Optional.of(usersResponse);
		} else {
			return Optional.ofNullable(null);
		}
	}

	@Override
	public Optional<UsersResponse> findByUserId(String userId) {
		log.debug("Request to get User by userId : {} ", userId);
		Optional<User> userOptional = usersRepository.findByUserId(userId).map(userResponseMapper::toDto);
		if (userOptional.isPresent()) {
			UsersResponse usersResponse = new UsersResponse();
			usersResponse.setUser(userOptional.get());
			return Optional.of(usersResponse);
		} else {
			return Optional.ofNullable(null);
		}
	}

	@Override
	public List<UsersResponse> findByUserName(String userName) {
		log.debug("Request to get Users by username : {} ", userName);
		List<UsersResponse> usersResponses = new ArrayList<>();

		List<Users> users = usersRepository.findByUsername(userName);
		users.forEach(user -> {
			UsersResponse usersResponse = new UsersResponse();
			User newUser = userResponseMapper.toDto(user);
			usersResponse.setUser(newUser);
			usersResponses.add(usersResponse);
		});

		return usersResponses;
	}

	@Override
	public Optional<UsersResponse> acceptPrivacyOfCurrentUser() {
		log.debug("Request to accept privacy of current user");

		Optional<String> userIdOptional = SecurityUtils.getCurrentUserLoginUserId();
		if (userIdOptional.isEmpty()) {
			throw new BadRequestAlertException("Invalid user id", "Users", "Invalid user id");
		}
		Optional<Users> usersOptional = usersRepository.findByUserId(userIdOptional.get());

		if (usersOptional.isPresent()) {
			Users users = usersOptional.get();
			users.setPrivacyAccepted(true);
			users = usersRepository.save(users);
			User user = userResponseMapper.toDto(users);
			UsersResponse usersResponse = new UsersResponse();
			usersResponse.setUser(user);

			return Optional.of(usersResponse);
		} else {
			return Optional.ofNullable(null);
		}
	}

	@Override
	public void updateToken(FireBaseTokenRequest fireBaseTokenRequest) {
		log.debug("Request to update token");

		Optional<String> userIdOptional = SecurityUtils.getCurrentUserLoginUserId();
		if (userIdOptional.isEmpty()) {
			throw new BadRequestAlertException("Invalid user id", "Users", "Invalid user id");
		}
		Optional<Users> usersOptional = usersRepository.findByUserId(userIdOptional.get());

		if (usersOptional.isEmpty()) {
			throw new BadRequestAlertException("User is not onboarded", "Users", "User is not onboarded");
		}

		usersRepository.invalidOtherUsersToken(fireBaseTokenRequest.getToken(), userIdOptional.get());

		Users users = usersOptional.get();
		users.setFireBaseToken(fireBaseTokenRequest.getToken());
		usersRepository.save(users);
	}

	@Override
	public void updateNotificationSubscription(Boolean subscribe) {
		log.debug("Request to update notification subscription : {}", subscribe);

		Optional<String> userIdOptional = SecurityUtils.getCurrentUserLoginUserId();
		if (userIdOptional.isEmpty()) {
			throw new BadRequestAlertException("Invalid user id", "Users", "Invalid user id");
		}
		Optional<Users> usersOptional = usersRepository.findByUserId(userIdOptional.get());

		if (usersOptional.isEmpty()) {
			throw new BadRequestAlertException("User is not onboarded", "Users", "User is not onboarded");
		}

		Users users = usersOptional.get();
		users.setFireBaseToken(null);
		users.setNotificationSubscription(subscribe);
		usersRepository.save(users);
	}
}
