/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.PostCustomFieldsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.PostCustomFieldsDTO;

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
 * REST controller for managing {@link com.infy.domain.PostCustomFields}.
 */
@RestController
@RequestMapping("/api")
public class PostCustomFieldsResource {

    private final Logger log = LoggerFactory.getLogger(PostCustomFieldsResource.class);

    private static final String ENTITY_NAME = "postCustomFields";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PostCustomFieldsService postCustomFieldsService;

    public PostCustomFieldsResource(PostCustomFieldsService postCustomFieldsService) {
        this.postCustomFieldsService = postCustomFieldsService;
    }

    /**
     * {@code POST  /post-custom-fields} : Create a new postCustomFields.
     *
     * @param postCustomFieldsDTO the postCustomFieldsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new postCustomFieldsDTO, or with status {@code 400 (Bad Request)} if the postCustomFields has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/post-custom-fields")
    public ResponseEntity<PostCustomFieldsDTO> createPostCustomFields(@Valid @RequestBody PostCustomFieldsDTO postCustomFieldsDTO) throws URISyntaxException {
        log.debug("REST request to save PostCustomFields : {}", postCustomFieldsDTO);
        if (postCustomFieldsDTO.getId() != null) {
            throw new BadRequestAlertException("A new postCustomFields cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PostCustomFieldsDTO result = postCustomFieldsService.save(postCustomFieldsDTO);
        return ResponseEntity.created(new URI("/api/post-custom-fields/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /post-custom-fields} : Updates an existing postCustomFields.
     *
     * @param postCustomFieldsDTO the postCustomFieldsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated postCustomFieldsDTO,
     * or with status {@code 400 (Bad Request)} if the postCustomFieldsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the postCustomFieldsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/post-custom-fields")
    public ResponseEntity<PostCustomFieldsDTO> updatePostCustomFields(@Valid @RequestBody PostCustomFieldsDTO postCustomFieldsDTO) throws URISyntaxException {
        log.debug("REST request to update PostCustomFields : {}", postCustomFieldsDTO);
        if (postCustomFieldsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PostCustomFieldsDTO result = postCustomFieldsService.save(postCustomFieldsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, postCustomFieldsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /post-custom-fields} : get all the postCustomFields.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of postCustomFields in body.
     */
    @GetMapping("/post-custom-fields")
    public ResponseEntity<List<PostCustomFieldsDTO>> getAllPostCustomFields(Pageable pageable) {
        log.debug("REST request to get a page of PostCustomFields");
        Page<PostCustomFieldsDTO> page = postCustomFieldsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /post-custom-fields/:id} : get the "id" postCustomFields.
     *
     * @param id the id of the postCustomFieldsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the postCustomFieldsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/post-custom-fields/{id}")
    public ResponseEntity<PostCustomFieldsDTO> getPostCustomFields(@PathVariable Long id) {
        log.debug("REST request to get PostCustomFields : {}", id);
        Optional<PostCustomFieldsDTO> postCustomFieldsDTO = postCustomFieldsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(postCustomFieldsDTO);
    }

    /**
     * {@code DELETE  /post-custom-fields/:id} : delete the "id" postCustomFields.
     *
     * @param id the id of the postCustomFieldsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/post-custom-fields/{id}")
    public ResponseEntity<Void> deletePostCustomFields(@PathVariable Long id) {
        log.debug("REST request to delete PostCustomFields : {}", id);
        postCustomFieldsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
