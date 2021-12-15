/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.impl;

import com.infy.service.BookmarksService;
import com.infy.domain.Bookmarks;
import com.infy.repository.BookmarksRepository;
import com.infy.service.dto.BookmarksDTO;
import com.infy.service.mapper.BookmarksMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Bookmarks}.
 */
@Service
@Transactional
public class BookmarksServiceImpl implements BookmarksService {

    private final Logger log = LoggerFactory.getLogger(BookmarksServiceImpl.class);

    private final BookmarksRepository bookmarksRepository;

    private final BookmarksMapper bookmarksMapper;

    public BookmarksServiceImpl(BookmarksRepository bookmarksRepository, BookmarksMapper bookmarksMapper) {
        this.bookmarksRepository = bookmarksRepository;
        this.bookmarksMapper = bookmarksMapper;
    }

    @Override
    public BookmarksDTO save(BookmarksDTO bookmarksDTO) {
        log.debug("Request to save Bookmarks : {}", bookmarksDTO);
        Bookmarks bookmarks = bookmarksMapper.toEntity(bookmarksDTO);
        bookmarks = bookmarksRepository.save(bookmarks);
        return bookmarksMapper.toDto(bookmarks);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BookmarksDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Bookmarks");
        return bookmarksRepository.findAll(pageable)
            .map(bookmarksMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<BookmarksDTO> findOne(Long id) {
        log.debug("Request to get Bookmarks : {}", id);
        return bookmarksRepository.findById(id)
            .map(bookmarksMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Bookmarks : {}", id);
        bookmarksRepository.deleteById(id);
    }
}
