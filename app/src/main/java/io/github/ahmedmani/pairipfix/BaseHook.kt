package io.github.ahmedmani.pairipfix

import com.highcapable.kavaref.extension.hasClass
import com.highcapable.kavaref.extension.loadClassOrNull
import com.highcapable.yukihookapi.hook.entity.YukiBaseHooker
import com.highcapable.yukihookapi.hook.log.YLog


abstract class BaseHook : YukiBaseHooker() {
    protected lateinit var classLoader: ClassLoader

    abstract fun getName(): String
    fun init(_classLoader: ClassLoader): BaseHook {
        this.classLoader = _classLoader
        return this
    }

    protected fun classExists(className: String): Boolean = classLoader.hasClass(className)

    protected fun findClass(className: String): Class<*>? = classLoader.loadClassOrNull(className)

    protected fun setStaticField(className: String, fieldName: String, value: Any?): Boolean {
        return try {
            val field = classLoader.loadClass(className).getDeclaredField(fieldName)
                .apply { isAccessible = true }
            field.set(null, value)
            true
        } catch (t: Throwable) {
            YLog.error(
                "Failed to set static field: $className.$fieldName = $value", t
            )
            false
        }
    }

    protected fun hookDoNothing(
        className: String?, methodName: String, vararg paramTypes: Class<*>
    ): Boolean {
        try {
            classLoader.loadClass(className).getDeclaredMethod(methodName, *paramTypes)
                .hook { intercept() }
            return true
        } catch (t: Throwable) {
            YLog.error("Failed to hook method with path $className.$methodName", t)
            return false
        }
    }


    protected fun hookReturnConstant(
        className: String?, methodName: String, returnValue: Any?, vararg paramTypes: Class<*>
    ): Boolean {
        try {
            classLoader.loadClass(className).getDeclaredMethod(methodName, *paramTypes)
                .hook { intercept(returnValue) }
            return true
        } catch (t: Throwable) {
            YLog.error("Failed to hook method with path $className.$methodName", t)
            return false
        }
    }

//    protected fun hookAfter(
//        className: String?,
//        methodName: String?,
//        callback: AfterHookCallback,
//        vararg paramTypes: Any?
//    ): Boolean {
//        try {
//            val hook: XC_MethodHook = object : XC_MethodHook() {
//                protected override fun afterHookedMethod(param: MethodHookParam?) {
//                    callback.onAfter(param)
//                }
//            }
//            val args = buildArgs(paramTypes, hook)
//            XposedHelpers.findAndHookMethod(className, this.classLoader, methodName, args)
//            return true
//        } catch (t: Throwable) {
//            YLog.error("[PairIPFix] Failed to hook method with path $className.$methodName", t)
//            return false
//        }
//    }
//
//    protected fun hookReplace(
//        className: String?, methodName: String?, callback: ReplaceCallback, vararg paramTypes: Any?
//    ): Boolean {
//        try {
//            val replacement: XC_MethodReplacement = object : XC_MethodReplacement() {
//                @Throws(Throwable::class)
//                protected override fun replaceHookedMethod(param: MethodHookParam?): Any? {
//                    return callback.onReplace(param)
//                }
//            }
//            val args = buildArgs(paramTypes, replacement)
//            XposedHelpers.findAndHookMethod(className, this.classLoader, methodName, args)
//            return true
//        } catch (t: Throwable) {
//            XposedBridge.log("[PairIPFix] Failed to replace method with path " + className + "." + methodName)
//            return false
//        }
//    }

//    private fun buildArgs(paramTypes: Array<out Any?>, callback: Any): Array<Any?> {
//        val args = mutableListOf<Any?>()
//        for (type in paramTypes) {
//            args.add(type)
//        }
//        args.add(callback)
//        return args.toTypedArray()
//    }

//    interface AfterHookCallback {
//        fun onAfter(param: XC_MethodHook.MethodHookParam?)
//    }
//
//    interface ReplaceCallback {
//        @Throws(Throwable::class)
//        fun onReplace(param: XC_MethodHook.MethodHookParam?): Any?
//    }

}