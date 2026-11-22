package io.github.ahmedmani.pairipfix.hooks;


import de.robv.android.xposed.XposedBridge;
import io.github.ahmedmani.pairipfix.BaseHook;

public class LicenseActivityBypass extends BaseHook {
    String LICENSE_ACTIVITY = "com.pairip.licensecheck.LicenseActivity";

    @Override
    public String getName() {
        return "LicenseActivityBypass";
    }

    @Override
    public boolean apply() {
        boolean success = false;

        if (!classExists(LICENSE_ACTIVITY)) {
            XposedBridge.log("[PairIPFix] [LicenseActivity] not found");
        }

            success |= hookDoNothing(LICENSE_ACTIVITY, "closeApp");
            XposedBridge.log("[PairIPFix] [LicenseActivity] closeApp hook result: " + success);
            success |= hookDoNothing(LICENSE_ACTIVITY, "exitApp");
            XposedBridge.log("[PairIPFix] [LicenseActivity] exitApp hook result: " + success);
            success |= hookDoNothing(LICENSE_ACTIVITY, "showErrorDialog");
            XposedBridge.log("[PairIPFix] [LicenseActivity] showErrorDialog hook result: " + success);
            success |= hookDoNothing(LICENSE_ACTIVITY, "showPaywallAndCloseApp");
            XposedBridge.log("[PairIPFix] [LicenseActivity] showPaywallAndCloseApp hook result: " + success);
            success |= hookDoNothing(LICENSE_ACTIVITY, "logAndShowErrorDialog", String.class);
            XposedBridge.log("[PairIPFix] [LicenseActivity] logAndShowErrorDialog hook result: " + success);
            success |= hookDoNothing(LICENSE_ACTIVITY, "logAndShowErrorDialog", String.class, Exception.class);
            XposedBridge.log("[PairIPFix] [LicenseActivity] logAndShowErrorDialog hook result: " + success);

        return success;
    }
}