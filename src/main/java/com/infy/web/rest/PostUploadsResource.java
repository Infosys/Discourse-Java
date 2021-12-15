/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.PostUploadsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.PostUploadsDTO;

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
 * REST controller for managing {@link com.infy.domain.PostUploads}.
 */
@RestController
@RequestMapping("/api")
public class PostUploadsResource {

    private final Logger log = LoggerFactory.getLogger(PostUploadsResource.class);

    private static final String ENTITY_NAME = "postUploads";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PostUploadsService postUploadsService;

    public PostUploadsResource(PostUploadsService postUploadsService) {
        this.postUploadsService = postUploadsService;
    }

    /**
     * {@code POST  /post-uploads} : Create a new postUploads.
     *
     * @param postUploadsDTO the postUploadsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new postUploadsDTO, or with status {@code 400 (Bad Request)} if the postUploads has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/post-uploads")
    public ResponseEntity<PostUploadsDTO> createPostUploads(@Valid @RequestBody PostUploadsDTO postUploadsDTO) throws URISyntaxException {
        log.debug("REST request to save PostUploads : {}", postUploadsDTO);
        if (postUploadsDTO.getId() != null) {
            throw new BadRequestAlertException("A new postUploads cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PostUploadsDTO result = postUploadsService.save(postUploadsDTO);
        return ResponseEntity.created(new URI("/api/post-uploads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /post-uploads} : Updates an existing postUploads.
     *
     * @param postUploadsDTO the postUploadsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated postUploadsDTO,
     * or with status {@code 400 (Bad Request)} if the postUploadsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the postUploadsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/post-uploads")
    public ResponseEntity<PostUploadsDTO> updatePostUploads(@Valid @RequestBody PostUploadsDTO postUploadsDTO) throws URISyntaxException {
        log.debug("REST request to update PostUploads : {}", postUploadsDTO);
        if (postUploadsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PostUploadsDTO result = postUploadsService.save(postUploadsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, postUploadsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /post-uploads} : get all the postUploads.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of postUploads in body.
     */
    @GetMapping("/post-uploads")
    public ResponseEntity<List<PostUploadsDTO>> getAllPostUploads(Pageable pageable) {
        log.debug("REST request to get a page of PostUploads");
        Page<PostUploadsDTO> page = postUploadsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /post-uploads/:id} : get the "id" postUploads.
     *
     * @param id the id of the postUploadsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the postUploadsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/post-uploads/{id}")
    public ResponseEntity<PostUploadsDTO> getPostUploads(@PathVariable Long id) {
        log.debug("REST request to get PostUploads : {}", id);
        Optional<PostUploadsDTO> postUploadsDTO = postUploadsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(postUploadsDTO);
    }

    /**
     * {@code DELETE  /post-uploads/:id} : delete the "id" postUploads.
     *
     * @param id the id of the postUploadsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/post-uploads/{id}")
    public ResponseEntity<Void> deletePostUploads(@PathVariable Long id) {
        log.debug("REST request to delete PostUploads : {}", id);
        postUploadsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
