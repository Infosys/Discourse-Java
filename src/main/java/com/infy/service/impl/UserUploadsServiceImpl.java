/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.UserUploadsService;
import com.infy.domain.UserUploads;
import com.infy.repository.UserUploadsRepository;
import com.infy.service.dto.UserUploadsDTO;
import com.infy.service.mapper.UserUploadsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UserUploads}.
 */
@Service
@Transactional
public class UserUploadsServiceImpl implements UserUploadsService {

    private final Logger log = LoggerFactory.getLogger(UserUploadsServiceImpl.class);

    private final UserUploadsRepository userUploadsRepository;

    private final UserUploadsMapper userUploadsMapper;

    public UserUploadsServiceImpl(UserUploadsRepository userUploadsRepository, UserUploadsMapper userUploadsMapper) {
        this.userUploadsRepository = userUploadsRepository;
        this.userUploadsMapper = userUploadsMapper;
    }

    @Override
    public UserUploadsDTO save(UserUploadsDTO userUploadsDTO) {
        log.debug("Request to save UserUploads : {}", userUploadsDTO);
        UserUploads userUploads = userUploadsMapper.toEntity(userUploadsDTO);
        userUploads = userUploadsRepository.save(userUploads);
        return userUploadsMapper.toDto(userUploads);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserUploadsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserUploads");
        return userUploadsRepository.findAll(pageable)
            .map(userUploadsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<UserUploadsDTO> findOne(Long id) {
        log.debug("Request to get UserUploads : {}", id);
        return userUploadsRepository.findById(id)
            .map(userUploadsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserUploads : {}", id);
        userUploadsRepository.deleteById(id);
    }
}
