/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.UserUploadsService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.UserUploadsDTO;

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
 * REST controller for managing {@link com.infy.domain.UserUploads}.
 */
@RestController
@RequestMapping("/api")
public class UserUploadsResource {

    private final Logger log = LoggerFactory.getLogger(UserUploadsResource.class);

    private static final String ENTITY_NAME = "userUploads";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserUploadsService userUploadsService;

    public UserUploadsResource(UserUploadsService userUploadsService) {
        this.userUploadsService = userUploadsService;
    }

    /**
     * {@code POST  /user-uploads} : Create a new userUploads.
     *
     * @param userUploadsDTO the userUploadsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userUploadsDTO, or with status {@code 400 (Bad Request)} if the userUploads has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-uploads")
    public ResponseEntity<UserUploadsDTO> createUserUploads(@Valid @RequestBody UserUploadsDTO userUploadsDTO) throws URISyntaxException {
        log.debug("REST request to save UserUploads : {}", userUploadsDTO);
        if (userUploadsDTO.getId() != null) {
            throw new BadRequestAlertException("A new userUploads cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserUploadsDTO result = userUploadsService.save(userUploadsDTO);
        return ResponseEntity.created(new URI("/api/user-uploads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-uploads} : Updates an existing userUploads.
     *
     * @param userUploadsDTO the userUploadsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userUploadsDTO,
     * or with status {@code 400 (Bad Request)} if the userUploadsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userUploadsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-uploads")
    public ResponseEntity<UserUploadsDTO> updateUserUploads(@Valid @RequestBody UserUploadsDTO userUploadsDTO) throws URISyntaxException {
        log.debug("REST request to update UserUploads : {}", userUploadsDTO);
        if (userUploadsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserUploadsDTO result = userUploadsService.save(userUploadsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userUploadsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-uploads} : get all the userUploads.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userUploads in body.
     */
    @GetMapping("/user-uploads")
    public ResponseEntity<List<UserUploadsDTO>> getAllUserUploads(Pageable pageable) {
        log.debug("REST request to get a page of UserUploads");
        Page<UserUploadsDTO> page = userUploadsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-uploads/:id} : get the "id" userUploads.
     *
     * @param id the id of the userUploadsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userUploadsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-uploads/{id}")
    public ResponseEntity<UserUploadsDTO> getUserUploads(@PathVariable Long id) {
        log.debug("REST request to get UserUploads : {}", id);
        Optional<UserUploadsDTO> userUploadsDTO = userUploadsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userUploadsDTO);
    }

    /**
     * {@code DELETE  /user-uploads/:id} : delete the "id" userUploads.
     *
     * @param id the id of the userUploadsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-uploads/{id}")
    public ResponseEntity<Void> deleteUserUploads(@PathVariable Long id) {
        log.debug("REST request to delete UserUploads : {}", id);
        userUploadsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
