/*
 * Copyright 2021 Infosys Ltd.
 * Use of this source code is governed by GNU General Public License version 2
 * that can be found in the LICENSE file or at
 * https://opensource.org/licenses/GPL-2.0
 */

package com.infy.domain.enumeration;

public enum PostStatus {
	ACTIVE(1), INACTIVE(2);

	private final int value;

	private PostStatus(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
