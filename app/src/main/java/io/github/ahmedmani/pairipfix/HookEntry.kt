package io.github.ahmedmani.pairipfix

import com.highcapable.yukihookapi.annotation.xposed.YukiHookLibXposedEntry
import com.highcapable.yukihookapi.extension.kavaref.toClassOrNull
import com.highcapable.yukihookapi.hook.factory.configure
import com.highcapable.yukihookapi.hook.factory.encase
import com.highcapable.yukihookapi.hook.log.YLog
import com.highcapable.yukihookapi.hook.xposed.YukiHookXposedModule
import io.github.ahmedmani.pairipfix.hooks.LicenseActivityBypass
import io.github.ahmedmani.pairipfix.hooks.LicenseClientBypass
import io.github.ahmedmani.pairipfix.hooks.LicenseResponseBypass
import io.github.ahmedmani.pairipfix.hooks.SignatureBypass

@YukiHookLibXposedEntry(targetApiVersion = 101)
object HookEntry : YukiHookXposedModule {
    override fun onInit() = configure {
        logging {
            tag = "PairIPFix"
            enabled = true
            recording = true
        }
        debug = true
    }

    override fun onHook() = encase {
        if (processName != packageName) {
            YLog.info("Skipping subprocess $processName ($packageName)")
            return@encase
        }

        YLog.info("Module loaded for package: $packageName")

        val clazz = "com.pairip.VMRunner".toClassOrNull()
        if (clazz == null) {
            YLog.error("App with package name $packageName does not have pairip protection, or protection is not recognized, continuing anyway...")
        }

        val hooks = listOf(
            SignatureBypass(),
            LicenseClientBypass(),
            LicenseActivityBypass(),
            LicenseResponseBypass()
        )

        var successCount = 0
        for (hook in hooks) {
            try {
                hook.init(hostClassLoader!!).onHook()
                YLog.info("${hook.getName()} applied successfully")
                successCount++
            } catch (t: Throwable) {
                YLog.error("Error applying hook with name ${hook.getName()}", t)
            }
        }

        YLog.info("$successCount/${hooks.size} hooks applied")
    }
}
