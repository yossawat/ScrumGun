package per.scrumgun.core.util

import android.content.ContentResolver
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.core.content.pm.PackageInfoCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ViewModelOwner
import org.koin.androidx.viewmodel.koin.getViewModel
import org.koin.androidx.viewmodel.scope.getViewModel
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.core.scope.Scope
import per.scrumgun.core.R

fun Context.toast(
    message: CharSequence,
    length: Int = Toast.LENGTH_LONG
) {
    val inflater = LayoutInflater.from(this)
    val layout = inflater.inflate(R.layout.toast_alert, null, false)
    val text: TextView = layout.findViewById(R.id.toastTextView)
    text.text = message
    with(Toast(applicationContext)) {
        duration = length
        view = layout
        show()
    }
}

fun Context.resourceUri(resourceId: Int): Uri = with(resources) {
    Uri.Builder()
        .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
        .authority(getResourcePackageName(resourceId))
        .appendPath(getResourceTypeName(resourceId))
        .appendPath(getResourceEntryName(resourceId))
        .build()
}

fun Context.appVersionCode(): Long {
    return try {
        val packageInfo = applicationContext.packageManager.getPackageInfo(
            applicationContext.packageName,
            0
        )
        PackageInfoCompat.getLongVersionCode(packageInfo)
    } catch (e: PackageManager.NameNotFoundException) {
        throw RuntimeException("Could not get package name: $e")
    }
}

fun Context.appVersionName(): String {
    return try {
        val packageInfo = applicationContext.packageManager.getPackageInfo(
            applicationContext.packageName,
            0
        )
        packageInfo.versionName
    } catch (e: PackageManager.NameNotFoundException) {
        throw RuntimeException("Could not get package name: $e")
    }
}

fun Context.isDebug(): Boolean {
    return 0 != this.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE
}

inline fun <reified VM : ViewModel> Fragment.sharedGraphViewModel(
    @IdRes
    navGraphId: Int,
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null
) = lazy {
    val definition = {
        ViewModelOwner.from(findNavController().getViewModelStoreOwner(navGraphId))
    }
    getKoin().getViewModel<VM>(qualifier, null, definition, parameters)
}

inline fun <reified VM : ViewModel> Fragment.sharedFragmentViewModel(
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null,
) = lazy {
    val definition = {
        ViewModelOwner.from(requireParentFragment().viewModelStore)
    }
    getKoin().getViewModel<VM>(qualifier, null, definition, parameters)
}

inline fun <reified VM : ViewModel> Fragment.sharedGrandFragmentViewModel(
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null,
) = lazy {
    val definition = {
        ViewModelOwner.from(requireParentFragment().requireParentFragment().viewModelStore)
    }
    getKoin().getViewModel<VM>(qualifier, null, definition, parameters)
}

inline fun <reified VM : ViewModel> Fragment.sharedGraphViewModel(
    scope: Scope,
    @IdRes
    navGraphId: Int,
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null,
) = lazy {
    val definition = {
        ViewModelOwner.from(findNavController().getViewModelStoreOwner(navGraphId))
    }
    scope.getViewModel<VM>(qualifier, null, definition, parameters)
}

fun Fragment.findNavControllerSafety(currentId: Int): NavController? {
    try {
        val controller = findNavController()

        if (controller.currentDestination?.id != currentId) {
            return null
        }
        return controller
    } catch (e: Exception) {
        return null
    }
}
