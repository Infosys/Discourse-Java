/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.OnceoffLogsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.OnceoffLogsDTO;

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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.infy.domain.OnceoffLogs}.
 */
@RestController
@RequestMapping("/api")
public class OnceoffLogsResource {

    private final Logger log = LoggerFactory.getLogger(OnceoffLogsResource.class);

    private static final String ENTITY_NAME = "onceoffLogs";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OnceoffLogsService onceoffLogsService;

    public OnceoffLogsResource(OnceoffLogsService onceoffLogsService) {
        this.onceoffLogsService = onceoffLogsService;
    }

    /**
     * {@code POST  /onceoff-logs} : Create a new onceoffLogs.
     *
     * @param onceoffLogsDTO the onceoffLogsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new onceoffLogsDTO, or with status {@code 400 (Bad Request)} if the onceoffLogs has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/onceoff-logs")
    public ResponseEntity<OnceoffLogsDTO> createOnceoffLogs(@RequestBody OnceoffLogsDTO onceoffLogsDTO) throws URISyntaxException {
        log.debug("REST request to save OnceoffLogs : {}", onceoffLogsDTO);
        if (onceoffLogsDTO.getId() != null) {
            throw new BadRequestAlertException("A new onceoffLogs cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OnceoffLogsDTO result = onceoffLogsService.save(onceoffLogsDTO);
        return ResponseEntity.created(new URI("/api/onceoff-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /onceoff-logs} : Updates an existing onceoffLogs.
     *
     * @param onceoffLogsDTO the onceoffLogsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated onceoffLogsDTO,
     * or with status {@code 400 (Bad Request)} if the onceoffLogsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the onceoffLogsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/onceoff-logs")
    public ResponseEntity<OnceoffLogsDTO> updateOnceoffLogs(@RequestBody OnceoffLogsDTO onceoffLogsDTO) throws URISyntaxException {
        log.debug("REST request to update OnceoffLogs : {}", onceoffLogsDTO);
        if (onceoffLogsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OnceoffLogsDTO result = onceoffLogsService.save(onceoffLogsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, onceoffLogsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /onceoff-logs} : get all the onceoffLogs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of onceoffLogs in body.
     */
    @GetMapping("/onceoff-logs")
    public ResponseEntity<List<OnceoffLogsDTO>> getAllOnceoffLogs(Pageable pageable) {
        log.debug("REST request to get a page of OnceoffLogs");
        Page<OnceoffLogsDTO> page = onceoffLogsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /onceoff-logs/:id} : get the "id" onceoffLogs.
     *
     * @param id the id of the onceoffLogsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the onceoffLogsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/onceoff-logs/{id}")
    public ResponseEntity<OnceoffLogsDTO> getOnceoffLogs(@PathVariable Long id) {
        log.debug("REST request to get OnceoffLogs : {}", id);
        Optional<OnceoffLogsDTO> onceoffLogsDTO = onceoffLogsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(onceoffLogsDTO);
    }

    /**
     * {@code DELETE  /onceoff-logs/:id} : delete the "id" onceoffLogs.
     *
     * @param id the id of the onceoffLogsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/onceoff-logs/{id}")
    public ResponseEntity<Void> deleteOnceoffLogs(@PathVariable Long id) {
        log.debug("REST request to delete OnceoffLogs : {}", id);
        onceoffLogsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
