/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.ScreenedIpAddressesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ScreenedIpAddresses} and its DTO {@link ScreenedIpAddressesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ScreenedIpAddressesMapper extends EntityMapper<ScreenedIpAddressesDTO, ScreenedIpAddresses> {



    default ScreenedIpAddresses fromId(Long id) {
        if (id == null) {
            return null;
        }
        ScreenedIpAddresses screenedIpAddresses = new ScreenedIpAddresses();
        screenedIpAddresses.setId(id);
        return screenedIpAddresses;
    }
}
