/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.GroupsWebHooksService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.GroupsWebHooksDTO;

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
 * REST controller for managing {@link com.infy.domain.GroupsWebHooks}.
 */
@RestController
@RequestMapping("/api")
public class GroupsWebHooksResource {

    private final Logger log = LoggerFactory.getLogger(GroupsWebHooksResource.class);

    private static final String ENTITY_NAME = "groupsWebHooks";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GroupsWebHooksService groupsWebHooksService;

    public GroupsWebHooksResource(GroupsWebHooksService groupsWebHooksService) {
        this.groupsWebHooksService = groupsWebHooksService;
    }

    /**
     * {@code POST  /groups-web-hooks} : Create a new groupsWebHooks.
     *
     * @param groupsWebHooksDTO the groupsWebHooksDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new groupsWebHooksDTO, or with status {@code 400 (Bad Request)} if the groupsWebHooks has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/groups-web-hooks")
    public ResponseEntity<GroupsWebHooksDTO> createGroupsWebHooks(@Valid @RequestBody GroupsWebHooksDTO groupsWebHooksDTO) throws URISyntaxException {
        log.debug("REST request to save GroupsWebHooks : {}", groupsWebHooksDTO);
        if (groupsWebHooksDTO.getId() != null) {
            throw new BadRequestAlertException("A new groupsWebHooks cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GroupsWebHooksDTO result = groupsWebHooksService.save(groupsWebHooksDTO);
        return ResponseEntity.created(new URI("/api/groups-web-hooks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /groups-web-hooks} : Updates an existing groupsWebHooks.
     *
     * @param groupsWebHooksDTO the groupsWebHooksDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated groupsWebHooksDTO,
     * or with status {@code 400 (Bad Request)} if the groupsWebHooksDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the groupsWebHooksDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/groups-web-hooks")
    public ResponseEntity<GroupsWebHooksDTO> updateGroupsWebHooks(@Valid @RequestBody GroupsWebHooksDTO groupsWebHooksDTO) throws URISyntaxException {
        log.debug("REST request to update GroupsWebHooks : {}", groupsWebHooksDTO);
        if (groupsWebHooksDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GroupsWebHooksDTO result = groupsWebHooksService.save(groupsWebHooksDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, groupsWebHooksDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /groups-web-hooks} : get all the groupsWebHooks.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of groupsWebHooks in body.
     */
    @GetMapping("/groups-web-hooks")
    public ResponseEntity<List<GroupsWebHooksDTO>> getAllGroupsWebHooks(Pageable pageable) {
        log.debug("REST request to get a page of GroupsWebHooks");
        Page<GroupsWebHooksDTO> page = groupsWebHooksService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /groups-web-hooks/:id} : get the "id" groupsWebHooks.
     *
     * @param id the id of the groupsWebHooksDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the groupsWebHooksDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/groups-web-hooks/{id}")
    public ResponseEntity<GroupsWebHooksDTO> getGroupsWebHooks(@PathVariable Long id) {
        log.debug("REST request to get GroupsWebHooks : {}", id);
        Optional<GroupsWebHooksDTO> groupsWebHooksDTO = groupsWebHooksService.findOne(id);
        return ResponseUtil.wrapOrNotFound(groupsWebHooksDTO);
    }

    /**
     * {@code DELETE  /groups-web-hooks/:id} : delete the "id" groupsWebHooks.
     *
     * @param id the id of the groupsWebHooksDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/groups-web-hooks/{id}")
    public ResponseEntity<Void> deleteGroupsWebHooks(@PathVariable Long id) {
        log.debug("REST request to delete GroupsWebHooks : {}", id);
        groupsWebHooksService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
