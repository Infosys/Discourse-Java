/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.SingleSignOnRecordsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.SingleSignOnRecordsDTO;

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
 * REST controller for managing {@link com.infy.domain.SingleSignOnRecords}.
 */
@RestController
@RequestMapping("/api")
public class SingleSignOnRecordsResource {

    private final Logger log = LoggerFactory.getLogger(SingleSignOnRecordsResource.class);

    private static final String ENTITY_NAME = "singleSignOnRecords";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SingleSignOnRecordsService singleSignOnRecordsService;

    public SingleSignOnRecordsResource(SingleSignOnRecordsService singleSignOnRecordsService) {
        this.singleSignOnRecordsService = singleSignOnRecordsService;
    }

    /**
     * {@code POST  /single-sign-on-records} : Create a new singleSignOnRecords.
     *
     * @param singleSignOnRecordsDTO the singleSignOnRecordsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new singleSignOnRecordsDTO, or with status {@code 400 (Bad Request)} if the singleSignOnRecords has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/single-sign-on-records")
    public ResponseEntity<SingleSignOnRecordsDTO> createSingleSignOnRecords(@Valid @RequestBody SingleSignOnRecordsDTO singleSignOnRecordsDTO) throws URISyntaxException {
        log.debug("REST request to save SingleSignOnRecords : {}", singleSignOnRecordsDTO);
        if (singleSignOnRecordsDTO.getId() != null) {
            throw new BadRequestAlertException("A new singleSignOnRecords cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SingleSignOnRecordsDTO result = singleSignOnRecordsService.save(singleSignOnRecordsDTO);
        return ResponseEntity.created(new URI("/api/single-sign-on-records/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /single-sign-on-records} : Updates an existing singleSignOnRecords.
     *
     * @param singleSignOnRecordsDTO the singleSignOnRecordsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated singleSignOnRecordsDTO,
     * or with status {@code 400 (Bad Request)} if the singleSignOnRecordsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the singleSignOnRecordsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/single-sign-on-records")
    public ResponseEntity<SingleSignOnRecordsDTO> updateSingleSignOnRecords(@Valid @RequestBody SingleSignOnRecordsDTO singleSignOnRecordsDTO) throws URISyntaxException {
        log.debug("REST request to update SingleSignOnRecords : {}", singleSignOnRecordsDTO);
        if (singleSignOnRecordsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SingleSignOnRecordsDTO result = singleSignOnRecordsService.save(singleSignOnRecordsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, singleSignOnRecordsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /single-sign-on-records} : get all the singleSignOnRecords.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of singleSignOnRecords in body.
     */
    @GetMapping("/single-sign-on-records")
    public ResponseEntity<List<SingleSignOnRecordsDTO>> getAllSingleSignOnRecords(Pageable pageable) {
        log.debug("REST request to get a page of SingleSignOnRecords");
        Page<SingleSignOnRecordsDTO> page = singleSignOnRecordsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /single-sign-on-records/:id} : get the "id" singleSignOnRecords.
     *
     * @param id the id of the singleSignOnRecordsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the singleSignOnRecordsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/single-sign-on-records/{id}")
    public ResponseEntity<SingleSignOnRecordsDTO> getSingleSignOnRecords(@PathVariable Long id) {
        log.debug("REST request to get SingleSignOnRecords : {}", id);
        Optional<SingleSignOnRecordsDTO> singleSignOnRecordsDTO = singleSignOnRecordsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(singleSignOnRecordsDTO);
    }

    /**
     * {@code DELETE  /single-sign-on-records/:id} : delete the "id" singleSignOnRecords.
     *
     * @param id the id of the singleSignOnRecordsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/single-sign-on-records/{id}")
    public ResponseEntity<Void> deleteSingleSignOnRecords(@PathVariable Long id) {
        log.debug("REST request to delete SingleSignOnRecords : {}", id);
        singleSignOnRecordsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
