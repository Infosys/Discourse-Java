/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.web.rest;

import com.infy.service.Oauth2UserInfosService;
import com.infy.web.rest.errors.BadRequestAlertException;
import com.infy.service.dto.Oauth2UserInfosDTO;

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
 * REST controller for managing {@link com.infy.domain.Oauth2UserInfos}.
 */
@RestController
@RequestMapping("/api")
public class Oauth2UserInfosResource {

    private final Logger log = LoggerFactory.getLogger(Oauth2UserInfosResource.class);

    private static final String ENTITY_NAME = "oauth2UserInfos";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final Oauth2UserInfosService oauth2UserInfosService;

    public Oauth2UserInfosResource(Oauth2UserInfosService oauth2UserInfosService) {
        this.oauth2UserInfosService = oauth2UserInfosService;
    }

    /**
     * {@code POST  /oauth-2-user-infos} : Create a new oauth2UserInfos.
     *
     * @param oauth2UserInfosDTO the oauth2UserInfosDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new oauth2UserInfosDTO, or with status {@code 400 (Bad Request)} if the oauth2UserInfos has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/oauth-2-user-infos")
    public ResponseEntity<Oauth2UserInfosDTO> createOauth2UserInfos(@Valid @RequestBody Oauth2UserInfosDTO oauth2UserInfosDTO) throws URISyntaxException {
        log.debug("REST request to save Oauth2UserInfos : {}", oauth2UserInfosDTO);
        if (oauth2UserInfosDTO.getId() != null) {
            throw new BadRequestAlertException("A new oauth2UserInfos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Oauth2UserInfosDTO result = oauth2UserInfosService.save(oauth2UserInfosDTO);
        return ResponseEntity.created(new URI("/api/oauth-2-user-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /oauth-2-user-infos} : Updates an existing oauth2UserInfos.
     *
     * @param oauth2UserInfosDTO the oauth2UserInfosDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated oauth2UserInfosDTO,
     * or with status {@code 400 (Bad Request)} if the oauth2UserInfosDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the oauth2UserInfosDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/oauth-2-user-infos")
    public ResponseEntity<Oauth2UserInfosDTO> updateOauth2UserInfos(@Valid @RequestBody Oauth2UserInfosDTO oauth2UserInfosDTO) throws URISyntaxException {
        log.debug("REST request to update Oauth2UserInfos : {}", oauth2UserInfosDTO);
        if (oauth2UserInfosDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Oauth2UserInfosDTO result = oauth2UserInfosService.save(oauth2UserInfosDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, oauth2UserInfosDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /oauth-2-user-infos} : get all the oauth2UserInfos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of oauth2UserInfos in body.
     */
    @GetMapping("/oauth-2-user-infos")
    public ResponseEntity<List<Oauth2UserInfosDTO>> getAllOauth2UserInfos(Pageable pageable) {
        log.debug("REST request to get a page of Oauth2UserInfos");
        Page<Oauth2UserInfosDTO> page = oauth2UserInfosService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /oauth-2-user-infos/:id} : get the "id" oauth2UserInfos.
     *
     * @param id the id of the oauth2UserInfosDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the oauth2UserInfosDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/oauth-2-user-infos/{id}")
    public ResponseEntity<Oauth2UserInfosDTO> getOauth2UserInfos(@PathVariable Long id) {
        log.debug("REST request to get Oauth2UserInfos : {}", id);
        Optional<Oauth2UserInfosDTO> oauth2UserInfosDTO = oauth2UserInfosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(oauth2UserInfosDTO);
    }

    /**
     * {@code DELETE  /oauth-2-user-infos/:id} : delete the "id" oauth2UserInfos.
     *
     * @param id the id of the oauth2UserInfosDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/oauth-2-user-infos/{id}")
    public ResponseEntity<Void> deleteOauth2UserInfos(@PathVariable Long id) {
        log.debug("REST request to delete Oauth2UserInfos : {}", id);
        oauth2UserInfosService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
