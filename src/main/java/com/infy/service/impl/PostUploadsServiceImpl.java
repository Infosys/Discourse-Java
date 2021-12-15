/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.PostUploadsService;
import com.infy.domain.PostUploads;
import com.infy.repository.PostUploadsRepository;
import com.infy.service.dto.PostUploadsDTO;
import com.infy.service.mapper.PostUploadsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PostUploads}.
 */
@Service
@Transactional
public class PostUploadsServiceImpl implements PostUploadsService {

    private final Logger log = LoggerFactory.getLogger(PostUploadsServiceImpl.class);

    private final PostUploadsRepository postUploadsRepository;

    private final PostUploadsMapper postUploadsMapper;

    public PostUploadsServiceImpl(PostUploadsRepository postUploadsRepository, PostUploadsMapper postUploadsMapper) {
        this.postUploadsRepository = postUploadsRepository;
        this.postUploadsMapper = postUploadsMapper;
    }

    @Override
    public PostUploadsDTO save(PostUploadsDTO postUploadsDTO) {
        log.debug("Request to save PostUploads : {}", postUploadsDTO);
        PostUploads postUploads = postUploadsMapper.toEntity(postUploadsDTO);
        postUploads = postUploadsRepository.save(postUploads);
        return postUploadsMapper.toDto(postUploads);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PostUploadsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PostUploads");
        return postUploadsRepository.findAll(pageable)
            .map(postUploadsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<PostUploadsDTO> findOne(Long id) {
        log.debug("Request to get PostUploads : {}", id);
        return postUploadsRepository.findById(id)
            .map(postUploadsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PostUploads : {}", id);
        postUploadsRepository.deleteById(id);
    }
}
