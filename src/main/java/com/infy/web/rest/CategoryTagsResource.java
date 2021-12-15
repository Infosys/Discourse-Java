/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.CategoryTagsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.CategoryTagsDTO;

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
 * REST controller for managing {@link com.infy.domain.CategoryTags}.
 */
@RestController
@RequestMapping("/api")
public class CategoryTagsResource {

    private final Logger log = LoggerFactory.getLogger(CategoryTagsResource.class);

    private static final String ENTITY_NAME = "categoryTags";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CategoryTagsService categoryTagsService;

    public CategoryTagsResource(CategoryTagsService categoryTagsService) {
        this.categoryTagsService = categoryTagsService;
    }

    /**
     * {@code POST  /category-tags} : Create a new categoryTags.
     *
     * @param categoryTagsDTO the categoryTagsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new categoryTagsDTO, or with status {@code 400 (Bad Request)} if the categoryTags has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/category-tags")
    public ResponseEntity<CategoryTagsDTO> createCategoryTags(@Valid @RequestBody CategoryTagsDTO categoryTagsDTO) throws URISyntaxException {
        log.debug("REST request to save CategoryTags : {}", categoryTagsDTO);
        if (categoryTagsDTO.getId() != null) {
            throw new BadRequestAlertException("A new categoryTags cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CategoryTagsDTO result = categoryTagsService.save(categoryTagsDTO);
        return ResponseEntity.created(new URI("/api/category-tags/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /category-tags} : Updates an existing categoryTags.
     *
     * @param categoryTagsDTO the categoryTagsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categoryTagsDTO,
     * or with status {@code 400 (Bad Request)} if the categoryTagsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the categoryTagsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/category-tags")
    public ResponseEntity<CategoryTagsDTO> updateCategoryTags(@Valid @RequestBody CategoryTagsDTO categoryTagsDTO) throws URISyntaxException {
        log.debug("REST request to update CategoryTags : {}", categoryTagsDTO);
        if (categoryTagsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CategoryTagsDTO result = categoryTagsService.save(categoryTagsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, categoryTagsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /category-tags} : get all the categoryTags.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categoryTags in body.
     */
    @GetMapping("/category-tags")
    public ResponseEntity<List<CategoryTagsDTO>> getAllCategoryTags(Pageable pageable) {
        log.debug("REST request to get a page of CategoryTags");
        Page<CategoryTagsDTO> page = categoryTagsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /category-tags/:id} : get the "id" categoryTags.
     *
     * @param id the id of the categoryTagsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the categoryTagsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/category-tags/{id}")
    public ResponseEntity<CategoryTagsDTO> getCategoryTags(@PathVariable Long id) {
        log.debug("REST request to get CategoryTags : {}", id);
        Optional<CategoryTagsDTO> categoryTagsDTO = categoryTagsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(categoryTagsDTO);
    }

    /**
     * {@code DELETE  /category-tags/:id} : delete the "id" categoryTags.
     *
     * @param id the id of the categoryTagsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/category-tags/{id}")
    public ResponseEntity<Void> deleteCategoryTags(@PathVariable Long id) {
        log.debug("REST request to delete CategoryTags : {}", id);
        categoryTagsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
