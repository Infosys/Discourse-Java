/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.BackupMetadataService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.BackupMetadataDTO;

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
 * REST controller for managing {@link com.infy.domain.BackupMetadata}.
 */
@RestController
@RequestMapping("/api")
public class BackupMetadataResource {

    private final Logger log = LoggerFactory.getLogger(BackupMetadataResource.class);

    private static final String ENTITY_NAME = "backupMetadata";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BackupMetadataService backupMetadataService;

    public BackupMetadataResource(BackupMetadataService backupMetadataService) {
        this.backupMetadataService = backupMetadataService;
    }

    /**
     * {@code POST  /backup-metadata} : Create a new backupMetadata.
     *
     * @param backupMetadataDTO the backupMetadataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new backupMetadataDTO, or with status {@code 400 (Bad Request)} if the backupMetadata has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/backup-metadata")
    public ResponseEntity<BackupMetadataDTO> createBackupMetadata(@Valid @RequestBody BackupMetadataDTO backupMetadataDTO) throws URISyntaxException {
        log.debug("REST request to save BackupMetadata : {}", backupMetadataDTO);
        if (backupMetadataDTO.getId() != null) {
            throw new BadRequestAlertException("A new backupMetadata cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BackupMetadataDTO result = backupMetadataService.save(backupMetadataDTO);
        return ResponseEntity.created(new URI("/api/backup-metadata/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /backup-metadata} : Updates an existing backupMetadata.
     *
     * @param backupMetadataDTO the backupMetadataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated backupMetadataDTO,
     * or with status {@code 400 (Bad Request)} if the backupMetadataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the backupMetadataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/backup-metadata")
    public ResponseEntity<BackupMetadataDTO> updateBackupMetadata(@Valid @RequestBody BackupMetadataDTO backupMetadataDTO) throws URISyntaxException {
        log.debug("REST request to update BackupMetadata : {}", backupMetadataDTO);
        if (backupMetadataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BackupMetadataDTO result = backupMetadataService.save(backupMetadataDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, backupMetadataDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /backup-metadata} : get all the backupMetadata.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of backupMetadata in body.
     */
    @GetMapping("/backup-metadata")
    public ResponseEntity<List<BackupMetadataDTO>> getAllBackupMetadata(Pageable pageable) {
        log.debug("REST request to get a page of BackupMetadata");
        Page<BackupMetadataDTO> page = backupMetadataService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /backup-metadata/:id} : get the "id" backupMetadata.
     *
     * @param id the id of the backupMetadataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the backupMetadataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/backup-metadata/{id}")
    public ResponseEntity<BackupMetadataDTO> getBackupMetadata(@PathVariable Long id) {
        log.debug("REST request to get BackupMetadata : {}", id);
        Optional<BackupMetadataDTO> backupMetadataDTO = backupMetadataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(backupMetadataDTO);
    }

    /**
     * {@code DELETE  /backup-metadata/:id} : delete the "id" backupMetadata.
     *
     * @param id the id of the backupMetadataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/backup-metadata/{id}")
    public ResponseEntity<Void> deleteBackupMetadata(@PathVariable Long id) {
        log.debug("REST request to delete BackupMetadata : {}", id);
        backupMetadataService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
