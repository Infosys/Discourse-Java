/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.UsersDTO;
import com.infy.service.model.FireBaseTokenRequest;
import com.infy.service.model.UsersResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.Users}.
 */
public interface UsersService {

	/**
	 * Save a users.
	 *
	 * @param usersDTO the entity to save.
	 * @return the persisted entity.
	 */
	UsersDTO save(UsersDTO usersDTO);

	/**
	 * Get all the users.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	Page<UsersDTO> findAll(Pageable pageable);

	/**
	 * Get the "id" users.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	Optional<UsersDTO> findOne(Long id);

	/**
	 * Delete the "id" users.
	 *
	 * @param id the id of the entity.
	 */
	void delete(Long id);

	Optional<UsersResponse> currentLoginUser();

	Optional<UsersResponse> findByUserId(String userId);

	List<UsersResponse> findByUserName(String userName);

	Optional<UsersResponse> acceptPrivacyOfCurrentUser();

	void updateToken(FireBaseTokenRequest fireBaseTokenRequest);

	void updateNotificationSubscription(Boolean subscribe);
}
