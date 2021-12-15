/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.ImapSyncLogsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.ImapSyncLogsDTO;

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
 * REST controller for managing {@link com.infy.domain.ImapSyncLogs}.
 */
@RestController
@RequestMapping("/api")
public class ImapSyncLogsResource {

    private final Logger log = LoggerFactory.getLogger(ImapSyncLogsResource.class);

    private static final String ENTITY_NAME = "imapSyncLogs";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ImapSyncLogsService imapSyncLogsService;

    public ImapSyncLogsResource(ImapSyncLogsService imapSyncLogsService) {
        this.imapSyncLogsService = imapSyncLogsService;
    }

    /**
     * {@code POST  /imap-sync-logs} : Create a new imapSyncLogs.
     *
     * @param imapSyncLogsDTO the imapSyncLogsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new imapSyncLogsDTO, or with status {@code 400 (Bad Request)} if the imapSyncLogs has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/imap-sync-logs")
    public ResponseEntity<ImapSyncLogsDTO> createImapSyncLogs(@Valid @RequestBody ImapSyncLogsDTO imapSyncLogsDTO) throws URISyntaxException {
        log.debug("REST request to save ImapSyncLogs : {}", imapSyncLogsDTO);
        if (imapSyncLogsDTO.getId() != null) {
            throw new BadRequestAlertException("A new imapSyncLogs cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ImapSyncLogsDTO result = imapSyncLogsService.save(imapSyncLogsDTO);
        return ResponseEntity.created(new URI("/api/imap-sync-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /imap-sync-logs} : Updates an existing imapSyncLogs.
     *
     * @param imapSyncLogsDTO the imapSyncLogsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated imapSyncLogsDTO,
     * or with status {@code 400 (Bad Request)} if the imapSyncLogsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the imapSyncLogsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/imap-sync-logs")
    public ResponseEntity<ImapSyncLogsDTO> updateImapSyncLogs(@Valid @RequestBody ImapSyncLogsDTO imapSyncLogsDTO) throws URISyntaxException {
        log.debug("REST request to update ImapSyncLogs : {}", imapSyncLogsDTO);
        if (imapSyncLogsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ImapSyncLogsDTO result = imapSyncLogsService.save(imapSyncLogsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, imapSyncLogsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /imap-sync-logs} : get all the imapSyncLogs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of imapSyncLogs in body.
     */
    @GetMapping("/imap-sync-logs")
    public ResponseEntity<List<ImapSyncLogsDTO>> getAllImapSyncLogs(Pageable pageable) {
        log.debug("REST request to get a page of ImapSyncLogs");
        Page<ImapSyncLogsDTO> page = imapSyncLogsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /imap-sync-logs/:id} : get the "id" imapSyncLogs.
     *
     * @param id the id of the imapSyncLogsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the imapSyncLogsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/imap-sync-logs/{id}")
    public ResponseEntity<ImapSyncLogsDTO> getImapSyncLogs(@PathVariable Long id) {
        log.debug("REST request to get ImapSyncLogs : {}", id);
        Optional<ImapSyncLogsDTO> imapSyncLogsDTO = imapSyncLogsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(imapSyncLogsDTO);
    }

    /**
     * {@code DELETE  /imap-sync-logs/:id} : delete the "id" imapSyncLogs.
     *
     * @param id the id of the imapSyncLogsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/imap-sync-logs/{id}")
    public ResponseEntity<Void> deleteImapSyncLogs(@PathVariable Long id) {
        log.debug("REST request to delete ImapSyncLogs : {}", id);
        imapSyncLogsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
