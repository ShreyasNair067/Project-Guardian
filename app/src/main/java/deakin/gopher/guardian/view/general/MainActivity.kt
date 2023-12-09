package deakin.gopher.guardian.view.general

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.google.android.gms.tasks.Task
import com.google.firebase.messaging.FirebaseMessaging
import deakin.gopher.guardian.R
import deakin.gopher.guardian.model.login.SessionManager
import deakin.gopher.guardian.services.EmailPasswordAuthService

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        val getStartedButton = findViewById<Button>(R.id.get_started_button)
        val anim = ObjectAnimator.ofFloat(getStartedButton, "translationY", 0f, 20f, 0f)
        anim.duration = 1000
        anim.repeatCount = ValueAnimator.INFINITE
        anim.start()
        //val logoutButton = findViewById<Button>(R.id.logoutButton)

        getStartedButton.setOnClickListener { _ -> onGetStartedClick() }
        //logoutButton.setOnClickListener { _ -> onLogoutClick() }

        FirebaseMessaging.getInstance()
            .token
            .addOnCompleteListener { task: Task<String?> ->
                if (task.isSuccessful) {
                    val token = task.result
                    Log.d("FCM Token", token ?: "Token is null")
                } else {
                    Log.w("MainActivity", "Fetching FCM registration token failed", task.exception)
                }
            }
    }

    private fun onGetStartedClick() {
        val sessionManager = SessionManager(this)
        if (!sessionManager.isLoggedIn) {
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
        } else {
            startActivity(Intent(this@MainActivity, Homepage4caretaker::class.java))
        }
    }

    private fun onLogoutClick() {
        EmailPasswordAuthService.signOut(this)
        finish()
    }
}
