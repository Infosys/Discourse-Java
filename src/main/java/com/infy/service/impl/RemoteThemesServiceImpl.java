/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.RemoteThemesService;
import com.infy.domain.RemoteThemes;
import com.infy.repository.RemoteThemesRepository;
import com.infy.service.dto.RemoteThemesDTO;
import com.infy.service.mapper.RemoteThemesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link RemoteThemes}.
 */
@Service
@Transactional
public class RemoteThemesServiceImpl implements RemoteThemesService {

    private final Logger log = LoggerFactory.getLogger(RemoteThemesServiceImpl.class);

    private final RemoteThemesRepository remoteThemesRepository;

    private final RemoteThemesMapper remoteThemesMapper;

    public RemoteThemesServiceImpl(RemoteThemesRepository remoteThemesRepository, RemoteThemesMapper remoteThemesMapper) {
        this.remoteThemesRepository = remoteThemesRepository;
        this.remoteThemesMapper = remoteThemesMapper;
    }

    @Override
    public RemoteThemesDTO save(RemoteThemesDTO remoteThemesDTO) {
        log.debug("Request to save RemoteThemes : {}", remoteThemesDTO);
        RemoteThemes remoteThemes = remoteThemesMapper.toEntity(remoteThemesDTO);
        remoteThemes = remoteThemesRepository.save(remoteThemes);
        return remoteThemesMapper.toDto(remoteThemes);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RemoteThemesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RemoteThemes");
        return remoteThemesRepository.findAll(pageable)
            .map(remoteThemesMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<RemoteThemesDTO> findOne(Long id) {
        log.debug("Request to get RemoteThemes : {}", id);
        return remoteThemesRepository.findById(id)
            .map(remoteThemesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RemoteThemes : {}", id);
        remoteThemesRepository.deleteById(id);
    }
}
