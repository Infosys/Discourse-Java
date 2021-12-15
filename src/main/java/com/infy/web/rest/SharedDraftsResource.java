/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.SharedDraftsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.SharedDraftsDTO;

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
 * REST controller for managing {@link com.infy.domain.SharedDrafts}.
 */
@RestController
@RequestMapping("/api")
public class SharedDraftsResource {

    private final Logger log = LoggerFactory.getLogger(SharedDraftsResource.class);

    private static final String ENTITY_NAME = "sharedDrafts";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SharedDraftsService sharedDraftsService;

    public SharedDraftsResource(SharedDraftsService sharedDraftsService) {
        this.sharedDraftsService = sharedDraftsService;
    }

    /**
     * {@code POST  /shared-drafts} : Create a new sharedDrafts.
     *
     * @param sharedDraftsDTO the sharedDraftsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sharedDraftsDTO, or with status {@code 400 (Bad Request)} if the sharedDrafts has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/shared-drafts")
    public ResponseEntity<SharedDraftsDTO> createSharedDrafts(@Valid @RequestBody SharedDraftsDTO sharedDraftsDTO) throws URISyntaxException {
        log.debug("REST request to save SharedDrafts : {}", sharedDraftsDTO);
        if (sharedDraftsDTO.getId() != null) {
            throw new BadRequestAlertException("A new sharedDrafts cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SharedDraftsDTO result = sharedDraftsService.save(sharedDraftsDTO);
        return ResponseEntity.created(new URI("/api/shared-drafts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /shared-drafts} : Updates an existing sharedDrafts.
     *
     * @param sharedDraftsDTO the sharedDraftsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sharedDraftsDTO,
     * or with status {@code 400 (Bad Request)} if the sharedDraftsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sharedDraftsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/shared-drafts")
    public ResponseEntity<SharedDraftsDTO> updateSharedDrafts(@Valid @RequestBody SharedDraftsDTO sharedDraftsDTO) throws URISyntaxException {
        log.debug("REST request to update SharedDrafts : {}", sharedDraftsDTO);
        if (sharedDraftsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SharedDraftsDTO result = sharedDraftsService.save(sharedDraftsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sharedDraftsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /shared-drafts} : get all the sharedDrafts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sharedDrafts in body.
     */
    @GetMapping("/shared-drafts")
    public ResponseEntity<List<SharedDraftsDTO>> getAllSharedDrafts(Pageable pageable) {
        log.debug("REST request to get a page of SharedDrafts");
        Page<SharedDraftsDTO> page = sharedDraftsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /shared-drafts/:id} : get the "id" sharedDrafts.
     *
     * @param id the id of the sharedDraftsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sharedDraftsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/shared-drafts/{id}")
    public ResponseEntity<SharedDraftsDTO> getSharedDrafts(@PathVariable Long id) {
        log.debug("REST request to get SharedDrafts : {}", id);
        Optional<SharedDraftsDTO> sharedDraftsDTO = sharedDraftsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sharedDraftsDTO);
    }

    /**
     * {@code DELETE  /shared-drafts/:id} : delete the "id" sharedDrafts.
     *
     * @param id the id of the sharedDraftsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/shared-drafts/{id}")
    public ResponseEntity<Void> deleteSharedDrafts(@PathVariable Long id) {
        log.debug("REST request to delete SharedDrafts : {}", id);
        sharedDraftsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
