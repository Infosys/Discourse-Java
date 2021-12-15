/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.GroupCustomFieldsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.GroupCustomFieldsDTO;

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
 * REST controller for managing {@link com.infy.domain.GroupCustomFields}.
 */
@RestController
@RequestMapping("/api")
public class GroupCustomFieldsResource {

    private final Logger log = LoggerFactory.getLogger(GroupCustomFieldsResource.class);

    private static final String ENTITY_NAME = "groupCustomFields";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GroupCustomFieldsService groupCustomFieldsService;

    public GroupCustomFieldsResource(GroupCustomFieldsService groupCustomFieldsService) {
        this.groupCustomFieldsService = groupCustomFieldsService;
    }

    /**
     * {@code POST  /group-custom-fields} : Create a new groupCustomFields.
     *
     * @param groupCustomFieldsDTO the groupCustomFieldsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new groupCustomFieldsDTO, or with status {@code 400 (Bad Request)} if the groupCustomFields has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/group-custom-fields")
    public ResponseEntity<GroupCustomFieldsDTO> createGroupCustomFields(@Valid @RequestBody GroupCustomFieldsDTO groupCustomFieldsDTO) throws URISyntaxException {
        log.debug("REST request to save GroupCustomFields : {}", groupCustomFieldsDTO);
        if (groupCustomFieldsDTO.getId() != null) {
            throw new BadRequestAlertException("A new groupCustomFields cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GroupCustomFieldsDTO result = groupCustomFieldsService.save(groupCustomFieldsDTO);
        return ResponseEntity.created(new URI("/api/group-custom-fields/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /group-custom-fields} : Updates an existing groupCustomFields.
     *
     * @param groupCustomFieldsDTO the groupCustomFieldsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated groupCustomFieldsDTO,
     * or with status {@code 400 (Bad Request)} if the groupCustomFieldsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the groupCustomFieldsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/group-custom-fields")
    public ResponseEntity<GroupCustomFieldsDTO> updateGroupCustomFields(@Valid @RequestBody GroupCustomFieldsDTO groupCustomFieldsDTO) throws URISyntaxException {
        log.debug("REST request to update GroupCustomFields : {}", groupCustomFieldsDTO);
        if (groupCustomFieldsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GroupCustomFieldsDTO result = groupCustomFieldsService.save(groupCustomFieldsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, groupCustomFieldsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /group-custom-fields} : get all the groupCustomFields.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of groupCustomFields in body.
     */
    @GetMapping("/group-custom-fields")
    public ResponseEntity<List<GroupCustomFieldsDTO>> getAllGroupCustomFields(Pageable pageable) {
        log.debug("REST request to get a page of GroupCustomFields");
        Page<GroupCustomFieldsDTO> page = groupCustomFieldsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /group-custom-fields/:id} : get the "id" groupCustomFields.
     *
     * @param id the id of the groupCustomFieldsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the groupCustomFieldsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/group-custom-fields/{id}")
    public ResponseEntity<GroupCustomFieldsDTO> getGroupCustomFields(@PathVariable Long id) {
        log.debug("REST request to get GroupCustomFields : {}", id);
        Optional<GroupCustomFieldsDTO> groupCustomFieldsDTO = groupCustomFieldsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(groupCustomFieldsDTO);
    }

    /**
     * {@code DELETE  /group-custom-fields/:id} : delete the "id" groupCustomFields.
     *
     * @param id the id of the groupCustomFieldsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/group-custom-fields/{id}")
    public ResponseEntity<Void> deleteGroupCustomFields(@PathVariable Long id) {
        log.debug("REST request to delete GroupCustomFields : {}", id);
        groupCustomFieldsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
