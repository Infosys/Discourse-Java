/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.TagSearchDataService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.TagSearchDataDTO;

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
 * REST controller for managing {@link com.infy.domain.TagSearchData}.
 */
@RestController
@RequestMapping("/api")
public class TagSearchDataResource {

    private final Logger log = LoggerFactory.getLogger(TagSearchDataResource.class);

    private static final String ENTITY_NAME = "tagSearchData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TagSearchDataService tagSearchDataService;

    public TagSearchDataResource(TagSearchDataService tagSearchDataService) {
        this.tagSearchDataService = tagSearchDataService;
    }

    /**
     * {@code POST  /tag-search-data} : Create a new tagSearchData.
     *
     * @param tagSearchDataDTO the tagSearchDataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tagSearchDataDTO, or with status {@code 400 (Bad Request)} if the tagSearchData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tag-search-data")
    public ResponseEntity<TagSearchDataDTO> createTagSearchData(@Valid @RequestBody TagSearchDataDTO tagSearchDataDTO) throws URISyntaxException {
        log.debug("REST request to save TagSearchData : {}", tagSearchDataDTO);
        if (tagSearchDataDTO.getId() != null) {
            throw new BadRequestAlertException("A new tagSearchData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TagSearchDataDTO result = tagSearchDataService.save(tagSearchDataDTO);
        return ResponseEntity.created(new URI("/api/tag-search-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tag-search-data} : Updates an existing tagSearchData.
     *
     * @param tagSearchDataDTO the tagSearchDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tagSearchDataDTO,
     * or with status {@code 400 (Bad Request)} if the tagSearchDataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tagSearchDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tag-search-data")
    public ResponseEntity<TagSearchDataDTO> updateTagSearchData(@Valid @RequestBody TagSearchDataDTO tagSearchDataDTO) throws URISyntaxException {
        log.debug("REST request to update TagSearchData : {}", tagSearchDataDTO);
        if (tagSearchDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TagSearchDataDTO result = tagSearchDataService.save(tagSearchDataDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tagSearchDataDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tag-search-data} : get all the tagSearchData.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tagSearchData in body.
     */
    @GetMapping("/tag-search-data")
    public ResponseEntity<List<TagSearchDataDTO>> getAllTagSearchData(Pageable pageable) {
        log.debug("REST request to get a page of TagSearchData");
        Page<TagSearchDataDTO> page = tagSearchDataService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tag-search-data/:id} : get the "id" tagSearchData.
     *
     * @param id the id of the tagSearchDataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tagSearchDataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tag-search-data/{id}")
    public ResponseEntity<TagSearchDataDTO> getTagSearchData(@PathVariable Long id) {
        log.debug("REST request to get TagSearchData : {}", id);
        Optional<TagSearchDataDTO> tagSearchDataDTO = tagSearchDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tagSearchDataDTO);
    }

    /**
     * {@code DELETE  /tag-search-data/:id} : delete the "id" tagSearchData.
     *
     * @param id the id of the tagSearchDataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tag-search-data/{id}")
    public ResponseEntity<Void> deleteTagSearchData(@PathVariable Long id) {
        log.debug("REST request to delete TagSearchData : {}", id);
        tagSearchDataService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
