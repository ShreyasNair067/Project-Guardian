package deakin.gopher.guardian.view.general

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import com.google.android.material.button.MaterialButton
import deakin.gopher.guardian.R

class WelcomeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val getStartedButton = findViewById<MaterialButton>(R.id.get_started_button)
        val anim = ObjectAnimator.ofFloat(getStartedButton, "translationY", 0f, 20f, 0f)
        anim.duration = 1000
        anim.repeatCount = ValueAnimator.INFINITE
        anim.start()
    }
}

