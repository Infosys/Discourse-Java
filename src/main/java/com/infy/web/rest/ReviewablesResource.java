/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.ReviewablesService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.ReviewablesDTO;

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
 * REST controller for managing {@link com.infy.domain.Reviewables}.
 */
@RestController
@RequestMapping("/api")
public class ReviewablesResource {

    private final Logger log = LoggerFactory.getLogger(ReviewablesResource.class);

    private static final String ENTITY_NAME = "reviewables";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReviewablesService reviewablesService;

    public ReviewablesResource(ReviewablesService reviewablesService) {
        this.reviewablesService = reviewablesService;
    }

    /**
     * {@code POST  /reviewables} : Create a new reviewables.
     *
     * @param reviewablesDTO the reviewablesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reviewablesDTO, or with status {@code 400 (Bad Request)} if the reviewables has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/reviewables")
    public ResponseEntity<ReviewablesDTO> createReviewables(@Valid @RequestBody ReviewablesDTO reviewablesDTO) throws URISyntaxException {
        log.debug("REST request to save Reviewables : {}", reviewablesDTO);
        if (reviewablesDTO.getId() != null) {
            throw new BadRequestAlertException("A new reviewables cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReviewablesDTO result = reviewablesService.save(reviewablesDTO);
        return ResponseEntity.created(new URI("/api/reviewables/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /reviewables} : Updates an existing reviewables.
     *
     * @param reviewablesDTO the reviewablesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reviewablesDTO,
     * or with status {@code 400 (Bad Request)} if the reviewablesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reviewablesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/reviewables")
    public ResponseEntity<ReviewablesDTO> updateReviewables(@Valid @RequestBody ReviewablesDTO reviewablesDTO) throws URISyntaxException {
        log.debug("REST request to update Reviewables : {}", reviewablesDTO);
        if (reviewablesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ReviewablesDTO result = reviewablesService.save(reviewablesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, reviewablesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /reviewables} : get all the reviewables.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reviewables in body.
     */
    @GetMapping("/reviewables")
    public ResponseEntity<List<ReviewablesDTO>> getAllReviewables(Pageable pageable) {
        log.debug("REST request to get a page of Reviewables");
        Page<ReviewablesDTO> page = reviewablesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /reviewables/:id} : get the "id" reviewables.
     *
     * @param id the id of the reviewablesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reviewablesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/reviewables/{id}")
    public ResponseEntity<ReviewablesDTO> getReviewables(@PathVariable Long id) {
        log.debug("REST request to get Reviewables : {}", id);
        Optional<ReviewablesDTO> reviewablesDTO = reviewablesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(reviewablesDTO);
    }

    /**
     * {@code DELETE  /reviewables/:id} : delete the "id" reviewables.
     *
     * @param id the id of the reviewablesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/reviewables/{id}")
    public ResponseEntity<Void> deleteReviewables(@PathVariable Long id) {
        log.debug("REST request to delete Reviewables : {}", id);
        reviewablesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
