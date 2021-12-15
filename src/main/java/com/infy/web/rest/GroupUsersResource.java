/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.GroupUsersService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.GroupUsersDTO;

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
 * REST controller for managing {@link com.infy.domain.GroupUsers}.
 */
@RestController
@RequestMapping("/api")
public class GroupUsersResource {

    private final Logger log = LoggerFactory.getLogger(GroupUsersResource.class);

    private static final String ENTITY_NAME = "groupUsers";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GroupUsersService groupUsersService;

    public GroupUsersResource(GroupUsersService groupUsersService) {
        this.groupUsersService = groupUsersService;
    }

    /**
     * {@code POST  /group-users} : Create a new groupUsers.
     *
     * @param groupUsersDTO the groupUsersDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new groupUsersDTO, or with status {@code 400 (Bad Request)} if the groupUsers has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/group-users")
    public ResponseEntity<GroupUsersDTO> createGroupUsers(@Valid @RequestBody GroupUsersDTO groupUsersDTO) throws URISyntaxException {
        log.debug("REST request to save GroupUsers : {}", groupUsersDTO);
        if (groupUsersDTO.getId() != null) {
            throw new BadRequestAlertException("A new groupUsers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GroupUsersDTO result = groupUsersService.save(groupUsersDTO);
        return ResponseEntity.created(new URI("/api/group-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /group-users} : Updates an existing groupUsers.
     *
     * @param groupUsersDTO the groupUsersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated groupUsersDTO,
     * or with status {@code 400 (Bad Request)} if the groupUsersDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the groupUsersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/group-users")
    public ResponseEntity<GroupUsersDTO> updateGroupUsers(@Valid @RequestBody GroupUsersDTO groupUsersDTO) throws URISyntaxException {
        log.debug("REST request to update GroupUsers : {}", groupUsersDTO);
        if (groupUsersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GroupUsersDTO result = groupUsersService.save(groupUsersDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, groupUsersDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /group-users} : get all the groupUsers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of groupUsers in body.
     */
    @GetMapping("/group-users")
    public ResponseEntity<List<GroupUsersDTO>> getAllGroupUsers(Pageable pageable) {
        log.debug("REST request to get a page of GroupUsers");
        Page<GroupUsersDTO> page = groupUsersService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /group-users/:id} : get the "id" groupUsers.
     *
     * @param id the id of the groupUsersDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the groupUsersDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/group-users/{id}")
    public ResponseEntity<GroupUsersDTO> getGroupUsers(@PathVariable Long id) {
        log.debug("REST request to get GroupUsers : {}", id);
        Optional<GroupUsersDTO> groupUsersDTO = groupUsersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(groupUsersDTO);
    }

    /**
     * {@code DELETE  /group-users/:id} : delete the "id" groupUsers.
     *
     * @param id the id of the groupUsersDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/group-users/{id}")
    public ResponseEntity<Void> deleteGroupUsers(@PathVariable Long id) {
        log.debug("REST request to delete GroupUsers : {}", id);
        groupUsersService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
