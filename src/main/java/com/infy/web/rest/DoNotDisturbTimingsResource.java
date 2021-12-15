/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.DoNotDisturbTimingsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.DoNotDisturbTimingsDTO;

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
 * REST controller for managing {@link com.infy.domain.DoNotDisturbTimings}.
 */
@RestController
@RequestMapping("/api")
public class DoNotDisturbTimingsResource {

    private final Logger log = LoggerFactory.getLogger(DoNotDisturbTimingsResource.class);

    private static final String ENTITY_NAME = "doNotDisturbTimings";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DoNotDisturbTimingsService doNotDisturbTimingsService;

    public DoNotDisturbTimingsResource(DoNotDisturbTimingsService doNotDisturbTimingsService) {
        this.doNotDisturbTimingsService = doNotDisturbTimingsService;
    }

    /**
     * {@code POST  /do-not-disturb-timings} : Create a new doNotDisturbTimings.
     *
     * @param doNotDisturbTimingsDTO the doNotDisturbTimingsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new doNotDisturbTimingsDTO, or with status {@code 400 (Bad Request)} if the doNotDisturbTimings has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/do-not-disturb-timings")
    public ResponseEntity<DoNotDisturbTimingsDTO> createDoNotDisturbTimings(@Valid @RequestBody DoNotDisturbTimingsDTO doNotDisturbTimingsDTO) throws URISyntaxException {
        log.debug("REST request to save DoNotDisturbTimings : {}", doNotDisturbTimingsDTO);
        if (doNotDisturbTimingsDTO.getId() != null) {
            throw new BadRequestAlertException("A new doNotDisturbTimings cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DoNotDisturbTimingsDTO result = doNotDisturbTimingsService.save(doNotDisturbTimingsDTO);
        return ResponseEntity.created(new URI("/api/do-not-disturb-timings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /do-not-disturb-timings} : Updates an existing doNotDisturbTimings.
     *
     * @param doNotDisturbTimingsDTO the doNotDisturbTimingsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated doNotDisturbTimingsDTO,
     * or with status {@code 400 (Bad Request)} if the doNotDisturbTimingsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the doNotDisturbTimingsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/do-not-disturb-timings")
    public ResponseEntity<DoNotDisturbTimingsDTO> updateDoNotDisturbTimings(@Valid @RequestBody DoNotDisturbTimingsDTO doNotDisturbTimingsDTO) throws URISyntaxException {
        log.debug("REST request to update DoNotDisturbTimings : {}", doNotDisturbTimingsDTO);
        if (doNotDisturbTimingsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DoNotDisturbTimingsDTO result = doNotDisturbTimingsService.save(doNotDisturbTimingsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, doNotDisturbTimingsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /do-not-disturb-timings} : get all the doNotDisturbTimings.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of doNotDisturbTimings in body.
     */
    @GetMapping("/do-not-disturb-timings")
    public ResponseEntity<List<DoNotDisturbTimingsDTO>> getAllDoNotDisturbTimings(Pageable pageable) {
        log.debug("REST request to get a page of DoNotDisturbTimings");
        Page<DoNotDisturbTimingsDTO> page = doNotDisturbTimingsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /do-not-disturb-timings/:id} : get the "id" doNotDisturbTimings.
     *
     * @param id the id of the doNotDisturbTimingsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the doNotDisturbTimingsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/do-not-disturb-timings/{id}")
    public ResponseEntity<DoNotDisturbTimingsDTO> getDoNotDisturbTimings(@PathVariable Long id) {
        log.debug("REST request to get DoNotDisturbTimings : {}", id);
        Optional<DoNotDisturbTimingsDTO> doNotDisturbTimingsDTO = doNotDisturbTimingsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(doNotDisturbTimingsDTO);
    }

    /**
     * {@code DELETE  /do-not-disturb-timings/:id} : delete the "id" doNotDisturbTimings.
     *
     * @param id the id of the doNotDisturbTimingsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/do-not-disturb-timings/{id}")
    public ResponseEntity<Void> deleteDoNotDisturbTimings(@PathVariable Long id) {
        log.debug("REST request to delete DoNotDisturbTimings : {}", id);
        doNotDisturbTimingsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
