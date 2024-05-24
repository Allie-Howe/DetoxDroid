package com.flx_apps.digitaldetox.ui.screens.feature.allie

import androidx.lifecycle.ViewModel
import com.flx_apps.digitaldetox.features.GrayscaleAppsFeature
import com.flx_apps.digitaldetox.features.AllieFeature
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.minutes

/**
 * The view model for the grayscale apps feature settings tile.
 */
@HiltViewModel
class AllieFeatureSettingsViewModel @Inject constructor() : ViewModel() {
    /**
     * Whether the extra dim feature is activated.
     * @see GrayscaleAppsFeature.extraDim
     */
    private var _extraDimActivated: MutableStateFlow<Boolean> =
        MutableStateFlow(GrayscaleAppsFeature.extraDim)
    val extraDimActivated: StateFlow<Boolean> = _extraDimActivated

    /**
     * Whether the grayscale filter should be ignored when the current app is not in full screen mode.
     * @see GrayscaleAppsFeature.ignoreNonFullScreenApps
     */
    private var _ignoreNonFullScreenApps: MutableStateFlow<Boolean> =
        MutableStateFlow(GrayscaleAppsFeature.ignoreNonFullScreenApps)
    val ignoreNonFullScreenApps: StateFlow<Boolean> = _ignoreNonFullScreenApps

    /**
     * The allowed daily color screen time *in minutes*.
     * @see GrayscaleAppsFeature.allowedDailyColorScreenTime
     */
    private var _allowedDailyColorScreenTime: MutableStateFlow<Long> =
        MutableStateFlow(GrayscaleAppsFeature.allowedDailyColorScreenTime.milliseconds.inWholeMinutes)
    val allowedDailyColorScreenTime: StateFlow<Long> = _allowedDailyColorScreenTime

    /**
     * The user's PIN
     * @see GrayscaleAppsFeature.allowedDailyColorScreenTime
     */
    private var _pin: MutableStateFlow<String> =
        MutableStateFlow(AllieFeature.pin)
    val pin: StateFlow<String> = _pin

    /**
     * Whether the dialog to set the allowed daily color screen time should be shown.
     */
    private var _showAllowedDailyColorScreenTimeDialog: MutableStateFlow<Boolean> =
        MutableStateFlow(false)
    val showAllowedDailyColorScreenTimeDialog: StateFlow<Boolean> =
        _showAllowedDailyColorScreenTimeDialog

    /**
     * Toggles the extra dim setting.
     */
    fun toggleExtraDim() {
        GrayscaleAppsFeature.extraDim = !GrayscaleAppsFeature.extraDim
        _extraDimActivated.value = GrayscaleAppsFeature.extraDim
    }

    /**
     * Toggles the ignore non full screen apps setting.
     */
    fun toggleIgnoreNonFullScreenApps() {
        GrayscaleAppsFeature.ignoreNonFullScreenApps = !GrayscaleAppsFeature.ignoreNonFullScreenApps
        _ignoreNonFullScreenApps.value = GrayscaleAppsFeature.ignoreNonFullScreenApps
    }

    /**
     * Sets whether the dialog to set the allowed daily color screen time should be shown.
     * @return whether the dialog can be shown (i.e. whether the user has given the permission to
     * access usage stats)
     */
    fun setShowAllowedDailyColorScreenTimeDialog(show: Boolean): Boolean {
        _showAllowedDailyColorScreenTimeDialog.value = show
        return true
    }

    fun setPinValue(pin: String): Boolean {
        _pin.value = pin
        return true

    }

    /**
     * Sets the allowed daily color screen time in minutes.
     */
    fun setAllowedDailyColorScreenTime(minutes: Long) {
        GrayscaleAppsFeature.allowedDailyColorScreenTime =
            minutes.minutes.inWholeMilliseconds // convert to milliseconds
        _allowedDailyColorScreenTime.value = minutes
    }
}