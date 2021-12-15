/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.GroupTagNotificationDefaultsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.GroupTagNotificationDefaultsDTO;

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
 * REST controller for managing {@link com.infy.domain.GroupTagNotificationDefaults}.
 */
@RestController
@RequestMapping("/api")
public class GroupTagNotificationDefaultsResource {

    private final Logger log = LoggerFactory.getLogger(GroupTagNotificationDefaultsResource.class);

    private static final String ENTITY_NAME = "groupTagNotificationDefaults";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GroupTagNotificationDefaultsService groupTagNotificationDefaultsService;

    public GroupTagNotificationDefaultsResource(GroupTagNotificationDefaultsService groupTagNotificationDefaultsService) {
        this.groupTagNotificationDefaultsService = groupTagNotificationDefaultsService;
    }

    /**
     * {@code POST  /group-tag-notification-defaults} : Create a new groupTagNotificationDefaults.
     *
     * @param groupTagNotificationDefaultsDTO the groupTagNotificationDefaultsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new groupTagNotificationDefaultsDTO, or with status {@code 400 (Bad Request)} if the groupTagNotificationDefaults has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/group-tag-notification-defaults")
    public ResponseEntity<GroupTagNotificationDefaultsDTO> createGroupTagNotificationDefaults(@Valid @RequestBody GroupTagNotificationDefaultsDTO groupTagNotificationDefaultsDTO) throws URISyntaxException {
        log.debug("REST request to save GroupTagNotificationDefaults : {}", groupTagNotificationDefaultsDTO);
        if (groupTagNotificationDefaultsDTO.getId() != null) {
            throw new BadRequestAlertException("A new groupTagNotificationDefaults cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GroupTagNotificationDefaultsDTO result = groupTagNotificationDefaultsService.save(groupTagNotificationDefaultsDTO);
        return ResponseEntity.created(new URI("/api/group-tag-notification-defaults/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /group-tag-notification-defaults} : Updates an existing groupTagNotificationDefaults.
     *
     * @param groupTagNotificationDefaultsDTO the groupTagNotificationDefaultsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated groupTagNotificationDefaultsDTO,
     * or with status {@code 400 (Bad Request)} if the groupTagNotificationDefaultsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the groupTagNotificationDefaultsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/group-tag-notification-defaults")
    public ResponseEntity<GroupTagNotificationDefaultsDTO> updateGroupTagNotificationDefaults(@Valid @RequestBody GroupTagNotificationDefaultsDTO groupTagNotificationDefaultsDTO) throws URISyntaxException {
        log.debug("REST request to update GroupTagNotificationDefaults : {}", groupTagNotificationDefaultsDTO);
        if (groupTagNotificationDefaultsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GroupTagNotificationDefaultsDTO result = groupTagNotificationDefaultsService.save(groupTagNotificationDefaultsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, groupTagNotificationDefaultsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /group-tag-notification-defaults} : get all the groupTagNotificationDefaults.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of groupTagNotificationDefaults in body.
     */
    @GetMapping("/group-tag-notification-defaults")
    public ResponseEntity<List<GroupTagNotificationDefaultsDTO>> getAllGroupTagNotificationDefaults(Pageable pageable) {
        log.debug("REST request to get a page of GroupTagNotificationDefaults");
        Page<GroupTagNotificationDefaultsDTO> page = groupTagNotificationDefaultsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /group-tag-notification-defaults/:id} : get the "id" groupTagNotificationDefaults.
     *
     * @param id the id of the groupTagNotificationDefaultsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the groupTagNotificationDefaultsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/group-tag-notification-defaults/{id}")
    public ResponseEntity<GroupTagNotificationDefaultsDTO> getGroupTagNotificationDefaults(@PathVariable Long id) {
        log.debug("REST request to get GroupTagNotificationDefaults : {}", id);
        Optional<GroupTagNotificationDefaultsDTO> groupTagNotificationDefaultsDTO = groupTagNotificationDefaultsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(groupTagNotificationDefaultsDTO);
    }

    /**
     * {@code DELETE  /group-tag-notification-defaults/:id} : delete the "id" groupTagNotificationDefaults.
     *
     * @param id the id of the groupTagNotificationDefaultsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/group-tag-notification-defaults/{id}")
    public ResponseEntity<Void> deleteGroupTagNotificationDefaults(@PathVariable Long id) {
        log.debug("REST request to delete GroupTagNotificationDefaults : {}", id);
        groupTagNotificationDefaultsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
