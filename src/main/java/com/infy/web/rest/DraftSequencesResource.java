/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.DraftSequencesService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.DraftSequencesDTO;

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
 * REST controller for managing {@link com.infy.domain.DraftSequences}.
 */
@RestController
@RequestMapping("/api")
public class DraftSequencesResource {

    private final Logger log = LoggerFactory.getLogger(DraftSequencesResource.class);

    private static final String ENTITY_NAME = "draftSequences";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DraftSequencesService draftSequencesService;

    public DraftSequencesResource(DraftSequencesService draftSequencesService) {
        this.draftSequencesService = draftSequencesService;
    }

    /**
     * {@code POST  /draft-sequences} : Create a new draftSequences.
     *
     * @param draftSequencesDTO the draftSequencesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new draftSequencesDTO, or with status {@code 400 (Bad Request)} if the draftSequences has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/draft-sequences")
    public ResponseEntity<DraftSequencesDTO> createDraftSequences(@Valid @RequestBody DraftSequencesDTO draftSequencesDTO) throws URISyntaxException {
        log.debug("REST request to save DraftSequences : {}", draftSequencesDTO);
        if (draftSequencesDTO.getId() != null) {
            throw new BadRequestAlertException("A new draftSequences cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DraftSequencesDTO result = draftSequencesService.save(draftSequencesDTO);
        return ResponseEntity.created(new URI("/api/draft-sequences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /draft-sequences} : Updates an existing draftSequences.
     *
     * @param draftSequencesDTO the draftSequencesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated draftSequencesDTO,
     * or with status {@code 400 (Bad Request)} if the draftSequencesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the draftSequencesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/draft-sequences")
    public ResponseEntity<DraftSequencesDTO> updateDraftSequences(@Valid @RequestBody DraftSequencesDTO draftSequencesDTO) throws URISyntaxException {
        log.debug("REST request to update DraftSequences : {}", draftSequencesDTO);
        if (draftSequencesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DraftSequencesDTO result = draftSequencesService.save(draftSequencesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, draftSequencesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /draft-sequences} : get all the draftSequences.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of draftSequences in body.
     */
    @GetMapping("/draft-sequences")
    public ResponseEntity<List<DraftSequencesDTO>> getAllDraftSequences(Pageable pageable) {
        log.debug("REST request to get a page of DraftSequences");
        Page<DraftSequencesDTO> page = draftSequencesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /draft-sequences/:id} : get the "id" draftSequences.
     *
     * @param id the id of the draftSequencesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the draftSequencesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/draft-sequences/{id}")
    public ResponseEntity<DraftSequencesDTO> getDraftSequences(@PathVariable Long id) {
        log.debug("REST request to get DraftSequences : {}", id);
        Optional<DraftSequencesDTO> draftSequencesDTO = draftSequencesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(draftSequencesDTO);
    }

    /**
     * {@code DELETE  /draft-sequences/:id} : delete the "id" draftSequences.
     *
     * @param id the id of the draftSequencesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/draft-sequences/{id}")
    public ResponseEntity<Void> deleteDraftSequences(@PathVariable Long id) {
        log.debug("REST request to delete DraftSequences : {}", id);
        draftSequencesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
