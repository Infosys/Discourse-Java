/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.SchedulerStatsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SchedulerStats} and its DTO {@link SchedulerStatsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SchedulerStatsMapper extends EntityMapper<SchedulerStatsDTO, SchedulerStats> {



    default SchedulerStats fromId(Long id) {
        if (id == null) {
            return null;
        }
        SchedulerStats schedulerStats = new SchedulerStats();
        schedulerStats.setId(id);
        return schedulerStats;
    }
}
