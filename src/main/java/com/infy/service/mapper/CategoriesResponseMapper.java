/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service.mapper;

import org.mapstruct.Mapper;

import com.infy.domain.Categories;
import com.infy.service.model.CategoryResponse;

@Mapper(componentModel = "spring", uses = {})
public interface CategoriesResponseMapper extends EntityMapper<CategoryResponse, Categories>{


	 default Categories fromId(Long id) {
	        if (id == null) {
	            return null;
	        }
	        Categories categories = new Categories();
	        categories.setId(id);
	        return categories;
	    }

}
