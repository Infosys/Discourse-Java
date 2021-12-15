/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.BadgeTypesService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.BadgeTypesDTO;

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
 * REST controller for managing {@link com.infy.domain.BadgeTypes}.
 */
@RestController
@RequestMapping("/api")
public class BadgeTypesResource {

    private final Logger log = LoggerFactory.getLogger(BadgeTypesResource.class);

    private static final String ENTITY_NAME = "badgeTypes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BadgeTypesService badgeTypesService;

    public BadgeTypesResource(BadgeTypesService badgeTypesService) {
        this.badgeTypesService = badgeTypesService;
    }

    /**
     * {@code POST  /badge-types} : Create a new badgeTypes.
     *
     * @param badgeTypesDTO the badgeTypesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new badgeTypesDTO, or with status {@code 400 (Bad Request)} if the badgeTypes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/badge-types")
    public ResponseEntity<BadgeTypesDTO> createBadgeTypes(@Valid @RequestBody BadgeTypesDTO badgeTypesDTO) throws URISyntaxException {
        log.debug("REST request to save BadgeTypes : {}", badgeTypesDTO);
        if (badgeTypesDTO.getId() != null) {
            throw new BadRequestAlertException("A new badgeTypes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BadgeTypesDTO result = badgeTypesService.save(badgeTypesDTO);
        return ResponseEntity.created(new URI("/api/badge-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /badge-types} : Updates an existing badgeTypes.
     *
     * @param badgeTypesDTO the badgeTypesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated badgeTypesDTO,
     * or with status {@code 400 (Bad Request)} if the badgeTypesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the badgeTypesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/badge-types")
    public ResponseEntity<BadgeTypesDTO> updateBadgeTypes(@Valid @RequestBody BadgeTypesDTO badgeTypesDTO) throws URISyntaxException {
        log.debug("REST request to update BadgeTypes : {}", badgeTypesDTO);
        if (badgeTypesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BadgeTypesDTO result = badgeTypesService.save(badgeTypesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, badgeTypesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /badge-types} : get all the badgeTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of badgeTypes in body.
     */
    @GetMapping("/badge-types")
    public ResponseEntity<List<BadgeTypesDTO>> getAllBadgeTypes(Pageable pageable) {
        log.debug("REST request to get a page of BadgeTypes");
        Page<BadgeTypesDTO> page = badgeTypesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /badge-types/:id} : get the "id" badgeTypes.
     *
     * @param id the id of the badgeTypesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the badgeTypesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/badge-types/{id}")
    public ResponseEntity<BadgeTypesDTO> getBadgeTypes(@PathVariable Long id) {
        log.debug("REST request to get BadgeTypes : {}", id);
        Optional<BadgeTypesDTO> badgeTypesDTO = badgeTypesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(badgeTypesDTO);
    }

    /**
     * {@code DELETE  /badge-types/:id} : delete the "id" badgeTypes.
     *
     * @param id the id of the badgeTypesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/badge-types/{id}")
    public ResponseEntity<Void> deleteBadgeTypes(@PathVariable Long id) {
        log.debug("REST request to delete BadgeTypes : {}", id);
        badgeTypesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
