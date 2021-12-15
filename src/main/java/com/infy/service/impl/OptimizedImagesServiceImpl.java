/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.OptimizedImagesService;
import com.infy.domain.OptimizedImages;
import com.infy.repository.OptimizedImagesRepository;
import com.infy.service.dto.OptimizedImagesDTO;
import com.infy.service.mapper.OptimizedImagesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link OptimizedImages}.
 */
@Service
@Transactional
public class OptimizedImagesServiceImpl implements OptimizedImagesService {

    private final Logger log = LoggerFactory.getLogger(OptimizedImagesServiceImpl.class);

    private final OptimizedImagesRepository optimizedImagesRepository;

    private final OptimizedImagesMapper optimizedImagesMapper;

    public OptimizedImagesServiceImpl(OptimizedImagesRepository optimizedImagesRepository, OptimizedImagesMapper optimizedImagesMapper) {
        this.optimizedImagesRepository = optimizedImagesRepository;
        this.optimizedImagesMapper = optimizedImagesMapper;
    }

    @Override
    public OptimizedImagesDTO save(OptimizedImagesDTO optimizedImagesDTO) {
        log.debug("Request to save OptimizedImages : {}", optimizedImagesDTO);
        OptimizedImages optimizedImages = optimizedImagesMapper.toEntity(optimizedImagesDTO);
        optimizedImages = optimizedImagesRepository.save(optimizedImages);
        return optimizedImagesMapper.toDto(optimizedImages);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OptimizedImagesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OptimizedImages");
        return optimizedImagesRepository.findAll(pageable)
            .map(optimizedImagesMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<OptimizedImagesDTO> findOne(Long id) {
        log.debug("Request to get OptimizedImages : {}", id);
        return optimizedImagesRepository.findById(id)
            .map(optimizedImagesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OptimizedImages : {}", id);
        optimizedImagesRepository.deleteById(id);
    }
}
