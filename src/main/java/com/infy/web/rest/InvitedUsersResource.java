/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.InvitedUsersService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.InvitedUsersDTO;

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
 * REST controller for managing {@link com.infy.domain.InvitedUsers}.
 */
@RestController
@RequestMapping("/api")
public class InvitedUsersResource {

    private final Logger log = LoggerFactory.getLogger(InvitedUsersResource.class);

    private static final String ENTITY_NAME = "invitedUsers";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InvitedUsersService invitedUsersService;

    public InvitedUsersResource(InvitedUsersService invitedUsersService) {
        this.invitedUsersService = invitedUsersService;
    }

    /**
     * {@code POST  /invited-users} : Create a new invitedUsers.
     *
     * @param invitedUsersDTO the invitedUsersDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new invitedUsersDTO, or with status {@code 400 (Bad Request)} if the invitedUsers has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/invited-users")
    public ResponseEntity<InvitedUsersDTO> createInvitedUsers(@Valid @RequestBody InvitedUsersDTO invitedUsersDTO) throws URISyntaxException {
        log.debug("REST request to save InvitedUsers : {}", invitedUsersDTO);
        if (invitedUsersDTO.getId() != null) {
            throw new BadRequestAlertException("A new invitedUsers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InvitedUsersDTO result = invitedUsersService.save(invitedUsersDTO);
        return ResponseEntity.created(new URI("/api/invited-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /invited-users} : Updates an existing invitedUsers.
     *
     * @param invitedUsersDTO the invitedUsersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated invitedUsersDTO,
     * or with status {@code 400 (Bad Request)} if the invitedUsersDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the invitedUsersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/invited-users")
    public ResponseEntity<InvitedUsersDTO> updateInvitedUsers(@Valid @RequestBody InvitedUsersDTO invitedUsersDTO) throws URISyntaxException {
        log.debug("REST request to update InvitedUsers : {}", invitedUsersDTO);
        if (invitedUsersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InvitedUsersDTO result = invitedUsersService.save(invitedUsersDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, invitedUsersDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /invited-users} : get all the invitedUsers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of invitedUsers in body.
     */
    @GetMapping("/invited-users")
    public ResponseEntity<List<InvitedUsersDTO>> getAllInvitedUsers(Pageable pageable) {
        log.debug("REST request to get a page of InvitedUsers");
        Page<InvitedUsersDTO> page = invitedUsersService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /invited-users/:id} : get the "id" invitedUsers.
     *
     * @param id the id of the invitedUsersDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the invitedUsersDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/invited-users/{id}")
    public ResponseEntity<InvitedUsersDTO> getInvitedUsers(@PathVariable Long id) {
        log.debug("REST request to get InvitedUsers : {}", id);
        Optional<InvitedUsersDTO> invitedUsersDTO = invitedUsersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(invitedUsersDTO);
    }

    /**
     * {@code DELETE  /invited-users/:id} : delete the "id" invitedUsers.
     *
     * @param id the id of the invitedUsersDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/invited-users/{id}")
    public ResponseEntity<Void> deleteInvitedUsers(@PathVariable Long id) {
        log.debug("REST request to delete InvitedUsers : {}", id);
        invitedUsersService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
