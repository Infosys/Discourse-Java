/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.AnonymousUsersService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.AnonymousUsersDTO;

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
 * REST controller for managing {@link com.infy.domain.AnonymousUsers}.
 */
@RestController
@RequestMapping("/api")
public class AnonymousUsersResource {

    private final Logger log = LoggerFactory.getLogger(AnonymousUsersResource.class);

    private static final String ENTITY_NAME = "anonymousUsers";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnonymousUsersService anonymousUsersService;

    public AnonymousUsersResource(AnonymousUsersService anonymousUsersService) {
        this.anonymousUsersService = anonymousUsersService;
    }

    /**
     * {@code POST  /anonymous-users} : Create a new anonymousUsers.
     *
     * @param anonymousUsersDTO the anonymousUsersDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new anonymousUsersDTO, or with status {@code 400 (Bad Request)} if the anonymousUsers has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/anonymous-users")
    public ResponseEntity<AnonymousUsersDTO> createAnonymousUsers(@Valid @RequestBody AnonymousUsersDTO anonymousUsersDTO) throws URISyntaxException {
        log.debug("REST request to save AnonymousUsers : {}", anonymousUsersDTO);
        if (anonymousUsersDTO.getId() != null) {
            throw new BadRequestAlertException("A new anonymousUsers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AnonymousUsersDTO result = anonymousUsersService.save(anonymousUsersDTO);
        return ResponseEntity.created(new URI("/api/anonymous-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /anonymous-users} : Updates an existing anonymousUsers.
     *
     * @param anonymousUsersDTO the anonymousUsersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated anonymousUsersDTO,
     * or with status {@code 400 (Bad Request)} if the anonymousUsersDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the anonymousUsersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/anonymous-users")
    public ResponseEntity<AnonymousUsersDTO> updateAnonymousUsers(@Valid @RequestBody AnonymousUsersDTO anonymousUsersDTO) throws URISyntaxException {
        log.debug("REST request to update AnonymousUsers : {}", anonymousUsersDTO);
        if (anonymousUsersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AnonymousUsersDTO result = anonymousUsersService.save(anonymousUsersDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, anonymousUsersDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /anonymous-users} : get all the anonymousUsers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of anonymousUsers in body.
     */
    @GetMapping("/anonymous-users")
    public ResponseEntity<List<AnonymousUsersDTO>> getAllAnonymousUsers(Pageable pageable) {
        log.debug("REST request to get a page of AnonymousUsers");
        Page<AnonymousUsersDTO> page = anonymousUsersService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /anonymous-users/:id} : get the "id" anonymousUsers.
     *
     * @param id the id of the anonymousUsersDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the anonymousUsersDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/anonymous-users/{id}")
    public ResponseEntity<AnonymousUsersDTO> getAnonymousUsers(@PathVariable Long id) {
        log.debug("REST request to get AnonymousUsers : {}", id);
        Optional<AnonymousUsersDTO> anonymousUsersDTO = anonymousUsersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(anonymousUsersDTO);
    }

    /**
     * {@code DELETE  /anonymous-users/:id} : delete the "id" anonymousUsers.
     *
     * @param id the id of the anonymousUsersDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/anonymous-users/{id}")
    public ResponseEntity<Void> deleteAnonymousUsers(@PathVariable Long id) {
        log.debug("REST request to delete AnonymousUsers : {}", id);
        anonymousUsersService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
