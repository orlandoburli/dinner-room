package br.com.orlandoburli.dinnerroom.matchers

import android.view.WindowManager
import androidx.test.espresso.Root
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

class ToastMatcher : TypeSafeMatcher<Root>() {

    override fun describeTo(description: Description) {
        description.appendText("is toastContador");
    }

    override fun matchesSafely(root: Root): Boolean {
        val type = root.getWindowLayoutParams().get().type
        if (type == WindowManager.LayoutParams.TYPE_TOAST) {
            val windowToken = root.getDecorView().getWindowToken()
            val appToken = root.getDecorView().getApplicationWindowToken()
            if (windowToken === appToken) {
                return true
            }
        }
        return false
    }
}
