/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.PostSearchDataService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.PostSearchDataDTO;

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
 * REST controller for managing {@link com.infy.domain.PostSearchData}.
 */
@RestController
@RequestMapping("/api")
public class PostSearchDataResource {

    private final Logger log = LoggerFactory.getLogger(PostSearchDataResource.class);

    private static final String ENTITY_NAME = "postSearchData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PostSearchDataService postSearchDataService;

    public PostSearchDataResource(PostSearchDataService postSearchDataService) {
        this.postSearchDataService = postSearchDataService;
    }

    /**
     * {@code POST  /post-search-data} : Create a new postSearchData.
     *
     * @param postSearchDataDTO the postSearchDataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new postSearchDataDTO, or with status {@code 400 (Bad Request)} if the postSearchData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/post-search-data")
    public ResponseEntity<PostSearchDataDTO> createPostSearchData(@Valid @RequestBody PostSearchDataDTO postSearchDataDTO) throws URISyntaxException {
        log.debug("REST request to save PostSearchData : {}", postSearchDataDTO);
        if (postSearchDataDTO.getId() != null) {
            throw new BadRequestAlertException("A new postSearchData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PostSearchDataDTO result = postSearchDataService.save(postSearchDataDTO);
        return ResponseEntity.created(new URI("/api/post-search-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /post-search-data} : Updates an existing postSearchData.
     *
     * @param postSearchDataDTO the postSearchDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated postSearchDataDTO,
     * or with status {@code 400 (Bad Request)} if the postSearchDataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the postSearchDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/post-search-data")
    public ResponseEntity<PostSearchDataDTO> updatePostSearchData(@Valid @RequestBody PostSearchDataDTO postSearchDataDTO) throws URISyntaxException {
        log.debug("REST request to update PostSearchData : {}", postSearchDataDTO);
        if (postSearchDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PostSearchDataDTO result = postSearchDataService.save(postSearchDataDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, postSearchDataDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /post-search-data} : get all the postSearchData.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of postSearchData in body.
     */
    @GetMapping("/post-search-data")
    public ResponseEntity<List<PostSearchDataDTO>> getAllPostSearchData(Pageable pageable) {
        log.debug("REST request to get a page of PostSearchData");
        Page<PostSearchDataDTO> page = postSearchDataService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /post-search-data/:id} : get the "id" postSearchData.
     *
     * @param id the id of the postSearchDataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the postSearchDataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/post-search-data/{id}")
    public ResponseEntity<PostSearchDataDTO> getPostSearchData(@PathVariable Long id) {
        log.debug("REST request to get PostSearchData : {}", id);
        Optional<PostSearchDataDTO> postSearchDataDTO = postSearchDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(postSearchDataDTO);
    }

    /**
     * {@code DELETE  /post-search-data/:id} : delete the "id" postSearchData.
     *
     * @param id the id of the postSearchDataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/post-search-data/{id}")
    public ResponseEntity<Void> deletePostSearchData(@PathVariable Long id) {
        log.debug("REST request to delete PostSearchData : {}", id);
        postSearchDataService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
