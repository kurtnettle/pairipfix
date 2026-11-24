package io.github.ahmedmani.pairipfix.hooks

import android.os.Bundle
import com.highcapable.yukihookapi.hook.log.YLog
import io.github.ahmedmani.pairipfix.BaseHook

class LicenseClientBypass : BaseHook() {
    private val LICENSE_CLIENT = "com.pairip.licensecheck.LicenseClient"
    private val LICENSE_CLIENT_V3 = "com.pairip.licensecheck3.LicenseClientV3"
    private val LICENSE_CHECK_STATE = $$"com.pairip.licensecheck.LicenseClient$LicenseCheckState"

    override fun getName(): String = "LicenseClientBypass"

    override fun onHook() {
        var status = false

        if (!classExists(LICENSE_CLIENT)) {
            YLog.error("[LicenseClient] not found")
        }

        status = hookDoNothing(LICENSE_CLIENT, "checkLicense")
        YLog.debug("[LicenseClient] checkLicense hook result: $status")

        status = hookDoNothing(LICENSE_CLIENT, "initializeLicenseCheck")
        YLog.debug("[LicenseClient] initializeLicenseCheck hook result: $status")

        status = hookReturnConstant(LICENSE_CLIENT, "performLocalInstallerCheck", true)
        YLog.debug("[LicenseClient] performLocalInstallerCheck hook result: $status")

        val stateClass = findClass(LICENSE_CHECK_STATE)
        if (stateClass != null) {
            val fullCheckOk =
                stateClass.getDeclaredField("FULL_CHECK_OK").apply { isAccessible = true }.get(null)
            status = setStaticField(LICENSE_CLIENT, "licenseCheckState", fullCheckOk)
            YLog.debug("[LicenseClient] licenseCheckState hook result: $status")
        } else {
            YLog.error("[LicenseClient] stateClass not found")
        }

        if (classExists(LICENSE_CLIENT_V3)) {
            status = hookDoNothing(
                LICENSE_CLIENT_V3,
                "processResponse",
                Int::class.javaPrimitiveType!!,
                Bundle::class.java
            )
            YLog.debug("[LicenseClient_v3] processResponse hook result: $status")
        } else {
            YLog.error("[LicenseClient_v3] not found")
        }
    }
}