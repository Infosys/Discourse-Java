/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.StylesheetCacheService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.StylesheetCacheDTO;

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
 * REST controller for managing {@link com.infy.domain.StylesheetCache}.
 */
@RestController
@RequestMapping("/api")
public class StylesheetCacheResource {

    private final Logger log = LoggerFactory.getLogger(StylesheetCacheResource.class);

    private static final String ENTITY_NAME = "stylesheetCache";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StylesheetCacheService stylesheetCacheService;

    public StylesheetCacheResource(StylesheetCacheService stylesheetCacheService) {
        this.stylesheetCacheService = stylesheetCacheService;
    }

    /**
     * {@code POST  /stylesheet-caches} : Create a new stylesheetCache.
     *
     * @param stylesheetCacheDTO the stylesheetCacheDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stylesheetCacheDTO, or with status {@code 400 (Bad Request)} if the stylesheetCache has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/stylesheet-caches")
    public ResponseEntity<StylesheetCacheDTO> createStylesheetCache(@Valid @RequestBody StylesheetCacheDTO stylesheetCacheDTO) throws URISyntaxException {
        log.debug("REST request to save StylesheetCache : {}", stylesheetCacheDTO);
        if (stylesheetCacheDTO.getId() != null) {
            throw new BadRequestAlertException("A new stylesheetCache cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StylesheetCacheDTO result = stylesheetCacheService.save(stylesheetCacheDTO);
        return ResponseEntity.created(new URI("/api/stylesheet-caches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /stylesheet-caches} : Updates an existing stylesheetCache.
     *
     * @param stylesheetCacheDTO the stylesheetCacheDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stylesheetCacheDTO,
     * or with status {@code 400 (Bad Request)} if the stylesheetCacheDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stylesheetCacheDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/stylesheet-caches")
    public ResponseEntity<StylesheetCacheDTO> updateStylesheetCache(@Valid @RequestBody StylesheetCacheDTO stylesheetCacheDTO) throws URISyntaxException {
        log.debug("REST request to update StylesheetCache : {}", stylesheetCacheDTO);
        if (stylesheetCacheDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StylesheetCacheDTO result = stylesheetCacheService.save(stylesheetCacheDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, stylesheetCacheDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /stylesheet-caches} : get all the stylesheetCaches.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stylesheetCaches in body.
     */
    @GetMapping("/stylesheet-caches")
    public ResponseEntity<List<StylesheetCacheDTO>> getAllStylesheetCaches(Pageable pageable) {
        log.debug("REST request to get a page of StylesheetCaches");
        Page<StylesheetCacheDTO> page = stylesheetCacheService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /stylesheet-caches/:id} : get the "id" stylesheetCache.
     *
     * @param id the id of the stylesheetCacheDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stylesheetCacheDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/stylesheet-caches/{id}")
    public ResponseEntity<StylesheetCacheDTO> getStylesheetCache(@PathVariable Long id) {
        log.debug("REST request to get StylesheetCache : {}", id);
        Optional<StylesheetCacheDTO> stylesheetCacheDTO = stylesheetCacheService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stylesheetCacheDTO);
    }

    /**
     * {@code DELETE  /stylesheet-caches/:id} : delete the "id" stylesheetCache.
     *
     * @param id the id of the stylesheetCacheDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/stylesheet-caches/{id}")
    public ResponseEntity<Void> deleteStylesheetCache(@PathVariable Long id) {
        log.debug("REST request to delete StylesheetCache : {}", id);
        stylesheetCacheService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
