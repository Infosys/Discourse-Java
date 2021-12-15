/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.ReviewableClaimedTopicsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.ReviewableClaimedTopicsDTO;

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
 * REST controller for managing {@link com.infy.domain.ReviewableClaimedTopics}.
 */
@RestController
@RequestMapping("/api")
public class ReviewableClaimedTopicsResource {

    private final Logger log = LoggerFactory.getLogger(ReviewableClaimedTopicsResource.class);

    private static final String ENTITY_NAME = "reviewableClaimedTopics";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReviewableClaimedTopicsService reviewableClaimedTopicsService;

    public ReviewableClaimedTopicsResource(ReviewableClaimedTopicsService reviewableClaimedTopicsService) {
        this.reviewableClaimedTopicsService = reviewableClaimedTopicsService;
    }

    /**
     * {@code POST  /reviewable-claimed-topics} : Create a new reviewableClaimedTopics.
     *
     * @param reviewableClaimedTopicsDTO the reviewableClaimedTopicsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reviewableClaimedTopicsDTO, or with status {@code 400 (Bad Request)} if the reviewableClaimedTopics has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/reviewable-claimed-topics")
    public ResponseEntity<ReviewableClaimedTopicsDTO> createReviewableClaimedTopics(@Valid @RequestBody ReviewableClaimedTopicsDTO reviewableClaimedTopicsDTO) throws URISyntaxException {
        log.debug("REST request to save ReviewableClaimedTopics : {}", reviewableClaimedTopicsDTO);
        if (reviewableClaimedTopicsDTO.getId() != null) {
            throw new BadRequestAlertException("A new reviewableClaimedTopics cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReviewableClaimedTopicsDTO result = reviewableClaimedTopicsService.save(reviewableClaimedTopicsDTO);
        return ResponseEntity.created(new URI("/api/reviewable-claimed-topics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /reviewable-claimed-topics} : Updates an existing reviewableClaimedTopics.
     *
     * @param reviewableClaimedTopicsDTO the reviewableClaimedTopicsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reviewableClaimedTopicsDTO,
     * or with status {@code 400 (Bad Request)} if the reviewableClaimedTopicsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reviewableClaimedTopicsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/reviewable-claimed-topics")
    public ResponseEntity<ReviewableClaimedTopicsDTO> updateReviewableClaimedTopics(@Valid @RequestBody ReviewableClaimedTopicsDTO reviewableClaimedTopicsDTO) throws URISyntaxException {
        log.debug("REST request to update ReviewableClaimedTopics : {}", reviewableClaimedTopicsDTO);
        if (reviewableClaimedTopicsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ReviewableClaimedTopicsDTO result = reviewableClaimedTopicsService.save(reviewableClaimedTopicsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, reviewableClaimedTopicsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /reviewable-claimed-topics} : get all the reviewableClaimedTopics.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reviewableClaimedTopics in body.
     */
    @GetMapping("/reviewable-claimed-topics")
    public ResponseEntity<List<ReviewableClaimedTopicsDTO>> getAllReviewableClaimedTopics(Pageable pageable) {
        log.debug("REST request to get a page of ReviewableClaimedTopics");
        Page<ReviewableClaimedTopicsDTO> page = reviewableClaimedTopicsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /reviewable-claimed-topics/:id} : get the "id" reviewableClaimedTopics.
     *
     * @param id the id of the reviewableClaimedTopicsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reviewableClaimedTopicsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/reviewable-claimed-topics/{id}")
    public ResponseEntity<ReviewableClaimedTopicsDTO> getReviewableClaimedTopics(@PathVariable Long id) {
        log.debug("REST request to get ReviewableClaimedTopics : {}", id);
        Optional<ReviewableClaimedTopicsDTO> reviewableClaimedTopicsDTO = reviewableClaimedTopicsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(reviewableClaimedTopicsDTO);
    }

    /**
     * {@code DELETE  /reviewable-claimed-topics/:id} : delete the "id" reviewableClaimedTopics.
     *
     * @param id the id of the reviewableClaimedTopicsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/reviewable-claimed-topics/{id}")
    public ResponseEntity<Void> deleteReviewableClaimedTopics(@PathVariable Long id) {
        log.debug("REST request to delete ReviewableClaimedTopics : {}", id);
        reviewableClaimedTopicsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
