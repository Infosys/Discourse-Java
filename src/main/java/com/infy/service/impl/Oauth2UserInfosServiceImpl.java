/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.Oauth2UserInfosService;
import com.infy.domain.Oauth2UserInfos;
import com.infy.repository.Oauth2UserInfosRepository;
import com.infy.service.dto.Oauth2UserInfosDTO;
import com.infy.service.mapper.Oauth2UserInfosMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Oauth2UserInfos}.
 */
@Service
@Transactional
public class Oauth2UserInfosServiceImpl implements Oauth2UserInfosService {

    private final Logger log = LoggerFactory.getLogger(Oauth2UserInfosServiceImpl.class);

    private final Oauth2UserInfosRepository oauth2UserInfosRepository;

    private final Oauth2UserInfosMapper oauth2UserInfosMapper;

    public Oauth2UserInfosServiceImpl(Oauth2UserInfosRepository oauth2UserInfosRepository, Oauth2UserInfosMapper oauth2UserInfosMapper) {
        this.oauth2UserInfosRepository = oauth2UserInfosRepository;
        this.oauth2UserInfosMapper = oauth2UserInfosMapper;
    }

    @Override
    public Oauth2UserInfosDTO save(Oauth2UserInfosDTO oauth2UserInfosDTO) {
        log.debug("Request to save Oauth2UserInfos : {}", oauth2UserInfosDTO);
        Oauth2UserInfos oauth2UserInfos = oauth2UserInfosMapper.toEntity(oauth2UserInfosDTO);
        oauth2UserInfos = oauth2UserInfosRepository.save(oauth2UserInfos);
        return oauth2UserInfosMapper.toDto(oauth2UserInfos);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Oauth2UserInfosDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Oauth2UserInfos");
        return oauth2UserInfosRepository.findAll(pageable)
            .map(oauth2UserInfosMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Oauth2UserInfosDTO> findOne(Long id) {
        log.debug("Request to get Oauth2UserInfos : {}", id);
        return oauth2UserInfosRepository.findById(id)
            .map(oauth2UserInfosMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Oauth2UserInfos : {}", id);
        oauth2UserInfosRepository.deleteById(id);
    }
}
