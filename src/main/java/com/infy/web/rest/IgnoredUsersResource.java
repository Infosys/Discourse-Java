/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.IgnoredUsersService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.IgnoredUsersDTO;

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
 * REST controller for managing {@link com.infy.domain.IgnoredUsers}.
 */
@RestController
@RequestMapping("/api")
public class IgnoredUsersResource {

    private final Logger log = LoggerFactory.getLogger(IgnoredUsersResource.class);

    private static final String ENTITY_NAME = "ignoredUsers";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IgnoredUsersService ignoredUsersService;

    public IgnoredUsersResource(IgnoredUsersService ignoredUsersService) {
        this.ignoredUsersService = ignoredUsersService;
    }

    /**
     * {@code POST  /ignored-users} : Create a new ignoredUsers.
     *
     * @param ignoredUsersDTO the ignoredUsersDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ignoredUsersDTO, or with status {@code 400 (Bad Request)} if the ignoredUsers has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ignored-users")
    public ResponseEntity<IgnoredUsersDTO> createIgnoredUsers(@Valid @RequestBody IgnoredUsersDTO ignoredUsersDTO) throws URISyntaxException {
        log.debug("REST request to save IgnoredUsers : {}", ignoredUsersDTO);
        if (ignoredUsersDTO.getId() != null) {
            throw new BadRequestAlertException("A new ignoredUsers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IgnoredUsersDTO result = ignoredUsersService.save(ignoredUsersDTO);
        return ResponseEntity.created(new URI("/api/ignored-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ignored-users} : Updates an existing ignoredUsers.
     *
     * @param ignoredUsersDTO the ignoredUsersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ignoredUsersDTO,
     * or with status {@code 400 (Bad Request)} if the ignoredUsersDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ignoredUsersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ignored-users")
    public ResponseEntity<IgnoredUsersDTO> updateIgnoredUsers(@Valid @RequestBody IgnoredUsersDTO ignoredUsersDTO) throws URISyntaxException {
        log.debug("REST request to update IgnoredUsers : {}", ignoredUsersDTO);
        if (ignoredUsersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        IgnoredUsersDTO result = ignoredUsersService.save(ignoredUsersDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ignoredUsersDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ignored-users} : get all the ignoredUsers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ignoredUsers in body.
     */
    @GetMapping("/ignored-users")
    public ResponseEntity<List<IgnoredUsersDTO>> getAllIgnoredUsers(Pageable pageable) {
        log.debug("REST request to get a page of IgnoredUsers");
        Page<IgnoredUsersDTO> page = ignoredUsersService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ignored-users/:id} : get the "id" ignoredUsers.
     *
     * @param id the id of the ignoredUsersDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ignoredUsersDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ignored-users/{id}")
    public ResponseEntity<IgnoredUsersDTO> getIgnoredUsers(@PathVariable Long id) {
        log.debug("REST request to get IgnoredUsers : {}", id);
        Optional<IgnoredUsersDTO> ignoredUsersDTO = ignoredUsersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ignoredUsersDTO);
    }

    /**
     * {@code DELETE  /ignored-users/:id} : delete the "id" ignoredUsers.
     *
     * @param id the id of the ignoredUsersDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ignored-users/{id}")
    public ResponseEntity<Void> deleteIgnoredUsers(@PathVariable Long id) {
        log.debug("REST request to delete IgnoredUsers : {}", id);
        ignoredUsersService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
