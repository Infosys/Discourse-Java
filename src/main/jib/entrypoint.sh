#!/bin/sh

#
# Copyright 2021 Infosys Ltd.
# Use of this source code is governed by GNU General Public License version 2
# that can be found in the LICENSE file or at
# https://opensource.org/licenses/GPL-2.0
#

echo "The application will start in ${JHIPSTER_SLEEP}s..." && sleep ${JHIPSTER_SLEEP}
exec java ${JAVA_OPTS} -noverify -XX:+AlwaysPreTouch -Djava.security.egd=file:/dev/./urandom -cp /app/resources/:/app/classes/:/app/libs/* "com.infy.DiscourseApp"  "$@"
