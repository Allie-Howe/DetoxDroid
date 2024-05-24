package com.flx_apps.digitaldetox.features

import android.content.Context
import android.provider.Settings
import android.view.accessibility.AccessibilityEvent
import androidx.compose.runtime.Composable
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import com.flx_apps.digitaldetox.R
import com.flx_apps.digitaldetox.data.DataStoreProperty
import com.flx_apps.digitaldetox.feature_types.AppExceptionListType
import com.flx_apps.digitaldetox.feature_types.Feature
import com.flx_apps.digitaldetox.feature_types.FeatureTexts
import com.flx_apps.digitaldetox.feature_types.NeedsPermissionsFeature
import com.flx_apps.digitaldetox.feature_types.NeedsWriteSecureSettingsPermission
import com.flx_apps.digitaldetox.feature_types.OnAppOpenedSubscriptionFeature
import com.flx_apps.digitaldetox.feature_types.OnScreenTurnedOffSubscriptionFeature
import com.flx_apps.digitaldetox.feature_types.ScreenTimeTrackingFeature
import com.flx_apps.digitaldetox.feature_types.SupportsAppExceptionsFeature
import com.flx_apps.digitaldetox.feature_types.SupportsScheduleFeature
import com.flx_apps.digitaldetox.features.DisableAppsFeature.eventuallyIncreaseUsedUpScreenTime
import com.flx_apps.digitaldetox.ui.screens.feature.allie.AllieFeatureSettingsSection
import com.flx_apps.digitaldetox.util.AccessibilityEventUtil

val AllieFeatureId = Feature.createId(AllieFeature::class.java)

/**
 * The grayscale feature can be used to turn the screen grayscale depending on the schedule and
 * which app is currently in the foreground.
 */
object AllieFeature : Feature(), OnAppOpenedSubscriptionFeature,
    OnScreenTurnedOffSubscriptionFeature,
    SupportsScheduleFeature by SupportsScheduleFeature.Impl(AllieFeatureId),
    SupportsAppExceptionsFeature by SupportsAppExceptionsFeature.Impl(AllieFeatureId),
    ScreenTimeTrackingFeature by ScreenTimeTrackingFeature.Impl(AllieFeatureId),
    NeedsPermissionsFeature by NeedsWriteSecureSettingsPermission() {
    override val texts: FeatureTexts = FeatureTexts(
        R.string.feature_allie,
        R.string.feature_allie_subtitle,
        R.string.feature_allie_description,
    )
    override val iconRes: Int = R.drawable.ic_contrast
    override val settingsContent: @Composable () -> Unit = { AllieFeatureSettingsSection() }

    /**
     * Represents, whether the grayscale filter is currently active.
     * We use this variable in order to avoid unnecessary calls to the system settings (i.e. only
     * call the system settings when the grayscale filter should be turned on or off).
     */
    var isPinSet: Boolean = false
    var pin: String = ""

    /**
     * On start, we trigger [onAppOpened] once to turn the grayscale filter on or off depending on
     * the current app.
     */
    override fun onStart(context: Context) {}

    /**
     * On a pause, turn the grayscale filter off.
     */
    override fun onPause(context: Context) {}

    /**
     * If an app is opened, turn the grayscale filter on or off depending on the app that is
     * currently in the foreground.
     */
    override fun onAppOpened(
        context: Context, packageName: String, accessibilityEvent: AccessibilityEvent
    ) {}

    /**
     * When the screen is turned off, the used up screen time is increased by the time since the
     * last grayscale app was opened.
     * @see eventuallyIncreaseUsedUpScreenTime
     */
    override fun onScreenTurnedOff(context: Context?) {
        eventuallyIncreaseUsedUpScreenTime()
    }
}
