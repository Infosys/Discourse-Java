/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.OptimizedImagesService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.OptimizedImagesDTO;

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
 * REST controller for managing {@link com.infy.domain.OptimizedImages}.
 */
@RestController
@RequestMapping("/api")
public class OptimizedImagesResource {

    private final Logger log = LoggerFactory.getLogger(OptimizedImagesResource.class);

    private static final String ENTITY_NAME = "optimizedImages";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OptimizedImagesService optimizedImagesService;

    public OptimizedImagesResource(OptimizedImagesService optimizedImagesService) {
        this.optimizedImagesService = optimizedImagesService;
    }

    /**
     * {@code POST  /optimized-images} : Create a new optimizedImages.
     *
     * @param optimizedImagesDTO the optimizedImagesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new optimizedImagesDTO, or with status {@code 400 (Bad Request)} if the optimizedImages has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/optimized-images")
    public ResponseEntity<OptimizedImagesDTO> createOptimizedImages(@Valid @RequestBody OptimizedImagesDTO optimizedImagesDTO) throws URISyntaxException {
        log.debug("REST request to save OptimizedImages : {}", optimizedImagesDTO);
        if (optimizedImagesDTO.getId() != null) {
            throw new BadRequestAlertException("A new optimizedImages cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OptimizedImagesDTO result = optimizedImagesService.save(optimizedImagesDTO);
        return ResponseEntity.created(new URI("/api/optimized-images/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /optimized-images} : Updates an existing optimizedImages.
     *
     * @param optimizedImagesDTO the optimizedImagesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated optimizedImagesDTO,
     * or with status {@code 400 (Bad Request)} if the optimizedImagesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the optimizedImagesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/optimized-images")
    public ResponseEntity<OptimizedImagesDTO> updateOptimizedImages(@Valid @RequestBody OptimizedImagesDTO optimizedImagesDTO) throws URISyntaxException {
        log.debug("REST request to update OptimizedImages : {}", optimizedImagesDTO);
        if (optimizedImagesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OptimizedImagesDTO result = optimizedImagesService.save(optimizedImagesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, optimizedImagesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /optimized-images} : get all the optimizedImages.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of optimizedImages in body.
     */
    @GetMapping("/optimized-images")
    public ResponseEntity<List<OptimizedImagesDTO>> getAllOptimizedImages(Pageable pageable) {
        log.debug("REST request to get a page of OptimizedImages");
        Page<OptimizedImagesDTO> page = optimizedImagesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /optimized-images/:id} : get the "id" optimizedImages.
     *
     * @param id the id of the optimizedImagesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the optimizedImagesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/optimized-images/{id}")
    public ResponseEntity<OptimizedImagesDTO> getOptimizedImages(@PathVariable Long id) {
        log.debug("REST request to get OptimizedImages : {}", id);
        Optional<OptimizedImagesDTO> optimizedImagesDTO = optimizedImagesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(optimizedImagesDTO);
    }

    /**
     * {@code DELETE  /optimized-images/:id} : delete the "id" optimizedImages.
     *
     * @param id the id of the optimizedImagesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/optimized-images/{id}")
    public ResponseEntity<Void> deleteOptimizedImages(@PathVariable Long id) {
        log.debug("REST request to delete OptimizedImages : {}", id);
        optimizedImagesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
