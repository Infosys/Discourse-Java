/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.service.dto.Oauth2UserInfosDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.infy.domain.Oauth2UserInfos}.
 */
public interface Oauth2UserInfosService {

    /**
     * Save a oauth2UserInfos.
     *
     * @param oauth2UserInfosDTO the entity to save.
     * @return the persisted entity.
     */
    Oauth2UserInfosDTO save(Oauth2UserInfosDTO oauth2UserInfosDTO);

    /**
     * Get all the oauth2UserInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Oauth2UserInfosDTO> findAll(Pageable pageable);


    /**
     * Get the "id" oauth2UserInfos.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Oauth2UserInfosDTO> findOne(Long id);

    /**
     * Delete the "id" oauth2UserInfos.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
