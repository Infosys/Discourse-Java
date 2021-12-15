/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.CategoryTagStatsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.CategoryTagStatsDTO;

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
 * REST controller for managing {@link com.infy.domain.CategoryTagStats}.
 */
@RestController
@RequestMapping("/api")
public class CategoryTagStatsResource {

    private final Logger log = LoggerFactory.getLogger(CategoryTagStatsResource.class);

    private static final String ENTITY_NAME = "categoryTagStats";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CategoryTagStatsService categoryTagStatsService;

    public CategoryTagStatsResource(CategoryTagStatsService categoryTagStatsService) {
        this.categoryTagStatsService = categoryTagStatsService;
    }

    /**
     * {@code POST  /category-tag-stats} : Create a new categoryTagStats.
     *
     * @param categoryTagStatsDTO the categoryTagStatsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new categoryTagStatsDTO, or with status {@code 400 (Bad Request)} if the categoryTagStats has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/category-tag-stats")
    public ResponseEntity<CategoryTagStatsDTO> createCategoryTagStats(@Valid @RequestBody CategoryTagStatsDTO categoryTagStatsDTO) throws URISyntaxException {
        log.debug("REST request to save CategoryTagStats : {}", categoryTagStatsDTO);
        if (categoryTagStatsDTO.getId() != null) {
            throw new BadRequestAlertException("A new categoryTagStats cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CategoryTagStatsDTO result = categoryTagStatsService.save(categoryTagStatsDTO);
        return ResponseEntity.created(new URI("/api/category-tag-stats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /category-tag-stats} : Updates an existing categoryTagStats.
     *
     * @param categoryTagStatsDTO the categoryTagStatsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categoryTagStatsDTO,
     * or with status {@code 400 (Bad Request)} if the categoryTagStatsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the categoryTagStatsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/category-tag-stats")
    public ResponseEntity<CategoryTagStatsDTO> updateCategoryTagStats(@Valid @RequestBody CategoryTagStatsDTO categoryTagStatsDTO) throws URISyntaxException {
        log.debug("REST request to update CategoryTagStats : {}", categoryTagStatsDTO);
        if (categoryTagStatsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CategoryTagStatsDTO result = categoryTagStatsService.save(categoryTagStatsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, categoryTagStatsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /category-tag-stats} : get all the categoryTagStats.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categoryTagStats in body.
     */
    @GetMapping("/category-tag-stats")
    public ResponseEntity<List<CategoryTagStatsDTO>> getAllCategoryTagStats(Pageable pageable) {
        log.debug("REST request to get a page of CategoryTagStats");
        Page<CategoryTagStatsDTO> page = categoryTagStatsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /category-tag-stats/:id} : get the "id" categoryTagStats.
     *
     * @param id the id of the categoryTagStatsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the categoryTagStatsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/category-tag-stats/{id}")
    public ResponseEntity<CategoryTagStatsDTO> getCategoryTagStats(@PathVariable Long id) {
        log.debug("REST request to get CategoryTagStats : {}", id);
        Optional<CategoryTagStatsDTO> categoryTagStatsDTO = categoryTagStatsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(categoryTagStatsDTO);
    }

    /**
     * {@code DELETE  /category-tag-stats/:id} : delete the "id" categoryTagStats.
     *
     * @param id the id of the categoryTagStatsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/category-tag-stats/{id}")
    public ResponseEntity<Void> deleteCategoryTagStats(@PathVariable Long id) {
        log.debug("REST request to delete CategoryTagStats : {}", id);
        categoryTagStatsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
