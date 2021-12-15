/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.BackupDraftPostsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.BackupDraftPostsDTO;

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
 * REST controller for managing {@link com.infy.domain.BackupDraftPosts}.
 */
@RestController
@RequestMapping("/api")
public class BackupDraftPostsResource {

    private final Logger log = LoggerFactory.getLogger(BackupDraftPostsResource.class);

    private static final String ENTITY_NAME = "backupDraftPosts";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BackupDraftPostsService backupDraftPostsService;

    public BackupDraftPostsResource(BackupDraftPostsService backupDraftPostsService) {
        this.backupDraftPostsService = backupDraftPostsService;
    }

    /**
     * {@code POST  /backup-draft-posts} : Create a new backupDraftPosts.
     *
     * @param backupDraftPostsDTO the backupDraftPostsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new backupDraftPostsDTO, or with status {@code 400 (Bad Request)} if the backupDraftPosts has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/backup-draft-posts")
    public ResponseEntity<BackupDraftPostsDTO> createBackupDraftPosts(@Valid @RequestBody BackupDraftPostsDTO backupDraftPostsDTO) throws URISyntaxException {
        log.debug("REST request to save BackupDraftPosts : {}", backupDraftPostsDTO);
        if (backupDraftPostsDTO.getId() != null) {
            throw new BadRequestAlertException("A new backupDraftPosts cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BackupDraftPostsDTO result = backupDraftPostsService.save(backupDraftPostsDTO);
        return ResponseEntity.created(new URI("/api/backup-draft-posts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /backup-draft-posts} : Updates an existing backupDraftPosts.
     *
     * @param backupDraftPostsDTO the backupDraftPostsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated backupDraftPostsDTO,
     * or with status {@code 400 (Bad Request)} if the backupDraftPostsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the backupDraftPostsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/backup-draft-posts")
    public ResponseEntity<BackupDraftPostsDTO> updateBackupDraftPosts(@Valid @RequestBody BackupDraftPostsDTO backupDraftPostsDTO) throws URISyntaxException {
        log.debug("REST request to update BackupDraftPosts : {}", backupDraftPostsDTO);
        if (backupDraftPostsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BackupDraftPostsDTO result = backupDraftPostsService.save(backupDraftPostsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, backupDraftPostsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /backup-draft-posts} : get all the backupDraftPosts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of backupDraftPosts in body.
     */
    @GetMapping("/backup-draft-posts")
    public ResponseEntity<List<BackupDraftPostsDTO>> getAllBackupDraftPosts(Pageable pageable) {
        log.debug("REST request to get a page of BackupDraftPosts");
        Page<BackupDraftPostsDTO> page = backupDraftPostsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /backup-draft-posts/:id} : get the "id" backupDraftPosts.
     *
     * @param id the id of the backupDraftPostsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the backupDraftPostsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/backup-draft-posts/{id}")
    public ResponseEntity<BackupDraftPostsDTO> getBackupDraftPosts(@PathVariable Long id) {
        log.debug("REST request to get BackupDraftPosts : {}", id);
        Optional<BackupDraftPostsDTO> backupDraftPostsDTO = backupDraftPostsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(backupDraftPostsDTO);
    }

    /**
     * {@code DELETE  /backup-draft-posts/:id} : delete the "id" backupDraftPosts.
     *
     * @param id the id of the backupDraftPostsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/backup-draft-posts/{id}")
    public ResponseEntity<Void> deleteBackupDraftPosts(@PathVariable Long id) {
        log.debug("REST request to delete BackupDraftPosts : {}", id);
        backupDraftPostsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
