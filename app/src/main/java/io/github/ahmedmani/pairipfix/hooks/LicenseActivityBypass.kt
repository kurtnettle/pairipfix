package io.github.ahmedmani.pairipfix.hooks

import com.highcapable.yukihookapi.hook.log.YLog
import io.github.ahmedmani.pairipfix.BaseHook

class LicenseActivityBypass : BaseHook() {

    private val LICENSE_ACTIVITY = "com.pairip.licensecheck.LicenseActivity"

    override fun getName(): String = "LicenseActivityBypass"

    override fun onHook() {
        var success = false

        if (!classExists(LICENSE_ACTIVITY)) {
            YLog.error("[LicenseActivity] not found")
        }

        success = hookDoNothing(LICENSE_ACTIVITY, "closeApp")
        YLog.debug("[LicenseActivity] closeApp hook result: $success")

        success = hookDoNothing(LICENSE_ACTIVITY, "exitApp")
        YLog.debug("[LicenseActivity] exitApp hook result: $success")

        success = hookDoNothing(LICENSE_ACTIVITY, "showErrorDialog")
        YLog.debug("[LicenseActivity] showErrorDialog hook result: $success")

        success = hookDoNothing(LICENSE_ACTIVITY, "showPaywallAndCloseApp")
        YLog.debug("[LicenseActivity] showPaywallAndCloseApp hook result: $success")

        success = hookDoNothing(LICENSE_ACTIVITY, "logAndShowErrorDialog", String::class.java)
        YLog.debug("[LicenseActivity] logAndShowErrorDialog hook result: $success")

        success = hookDoNothing(
            LICENSE_ACTIVITY, "logAndShowErrorDialog", String::class.java, Exception::class.java
        )
        YLog.debug("[LicenseActivity] logAndShowErrorDialog hook result: $success")

//        return success
    }
}