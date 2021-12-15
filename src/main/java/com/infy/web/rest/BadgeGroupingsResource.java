/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.BadgeGroupingsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.BadgeGroupingsDTO;

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
 * REST controller for managing {@link com.infy.domain.BadgeGroupings}.
 */
@RestController
@RequestMapping("/api")
public class BadgeGroupingsResource {

    private final Logger log = LoggerFactory.getLogger(BadgeGroupingsResource.class);

    private static final String ENTITY_NAME = "badgeGroupings";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BadgeGroupingsService badgeGroupingsService;

    public BadgeGroupingsResource(BadgeGroupingsService badgeGroupingsService) {
        this.badgeGroupingsService = badgeGroupingsService;
    }

    /**
     * {@code POST  /badge-groupings} : Create a new badgeGroupings.
     *
     * @param badgeGroupingsDTO the badgeGroupingsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new badgeGroupingsDTO, or with status {@code 400 (Bad Request)} if the badgeGroupings has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/badge-groupings")
    public ResponseEntity<BadgeGroupingsDTO> createBadgeGroupings(@Valid @RequestBody BadgeGroupingsDTO badgeGroupingsDTO) throws URISyntaxException {
        log.debug("REST request to save BadgeGroupings : {}", badgeGroupingsDTO);
        if (badgeGroupingsDTO.getId() != null) {
            throw new BadRequestAlertException("A new badgeGroupings cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BadgeGroupingsDTO result = badgeGroupingsService.save(badgeGroupingsDTO);
        return ResponseEntity.created(new URI("/api/badge-groupings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /badge-groupings} : Updates an existing badgeGroupings.
     *
     * @param badgeGroupingsDTO the badgeGroupingsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated badgeGroupingsDTO,
     * or with status {@code 400 (Bad Request)} if the badgeGroupingsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the badgeGroupingsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/badge-groupings")
    public ResponseEntity<BadgeGroupingsDTO> updateBadgeGroupings(@Valid @RequestBody BadgeGroupingsDTO badgeGroupingsDTO) throws URISyntaxException {
        log.debug("REST request to update BadgeGroupings : {}", badgeGroupingsDTO);
        if (badgeGroupingsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BadgeGroupingsDTO result = badgeGroupingsService.save(badgeGroupingsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, badgeGroupingsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /badge-groupings} : get all the badgeGroupings.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of badgeGroupings in body.
     */
    @GetMapping("/badge-groupings")
    public ResponseEntity<List<BadgeGroupingsDTO>> getAllBadgeGroupings(Pageable pageable) {
        log.debug("REST request to get a page of BadgeGroupings");
        Page<BadgeGroupingsDTO> page = badgeGroupingsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /badge-groupings/:id} : get the "id" badgeGroupings.
     *
     * @param id the id of the badgeGroupingsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the badgeGroupingsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/badge-groupings/{id}")
    public ResponseEntity<BadgeGroupingsDTO> getBadgeGroupings(@PathVariable Long id) {
        log.debug("REST request to get BadgeGroupings : {}", id);
        Optional<BadgeGroupingsDTO> badgeGroupingsDTO = badgeGroupingsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(badgeGroupingsDTO);
    }

    /**
     * {@code DELETE  /badge-groupings/:id} : delete the "id" badgeGroupings.
     *
     * @param id the id of the badgeGroupingsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/badge-groupings/{id}")
    public ResponseEntity<Void> deleteBadgeGroupings(@PathVariable Long id) {
        log.debug("REST request to delete BadgeGroupings : {}", id);
        badgeGroupingsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
