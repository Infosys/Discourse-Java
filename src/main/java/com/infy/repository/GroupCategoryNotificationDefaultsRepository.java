/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.repository;

import com.infy.domain.GroupCategoryNotificationDefaults;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the GroupCategoryNotificationDefaults entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GroupCategoryNotificationDefaultsRepository extends JpaRepository<GroupCategoryNotificationDefaults, Long> {
}
