package io.github.ahmedmani.pairipfix.hooks

import android.content.Context
import com.highcapable.yukihookapi.hook.log.YLog
import io.github.ahmedmani.pairipfix.BaseHook

class SignatureBypass : BaseHook() {

    private val SIGNATURE_CHECK = "com.pairip.SignatureCheck"

    override fun getName(): String = "SignatureBypass"

    override fun onHook() {
        val hook1 = hookDoNothing(SIGNATURE_CHECK, "verifyIntegrity", Context::class.java)
        YLog.debug("[SignatureCheck] verifyIntegrity hook result: $hook1")

        val hook2 =
            hookReturnConstant(SIGNATURE_CHECK, "verifySignatureMatches", true, String::class.java)
        YLog.debug("[SignatureCheck] verifySignatureMatches hook result: $hook2")
    }
}