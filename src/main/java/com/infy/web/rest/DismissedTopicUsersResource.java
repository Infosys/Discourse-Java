/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.DismissedTopicUsersService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.DismissedTopicUsersDTO;

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
 * REST controller for managing {@link com.infy.domain.DismissedTopicUsers}.
 */
@RestController
@RequestMapping("/api")
public class DismissedTopicUsersResource {

    private final Logger log = LoggerFactory.getLogger(DismissedTopicUsersResource.class);

    private static final String ENTITY_NAME = "dismissedTopicUsers";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DismissedTopicUsersService dismissedTopicUsersService;

    public DismissedTopicUsersResource(DismissedTopicUsersService dismissedTopicUsersService) {
        this.dismissedTopicUsersService = dismissedTopicUsersService;
    }

    /**
     * {@code POST  /dismissed-topic-users} : Create a new dismissedTopicUsers.
     *
     * @param dismissedTopicUsersDTO the dismissedTopicUsersDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dismissedTopicUsersDTO, or with status {@code 400 (Bad Request)} if the dismissedTopicUsers has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dismissed-topic-users")
    public ResponseEntity<DismissedTopicUsersDTO> createDismissedTopicUsers(@RequestBody DismissedTopicUsersDTO dismissedTopicUsersDTO) throws URISyntaxException {
        log.debug("REST request to save DismissedTopicUsers : {}", dismissedTopicUsersDTO);
        if (dismissedTopicUsersDTO.getId() != null) {
            throw new BadRequestAlertException("A new dismissedTopicUsers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DismissedTopicUsersDTO result = dismissedTopicUsersService.save(dismissedTopicUsersDTO);
        return ResponseEntity.created(new URI("/api/dismissed-topic-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dismissed-topic-users} : Updates an existing dismissedTopicUsers.
     *
     * @param dismissedTopicUsersDTO the dismissedTopicUsersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dismissedTopicUsersDTO,
     * or with status {@code 400 (Bad Request)} if the dismissedTopicUsersDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dismissedTopicUsersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dismissed-topic-users")
    public ResponseEntity<DismissedTopicUsersDTO> updateDismissedTopicUsers(@RequestBody DismissedTopicUsersDTO dismissedTopicUsersDTO) throws URISyntaxException {
        log.debug("REST request to update DismissedTopicUsers : {}", dismissedTopicUsersDTO);
        if (dismissedTopicUsersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DismissedTopicUsersDTO result = dismissedTopicUsersService.save(dismissedTopicUsersDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, dismissedTopicUsersDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /dismissed-topic-users} : get all the dismissedTopicUsers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dismissedTopicUsers in body.
     */
    @GetMapping("/dismissed-topic-users")
    public ResponseEntity<List<DismissedTopicUsersDTO>> getAllDismissedTopicUsers(Pageable pageable) {
        log.debug("REST request to get a page of DismissedTopicUsers");
        Page<DismissedTopicUsersDTO> page = dismissedTopicUsersService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /dismissed-topic-users/:id} : get the "id" dismissedTopicUsers.
     *
     * @param id the id of the dismissedTopicUsersDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dismissedTopicUsersDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dismissed-topic-users/{id}")
    public ResponseEntity<DismissedTopicUsersDTO> getDismissedTopicUsers(@PathVariable Long id) {
        log.debug("REST request to get DismissedTopicUsers : {}", id);
        Optional<DismissedTopicUsersDTO> dismissedTopicUsersDTO = dismissedTopicUsersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dismissedTopicUsersDTO);
    }

    /**
     * {@code DELETE  /dismissed-topic-users/:id} : delete the "id" dismissedTopicUsers.
     *
     * @param id the id of the dismissedTopicUsersDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dismissed-topic-users/{id}")
    public ResponseEntity<Void> deleteDismissedTopicUsers(@PathVariable Long id) {
        log.debug("REST request to delete DismissedTopicUsers : {}", id);
        dismissedTopicUsersService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
