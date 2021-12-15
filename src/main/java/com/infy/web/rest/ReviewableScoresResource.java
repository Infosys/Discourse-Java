/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.ReviewableScoresService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.ReviewableScoresDTO;

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
 * REST controller for managing {@link com.infy.domain.ReviewableScores}.
 */
@RestController
@RequestMapping("/api")
public class ReviewableScoresResource {

    private final Logger log = LoggerFactory.getLogger(ReviewableScoresResource.class);

    private static final String ENTITY_NAME = "reviewableScores";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReviewableScoresService reviewableScoresService;

    public ReviewableScoresResource(ReviewableScoresService reviewableScoresService) {
        this.reviewableScoresService = reviewableScoresService;
    }

    /**
     * {@code POST  /reviewable-scores} : Create a new reviewableScores.
     *
     * @param reviewableScoresDTO the reviewableScoresDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reviewableScoresDTO, or with status {@code 400 (Bad Request)} if the reviewableScores has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/reviewable-scores")
    public ResponseEntity<ReviewableScoresDTO> createReviewableScores(@Valid @RequestBody ReviewableScoresDTO reviewableScoresDTO) throws URISyntaxException {
        log.debug("REST request to save ReviewableScores : {}", reviewableScoresDTO);
        if (reviewableScoresDTO.getId() != null) {
            throw new BadRequestAlertException("A new reviewableScores cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReviewableScoresDTO result = reviewableScoresService.save(reviewableScoresDTO);
        return ResponseEntity.created(new URI("/api/reviewable-scores/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /reviewable-scores} : Updates an existing reviewableScores.
     *
     * @param reviewableScoresDTO the reviewableScoresDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reviewableScoresDTO,
     * or with status {@code 400 (Bad Request)} if the reviewableScoresDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reviewableScoresDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/reviewable-scores")
    public ResponseEntity<ReviewableScoresDTO> updateReviewableScores(@Valid @RequestBody ReviewableScoresDTO reviewableScoresDTO) throws URISyntaxException {
        log.debug("REST request to update ReviewableScores : {}", reviewableScoresDTO);
        if (reviewableScoresDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ReviewableScoresDTO result = reviewableScoresService.save(reviewableScoresDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, reviewableScoresDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /reviewable-scores} : get all the reviewableScores.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reviewableScores in body.
     */
    @GetMapping("/reviewable-scores")
    public ResponseEntity<List<ReviewableScoresDTO>> getAllReviewableScores(Pageable pageable) {
        log.debug("REST request to get a page of ReviewableScores");
        Page<ReviewableScoresDTO> page = reviewableScoresService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /reviewable-scores/:id} : get the "id" reviewableScores.
     *
     * @param id the id of the reviewableScoresDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reviewableScoresDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/reviewable-scores/{id}")
    public ResponseEntity<ReviewableScoresDTO> getReviewableScores(@PathVariable Long id) {
        log.debug("REST request to get ReviewableScores : {}", id);
        Optional<ReviewableScoresDTO> reviewableScoresDTO = reviewableScoresService.findOne(id);
        return ResponseUtil.wrapOrNotFound(reviewableScoresDTO);
    }

    /**
     * {@code DELETE  /reviewable-scores/:id} : delete the "id" reviewableScores.
     *
     * @param id the id of the reviewableScoresDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/reviewable-scores/{id}")
    public ResponseEntity<Void> deleteReviewableScores(@PathVariable Long id) {
        log.debug("REST request to delete ReviewableScores : {}", id);
        reviewableScoresService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
