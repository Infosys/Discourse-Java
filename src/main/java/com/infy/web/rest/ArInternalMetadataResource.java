/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.ArInternalMetadataService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.ArInternalMetadataDTO;

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
 * REST controller for managing {@link com.infy.domain.ArInternalMetadata}.
 */
@RestController
@RequestMapping("/api")
public class ArInternalMetadataResource {

    private final Logger log = LoggerFactory.getLogger(ArInternalMetadataResource.class);

    private static final String ENTITY_NAME = "arInternalMetadata";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArInternalMetadataService arInternalMetadataService;

    public ArInternalMetadataResource(ArInternalMetadataService arInternalMetadataService) {
        this.arInternalMetadataService = arInternalMetadataService;
    }

    /**
     * {@code POST  /ar-internal-metadata} : Create a new arInternalMetadata.
     *
     * @param arInternalMetadataDTO the arInternalMetadataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new arInternalMetadataDTO, or with status {@code 400 (Bad Request)} if the arInternalMetadata has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ar-internal-metadata")
    public ResponseEntity<ArInternalMetadataDTO> createArInternalMetadata(@Valid @RequestBody ArInternalMetadataDTO arInternalMetadataDTO) throws URISyntaxException {
        log.debug("REST request to save ArInternalMetadata : {}", arInternalMetadataDTO);
        if (arInternalMetadataDTO.getId() != null) {
            throw new BadRequestAlertException("A new arInternalMetadata cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ArInternalMetadataDTO result = arInternalMetadataService.save(arInternalMetadataDTO);
        return ResponseEntity.created(new URI("/api/ar-internal-metadata/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ar-internal-metadata} : Updates an existing arInternalMetadata.
     *
     * @param arInternalMetadataDTO the arInternalMetadataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated arInternalMetadataDTO,
     * or with status {@code 400 (Bad Request)} if the arInternalMetadataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the arInternalMetadataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ar-internal-metadata")
    public ResponseEntity<ArInternalMetadataDTO> updateArInternalMetadata(@Valid @RequestBody ArInternalMetadataDTO arInternalMetadataDTO) throws URISyntaxException {
        log.debug("REST request to update ArInternalMetadata : {}", arInternalMetadataDTO);
        if (arInternalMetadataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ArInternalMetadataDTO result = arInternalMetadataService.save(arInternalMetadataDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, arInternalMetadataDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ar-internal-metadata} : get all the arInternalMetadata.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of arInternalMetadata in body.
     */
    @GetMapping("/ar-internal-metadata")
    public ResponseEntity<List<ArInternalMetadataDTO>> getAllArInternalMetadata(Pageable pageable) {
        log.debug("REST request to get a page of ArInternalMetadata");
        Page<ArInternalMetadataDTO> page = arInternalMetadataService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ar-internal-metadata/:id} : get the "id" arInternalMetadata.
     *
     * @param id the id of the arInternalMetadataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the arInternalMetadataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ar-internal-metadata/{id}")
    public ResponseEntity<ArInternalMetadataDTO> getArInternalMetadata(@PathVariable Long id) {
        log.debug("REST request to get ArInternalMetadata : {}", id);
        Optional<ArInternalMetadataDTO> arInternalMetadataDTO = arInternalMetadataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(arInternalMetadataDTO);
    }

    /**
     * {@code DELETE  /ar-internal-metadata/:id} : delete the "id" arInternalMetadata.
     *
     * @param id the id of the arInternalMetadataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ar-internal-metadata/{id}")
    public ResponseEntity<Void> deleteArInternalMetadata(@PathVariable Long id) {
        log.debug("REST request to delete ArInternalMetadata : {}", id);
        arInternalMetadataService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
