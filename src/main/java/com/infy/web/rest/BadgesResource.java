/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.BadgesService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.BadgesDTO;

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
 * REST controller for managing {@link com.infy.domain.Badges}.
 */
@RestController
@RequestMapping("/api")
public class BadgesResource {

    private final Logger log = LoggerFactory.getLogger(BadgesResource.class);

    private static final String ENTITY_NAME = "badges";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BadgesService badgesService;

    public BadgesResource(BadgesService badgesService) {
        this.badgesService = badgesService;
    }

    /**
     * {@code POST  /badges} : Create a new badges.
     *
     * @param badgesDTO the badgesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new badgesDTO, or with status {@code 400 (Bad Request)} if the badges has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/badges")
    public ResponseEntity<BadgesDTO> createBadges(@Valid @RequestBody BadgesDTO badgesDTO) throws URISyntaxException {
        log.debug("REST request to save Badges : {}", badgesDTO);
        if (badgesDTO.getId() != null) {
            throw new BadRequestAlertException("A new badges cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BadgesDTO result = badgesService.save(badgesDTO);
        return ResponseEntity.created(new URI("/api/badges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /badges} : Updates an existing badges.
     *
     * @param badgesDTO the badgesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated badgesDTO,
     * or with status {@code 400 (Bad Request)} if the badgesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the badgesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/badges")
    public ResponseEntity<BadgesDTO> updateBadges(@Valid @RequestBody BadgesDTO badgesDTO) throws URISyntaxException {
        log.debug("REST request to update Badges : {}", badgesDTO);
        if (badgesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BadgesDTO result = badgesService.save(badgesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, badgesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /badges} : get all the badges.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of badges in body.
     */
    @GetMapping("/badges")
    public ResponseEntity<List<BadgesDTO>> getAllBadges(Pageable pageable) {
        log.debug("REST request to get a page of Badges");
        Page<BadgesDTO> page = badgesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /badges/:id} : get the "id" badges.
     *
     * @param id the id of the badgesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the badgesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/badges/{id}")
    public ResponseEntity<BadgesDTO> getBadges(@PathVariable Long id) {
        log.debug("REST request to get Badges : {}", id);
        Optional<BadgesDTO> badgesDTO = badgesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(badgesDTO);
    }

    /**
     * {@code DELETE  /badges/:id} : delete the "id" badges.
     *
     * @param id the id of the badgesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/badges/{id}")
    public ResponseEntity<Void> deleteBadges(@PathVariable Long id) {
        log.debug("REST request to delete Badges : {}", id);
        badgesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
