/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.GroupRequestsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.GroupRequestsDTO;

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
 * REST controller for managing {@link com.infy.domain.GroupRequests}.
 */
@RestController
@RequestMapping("/api")
public class GroupRequestsResource {

    private final Logger log = LoggerFactory.getLogger(GroupRequestsResource.class);

    private static final String ENTITY_NAME = "groupRequests";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GroupRequestsService groupRequestsService;

    public GroupRequestsResource(GroupRequestsService groupRequestsService) {
        this.groupRequestsService = groupRequestsService;
    }

    /**
     * {@code POST  /group-requests} : Create a new groupRequests.
     *
     * @param groupRequestsDTO the groupRequestsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new groupRequestsDTO, or with status {@code 400 (Bad Request)} if the groupRequests has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/group-requests")
    public ResponseEntity<GroupRequestsDTO> createGroupRequests(@Valid @RequestBody GroupRequestsDTO groupRequestsDTO) throws URISyntaxException {
        log.debug("REST request to save GroupRequests : {}", groupRequestsDTO);
        if (groupRequestsDTO.getId() != null) {
            throw new BadRequestAlertException("A new groupRequests cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GroupRequestsDTO result = groupRequestsService.save(groupRequestsDTO);
        return ResponseEntity.created(new URI("/api/group-requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /group-requests} : Updates an existing groupRequests.
     *
     * @param groupRequestsDTO the groupRequestsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated groupRequestsDTO,
     * or with status {@code 400 (Bad Request)} if the groupRequestsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the groupRequestsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/group-requests")
    public ResponseEntity<GroupRequestsDTO> updateGroupRequests(@Valid @RequestBody GroupRequestsDTO groupRequestsDTO) throws URISyntaxException {
        log.debug("REST request to update GroupRequests : {}", groupRequestsDTO);
        if (groupRequestsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GroupRequestsDTO result = groupRequestsService.save(groupRequestsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, groupRequestsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /group-requests} : get all the groupRequests.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of groupRequests in body.
     */
    @GetMapping("/group-requests")
    public ResponseEntity<List<GroupRequestsDTO>> getAllGroupRequests(Pageable pageable) {
        log.debug("REST request to get a page of GroupRequests");
        Page<GroupRequestsDTO> page = groupRequestsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /group-requests/:id} : get the "id" groupRequests.
     *
     * @param id the id of the groupRequestsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the groupRequestsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/group-requests/{id}")
    public ResponseEntity<GroupRequestsDTO> getGroupRequests(@PathVariable Long id) {
        log.debug("REST request to get GroupRequests : {}", id);
        Optional<GroupRequestsDTO> groupRequestsDTO = groupRequestsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(groupRequestsDTO);
    }

    /**
     * {@code DELETE  /group-requests/:id} : delete the "id" groupRequests.
     *
     * @param id the id of the groupRequestsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/group-requests/{id}")
    public ResponseEntity<Void> deleteGroupRequests(@PathVariable Long id) {
        log.debug("REST request to delete GroupRequests : {}", id);
        groupRequestsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
