/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.SkippedEmailLogsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.SkippedEmailLogsDTO;

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
 * REST controller for managing {@link com.infy.domain.SkippedEmailLogs}.
 */
@RestController
@RequestMapping("/api")
public class SkippedEmailLogsResource {

    private final Logger log = LoggerFactory.getLogger(SkippedEmailLogsResource.class);

    private static final String ENTITY_NAME = "skippedEmailLogs";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SkippedEmailLogsService skippedEmailLogsService;

    public SkippedEmailLogsResource(SkippedEmailLogsService skippedEmailLogsService) {
        this.skippedEmailLogsService = skippedEmailLogsService;
    }

    /**
     * {@code POST  /skipped-email-logs} : Create a new skippedEmailLogs.
     *
     * @param skippedEmailLogsDTO the skippedEmailLogsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new skippedEmailLogsDTO, or with status {@code 400 (Bad Request)} if the skippedEmailLogs has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/skipped-email-logs")
    public ResponseEntity<SkippedEmailLogsDTO> createSkippedEmailLogs(@Valid @RequestBody SkippedEmailLogsDTO skippedEmailLogsDTO) throws URISyntaxException {
        log.debug("REST request to save SkippedEmailLogs : {}", skippedEmailLogsDTO);
        if (skippedEmailLogsDTO.getId() != null) {
            throw new BadRequestAlertException("A new skippedEmailLogs cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SkippedEmailLogsDTO result = skippedEmailLogsService.save(skippedEmailLogsDTO);
        return ResponseEntity.created(new URI("/api/skipped-email-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /skipped-email-logs} : Updates an existing skippedEmailLogs.
     *
     * @param skippedEmailLogsDTO the skippedEmailLogsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated skippedEmailLogsDTO,
     * or with status {@code 400 (Bad Request)} if the skippedEmailLogsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the skippedEmailLogsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/skipped-email-logs")
    public ResponseEntity<SkippedEmailLogsDTO> updateSkippedEmailLogs(@Valid @RequestBody SkippedEmailLogsDTO skippedEmailLogsDTO) throws URISyntaxException {
        log.debug("REST request to update SkippedEmailLogs : {}", skippedEmailLogsDTO);
        if (skippedEmailLogsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SkippedEmailLogsDTO result = skippedEmailLogsService.save(skippedEmailLogsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, skippedEmailLogsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /skipped-email-logs} : get all the skippedEmailLogs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of skippedEmailLogs in body.
     */
    @GetMapping("/skipped-email-logs")
    public ResponseEntity<List<SkippedEmailLogsDTO>> getAllSkippedEmailLogs(Pageable pageable) {
        log.debug("REST request to get a page of SkippedEmailLogs");
        Page<SkippedEmailLogsDTO> page = skippedEmailLogsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /skipped-email-logs/:id} : get the "id" skippedEmailLogs.
     *
     * @param id the id of the skippedEmailLogsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the skippedEmailLogsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/skipped-email-logs/{id}")
    public ResponseEntity<SkippedEmailLogsDTO> getSkippedEmailLogs(@PathVariable Long id) {
        log.debug("REST request to get SkippedEmailLogs : {}", id);
        Optional<SkippedEmailLogsDTO> skippedEmailLogsDTO = skippedEmailLogsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(skippedEmailLogsDTO);
    }

    /**
     * {@code DELETE  /skipped-email-logs/:id} : delete the "id" skippedEmailLogs.
     *
     * @param id the id of the skippedEmailLogsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/skipped-email-logs/{id}")
    public ResponseEntity<Void> deleteSkippedEmailLogs(@PathVariable Long id) {
        log.debug("REST request to delete SkippedEmailLogs : {}", id);
        skippedEmailLogsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
