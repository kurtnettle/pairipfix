package io.github.ahmedmani.pairipfix.hooks;

import android.os.Bundle;

import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import io.github.ahmedmani.pairipfix.BaseHook;

public class LicenseClientBypass extends BaseHook {
    String LICENSE_CLIENT = "com.pairip.licensecheck.LicenseClient";
    String LICENSE_CLIENT_V3 = "com.pairip.licensecheck3.LicenseClientV3";
    String LICENSE_CHECK_STATE = "com.pairip.licensecheck.LicenseClient$LicenseCheckState";

    @Override
    public String getName() {
        return "LicenseClientBypass";
    }

    @Override
    public boolean apply() {
        boolean status = false;

        if (!classExists(LICENSE_CLIENT)) {
            XposedBridge.log("[PairIPFix] [LicenseClient] not found");
        }

            status |= hookDoNothing(LICENSE_CLIENT, "checkLicense");
            XposedBridge.log("[PairIPFix] [LicenseClient] checkLicense hook result: " + status);
            status |= hookDoNothing(LICENSE_CLIENT, "initializeLicenseCheck");
            XposedBridge.log("[PairIPFix] [LicenseClient] initializeLicenseCheck hook result: " + status);
            status |= hookReturnConstant(LICENSE_CLIENT, "performLocalInstallerCheck", true);
            XposedBridge.log("[PairIPFix] [LicenseClient] performLocalInstallerCheck hook result: " + status);

        Class<?> stateClass = findClass(LICENSE_CHECK_STATE);
        if (stateClass != null) {
            Object fullCheckOk = XposedHelpers.getStaticObjectField(stateClass, "FULL_CHECK_OK");
            status |= setStaticField(LICENSE_CLIENT, "licenseCheckState", fullCheckOk);
            XposedBridge.log("[PairIPFix] [LicenseClient] licenseCheckState hook result: " + status);
        } else {
            XposedBridge.log("[PairIPFix] [LicenseClient] stateClass not found");
        }

        if (classExists(LICENSE_CLIENT_V3)) {
            status |= hookDoNothing(LICENSE_CLIENT_V3, "processResponse", int.class, Bundle.class);
            XposedBridge.log("[PairIPFix] [LicenseClient_v3] processResponse hook result: " + status);
        } else {
            XposedBridge.log("[PairIPFix] [LicenseClient_v3] not found");
        }

        return status;
    }
}