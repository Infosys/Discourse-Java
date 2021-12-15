/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.ReviewableHistoriesService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.ReviewableHistoriesDTO;

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
 * REST controller for managing {@link com.infy.domain.ReviewableHistories}.
 */
@RestController
@RequestMapping("/api")
public class ReviewableHistoriesResource {

    private final Logger log = LoggerFactory.getLogger(ReviewableHistoriesResource.class);

    private static final String ENTITY_NAME = "reviewableHistories";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReviewableHistoriesService reviewableHistoriesService;

    public ReviewableHistoriesResource(ReviewableHistoriesService reviewableHistoriesService) {
        this.reviewableHistoriesService = reviewableHistoriesService;
    }

    /**
     * {@code POST  /reviewable-histories} : Create a new reviewableHistories.
     *
     * @param reviewableHistoriesDTO the reviewableHistoriesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reviewableHistoriesDTO, or with status {@code 400 (Bad Request)} if the reviewableHistories has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/reviewable-histories")
    public ResponseEntity<ReviewableHistoriesDTO> createReviewableHistories(@Valid @RequestBody ReviewableHistoriesDTO reviewableHistoriesDTO) throws URISyntaxException {
        log.debug("REST request to save ReviewableHistories : {}", reviewableHistoriesDTO);
        if (reviewableHistoriesDTO.getId() != null) {
            throw new BadRequestAlertException("A new reviewableHistories cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReviewableHistoriesDTO result = reviewableHistoriesService.save(reviewableHistoriesDTO);
        return ResponseEntity.created(new URI("/api/reviewable-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /reviewable-histories} : Updates an existing reviewableHistories.
     *
     * @param reviewableHistoriesDTO the reviewableHistoriesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reviewableHistoriesDTO,
     * or with status {@code 400 (Bad Request)} if the reviewableHistoriesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reviewableHistoriesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/reviewable-histories")
    public ResponseEntity<ReviewableHistoriesDTO> updateReviewableHistories(@Valid @RequestBody ReviewableHistoriesDTO reviewableHistoriesDTO) throws URISyntaxException {
        log.debug("REST request to update ReviewableHistories : {}", reviewableHistoriesDTO);
        if (reviewableHistoriesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ReviewableHistoriesDTO result = reviewableHistoriesService.save(reviewableHistoriesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, reviewableHistoriesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /reviewable-histories} : get all the reviewableHistories.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reviewableHistories in body.
     */
    @GetMapping("/reviewable-histories")
    public ResponseEntity<List<ReviewableHistoriesDTO>> getAllReviewableHistories(Pageable pageable) {
        log.debug("REST request to get a page of ReviewableHistories");
        Page<ReviewableHistoriesDTO> page = reviewableHistoriesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /reviewable-histories/:id} : get the "id" reviewableHistories.
     *
     * @param id the id of the reviewableHistoriesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reviewableHistoriesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/reviewable-histories/{id}")
    public ResponseEntity<ReviewableHistoriesDTO> getReviewableHistories(@PathVariable Long id) {
        log.debug("REST request to get ReviewableHistories : {}", id);
        Optional<ReviewableHistoriesDTO> reviewableHistoriesDTO = reviewableHistoriesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(reviewableHistoriesDTO);
    }

    /**
     * {@code DELETE  /reviewable-histories/:id} : delete the "id" reviewableHistories.
     *
     * @param id the id of the reviewableHistoriesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/reviewable-histories/{id}")
    public ResponseEntity<Void> deleteReviewableHistories(@PathVariable Long id) {
        log.debug("REST request to delete ReviewableHistories : {}", id);
        reviewableHistoriesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
