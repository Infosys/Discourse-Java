/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;


import com.infy.domain.*;
import com.infy.service.dto.WatchedWordsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link WatchedWords} and its DTO {@link WatchedWordsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WatchedWordsMapper extends EntityMapper<WatchedWordsDTO, WatchedWords> {



    default WatchedWords fromId(Long id) {
        if (id == null) {
            return null;
        }
        WatchedWords watchedWords = new WatchedWords();
        watchedWords.setId(id);
        return watchedWords;
    }
}
