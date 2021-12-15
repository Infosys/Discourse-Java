/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.CategoryFeaturedTopicsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.CategoryFeaturedTopicsDTO;

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
 * REST controller for managing {@link com.infy.domain.CategoryFeaturedTopics}.
 */
@RestController
@RequestMapping("/api")
public class CategoryFeaturedTopicsResource {

    private final Logger log = LoggerFactory.getLogger(CategoryFeaturedTopicsResource.class);

    private static final String ENTITY_NAME = "categoryFeaturedTopics";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CategoryFeaturedTopicsService categoryFeaturedTopicsService;

    public CategoryFeaturedTopicsResource(CategoryFeaturedTopicsService categoryFeaturedTopicsService) {
        this.categoryFeaturedTopicsService = categoryFeaturedTopicsService;
    }

    /**
     * {@code POST  /category-featured-topics} : Create a new categoryFeaturedTopics.
     *
     * @param categoryFeaturedTopicsDTO the categoryFeaturedTopicsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new categoryFeaturedTopicsDTO, or with status {@code 400 (Bad Request)} if the categoryFeaturedTopics has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/category-featured-topics")
    public ResponseEntity<CategoryFeaturedTopicsDTO> createCategoryFeaturedTopics(@Valid @RequestBody CategoryFeaturedTopicsDTO categoryFeaturedTopicsDTO) throws URISyntaxException {
        log.debug("REST request to save CategoryFeaturedTopics : {}", categoryFeaturedTopicsDTO);
        if (categoryFeaturedTopicsDTO.getId() != null) {
            throw new BadRequestAlertException("A new categoryFeaturedTopics cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CategoryFeaturedTopicsDTO result = categoryFeaturedTopicsService.save(categoryFeaturedTopicsDTO);
        return ResponseEntity.created(new URI("/api/category-featured-topics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /category-featured-topics} : Updates an existing categoryFeaturedTopics.
     *
     * @param categoryFeaturedTopicsDTO the categoryFeaturedTopicsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categoryFeaturedTopicsDTO,
     * or with status {@code 400 (Bad Request)} if the categoryFeaturedTopicsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the categoryFeaturedTopicsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/category-featured-topics")
    public ResponseEntity<CategoryFeaturedTopicsDTO> updateCategoryFeaturedTopics(@Valid @RequestBody CategoryFeaturedTopicsDTO categoryFeaturedTopicsDTO) throws URISyntaxException {
        log.debug("REST request to update CategoryFeaturedTopics : {}", categoryFeaturedTopicsDTO);
        if (categoryFeaturedTopicsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CategoryFeaturedTopicsDTO result = categoryFeaturedTopicsService.save(categoryFeaturedTopicsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, categoryFeaturedTopicsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /category-featured-topics} : get all the categoryFeaturedTopics.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categoryFeaturedTopics in body.
     */
    @GetMapping("/category-featured-topics")
    public ResponseEntity<List<CategoryFeaturedTopicsDTO>> getAllCategoryFeaturedTopics(Pageable pageable) {
        log.debug("REST request to get a page of CategoryFeaturedTopics");
        Page<CategoryFeaturedTopicsDTO> page = categoryFeaturedTopicsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /category-featured-topics/:id} : get the "id" categoryFeaturedTopics.
     *
     * @param id the id of the categoryFeaturedTopicsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the categoryFeaturedTopicsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/category-featured-topics/{id}")
    public ResponseEntity<CategoryFeaturedTopicsDTO> getCategoryFeaturedTopics(@PathVariable Long id) {
        log.debug("REST request to get CategoryFeaturedTopics : {}", id);
        Optional<CategoryFeaturedTopicsDTO> categoryFeaturedTopicsDTO = categoryFeaturedTopicsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(categoryFeaturedTopicsDTO);
    }

    /**
     * {@code DELETE  /category-featured-topics/:id} : delete the "id" categoryFeaturedTopics.
     *
     * @param id the id of the categoryFeaturedTopicsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/category-featured-topics/{id}")
    public ResponseEntity<Void> deleteCategoryFeaturedTopics(@PathVariable Long id) {
        log.debug("REST request to delete CategoryFeaturedTopics : {}", id);
        categoryFeaturedTopicsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
