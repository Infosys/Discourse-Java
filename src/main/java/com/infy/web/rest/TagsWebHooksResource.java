/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.TagsWebHooksService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.TagsWebHooksDTO;

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
 * REST controller for managing {@link com.infy.domain.TagsWebHooks}.
 */
@RestController
@RequestMapping("/api")
public class TagsWebHooksResource {

    private final Logger log = LoggerFactory.getLogger(TagsWebHooksResource.class);

    private static final String ENTITY_NAME = "tagsWebHooks";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TagsWebHooksService tagsWebHooksService;

    public TagsWebHooksResource(TagsWebHooksService tagsWebHooksService) {
        this.tagsWebHooksService = tagsWebHooksService;
    }

    /**
     * {@code POST  /tags-web-hooks} : Create a new tagsWebHooks.
     *
     * @param tagsWebHooksDTO the tagsWebHooksDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tagsWebHooksDTO, or with status {@code 400 (Bad Request)} if the tagsWebHooks has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tags-web-hooks")
    public ResponseEntity<TagsWebHooksDTO> createTagsWebHooks(@Valid @RequestBody TagsWebHooksDTO tagsWebHooksDTO) throws URISyntaxException {
        log.debug("REST request to save TagsWebHooks : {}", tagsWebHooksDTO);
        if (tagsWebHooksDTO.getId() != null) {
            throw new BadRequestAlertException("A new tagsWebHooks cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TagsWebHooksDTO result = tagsWebHooksService.save(tagsWebHooksDTO);
        return ResponseEntity.created(new URI("/api/tags-web-hooks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tags-web-hooks} : Updates an existing tagsWebHooks.
     *
     * @param tagsWebHooksDTO the tagsWebHooksDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tagsWebHooksDTO,
     * or with status {@code 400 (Bad Request)} if the tagsWebHooksDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tagsWebHooksDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tags-web-hooks")
    public ResponseEntity<TagsWebHooksDTO> updateTagsWebHooks(@Valid @RequestBody TagsWebHooksDTO tagsWebHooksDTO) throws URISyntaxException {
        log.debug("REST request to update TagsWebHooks : {}", tagsWebHooksDTO);
        if (tagsWebHooksDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TagsWebHooksDTO result = tagsWebHooksService.save(tagsWebHooksDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tagsWebHooksDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tags-web-hooks} : get all the tagsWebHooks.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tagsWebHooks in body.
     */
    @GetMapping("/tags-web-hooks")
    public ResponseEntity<List<TagsWebHooksDTO>> getAllTagsWebHooks(Pageable pageable) {
        log.debug("REST request to get a page of TagsWebHooks");
        Page<TagsWebHooksDTO> page = tagsWebHooksService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tags-web-hooks/:id} : get the "id" tagsWebHooks.
     *
     * @param id the id of the tagsWebHooksDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tagsWebHooksDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tags-web-hooks/{id}")
    public ResponseEntity<TagsWebHooksDTO> getTagsWebHooks(@PathVariable Long id) {
        log.debug("REST request to get TagsWebHooks : {}", id);
        Optional<TagsWebHooksDTO> tagsWebHooksDTO = tagsWebHooksService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tagsWebHooksDTO);
    }

    /**
     * {@code DELETE  /tags-web-hooks/:id} : delete the "id" tagsWebHooks.
     *
     * @param id the id of the tagsWebHooksDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tags-web-hooks/{id}")
    public ResponseEntity<Void> deleteTagsWebHooks(@PathVariable Long id) {
        log.debug("REST request to delete TagsWebHooks : {}", id);
        tagsWebHooksService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
