/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.repository;

import com.infy.domain.Announcement;
import com.infy.domain.enumeration.AnnouncementStatus;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Announcement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnnouncmentRepository extends JpaRepository<Announcement, Long> {

	Page<Announcement> findByStatus(AnnouncementStatus status,Pageable pageable);

	Optional<Announcement> findByIdAndStatus(Long id,AnnouncementStatus status);


}
