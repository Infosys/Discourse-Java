/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.QuotedPostsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.QuotedPostsDTO;

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
 * REST controller for managing {@link com.infy.domain.QuotedPosts}.
 */
@RestController
@RequestMapping("/api")
public class QuotedPostsResource {

    private final Logger log = LoggerFactory.getLogger(QuotedPostsResource.class);

    private static final String ENTITY_NAME = "quotedPosts";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QuotedPostsService quotedPostsService;

    public QuotedPostsResource(QuotedPostsService quotedPostsService) {
        this.quotedPostsService = quotedPostsService;
    }

    /**
     * {@code POST  /quoted-posts} : Create a new quotedPosts.
     *
     * @param quotedPostsDTO the quotedPostsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new quotedPostsDTO, or with status {@code 400 (Bad Request)} if the quotedPosts has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/quoted-posts")
    public ResponseEntity<QuotedPostsDTO> createQuotedPosts(@Valid @RequestBody QuotedPostsDTO quotedPostsDTO) throws URISyntaxException {
        log.debug("REST request to save QuotedPosts : {}", quotedPostsDTO);
        if (quotedPostsDTO.getId() != null) {
            throw new BadRequestAlertException("A new quotedPosts cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QuotedPostsDTO result = quotedPostsService.save(quotedPostsDTO);
        return ResponseEntity.created(new URI("/api/quoted-posts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /quoted-posts} : Updates an existing quotedPosts.
     *
     * @param quotedPostsDTO the quotedPostsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated quotedPostsDTO,
     * or with status {@code 400 (Bad Request)} if the quotedPostsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the quotedPostsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/quoted-posts")
    public ResponseEntity<QuotedPostsDTO> updateQuotedPosts(@Valid @RequestBody QuotedPostsDTO quotedPostsDTO) throws URISyntaxException {
        log.debug("REST request to update QuotedPosts : {}", quotedPostsDTO);
        if (quotedPostsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        QuotedPostsDTO result = quotedPostsService.save(quotedPostsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, quotedPostsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /quoted-posts} : get all the quotedPosts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of quotedPosts in body.
     */
    @GetMapping("/quoted-posts")
    public ResponseEntity<List<QuotedPostsDTO>> getAllQuotedPosts(Pageable pageable) {
        log.debug("REST request to get a page of QuotedPosts");
        Page<QuotedPostsDTO> page = quotedPostsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /quoted-posts/:id} : get the "id" quotedPosts.
     *
     * @param id the id of the quotedPostsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the quotedPostsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/quoted-posts/{id}")
    public ResponseEntity<QuotedPostsDTO> getQuotedPosts(@PathVariable Long id) {
        log.debug("REST request to get QuotedPosts : {}", id);
        Optional<QuotedPostsDTO> quotedPostsDTO = quotedPostsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(quotedPostsDTO);
    }

    /**
     * {@code DELETE  /quoted-posts/:id} : delete the "id" quotedPosts.
     *
     * @param id the id of the quotedPostsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/quoted-posts/{id}")
    public ResponseEntity<Void> deleteQuotedPosts(@PathVariable Long id) {
        log.debug("REST request to delete QuotedPosts : {}", id);
        quotedPostsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
