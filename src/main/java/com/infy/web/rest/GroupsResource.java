/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.GroupsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.GroupsDTO;

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
 * REST controller for managing {@link com.infy.domain.Groups}.
 */
@RestController
@RequestMapping("/api")
public class GroupsResource {

    private final Logger log = LoggerFactory.getLogger(GroupsResource.class);

    private static final String ENTITY_NAME = "groups";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GroupsService groupsService;

    public GroupsResource(GroupsService groupsService) {
        this.groupsService = groupsService;
    }

    /**
     * {@code POST  /groups} : Create a new groups.
     *
     * @param groupsDTO the groupsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new groupsDTO, or with status {@code 400 (Bad Request)} if the groups has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/groups")
    public ResponseEntity<GroupsDTO> createGroups(@Valid @RequestBody GroupsDTO groupsDTO) throws URISyntaxException {
        log.debug("REST request to save Groups : {}", groupsDTO);
        if (groupsDTO.getId() != null) {
            throw new BadRequestAlertException("A new groups cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GroupsDTO result = groupsService.save(groupsDTO);
        return ResponseEntity.created(new URI("/api/groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /groups} : Updates an existing groups.
     *
     * @param groupsDTO the groupsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated groupsDTO,
     * or with status {@code 400 (Bad Request)} if the groupsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the groupsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/groups")
    public ResponseEntity<GroupsDTO> updateGroups(@Valid @RequestBody GroupsDTO groupsDTO) throws URISyntaxException {
        log.debug("REST request to update Groups : {}", groupsDTO);
        if (groupsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GroupsDTO result = groupsService.save(groupsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, groupsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /groups} : get all the groups.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of groups in body.
     */
    @GetMapping("/groups")
    public ResponseEntity<List<GroupsDTO>> getAllGroups(Pageable pageable) {
        log.debug("REST request to get a page of Groups");
        Page<GroupsDTO> page = groupsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /groups/:id} : get the "id" groups.
     *
     * @param id the id of the groupsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the groupsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/groups/{id}")
    public ResponseEntity<GroupsDTO> getGroups(@PathVariable Long id) {
        log.debug("REST request to get Groups : {}", id);
        Optional<GroupsDTO> groupsDTO = groupsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(groupsDTO);
    }

    /**
     * {@code DELETE  /groups/:id} : delete the "id" groups.
     *
     * @param id the id of the groupsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/groups/{id}")
    public ResponseEntity<Void> deleteGroups(@PathVariable Long id) {
        log.debug("REST request to delete Groups : {}", id);
        groupsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
