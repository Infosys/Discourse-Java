/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.SchemaMigrationsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.SchemaMigrationsDTO;

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
 * REST controller for managing {@link com.infy.domain.SchemaMigrations}.
 */
@RestController
@RequestMapping("/api")
public class SchemaMigrationsResource {

    private final Logger log = LoggerFactory.getLogger(SchemaMigrationsResource.class);

    private static final String ENTITY_NAME = "schemaMigrations";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SchemaMigrationsService schemaMigrationsService;

    public SchemaMigrationsResource(SchemaMigrationsService schemaMigrationsService) {
        this.schemaMigrationsService = schemaMigrationsService;
    }

    /**
     * {@code POST  /schema-migrations} : Create a new schemaMigrations.
     *
     * @param schemaMigrationsDTO the schemaMigrationsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new schemaMigrationsDTO, or with status {@code 400 (Bad Request)} if the schemaMigrations has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/schema-migrations")
    public ResponseEntity<SchemaMigrationsDTO> createSchemaMigrations(@Valid @RequestBody SchemaMigrationsDTO schemaMigrationsDTO) throws URISyntaxException {
        log.debug("REST request to save SchemaMigrations : {}", schemaMigrationsDTO);
        if (schemaMigrationsDTO.getId() != null) {
            throw new BadRequestAlertException("A new schemaMigrations cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SchemaMigrationsDTO result = schemaMigrationsService.save(schemaMigrationsDTO);
        return ResponseEntity.created(new URI("/api/schema-migrations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /schema-migrations} : Updates an existing schemaMigrations.
     *
     * @param schemaMigrationsDTO the schemaMigrationsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated schemaMigrationsDTO,
     * or with status {@code 400 (Bad Request)} if the schemaMigrationsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the schemaMigrationsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/schema-migrations")
    public ResponseEntity<SchemaMigrationsDTO> updateSchemaMigrations(@Valid @RequestBody SchemaMigrationsDTO schemaMigrationsDTO) throws URISyntaxException {
        log.debug("REST request to update SchemaMigrations : {}", schemaMigrationsDTO);
        if (schemaMigrationsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SchemaMigrationsDTO result = schemaMigrationsService.save(schemaMigrationsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, schemaMigrationsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /schema-migrations} : get all the schemaMigrations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of schemaMigrations in body.
     */
    @GetMapping("/schema-migrations")
    public ResponseEntity<List<SchemaMigrationsDTO>> getAllSchemaMigrations(Pageable pageable) {
        log.debug("REST request to get a page of SchemaMigrations");
        Page<SchemaMigrationsDTO> page = schemaMigrationsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /schema-migrations/:id} : get the "id" schemaMigrations.
     *
     * @param id the id of the schemaMigrationsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the schemaMigrationsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/schema-migrations/{id}")
    public ResponseEntity<SchemaMigrationsDTO> getSchemaMigrations(@PathVariable Long id) {
        log.debug("REST request to get SchemaMigrations : {}", id);
        Optional<SchemaMigrationsDTO> schemaMigrationsDTO = schemaMigrationsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(schemaMigrationsDTO);
    }

    /**
     * {@code DELETE  /schema-migrations/:id} : delete the "id" schemaMigrations.
     *
     * @param id the id of the schemaMigrationsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/schema-migrations/{id}")
    public ResponseEntity<Void> deleteSchemaMigrations(@PathVariable Long id) {
        log.debug("REST request to delete SchemaMigrations : {}", id);
        schemaMigrationsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
