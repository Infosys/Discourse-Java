/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.PollsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.PollsDTO;

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
 * REST controller for managing {@link com.infy.domain.Polls}.
 */
@RestController
@RequestMapping("/api")
public class PollsResource {

    private final Logger log = LoggerFactory.getLogger(PollsResource.class);

    private static final String ENTITY_NAME = "polls";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PollsService pollsService;

    public PollsResource(PollsService pollsService) {
        this.pollsService = pollsService;
    }

    /**
     * {@code POST  /polls} : Create a new polls.
     *
     * @param pollsDTO the pollsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pollsDTO, or with status {@code 400 (Bad Request)} if the polls has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/polls")
    public ResponseEntity<PollsDTO> createPolls(@Valid @RequestBody PollsDTO pollsDTO) throws URISyntaxException {
        log.debug("REST request to save Polls : {}", pollsDTO);
        if (pollsDTO.getId() != null) {
            throw new BadRequestAlertException("A new polls cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PollsDTO result = pollsService.save(pollsDTO);
        return ResponseEntity.created(new URI("/api/polls/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /polls} : Updates an existing polls.
     *
     * @param pollsDTO the pollsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pollsDTO,
     * or with status {@code 400 (Bad Request)} if the pollsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pollsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/polls")
    public ResponseEntity<PollsDTO> updatePolls(@Valid @RequestBody PollsDTO pollsDTO) throws URISyntaxException {
        log.debug("REST request to update Polls : {}", pollsDTO);
        if (pollsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PollsDTO result = pollsService.save(pollsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pollsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /polls} : get all the polls.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of polls in body.
     */
    @GetMapping("/polls")
    public ResponseEntity<List<PollsDTO>> getAllPolls(Pageable pageable) {
        log.debug("REST request to get a page of Polls");
        Page<PollsDTO> page = pollsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /polls/:id} : get the "id" polls.
     *
     * @param id the id of the pollsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pollsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/polls/{id}")
    public ResponseEntity<PollsDTO> getPolls(@PathVariable Long id) {
        log.debug("REST request to get Polls : {}", id);
        Optional<PollsDTO> pollsDTO = pollsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pollsDTO);
    }

    /**
     * {@code DELETE  /polls/:id} : delete the "id" polls.
     *
     * @param id the id of the pollsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/polls/{id}")
    public ResponseEntity<Void> deletePolls(@PathVariable Long id) {
        log.debug("REST request to delete Polls : {}", id);
        pollsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
