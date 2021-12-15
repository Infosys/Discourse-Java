/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.JavascriptCachesService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.JavascriptCachesDTO;

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
 * REST controller for managing {@link com.infy.domain.JavascriptCaches}.
 */
@RestController
@RequestMapping("/api")
public class JavascriptCachesResource {

    private final Logger log = LoggerFactory.getLogger(JavascriptCachesResource.class);

    private static final String ENTITY_NAME = "javascriptCaches";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JavascriptCachesService javascriptCachesService;

    public JavascriptCachesResource(JavascriptCachesService javascriptCachesService) {
        this.javascriptCachesService = javascriptCachesService;
    }

    /**
     * {@code POST  /javascript-caches} : Create a new javascriptCaches.
     *
     * @param javascriptCachesDTO the javascriptCachesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new javascriptCachesDTO, or with status {@code 400 (Bad Request)} if the javascriptCaches has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/javascript-caches")
    public ResponseEntity<JavascriptCachesDTO> createJavascriptCaches(@Valid @RequestBody JavascriptCachesDTO javascriptCachesDTO) throws URISyntaxException {
        log.debug("REST request to save JavascriptCaches : {}", javascriptCachesDTO);
        if (javascriptCachesDTO.getId() != null) {
            throw new BadRequestAlertException("A new javascriptCaches cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JavascriptCachesDTO result = javascriptCachesService.save(javascriptCachesDTO);
        return ResponseEntity.created(new URI("/api/javascript-caches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /javascript-caches} : Updates an existing javascriptCaches.
     *
     * @param javascriptCachesDTO the javascriptCachesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated javascriptCachesDTO,
     * or with status {@code 400 (Bad Request)} if the javascriptCachesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the javascriptCachesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/javascript-caches")
    public ResponseEntity<JavascriptCachesDTO> updateJavascriptCaches(@Valid @RequestBody JavascriptCachesDTO javascriptCachesDTO) throws URISyntaxException {
        log.debug("REST request to update JavascriptCaches : {}", javascriptCachesDTO);
        if (javascriptCachesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        JavascriptCachesDTO result = javascriptCachesService.save(javascriptCachesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, javascriptCachesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /javascript-caches} : get all the javascriptCaches.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of javascriptCaches in body.
     */
    @GetMapping("/javascript-caches")
    public ResponseEntity<List<JavascriptCachesDTO>> getAllJavascriptCaches(Pageable pageable) {
        log.debug("REST request to get a page of JavascriptCaches");
        Page<JavascriptCachesDTO> page = javascriptCachesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /javascript-caches/:id} : get the "id" javascriptCaches.
     *
     * @param id the id of the javascriptCachesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the javascriptCachesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/javascript-caches/{id}")
    public ResponseEntity<JavascriptCachesDTO> getJavascriptCaches(@PathVariable Long id) {
        log.debug("REST request to get JavascriptCaches : {}", id);
        Optional<JavascriptCachesDTO> javascriptCachesDTO = javascriptCachesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(javascriptCachesDTO);
    }

    /**
     * {@code DELETE  /javascript-caches/:id} : delete the "id" javascriptCaches.
     *
     * @param id the id of the javascriptCachesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/javascript-caches/{id}")
    public ResponseEntity<Void> deleteJavascriptCaches(@PathVariable Long id) {
        log.debug("REST request to delete JavascriptCaches : {}", id);
        javascriptCachesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
