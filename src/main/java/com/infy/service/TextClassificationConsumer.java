/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.service;

import com.infy.avroschema.TextClassificationResponse;

public interface TextClassificationConsumer {

	void consumeTextClassification(TextClassificationResponse textClassificationResponse);

}
