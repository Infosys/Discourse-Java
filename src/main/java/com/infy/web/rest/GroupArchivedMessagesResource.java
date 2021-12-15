/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.GroupArchivedMessagesService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.GroupArchivedMessagesDTO;

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
 * REST controller for managing {@link com.infy.domain.GroupArchivedMessages}.
 */
@RestController
@RequestMapping("/api")
public class GroupArchivedMessagesResource {

    private final Logger log = LoggerFactory.getLogger(GroupArchivedMessagesResource.class);

    private static final String ENTITY_NAME = "groupArchivedMessages";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GroupArchivedMessagesService groupArchivedMessagesService;

    public GroupArchivedMessagesResource(GroupArchivedMessagesService groupArchivedMessagesService) {
        this.groupArchivedMessagesService = groupArchivedMessagesService;
    }

    /**
     * {@code POST  /group-archived-messages} : Create a new groupArchivedMessages.
     *
     * @param groupArchivedMessagesDTO the groupArchivedMessagesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new groupArchivedMessagesDTO, or with status {@code 400 (Bad Request)} if the groupArchivedMessages has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/group-archived-messages")
    public ResponseEntity<GroupArchivedMessagesDTO> createGroupArchivedMessages(@Valid @RequestBody GroupArchivedMessagesDTO groupArchivedMessagesDTO) throws URISyntaxException {
        log.debug("REST request to save GroupArchivedMessages : {}", groupArchivedMessagesDTO);
        if (groupArchivedMessagesDTO.getId() != null) {
            throw new BadRequestAlertException("A new groupArchivedMessages cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GroupArchivedMessagesDTO result = groupArchivedMessagesService.save(groupArchivedMessagesDTO);
        return ResponseEntity.created(new URI("/api/group-archived-messages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /group-archived-messages} : Updates an existing groupArchivedMessages.
     *
     * @param groupArchivedMessagesDTO the groupArchivedMessagesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated groupArchivedMessagesDTO,
     * or with status {@code 400 (Bad Request)} if the groupArchivedMessagesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the groupArchivedMessagesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/group-archived-messages")
    public ResponseEntity<GroupArchivedMessagesDTO> updateGroupArchivedMessages(@Valid @RequestBody GroupArchivedMessagesDTO groupArchivedMessagesDTO) throws URISyntaxException {
        log.debug("REST request to update GroupArchivedMessages : {}", groupArchivedMessagesDTO);
        if (groupArchivedMessagesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GroupArchivedMessagesDTO result = groupArchivedMessagesService.save(groupArchivedMessagesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, groupArchivedMessagesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /group-archived-messages} : get all the groupArchivedMessages.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of groupArchivedMessages in body.
     */
    @GetMapping("/group-archived-messages")
    public ResponseEntity<List<GroupArchivedMessagesDTO>> getAllGroupArchivedMessages(Pageable pageable) {
        log.debug("REST request to get a page of GroupArchivedMessages");
        Page<GroupArchivedMessagesDTO> page = groupArchivedMessagesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /group-archived-messages/:id} : get the "id" groupArchivedMessages.
     *
     * @param id the id of the groupArchivedMessagesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the groupArchivedMessagesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/group-archived-messages/{id}")
    public ResponseEntity<GroupArchivedMessagesDTO> getGroupArchivedMessages(@PathVariable Long id) {
        log.debug("REST request to get GroupArchivedMessages : {}", id);
        Optional<GroupArchivedMessagesDTO> groupArchivedMessagesDTO = groupArchivedMessagesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(groupArchivedMessagesDTO);
    }

    /**
     * {@code DELETE  /group-archived-messages/:id} : delete the "id" groupArchivedMessages.
     *
     * @param id the id of the groupArchivedMessagesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/group-archived-messages/{id}")
    public ResponseEntity<Void> deleteGroupArchivedMessages(@PathVariable Long id) {
        log.debug("REST request to delete GroupArchivedMessages : {}", id);
        groupArchivedMessagesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
