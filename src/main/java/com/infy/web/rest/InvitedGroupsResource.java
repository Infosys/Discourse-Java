/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.InvitedGroupsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.InvitedGroupsDTO;

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
 * REST controller for managing {@link com.infy.domain.InvitedGroups}.
 */
@RestController
@RequestMapping("/api")
public class InvitedGroupsResource {

    private final Logger log = LoggerFactory.getLogger(InvitedGroupsResource.class);

    private static final String ENTITY_NAME = "invitedGroups";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InvitedGroupsService invitedGroupsService;

    public InvitedGroupsResource(InvitedGroupsService invitedGroupsService) {
        this.invitedGroupsService = invitedGroupsService;
    }

    /**
     * {@code POST  /invited-groups} : Create a new invitedGroups.
     *
     * @param invitedGroupsDTO the invitedGroupsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new invitedGroupsDTO, or with status {@code 400 (Bad Request)} if the invitedGroups has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/invited-groups")
    public ResponseEntity<InvitedGroupsDTO> createInvitedGroups(@RequestBody InvitedGroupsDTO invitedGroupsDTO) throws URISyntaxException {
        log.debug("REST request to save InvitedGroups : {}", invitedGroupsDTO);
        if (invitedGroupsDTO.getId() != null) {
            throw new BadRequestAlertException("A new invitedGroups cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InvitedGroupsDTO result = invitedGroupsService.save(invitedGroupsDTO);
        return ResponseEntity.created(new URI("/api/invited-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /invited-groups} : Updates an existing invitedGroups.
     *
     * @param invitedGroupsDTO the invitedGroupsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated invitedGroupsDTO,
     * or with status {@code 400 (Bad Request)} if the invitedGroupsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the invitedGroupsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/invited-groups")
    public ResponseEntity<InvitedGroupsDTO> updateInvitedGroups(@RequestBody InvitedGroupsDTO invitedGroupsDTO) throws URISyntaxException {
        log.debug("REST request to update InvitedGroups : {}", invitedGroupsDTO);
        if (invitedGroupsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InvitedGroupsDTO result = invitedGroupsService.save(invitedGroupsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, invitedGroupsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /invited-groups} : get all the invitedGroups.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of invitedGroups in body.
     */
    @GetMapping("/invited-groups")
    public ResponseEntity<List<InvitedGroupsDTO>> getAllInvitedGroups(Pageable pageable) {
        log.debug("REST request to get a page of InvitedGroups");
        Page<InvitedGroupsDTO> page = invitedGroupsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /invited-groups/:id} : get the "id" invitedGroups.
     *
     * @param id the id of the invitedGroupsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the invitedGroupsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/invited-groups/{id}")
    public ResponseEntity<InvitedGroupsDTO> getInvitedGroups(@PathVariable Long id) {
        log.debug("REST request to get InvitedGroups : {}", id);
        Optional<InvitedGroupsDTO> invitedGroupsDTO = invitedGroupsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(invitedGroupsDTO);
    }

    /**
     * {@code DELETE  /invited-groups/:id} : delete the "id" invitedGroups.
     *
     * @param id the id of the invitedGroupsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/invited-groups/{id}")
    public ResponseEntity<Void> deleteInvitedGroups(@PathVariable Long id) {
        log.debug("REST request to delete InvitedGroups : {}", id);
        invitedGroupsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
