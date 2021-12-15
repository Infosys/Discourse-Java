/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.BackupDraftTopicsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.BackupDraftTopicsDTO;

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
 * REST controller for managing {@link com.infy.domain.BackupDraftTopics}.
 */
@RestController
@RequestMapping("/api")
public class BackupDraftTopicsResource {

    private final Logger log = LoggerFactory.getLogger(BackupDraftTopicsResource.class);

    private static final String ENTITY_NAME = "backupDraftTopics";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BackupDraftTopicsService backupDraftTopicsService;

    public BackupDraftTopicsResource(BackupDraftTopicsService backupDraftTopicsService) {
        this.backupDraftTopicsService = backupDraftTopicsService;
    }

    /**
     * {@code POST  /backup-draft-topics} : Create a new backupDraftTopics.
     *
     * @param backupDraftTopicsDTO the backupDraftTopicsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new backupDraftTopicsDTO, or with status {@code 400 (Bad Request)} if the backupDraftTopics has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/backup-draft-topics")
    public ResponseEntity<BackupDraftTopicsDTO> createBackupDraftTopics(@Valid @RequestBody BackupDraftTopicsDTO backupDraftTopicsDTO) throws URISyntaxException {
        log.debug("REST request to save BackupDraftTopics : {}", backupDraftTopicsDTO);
        if (backupDraftTopicsDTO.getId() != null) {
            throw new BadRequestAlertException("A new backupDraftTopics cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BackupDraftTopicsDTO result = backupDraftTopicsService.save(backupDraftTopicsDTO);
        return ResponseEntity.created(new URI("/api/backup-draft-topics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /backup-draft-topics} : Updates an existing backupDraftTopics.
     *
     * @param backupDraftTopicsDTO the backupDraftTopicsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated backupDraftTopicsDTO,
     * or with status {@code 400 (Bad Request)} if the backupDraftTopicsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the backupDraftTopicsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/backup-draft-topics")
    public ResponseEntity<BackupDraftTopicsDTO> updateBackupDraftTopics(@Valid @RequestBody BackupDraftTopicsDTO backupDraftTopicsDTO) throws URISyntaxException {
        log.debug("REST request to update BackupDraftTopics : {}", backupDraftTopicsDTO);
        if (backupDraftTopicsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BackupDraftTopicsDTO result = backupDraftTopicsService.save(backupDraftTopicsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, backupDraftTopicsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /backup-draft-topics} : get all the backupDraftTopics.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of backupDraftTopics in body.
     */
    @GetMapping("/backup-draft-topics")
    public ResponseEntity<List<BackupDraftTopicsDTO>> getAllBackupDraftTopics(Pageable pageable) {
        log.debug("REST request to get a page of BackupDraftTopics");
        Page<BackupDraftTopicsDTO> page = backupDraftTopicsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /backup-draft-topics/:id} : get the "id" backupDraftTopics.
     *
     * @param id the id of the backupDraftTopicsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the backupDraftTopicsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/backup-draft-topics/{id}")
    public ResponseEntity<BackupDraftTopicsDTO> getBackupDraftTopics(@PathVariable Long id) {
        log.debug("REST request to get BackupDraftTopics : {}", id);
        Optional<BackupDraftTopicsDTO> backupDraftTopicsDTO = backupDraftTopicsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(backupDraftTopicsDTO);
    }

    /**
     * {@code DELETE  /backup-draft-topics/:id} : delete the "id" backupDraftTopics.
     *
     * @param id the id of the backupDraftTopicsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/backup-draft-topics/{id}")
    public ResponseEntity<Void> deleteBackupDraftTopics(@PathVariable Long id) {
        log.debug("REST request to delete BackupDraftTopics : {}", id);
        backupDraftTopicsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
