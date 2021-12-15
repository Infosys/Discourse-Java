/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.PollOptionsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.PollOptionsDTO;

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
 * REST controller for managing {@link com.infy.domain.PollOptions}.
 */
@RestController
@RequestMapping("/api")
public class PollOptionsResource {

    private final Logger log = LoggerFactory.getLogger(PollOptionsResource.class);

    private static final String ENTITY_NAME = "pollOptions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PollOptionsService pollOptionsService;

    public PollOptionsResource(PollOptionsService pollOptionsService) {
        this.pollOptionsService = pollOptionsService;
    }

    /**
     * {@code POST  /poll-options} : Create a new pollOptions.
     *
     * @param pollOptionsDTO the pollOptionsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pollOptionsDTO, or with status {@code 400 (Bad Request)} if the pollOptions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/poll-options")
    public ResponseEntity<PollOptionsDTO> createPollOptions(@Valid @RequestBody PollOptionsDTO pollOptionsDTO) throws URISyntaxException {
        log.debug("REST request to save PollOptions : {}", pollOptionsDTO);
        if (pollOptionsDTO.getId() != null) {
            throw new BadRequestAlertException("A new pollOptions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PollOptionsDTO result = pollOptionsService.save(pollOptionsDTO);
        return ResponseEntity.created(new URI("/api/poll-options/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /poll-options} : Updates an existing pollOptions.
     *
     * @param pollOptionsDTO the pollOptionsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pollOptionsDTO,
     * or with status {@code 400 (Bad Request)} if the pollOptionsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pollOptionsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/poll-options")
    public ResponseEntity<PollOptionsDTO> updatePollOptions(@Valid @RequestBody PollOptionsDTO pollOptionsDTO) throws URISyntaxException {
        log.debug("REST request to update PollOptions : {}", pollOptionsDTO);
        if (pollOptionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PollOptionsDTO result = pollOptionsService.save(pollOptionsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pollOptionsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /poll-options} : get all the pollOptions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pollOptions in body.
     */
    @GetMapping("/poll-options")
    public ResponseEntity<List<PollOptionsDTO>> getAllPollOptions(Pageable pageable) {
        log.debug("REST request to get a page of PollOptions");
        Page<PollOptionsDTO> page = pollOptionsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /poll-options/:id} : get the "id" pollOptions.
     *
     * @param id the id of the pollOptionsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pollOptionsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/poll-options/{id}")
    public ResponseEntity<PollOptionsDTO> getPollOptions(@PathVariable Long id) {
        log.debug("REST request to get PollOptions : {}", id);
        Optional<PollOptionsDTO> pollOptionsDTO = pollOptionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pollOptionsDTO);
    }

    /**
     * {@code DELETE  /poll-options/:id} : delete the "id" pollOptions.
     *
     * @param id the id of the pollOptionsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/poll-options/{id}")
    public ResponseEntity<Void> deletePollOptions(@PathVariable Long id) {
        log.debug("REST request to delete PollOptions : {}", id);
        pollOptionsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
