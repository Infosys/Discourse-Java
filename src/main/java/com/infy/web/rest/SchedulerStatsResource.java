/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.SchedulerStatsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.SchedulerStatsDTO;

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
 * REST controller for managing {@link com.infy.domain.SchedulerStats}.
 */
@RestController
@RequestMapping("/api")
public class SchedulerStatsResource {

    private final Logger log = LoggerFactory.getLogger(SchedulerStatsResource.class);

    private static final String ENTITY_NAME = "schedulerStats";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SchedulerStatsService schedulerStatsService;

    public SchedulerStatsResource(SchedulerStatsService schedulerStatsService) {
        this.schedulerStatsService = schedulerStatsService;
    }

    /**
     * {@code POST  /scheduler-stats} : Create a new schedulerStats.
     *
     * @param schedulerStatsDTO the schedulerStatsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new schedulerStatsDTO, or with status {@code 400 (Bad Request)} if the schedulerStats has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/scheduler-stats")
    public ResponseEntity<SchedulerStatsDTO> createSchedulerStats(@Valid @RequestBody SchedulerStatsDTO schedulerStatsDTO) throws URISyntaxException {
        log.debug("REST request to save SchedulerStats : {}", schedulerStatsDTO);
        if (schedulerStatsDTO.getId() != null) {
            throw new BadRequestAlertException("A new schedulerStats cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SchedulerStatsDTO result = schedulerStatsService.save(schedulerStatsDTO);
        return ResponseEntity.created(new URI("/api/scheduler-stats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /scheduler-stats} : Updates an existing schedulerStats.
     *
     * @param schedulerStatsDTO the schedulerStatsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated schedulerStatsDTO,
     * or with status {@code 400 (Bad Request)} if the schedulerStatsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the schedulerStatsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/scheduler-stats")
    public ResponseEntity<SchedulerStatsDTO> updateSchedulerStats(@Valid @RequestBody SchedulerStatsDTO schedulerStatsDTO) throws URISyntaxException {
        log.debug("REST request to update SchedulerStats : {}", schedulerStatsDTO);
        if (schedulerStatsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SchedulerStatsDTO result = schedulerStatsService.save(schedulerStatsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, schedulerStatsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /scheduler-stats} : get all the schedulerStats.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of schedulerStats in body.
     */
    @GetMapping("/scheduler-stats")
    public ResponseEntity<List<SchedulerStatsDTO>> getAllSchedulerStats(Pageable pageable) {
        log.debug("REST request to get a page of SchedulerStats");
        Page<SchedulerStatsDTO> page = schedulerStatsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /scheduler-stats/:id} : get the "id" schedulerStats.
     *
     * @param id the id of the schedulerStatsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the schedulerStatsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/scheduler-stats/{id}")
    public ResponseEntity<SchedulerStatsDTO> getSchedulerStats(@PathVariable Long id) {
        log.debug("REST request to get SchedulerStats : {}", id);
        Optional<SchedulerStatsDTO> schedulerStatsDTO = schedulerStatsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(schedulerStatsDTO);
    }

    /**
     * {@code DELETE  /scheduler-stats/:id} : delete the "id" schedulerStats.
     *
     * @param id the id of the schedulerStatsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/scheduler-stats/{id}")
    public ResponseEntity<Void> deleteSchedulerStats(@PathVariable Long id) {
        log.debug("REST request to delete SchedulerStats : {}", id);
        schedulerStatsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
