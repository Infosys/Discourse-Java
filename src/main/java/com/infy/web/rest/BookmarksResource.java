/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.BookmarksService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.BookmarksDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.infy.domain.Bookmarks}.
 */
@RestController
@RequestMapping("/api")
public class BookmarksResource {

    private final Logger log = LoggerFactory.getLogger(BookmarksResource.class);

    private static final String ENTITY_NAME = "bookmarks";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BookmarksService bookmarksService;

    public BookmarksResource(BookmarksService bookmarksService) {
        this.bookmarksService = bookmarksService;
    }

    /**
     * {@code POST  /bookmarks} : Create a new bookmarks.
     *
     * @param bookmarksDTO the bookmarksDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bookmarksDTO, or with status {@code 400 (Bad Request)} if the bookmarks has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bookmarks")
    public ResponseEntity<BookmarksDTO> createBookmarks(@Valid @RequestBody BookmarksDTO bookmarksDTO) throws URISyntaxException {
        log.debug("REST request to save Bookmarks : {}", bookmarksDTO);
        if (bookmarksDTO.getId() != null) {
            throw new BadRequestAlertException("A new bookmarks cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BookmarksDTO result = bookmarksService.save(bookmarksDTO);
        return ResponseEntity.created(new URI("/api/bookmarks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bookmarks} : Updates an existing bookmarks.
     *
     * @param bookmarksDTO the bookmarksDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bookmarksDTO,
     * or with status {@code 400 (Bad Request)} if the bookmarksDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bookmarksDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bookmarks")
    public ResponseEntity<BookmarksDTO> updateBookmarks(@Valid @RequestBody BookmarksDTO bookmarksDTO) throws URISyntaxException {
        log.debug("REST request to update Bookmarks : {}", bookmarksDTO);
        if (bookmarksDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BookmarksDTO result = bookmarksService.save(bookmarksDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bookmarksDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /bookmarks} : get all the bookmarks.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bookmarks in body.
     */
    @GetMapping("/bookmarks")
    public ResponseEntity<List<BookmarksDTO>> getAllBookmarks(Pageable pageable) {
        log.debug("REST request to get a page of Bookmarks");
        Page<BookmarksDTO> page = bookmarksService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bookmarks/:id} : get the "id" bookmarks.
     *
     * @param id the id of the bookmarksDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bookmarksDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bookmarks/{id}")
    public ResponseEntity<BookmarksDTO> getBookmarks(@PathVariable Long id) {
        log.debug("REST request to get Bookmarks : {}", id);
        Optional<BookmarksDTO> bookmarksDTO = bookmarksService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bookmarksDTO);
    }

    /**
     * {@code DELETE  /bookmarks/:id} : delete the "id" bookmarks.
     *
     * @param id the id of the bookmarksDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bookmarks/{id}")
    public ResponseEntity<Void> deleteBookmarks(@PathVariable Long id) {
        log.debug("REST request to delete Bookmarks : {}", id);
        bookmarksService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
