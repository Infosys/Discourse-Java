/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.BadgePostsService;
import com.infy.domain.BadgePosts;
import com.infy.repository.BadgePostsRepository;
import com.infy.service.dto.BadgePostsDTO;
import com.infy.service.mapper.BadgePostsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link BadgePosts}.
 */
@Service
@Transactional
public class BadgePostsServiceImpl implements BadgePostsService {

    private final Logger log = LoggerFactory.getLogger(BadgePostsServiceImpl.class);

    private final BadgePostsRepository badgePostsRepository;

    private final BadgePostsMapper badgePostsMapper;

    public BadgePostsServiceImpl(BadgePostsRepository badgePostsRepository, BadgePostsMapper badgePostsMapper) {
        this.badgePostsRepository = badgePostsRepository;
        this.badgePostsMapper = badgePostsMapper;
    }

    @Override
    public BadgePostsDTO save(BadgePostsDTO badgePostsDTO) {
        log.debug("Request to save BadgePosts : {}", badgePostsDTO);
        BadgePosts badgePosts = badgePostsMapper.toEntity(badgePostsDTO);
        badgePosts = badgePostsRepository.save(badgePosts);
        return badgePostsMapper.toDto(badgePosts);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BadgePostsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BadgePosts");
        return badgePostsRepository.findAll(pageable)
            .map(badgePostsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<BadgePostsDTO> findOne(Long id) {
        log.debug("Request to get BadgePosts : {}", id);
        return badgePostsRepository.findById(id)
            .map(badgePostsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete BadgePosts : {}", id);
        badgePostsRepository.deleteById(id);
    }
}
