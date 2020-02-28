package com.ivzb.chicks.util

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.annotation.Keep
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomappbar.BottomAppBar

@Keep
@Suppress("UNUSED")
class NavigationBarScrimBehavior(
    context: Context,
    attrs: AttributeSet
) : CoordinatorLayout.Behavior<View>(context, attrs) {
    override fun onLayoutChild(
        parent: CoordinatorLayout,
        child: View,
        layoutDirection: Int
    ): Boolean {
        child.setOnApplyWindowInsetsListener(NoopWindowInsetsListener)
        // Return false so that the child is laid out by the parent
        return false
    }

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        if (dependency is BottomAppBar) {
            // Copy over the elevation value
            child.elevation = dependency.elevation
            return true
        }
        return false
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        child.elevation = dependency.elevation
        return false
    }

    override fun onApplyWindowInsets(
        parent: CoordinatorLayout,
        child: View,
        insets: WindowInsetsCompat
    ): WindowInsetsCompat {
        child.layoutParams.height = insets.systemWindowInsetBottom
        child.requestLayout()
        return insets
    }
}
