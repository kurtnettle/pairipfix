package io.github.ahmedmani.pairipfix.hooks

import android.os.Bundle
import com.highcapable.yukihookapi.hook.log.YLog
import io.github.ahmedmani.pairipfix.BaseHook
import java.security.PublicKey

class LicenseResponseBypass : BaseHook() {

    private val LICENSE_RESPONSE_HELPER = "com.pairip.licensecheck.LicenseResponseHelper"
    private val OLD_RESPONSE_VALIDATOR = "com.pairip.licensecheck.ResponseValidator"
    private val RESPONSE_VALIDATOR_V3 = "com.pairip.licensecheck3.ResponseValidator"

    override fun getName(): String = "LicenseResponseBypass"

    override fun onHook() {
        var success = false

        if (!classExists(LICENSE_RESPONSE_HELPER)) {
            YLog.error("[LicenseResponseHelper] not found")
        }

        success = hookDoNothing(
            LICENSE_RESPONSE_HELPER, "validateResponse", Bundle::class.java, String::class.java
        )
        YLog.debug("[LicenseResponseHelper] validateResponse hook result: $success")

        success = hookReturnConstant(
            LICENSE_RESPONSE_HELPER, "getRepeatedCheckMetadata", null, Bundle::class.java
        )
        YLog.debug("[LicenseResponseHelper] getRepeatedCheckMetadata hook result: $success")

        success = hookDoNothing(
            LICENSE_RESPONSE_HELPER,
            "verifySignature",
            String::class.java,
            String::class.java,
            String::class.java,
            PublicKey::class.java
        )
        YLog.debug("[LicenseResponseHelper] verifySignature hook result: $success")

        if (classExists(OLD_RESPONSE_VALIDATOR)) {
            success = hookDoNothing(
                OLD_RESPONSE_VALIDATOR, "validateResponse", Bundle::class.java, String::class.java
            )
            YLog.debug("[ResponseValidator_Old] validateResponse hook result: $success")

            success = hookDoNothing(
                OLD_RESPONSE_VALIDATOR,
                "verifySignature",
                String::class.java,
                String::class.java,
                String::class.java,
                PublicKey::class.java
            )
            YLog.debug("[ResponseValidator_Old] verifySignature hook result: $success")
        }

        if (classExists(RESPONSE_VALIDATOR_V3)) {
            success = hookDoNothing(
                RESPONSE_VALIDATOR_V3, "validateResponse", Bundle::class.java, String::class.java
            )
            YLog.debug("[ResponseValidator_v3] validateResponse hook result: $success")
        }
    }
}