/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.PollVotesService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.PollVotesDTO;

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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.infy.domain.PollVotes}.
 */
@RestController
@RequestMapping("/api")
public class PollVotesResource {

    private final Logger log = LoggerFactory.getLogger(PollVotesResource.class);

    private static final String ENTITY_NAME = "pollVotes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PollVotesService pollVotesService;

    public PollVotesResource(PollVotesService pollVotesService) {
        this.pollVotesService = pollVotesService;
    }

    /**
     * {@code POST  /poll-votes} : Create a new pollVotes.
     *
     * @param pollVotesDTO the pollVotesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pollVotesDTO, or with status {@code 400 (Bad Request)} if the pollVotes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/poll-votes")
    public ResponseEntity<PollVotesDTO> createPollVotes(@RequestBody PollVotesDTO pollVotesDTO) throws URISyntaxException {
        log.debug("REST request to save PollVotes : {}", pollVotesDTO);
        if (pollVotesDTO.getId() != null) {
            throw new BadRequestAlertException("A new pollVotes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PollVotesDTO result = pollVotesService.save(pollVotesDTO);
        return ResponseEntity.created(new URI("/api/poll-votes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /poll-votes} : Updates an existing pollVotes.
     *
     * @param pollVotesDTO the pollVotesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pollVotesDTO,
     * or with status {@code 400 (Bad Request)} if the pollVotesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pollVotesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/poll-votes")
    public ResponseEntity<PollVotesDTO> updatePollVotes(@RequestBody PollVotesDTO pollVotesDTO) throws URISyntaxException {
        log.debug("REST request to update PollVotes : {}", pollVotesDTO);
        if (pollVotesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PollVotesDTO result = pollVotesService.save(pollVotesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pollVotesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /poll-votes} : get all the pollVotes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pollVotes in body.
     */
    @GetMapping("/poll-votes")
    public ResponseEntity<List<PollVotesDTO>> getAllPollVotes(Pageable pageable) {
        log.debug("REST request to get a page of PollVotes");
        Page<PollVotesDTO> page = pollVotesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /poll-votes/:id} : get the "id" pollVotes.
     *
     * @param id the id of the pollVotesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pollVotesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/poll-votes/{id}")
    public ResponseEntity<PollVotesDTO> getPollVotes(@PathVariable Long id) {
        log.debug("REST request to get PollVotes : {}", id);
        Optional<PollVotesDTO> pollVotesDTO = pollVotesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pollVotesDTO);
    }

    /**
     * {@code DELETE  /poll-votes/:id} : delete the "id" pollVotes.
     *
     * @param id the id of the pollVotesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/poll-votes/{id}")
    public ResponseEntity<Void> deletePollVotes(@PathVariable Long id) {
        log.debug("REST request to delete PollVotes : {}", id);
        pollVotesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
