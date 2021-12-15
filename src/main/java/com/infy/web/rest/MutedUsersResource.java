/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.MutedUsersService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.MutedUsersDTO;

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
 * REST controller for managing {@link com.infy.domain.MutedUsers}.
 */
@RestController
@RequestMapping("/api")
public class MutedUsersResource {

    private final Logger log = LoggerFactory.getLogger(MutedUsersResource.class);

    private static final String ENTITY_NAME = "mutedUsers";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MutedUsersService mutedUsersService;

    public MutedUsersResource(MutedUsersService mutedUsersService) {
        this.mutedUsersService = mutedUsersService;
    }

    /**
     * {@code POST  /muted-users} : Create a new mutedUsers.
     *
     * @param mutedUsersDTO the mutedUsersDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mutedUsersDTO, or with status {@code 400 (Bad Request)} if the mutedUsers has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/muted-users")
    public ResponseEntity<MutedUsersDTO> createMutedUsers(@Valid @RequestBody MutedUsersDTO mutedUsersDTO) throws URISyntaxException {
        log.debug("REST request to save MutedUsers : {}", mutedUsersDTO);
        if (mutedUsersDTO.getId() != null) {
            throw new BadRequestAlertException("A new mutedUsers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MutedUsersDTO result = mutedUsersService.save(mutedUsersDTO);
        return ResponseEntity.created(new URI("/api/muted-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /muted-users} : Updates an existing mutedUsers.
     *
     * @param mutedUsersDTO the mutedUsersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mutedUsersDTO,
     * or with status {@code 400 (Bad Request)} if the mutedUsersDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mutedUsersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/muted-users")
    public ResponseEntity<MutedUsersDTO> updateMutedUsers(@Valid @RequestBody MutedUsersDTO mutedUsersDTO) throws URISyntaxException {
        log.debug("REST request to update MutedUsers : {}", mutedUsersDTO);
        if (mutedUsersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MutedUsersDTO result = mutedUsersService.save(mutedUsersDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, mutedUsersDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /muted-users} : get all the mutedUsers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mutedUsers in body.
     */
    @GetMapping("/muted-users")
    public ResponseEntity<List<MutedUsersDTO>> getAllMutedUsers(Pageable pageable) {
        log.debug("REST request to get a page of MutedUsers");
        Page<MutedUsersDTO> page = mutedUsersService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /muted-users/:id} : get the "id" mutedUsers.
     *
     * @param id the id of the mutedUsersDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mutedUsersDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/muted-users/{id}")
    public ResponseEntity<MutedUsersDTO> getMutedUsers(@PathVariable Long id) {
        log.debug("REST request to get MutedUsers : {}", id);
        Optional<MutedUsersDTO> mutedUsersDTO = mutedUsersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mutedUsersDTO);
    }

    /**
     * {@code DELETE  /muted-users/:id} : delete the "id" mutedUsers.
     *
     * @param id the id of the mutedUsersDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/muted-users/{id}")
    public ResponseEntity<Void> deleteMutedUsers(@PathVariable Long id) {
        log.debug("REST request to delete MutedUsers : {}", id);
        mutedUsersService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
