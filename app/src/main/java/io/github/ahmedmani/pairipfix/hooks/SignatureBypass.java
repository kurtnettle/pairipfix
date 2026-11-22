package io.github.ahmedmani.pairipfix.hooks;

import android.content.Context;

import de.robv.android.xposed.XposedBridge;
import io.github.ahmedmani.pairipfix.BaseHook;


public class SignatureBypass extends BaseHook {
    String SIGNATURE_CHECK = "com.pairip.SignatureCheck";

    @Override
    public String getName() {
        return "SignatureBypass";
    }

    @Override
    public boolean apply() {

        boolean hook1 = hookDoNothing(SIGNATURE_CHECK, "verifyIntegrity", Context.class);
        XposedBridge.log("[PairIPFix] [SignatureCheck] verifyIntegrity hook result: " + hook1);
        boolean hook2 = hookReturnConstant(SIGNATURE_CHECK, "verifySignatureMatches", true, String.class);
        XposedBridge.log("[PairIPFix] [SignatureCheck] verifySignatureMatches hook result: " + hook2);

        return hook1 || hook2;
    }
}