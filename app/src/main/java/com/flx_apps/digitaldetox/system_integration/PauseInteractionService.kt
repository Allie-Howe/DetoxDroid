package com.flx_apps.digitaldetox.system_integration

import android.content.Context
import android.os.Bundle
import android.service.voice.VoiceInteractionSession
import android.service.voice.VoiceInteractionSessionService
import com.flx_apps.digitaldetox.features.PauseButtonFeature
import timber.log.Timber

/**
 * The [PauseInteractionService] is used to launch pauses from the default digital
 * assistant shortcut (typically long-pressing the home button).
 */
class PauseInteractionService : VoiceInteractionSessionService() {
    override fun onNewSession(args: Bundle?): VoiceInteractionSession {
        return PauseInteractionSession(this)
    }
}

/**
 * A [VoiceInteractionSession] that just launches a pause.
 */
class PauseInteractionSession(context: Context) : VoiceInteractionSession(context) {
    override fun onHandleAssist(state: AssistState) {
        Timber.d("onHandleAssist")
        PauseButtonFeature.togglePause(context)
        super.onHandleAssist(state)
        hide()
    }
}