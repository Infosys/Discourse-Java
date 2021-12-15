/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.CategoryTagGroupsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.CategoryTagGroupsDTO;

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
 * REST controller for managing {@link com.infy.domain.CategoryTagGroups}.
 */
@RestController
@RequestMapping("/api")
public class CategoryTagGroupsResource {

    private final Logger log = LoggerFactory.getLogger(CategoryTagGroupsResource.class);

    private static final String ENTITY_NAME = "categoryTagGroups";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CategoryTagGroupsService categoryTagGroupsService;

    public CategoryTagGroupsResource(CategoryTagGroupsService categoryTagGroupsService) {
        this.categoryTagGroupsService = categoryTagGroupsService;
    }

    /**
     * {@code POST  /category-tag-groups} : Create a new categoryTagGroups.
     *
     * @param categoryTagGroupsDTO the categoryTagGroupsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new categoryTagGroupsDTO, or with status {@code 400 (Bad Request)} if the categoryTagGroups has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/category-tag-groups")
    public ResponseEntity<CategoryTagGroupsDTO> createCategoryTagGroups(@Valid @RequestBody CategoryTagGroupsDTO categoryTagGroupsDTO) throws URISyntaxException {
        log.debug("REST request to save CategoryTagGroups : {}", categoryTagGroupsDTO);
        if (categoryTagGroupsDTO.getId() != null) {
            throw new BadRequestAlertException("A new categoryTagGroups cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CategoryTagGroupsDTO result = categoryTagGroupsService.save(categoryTagGroupsDTO);
        return ResponseEntity.created(new URI("/api/category-tag-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /category-tag-groups} : Updates an existing categoryTagGroups.
     *
     * @param categoryTagGroupsDTO the categoryTagGroupsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categoryTagGroupsDTO,
     * or with status {@code 400 (Bad Request)} if the categoryTagGroupsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the categoryTagGroupsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/category-tag-groups")
    public ResponseEntity<CategoryTagGroupsDTO> updateCategoryTagGroups(@Valid @RequestBody CategoryTagGroupsDTO categoryTagGroupsDTO) throws URISyntaxException {
        log.debug("REST request to update CategoryTagGroups : {}", categoryTagGroupsDTO);
        if (categoryTagGroupsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CategoryTagGroupsDTO result = categoryTagGroupsService.save(categoryTagGroupsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, categoryTagGroupsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /category-tag-groups} : get all the categoryTagGroups.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categoryTagGroups in body.
     */
    @GetMapping("/category-tag-groups")
    public ResponseEntity<List<CategoryTagGroupsDTO>> getAllCategoryTagGroups(Pageable pageable) {
        log.debug("REST request to get a page of CategoryTagGroups");
        Page<CategoryTagGroupsDTO> page = categoryTagGroupsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /category-tag-groups/:id} : get the "id" categoryTagGroups.
     *
     * @param id the id of the categoryTagGroupsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the categoryTagGroupsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/category-tag-groups/{id}")
    public ResponseEntity<CategoryTagGroupsDTO> getCategoryTagGroups(@PathVariable Long id) {
        log.debug("REST request to get CategoryTagGroups : {}", id);
        Optional<CategoryTagGroupsDTO> categoryTagGroupsDTO = categoryTagGroupsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(categoryTagGroupsDTO);
    }

    /**
     * {@code DELETE  /category-tag-groups/:id} : delete the "id" categoryTagGroups.
     *
     * @param id the id of the categoryTagGroupsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/category-tag-groups/{id}")
    public ResponseEntity<Void> deleteCategoryTagGroups(@PathVariable Long id) {
        log.debug("REST request to delete CategoryTagGroups : {}", id);
        categoryTagGroupsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
