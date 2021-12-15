/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.GroupMentionsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.GroupMentionsDTO;

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
 * REST controller for managing {@link com.infy.domain.GroupMentions}.
 */
@RestController
@RequestMapping("/api")
public class GroupMentionsResource {

    private final Logger log = LoggerFactory.getLogger(GroupMentionsResource.class);

    private static final String ENTITY_NAME = "groupMentions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GroupMentionsService groupMentionsService;

    public GroupMentionsResource(GroupMentionsService groupMentionsService) {
        this.groupMentionsService = groupMentionsService;
    }

    /**
     * {@code POST  /group-mentions} : Create a new groupMentions.
     *
     * @param groupMentionsDTO the groupMentionsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new groupMentionsDTO, or with status {@code 400 (Bad Request)} if the groupMentions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/group-mentions")
    public ResponseEntity<GroupMentionsDTO> createGroupMentions(@Valid @RequestBody GroupMentionsDTO groupMentionsDTO) throws URISyntaxException {
        log.debug("REST request to save GroupMentions : {}", groupMentionsDTO);
        if (groupMentionsDTO.getId() != null) {
            throw new BadRequestAlertException("A new groupMentions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GroupMentionsDTO result = groupMentionsService.save(groupMentionsDTO);
        return ResponseEntity.created(new URI("/api/group-mentions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /group-mentions} : Updates an existing groupMentions.
     *
     * @param groupMentionsDTO the groupMentionsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated groupMentionsDTO,
     * or with status {@code 400 (Bad Request)} if the groupMentionsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the groupMentionsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/group-mentions")
    public ResponseEntity<GroupMentionsDTO> updateGroupMentions(@Valid @RequestBody GroupMentionsDTO groupMentionsDTO) throws URISyntaxException {
        log.debug("REST request to update GroupMentions : {}", groupMentionsDTO);
        if (groupMentionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GroupMentionsDTO result = groupMentionsService.save(groupMentionsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, groupMentionsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /group-mentions} : get all the groupMentions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of groupMentions in body.
     */
    @GetMapping("/group-mentions")
    public ResponseEntity<List<GroupMentionsDTO>> getAllGroupMentions(Pageable pageable) {
        log.debug("REST request to get a page of GroupMentions");
        Page<GroupMentionsDTO> page = groupMentionsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /group-mentions/:id} : get the "id" groupMentions.
     *
     * @param id the id of the groupMentionsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the groupMentionsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/group-mentions/{id}")
    public ResponseEntity<GroupMentionsDTO> getGroupMentions(@PathVariable Long id) {
        log.debug("REST request to get GroupMentions : {}", id);
        Optional<GroupMentionsDTO> groupMentionsDTO = groupMentionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(groupMentionsDTO);
    }

    /**
     * {@code DELETE  /group-mentions/:id} : delete the "id" groupMentions.
     *
     * @param id the id of the groupMentionsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/group-mentions/{id}")
    public ResponseEntity<Void> deleteGroupMentions(@PathVariable Long id) {
        log.debug("REST request to delete GroupMentions : {}", id);
        groupMentionsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
