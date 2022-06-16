/*
 * Copyright (c) 2022 New Vector Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package im.vector.app.features.location.live

import im.vector.app.features.location.LocationSharingServiceConnection
import org.matrix.android.sdk.api.session.Session
import org.matrix.android.sdk.api.session.getRoom
import org.matrix.android.sdk.api.session.room.location.UpdateLiveLocationShareResult
import javax.inject.Inject

class StopLiveLocationShareUseCase @Inject constructor(
        private val locationSharingServiceConnection: LocationSharingServiceConnection,
        private val session: Session
) {

    suspend fun execute(roomId: String): UpdateLiveLocationShareResult? {
        val result = sendStoppedBeaconInfo(session, roomId)
        when (result) {
            is UpdateLiveLocationShareResult.Success -> locationSharingServiceConnection.stopLiveLocationSharing(roomId)
            else -> Unit
        }
        return result
    }

    private suspend fun sendStoppedBeaconInfo(session: Session, roomId: String): UpdateLiveLocationShareResult? {
        return session.getRoom(roomId)
                ?.locationSharingService()
                ?.stopLiveLocationShare()
    }
}
