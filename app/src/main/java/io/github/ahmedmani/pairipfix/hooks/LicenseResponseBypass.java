package io.github.ahmedmani.pairipfix.hooks;

import android.os.Bundle;

import java.security.PublicKey;

import de.robv.android.xposed.XposedBridge;
import io.github.ahmedmani.pairipfix.BaseHook;

public class LicenseResponseBypass extends BaseHook {
    String LICENSE_RESPONSE_HELPER = "com.pairip.licensecheck.LicenseResponseHelper";
    String OLD_RESPONSE_VALIDATOR = "com.pairip.licensecheck.ResponseValidator";
    String RESPONSE_VALIDATOR_V3 = "com.pairip.licensecheck3.ResponseValidator";

    @Override
    public String getName() {
        return "LicenseResponseBypass";
    }

    @Override
    public boolean apply() {
        boolean success = false;

        if (!classExists(LICENSE_RESPONSE_HELPER)) {
            XposedBridge.log("[PairIPFix] [LicenseResponseHelper] not found");
        }

            success |= hookDoNothing(LICENSE_RESPONSE_HELPER, "validateResponse", Bundle.class, String.class);
            XposedBridge.log("[PairIPFix] [LicenseResponseHelper] validateResponse hook result: " + success);
            success |= hookReturnConstant(LICENSE_RESPONSE_HELPER, "getRepeatedCheckMetadata", null, Bundle.class);
            XposedBridge.log("[PairIPFix] [LicenseResponseHelper] getRepeatedCheckMetadata hook result: " + success);
            success |= hookDoNothing(LICENSE_RESPONSE_HELPER, "verifySignature", String.class, String.class, String.class, PublicKey.class);
            XposedBridge.log("[PairIPFix] [LicenseResponseHelper] verifySignature hook result: " + success);

        if (classExists(OLD_RESPONSE_VALIDATOR)) {
            success |= hookDoNothing(OLD_RESPONSE_VALIDATOR, "validateResponse", Bundle.class, String.class);
            XposedBridge.log("[PairIPFix] [ResponseValidator_Old] validateResponse hook result: " + success);
            success |= hookDoNothing(OLD_RESPONSE_VALIDATOR, "verifySignature", String.class, String.class, String.class, PublicKey.class);
            XposedBridge.log("[PairIPFix] [ResponseValidator_Old] verifySignature hook result: " + success);
        }

        if (classExists(RESPONSE_VALIDATOR_V3)) {
            success |= hookDoNothing(RESPONSE_VALIDATOR_V3, "validateResponse", Bundle.class, String.class);
            XposedBridge.log("[PairIPFix] [ResponseValidator_v3] validateResponse hook result: " + success);
        }

        return success;
    }
}