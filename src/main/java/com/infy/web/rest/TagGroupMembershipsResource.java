/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.TagGroupMembershipsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.TagGroupMembershipsDTO;

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
 * REST controller for managing {@link com.infy.domain.TagGroupMemberships}.
 */
@RestController
@RequestMapping("/api")
public class TagGroupMembershipsResource {

    private final Logger log = LoggerFactory.getLogger(TagGroupMembershipsResource.class);

    private static final String ENTITY_NAME = "tagGroupMemberships";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TagGroupMembershipsService tagGroupMembershipsService;

    public TagGroupMembershipsResource(TagGroupMembershipsService tagGroupMembershipsService) {
        this.tagGroupMembershipsService = tagGroupMembershipsService;
    }

    /**
     * {@code POST  /tag-group-memberships} : Create a new tagGroupMemberships.
     *
     * @param tagGroupMembershipsDTO the tagGroupMembershipsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tagGroupMembershipsDTO, or with status {@code 400 (Bad Request)} if the tagGroupMemberships has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tag-group-memberships")
    public ResponseEntity<TagGroupMembershipsDTO> createTagGroupMemberships(@Valid @RequestBody TagGroupMembershipsDTO tagGroupMembershipsDTO) throws URISyntaxException {
        log.debug("REST request to save TagGroupMemberships : {}", tagGroupMembershipsDTO);
        if (tagGroupMembershipsDTO.getId() != null) {
            throw new BadRequestAlertException("A new tagGroupMemberships cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TagGroupMembershipsDTO result = tagGroupMembershipsService.save(tagGroupMembershipsDTO);
        return ResponseEntity.created(new URI("/api/tag-group-memberships/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tag-group-memberships} : Updates an existing tagGroupMemberships.
     *
     * @param tagGroupMembershipsDTO the tagGroupMembershipsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tagGroupMembershipsDTO,
     * or with status {@code 400 (Bad Request)} if the tagGroupMembershipsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tagGroupMembershipsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tag-group-memberships")
    public ResponseEntity<TagGroupMembershipsDTO> updateTagGroupMemberships(@Valid @RequestBody TagGroupMembershipsDTO tagGroupMembershipsDTO) throws URISyntaxException {
        log.debug("REST request to update TagGroupMemberships : {}", tagGroupMembershipsDTO);
        if (tagGroupMembershipsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TagGroupMembershipsDTO result = tagGroupMembershipsService.save(tagGroupMembershipsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tagGroupMembershipsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tag-group-memberships} : get all the tagGroupMemberships.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tagGroupMemberships in body.
     */
    @GetMapping("/tag-group-memberships")
    public ResponseEntity<List<TagGroupMembershipsDTO>> getAllTagGroupMemberships(Pageable pageable) {
        log.debug("REST request to get a page of TagGroupMemberships");
        Page<TagGroupMembershipsDTO> page = tagGroupMembershipsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tag-group-memberships/:id} : get the "id" tagGroupMemberships.
     *
     * @param id the id of the tagGroupMembershipsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tagGroupMembershipsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tag-group-memberships/{id}")
    public ResponseEntity<TagGroupMembershipsDTO> getTagGroupMemberships(@PathVariable Long id) {
        log.debug("REST request to get TagGroupMemberships : {}", id);
        Optional<TagGroupMembershipsDTO> tagGroupMembershipsDTO = tagGroupMembershipsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tagGroupMembershipsDTO);
    }

    /**
     * {@code DELETE  /tag-group-memberships/:id} : delete the "id" tagGroupMemberships.
     *
     * @param id the id of the tagGroupMembershipsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tag-group-memberships/{id}")
    public ResponseEntity<Void> deleteTagGroupMemberships(@PathVariable Long id) {
        log.debug("REST request to delete TagGroupMemberships : {}", id);
        tagGroupMembershipsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
