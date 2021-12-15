/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.TagUsersService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.TagUsersDTO;

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
 * REST controller for managing {@link com.infy.domain.TagUsers}.
 */
@RestController
@RequestMapping("/api")
public class TagUsersResource {

    private final Logger log = LoggerFactory.getLogger(TagUsersResource.class);

    private static final String ENTITY_NAME = "tagUsers";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TagUsersService tagUsersService;

    public TagUsersResource(TagUsersService tagUsersService) {
        this.tagUsersService = tagUsersService;
    }

    /**
     * {@code POST  /tag-users} : Create a new tagUsers.
     *
     * @param tagUsersDTO the tagUsersDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tagUsersDTO, or with status {@code 400 (Bad Request)} if the tagUsers has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tag-users")
    public ResponseEntity<TagUsersDTO> createTagUsers(@Valid @RequestBody TagUsersDTO tagUsersDTO) throws URISyntaxException {
        log.debug("REST request to save TagUsers : {}", tagUsersDTO);
        if (tagUsersDTO.getId() != null) {
            throw new BadRequestAlertException("A new tagUsers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TagUsersDTO result = tagUsersService.save(tagUsersDTO);
        return ResponseEntity.created(new URI("/api/tag-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tag-users} : Updates an existing tagUsers.
     *
     * @param tagUsersDTO the tagUsersDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tagUsersDTO,
     * or with status {@code 400 (Bad Request)} if the tagUsersDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tagUsersDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tag-users")
    public ResponseEntity<TagUsersDTO> updateTagUsers(@Valid @RequestBody TagUsersDTO tagUsersDTO) throws URISyntaxException {
        log.debug("REST request to update TagUsers : {}", tagUsersDTO);
        if (tagUsersDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TagUsersDTO result = tagUsersService.save(tagUsersDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tagUsersDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tag-users} : get all the tagUsers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tagUsers in body.
     */
    @GetMapping("/tag-users")
    public ResponseEntity<List<TagUsersDTO>> getAllTagUsers(Pageable pageable) {
        log.debug("REST request to get a page of TagUsers");
        Page<TagUsersDTO> page = tagUsersService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tag-users/:id} : get the "id" tagUsers.
     *
     * @param id the id of the tagUsersDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tagUsersDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tag-users/{id}")
    public ResponseEntity<TagUsersDTO> getTagUsers(@PathVariable Long id) {
        log.debug("REST request to get TagUsers : {}", id);
        Optional<TagUsersDTO> tagUsersDTO = tagUsersService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tagUsersDTO);
    }

    /**
     * {@code DELETE  /tag-users/:id} : delete the "id" tagUsers.
     *
     * @param id the id of the tagUsersDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tag-users/{id}")
    public ResponseEntity<Void> deleteTagUsers(@PathVariable Long id) {
        log.debug("REST request to delete TagUsers : {}", id);
        tagUsersService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
