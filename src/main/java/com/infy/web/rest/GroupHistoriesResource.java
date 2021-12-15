/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.GroupHistoriesService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.GroupHistoriesDTO;

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
 * REST controller for managing {@link com.infy.domain.GroupHistories}.
 */
@RestController
@RequestMapping("/api")
public class GroupHistoriesResource {

    private final Logger log = LoggerFactory.getLogger(GroupHistoriesResource.class);

    private static final String ENTITY_NAME = "groupHistories";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GroupHistoriesService groupHistoriesService;

    public GroupHistoriesResource(GroupHistoriesService groupHistoriesService) {
        this.groupHistoriesService = groupHistoriesService;
    }

    /**
     * {@code POST  /group-histories} : Create a new groupHistories.
     *
     * @param groupHistoriesDTO the groupHistoriesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new groupHistoriesDTO, or with status {@code 400 (Bad Request)} if the groupHistories has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/group-histories")
    public ResponseEntity<GroupHistoriesDTO> createGroupHistories(@Valid @RequestBody GroupHistoriesDTO groupHistoriesDTO) throws URISyntaxException {
        log.debug("REST request to save GroupHistories : {}", groupHistoriesDTO);
        if (groupHistoriesDTO.getId() != null) {
            throw new BadRequestAlertException("A new groupHistories cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GroupHistoriesDTO result = groupHistoriesService.save(groupHistoriesDTO);
        return ResponseEntity.created(new URI("/api/group-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /group-histories} : Updates an existing groupHistories.
     *
     * @param groupHistoriesDTO the groupHistoriesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated groupHistoriesDTO,
     * or with status {@code 400 (Bad Request)} if the groupHistoriesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the groupHistoriesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/group-histories")
    public ResponseEntity<GroupHistoriesDTO> updateGroupHistories(@Valid @RequestBody GroupHistoriesDTO groupHistoriesDTO) throws URISyntaxException {
        log.debug("REST request to update GroupHistories : {}", groupHistoriesDTO);
        if (groupHistoriesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GroupHistoriesDTO result = groupHistoriesService.save(groupHistoriesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, groupHistoriesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /group-histories} : get all the groupHistories.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of groupHistories in body.
     */
    @GetMapping("/group-histories")
    public ResponseEntity<List<GroupHistoriesDTO>> getAllGroupHistories(Pageable pageable) {
        log.debug("REST request to get a page of GroupHistories");
        Page<GroupHistoriesDTO> page = groupHistoriesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /group-histories/:id} : get the "id" groupHistories.
     *
     * @param id the id of the groupHistoriesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the groupHistoriesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/group-histories/{id}")
    public ResponseEntity<GroupHistoriesDTO> getGroupHistories(@PathVariable Long id) {
        log.debug("REST request to get GroupHistories : {}", id);
        Optional<GroupHistoriesDTO> groupHistoriesDTO = groupHistoriesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(groupHistoriesDTO);
    }

    /**
     * {@code DELETE  /group-histories/:id} : delete the "id" groupHistories.
     *
     * @param id the id of the groupHistoriesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/group-histories/{id}")
    public ResponseEntity<Void> deleteGroupHistories(@PathVariable Long id) {
        log.debug("REST request to delete GroupHistories : {}", id);
        groupHistoriesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
