package com.ivzb.chicks.util

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.browser.customtabs.CustomTabsIntent
import androidx.browser.customtabs.CustomTabsService
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ivzb.chicks.R
import com.ivzb.chicks.model.Theme
import com.ivzb.chicks.widget.CustomSwipeRefreshLayout

@BindingAdapter("goneUnless")
fun goneUnless(view: View, visible: Boolean) {
    view.visibility = if (visible) VISIBLE else GONE
}

@BindingAdapter("fabVisibility")
fun fabVisibility(fab: FloatingActionButton, visible: Boolean) {
    if (visible) fab.show() else fab.hide()
}

@BindingAdapter("pageMargin")
fun pageMargin(viewPager: ViewPager, pageMargin: Float) {
    viewPager.pageMargin = pageMargin.toInt()
}

@BindingAdapter(value = [
    "momentImageUrl",
    "momentImageUrlDarkTheme",
    "momentTheme"],
    requireAll = false)
fun momentImageUrl(
    imageView: ImageView,
    momentImageUrl: String?,
    momentImageUrlDarkTheme: String?,
    momentTheme: Theme?
) {
    when (momentTheme) {
        Theme.DARK -> imageUri(imageView, momentImageUrlDarkTheme?.toUri(), null)
        else -> imageUri(imageView, momentImageUrl?.toUri(), null)
    }
}

@BindingAdapter(value = ["imageUri", "placeholder"], requireAll = false)
fun imageUri(imageView: ImageView, imageUri: Uri?, placeholder: Drawable?) {
    when (imageUri) {
        null -> {
            Glide.with(imageView)
                .load(placeholder)
                .into(imageView)
        }
        else -> {
            Glide.with(imageView)
                .load(imageUri)
                .apply(RequestOptions().placeholder(placeholder))
                .into(imageView)
        }
    }
}

@BindingAdapter(value = ["imageUrl", "placeholder"], requireAll = false)
fun imageUrl(imageView: ImageView, imageUrl: String?, placeholder: Drawable?) {
    imageUri(imageView, imageUrl?.toUri(), placeholder)
}

/**
 * Sets the colors of the [CustomSwipeRefreshLayout] loading indicator.
 */
@BindingAdapter("swipeRefreshColors")
fun setSwipeRefreshColors(swipeRefreshLayout: CustomSwipeRefreshLayout, colorResIds: IntArray) {
    swipeRefreshLayout.setColorSchemeColors(*colorResIds)
}

/** Set text on a [TextView] from a string resource. */
@BindingAdapter("android:text")
fun setText(view: TextView, @StringRes resId: Int) {
    if (resId == 0) {
        view.text = null
    } else {
        view.setText(resId)
    }
}

private const val CHROME_PACKAGE = "com.android.chrome"

@BindingAdapter("websiteLink", "hideWhenEmpty", requireAll = false)
fun websiteLink(
    button: View,
    url: String?,
    hideWhenEmpty: Boolean = false
) {
    if (url.isNullOrEmpty()) {
        if (hideWhenEmpty) {
            button.isVisible = false
        } else {
            button.isClickable = false
        }
        return
    }
    button.isVisible = true
    button.setOnClickListener {
        openWebsiteUrl(it.context, url)
    }
}

fun openWebsiteUrl(context: Context, url: String) {
    if (url.isBlank()) {
        return
    }
    openWebsiteUri(context, Uri.parse(url))
}

fun openWebsiteUri(context: Context, uri: Uri) {
    if (context.isChromeCustomTabsSupported()) {
        CustomTabsIntent.Builder()
            .setToolbarColor(ContextCompat.getColor(context, R.color.color_primary))
            .setSecondaryToolbarColor(ContextCompat.getColor(context, R.color.color_primary_dark))
            .build()
            .launchUrl(context, uri)
    } else {
        context.startActivity(Intent(Intent.ACTION_VIEW, uri))
    }
}

private fun Context.isChromeCustomTabsSupported(): Boolean {
    val serviceIntent = Intent(CustomTabsService.ACTION_CUSTOM_TABS_CONNECTION)
    serviceIntent.setPackage(CHROME_PACKAGE)
    val resolveInfos = packageManager.queryIntentServices(serviceIntent, 0)
    return !resolveInfos.isNullOrEmpty()
}
