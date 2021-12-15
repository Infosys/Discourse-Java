/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.DraftsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.DraftsDTO;

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
 * REST controller for managing {@link com.infy.domain.Drafts}.
 */
@RestController
@RequestMapping("/api")
public class DraftsResource {

    private final Logger log = LoggerFactory.getLogger(DraftsResource.class);

    private static final String ENTITY_NAME = "drafts";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DraftsService draftsService;

    public DraftsResource(DraftsService draftsService) {
        this.draftsService = draftsService;
    }

    /**
     * {@code POST  /drafts} : Create a new drafts.
     *
     * @param draftsDTO the draftsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new draftsDTO, or with status {@code 400 (Bad Request)} if the drafts has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/drafts")
    public ResponseEntity<DraftsDTO> createDrafts(@Valid @RequestBody DraftsDTO draftsDTO) throws URISyntaxException {
        log.debug("REST request to save Drafts : {}", draftsDTO);
        if (draftsDTO.getId() != null) {
            throw new BadRequestAlertException("A new drafts cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DraftsDTO result = draftsService.save(draftsDTO);
        return ResponseEntity.created(new URI("/api/drafts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /drafts} : Updates an existing drafts.
     *
     * @param draftsDTO the draftsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated draftsDTO,
     * or with status {@code 400 (Bad Request)} if the draftsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the draftsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/drafts")
    public ResponseEntity<DraftsDTO> updateDrafts(@Valid @RequestBody DraftsDTO draftsDTO) throws URISyntaxException {
        log.debug("REST request to update Drafts : {}", draftsDTO);
        if (draftsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DraftsDTO result = draftsService.save(draftsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, draftsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /drafts} : get all the drafts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of drafts in body.
     */
    @GetMapping("/drafts")
    public ResponseEntity<List<DraftsDTO>> getAllDrafts(Pageable pageable) {
        log.debug("REST request to get a page of Drafts");
        Page<DraftsDTO> page = draftsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /drafts/:id} : get the "id" drafts.
     *
     * @param id the id of the draftsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the draftsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/drafts/{id}")
    public ResponseEntity<DraftsDTO> getDrafts(@PathVariable Long id) {
        log.debug("REST request to get Drafts : {}", id);
        Optional<DraftsDTO> draftsDTO = draftsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(draftsDTO);
    }

    /**
     * {@code DELETE  /drafts/:id} : delete the "id" drafts.
     *
     * @param id the id of the draftsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/drafts/{id}")
    public ResponseEntity<Void> deleteDrafts(@PathVariable Long id) {
        log.debug("REST request to delete Drafts : {}", id);
        draftsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
