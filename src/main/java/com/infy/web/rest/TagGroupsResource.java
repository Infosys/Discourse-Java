/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.TagGroupsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.TagGroupsDTO;

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
 * REST controller for managing {@link com.infy.domain.TagGroups}.
 */
@RestController
@RequestMapping("/api")
public class TagGroupsResource {

    private final Logger log = LoggerFactory.getLogger(TagGroupsResource.class);

    private static final String ENTITY_NAME = "tagGroups";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TagGroupsService tagGroupsService;

    public TagGroupsResource(TagGroupsService tagGroupsService) {
        this.tagGroupsService = tagGroupsService;
    }

    /**
     * {@code POST  /tag-groups} : Create a new tagGroups.
     *
     * @param tagGroupsDTO the tagGroupsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tagGroupsDTO, or with status {@code 400 (Bad Request)} if the tagGroups has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tag-groups")
    public ResponseEntity<TagGroupsDTO> createTagGroups(@Valid @RequestBody TagGroupsDTO tagGroupsDTO) throws URISyntaxException {
        log.debug("REST request to save TagGroups : {}", tagGroupsDTO);
        if (tagGroupsDTO.getId() != null) {
            throw new BadRequestAlertException("A new tagGroups cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TagGroupsDTO result = tagGroupsService.save(tagGroupsDTO);
        return ResponseEntity.created(new URI("/api/tag-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tag-groups} : Updates an existing tagGroups.
     *
     * @param tagGroupsDTO the tagGroupsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tagGroupsDTO,
     * or with status {@code 400 (Bad Request)} if the tagGroupsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tagGroupsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tag-groups")
    public ResponseEntity<TagGroupsDTO> updateTagGroups(@Valid @RequestBody TagGroupsDTO tagGroupsDTO) throws URISyntaxException {
        log.debug("REST request to update TagGroups : {}", tagGroupsDTO);
        if (tagGroupsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TagGroupsDTO result = tagGroupsService.save(tagGroupsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tagGroupsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tag-groups} : get all the tagGroups.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tagGroups in body.
     */
    @GetMapping("/tag-groups")
    public ResponseEntity<List<TagGroupsDTO>> getAllTagGroups(Pageable pageable) {
        log.debug("REST request to get a page of TagGroups");
        Page<TagGroupsDTO> page = tagGroupsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tag-groups/:id} : get the "id" tagGroups.
     *
     * @param id the id of the tagGroupsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tagGroupsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tag-groups/{id}")
    public ResponseEntity<TagGroupsDTO> getTagGroups(@PathVariable Long id) {
        log.debug("REST request to get TagGroups : {}", id);
        Optional<TagGroupsDTO> tagGroupsDTO = tagGroupsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tagGroupsDTO);
    }

    /**
     * {@code DELETE  /tag-groups/:id} : delete the "id" tagGroups.
     *
     * @param id the id of the tagGroupsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tag-groups/{id}")
    public ResponseEntity<Void> deleteTagGroups(@PathVariable Long id) {
        log.debug("REST request to delete TagGroups : {}", id);
        tagGroupsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
