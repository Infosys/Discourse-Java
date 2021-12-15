/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.BadgePostsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.BadgePostsDTO;

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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.infy.domain.BadgePosts}.
 */
@RestController
@RequestMapping("/api")
public class BadgePostsResource {

    private final Logger log = LoggerFactory.getLogger(BadgePostsResource.class);

    private static final String ENTITY_NAME = "badgePosts";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BadgePostsService badgePostsService;

    public BadgePostsResource(BadgePostsService badgePostsService) {
        this.badgePostsService = badgePostsService;
    }

    /**
     * {@code POST  /badge-posts} : Create a new badgePosts.
     *
     * @param badgePostsDTO the badgePostsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new badgePostsDTO, or with status {@code 400 (Bad Request)} if the badgePosts has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/badge-posts")
    public ResponseEntity<BadgePostsDTO> createBadgePosts(@RequestBody BadgePostsDTO badgePostsDTO) throws URISyntaxException {
        log.debug("REST request to save BadgePosts : {}", badgePostsDTO);
        if (badgePostsDTO.getId() != null) {
            throw new BadRequestAlertException("A new badgePosts cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BadgePostsDTO result = badgePostsService.save(badgePostsDTO);
        return ResponseEntity.created(new URI("/api/badge-posts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /badge-posts} : Updates an existing badgePosts.
     *
     * @param badgePostsDTO the badgePostsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated badgePostsDTO,
     * or with status {@code 400 (Bad Request)} if the badgePostsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the badgePostsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/badge-posts")
    public ResponseEntity<BadgePostsDTO> updateBadgePosts(@RequestBody BadgePostsDTO badgePostsDTO) throws URISyntaxException {
        log.debug("REST request to update BadgePosts : {}", badgePostsDTO);
        if (badgePostsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BadgePostsDTO result = badgePostsService.save(badgePostsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, badgePostsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /badge-posts} : get all the badgePosts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of badgePosts in body.
     */
    @GetMapping("/badge-posts")
    public ResponseEntity<List<BadgePostsDTO>> getAllBadgePosts(Pageable pageable) {
        log.debug("REST request to get a page of BadgePosts");
        Page<BadgePostsDTO> page = badgePostsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /badge-posts/:id} : get the "id" badgePosts.
     *
     * @param id the id of the badgePostsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the badgePostsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/badge-posts/{id}")
    public ResponseEntity<BadgePostsDTO> getBadgePosts(@PathVariable Long id) {
        log.debug("REST request to get BadgePosts : {}", id);
        Optional<BadgePostsDTO> badgePostsDTO = badgePostsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(badgePostsDTO);
    }

    /**
     * {@code DELETE  /badge-posts/:id} : delete the "id" badgePosts.
     *
     * @param id the id of the badgePostsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/badge-posts/{id}")
    public ResponseEntity<Void> deleteBadgePosts(@PathVariable Long id) {
        log.debug("REST request to delete BadgePosts : {}", id);
        badgePostsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
