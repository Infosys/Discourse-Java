/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.SearchLogsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.SearchLogsDTO;

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
 * REST controller for managing {@link com.infy.domain.SearchLogs}.
 */
@RestController
@RequestMapping("/api")
public class SearchLogsResource {

    private final Logger log = LoggerFactory.getLogger(SearchLogsResource.class);

    private static final String ENTITY_NAME = "searchLogs";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SearchLogsService searchLogsService;

    public SearchLogsResource(SearchLogsService searchLogsService) {
        this.searchLogsService = searchLogsService;
    }

    /**
     * {@code POST  /search-logs} : Create a new searchLogs.
     *
     * @param searchLogsDTO the searchLogsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new searchLogsDTO, or with status {@code 400 (Bad Request)} if the searchLogs has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/search-logs")
    public ResponseEntity<SearchLogsDTO> createSearchLogs(@Valid @RequestBody SearchLogsDTO searchLogsDTO) throws URISyntaxException {
        log.debug("REST request to save SearchLogs : {}", searchLogsDTO);
        if (searchLogsDTO.getId() != null) {
            throw new BadRequestAlertException("A new searchLogs cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SearchLogsDTO result = searchLogsService.save(searchLogsDTO);
        return ResponseEntity.created(new URI("/api/search-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /search-logs} : Updates an existing searchLogs.
     *
     * @param searchLogsDTO the searchLogsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated searchLogsDTO,
     * or with status {@code 400 (Bad Request)} if the searchLogsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the searchLogsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/search-logs")
    public ResponseEntity<SearchLogsDTO> updateSearchLogs(@Valid @RequestBody SearchLogsDTO searchLogsDTO) throws URISyntaxException {
        log.debug("REST request to update SearchLogs : {}", searchLogsDTO);
        if (searchLogsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SearchLogsDTO result = searchLogsService.save(searchLogsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, searchLogsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /search-logs} : get all the searchLogs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of searchLogs in body.
     */
    @GetMapping("/search-logs")
    public ResponseEntity<List<SearchLogsDTO>> getAllSearchLogs(Pageable pageable) {
        log.debug("REST request to get a page of SearchLogs");
        Page<SearchLogsDTO> page = searchLogsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /search-logs/:id} : get the "id" searchLogs.
     *
     * @param id the id of the searchLogsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the searchLogsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/search-logs/{id}")
    public ResponseEntity<SearchLogsDTO> getSearchLogs(@PathVariable Long id) {
        log.debug("REST request to get SearchLogs : {}", id);
        Optional<SearchLogsDTO> searchLogsDTO = searchLogsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(searchLogsDTO);
    }

    /**
     * {@code DELETE  /search-logs/:id} : delete the "id" searchLogs.
     *
     * @param id the id of the searchLogsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/search-logs/{id}")
    public ResponseEntity<Void> deleteSearchLogs(@PathVariable Long id) {
        log.debug("REST request to delete SearchLogs : {}", id);
        searchLogsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
