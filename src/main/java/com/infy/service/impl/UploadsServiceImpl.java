/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.UploadsService;
import com.infy.domain.Uploads;
import com.infy.repository.UploadsRepository;
import com.infy.service.dto.UploadsDTO;
import com.infy.service.mapper.UploadsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Uploads}.
 */
@Service
@Transactional
public class UploadsServiceImpl implements UploadsService {

    private final Logger log = LoggerFactory.getLogger(UploadsServiceImpl.class);

    private final UploadsRepository uploadsRepository;

    private final UploadsMapper uploadsMapper;

    public UploadsServiceImpl(UploadsRepository uploadsRepository, UploadsMapper uploadsMapper) {
        this.uploadsRepository = uploadsRepository;
        this.uploadsMapper = uploadsMapper;
    }

    @Override
    public UploadsDTO save(UploadsDTO uploadsDTO) {
        log.debug("Request to save Uploads : {}", uploadsDTO);
        Uploads uploads = uploadsMapper.toEntity(uploadsDTO);
        uploads = uploadsRepository.save(uploads);
        return uploadsMapper.toDto(uploads);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UploadsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Uploads");
        return uploadsRepository.findAll(pageable)
            .map(uploadsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<UploadsDTO> findOne(Long id) {
        log.debug("Request to get Uploads : {}", id);
        return uploadsRepository.findById(id)
            .map(uploadsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Uploads : {}", id);
        uploadsRepository.deleteById(id);
    }
}
