package com.example.search

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.AnticipateInterpolator
import androidx.core.animation.doOnEnd
import com.example.search.databinding.ActivityIntroBinding
import com.example.search.presention.base.BaseActivity

import com.example.search.presention.view.search.MovieSearchActivity
import kotlin.concurrent.thread

class IntroActivity : BaseActivity<ActivityIntroBinding>(R.layout.activity_intro) {

    var isReady = false
    var isStart = false

    override fun onCreate(savedInstanceState: Bundle?) {

        initData()
        startMainActivity()
        super.onCreate(savedInstanceState)
    }

//    private fun initSplashScreen() {
//
//        initData()
//
//        // 31 버전일 때와 아닐 때의 분기 처리.
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//            errorGuard()
//
//            // Android 12 미만 버전에서 바로 IntroActivity의 UI가 뜨도록.
//            val content: View = findViewById(android.R.id.content)
//            // SplashScreen이 생성되고 그려질 때 계속해서 호출된다.
//            content.viewTreeObserver.addOnPreDrawListener(
//                object : ViewTreeObserver.OnPreDrawListener {
//                    override fun onPreDraw(): Boolean {
//                        // Check if the initial data is ready.
//                        return if (isReady) {
//                            // 3초 후 Splash Screen 제거
//                            content.viewTreeObserver.removeOnPreDrawListener(this)
//                            true
//                        } else {
//                            // The content is not ready
//                            false
//                        }
//                    }
//                }
//            )
//
//            // splashScreen이 종료 될 때 애니메이션 컨트롤.
//            splashScreen.setOnExitAnimationListener { splashScreenView ->
//                // Create your custom animation.
//                val slideUp = ObjectAnimator.ofFloat(
//                    splashScreenView,
//                    View.TRANSLATION_Y, // View.TRANSLATION_X 로 하면 왼쪽으로 사라짐.
//                    0f,
//                    -splashScreenView.height.toFloat()
//                )
//                slideUp.interpolator = AnticipateInterpolator()
//                slideUp.duration = 1000L // Splash Screen이 사라지는 시간.
//                isStart = true
//
//                // Custom animation이 끝나면 SplashScreenView.remove 호출
//                slideUp.doOnEnd {
//                    splashScreenView.remove()
//                    startMainActivity()
//                }
//
//                // Run your animation.
//                slideUp.start()
//            }
//        } else {
//            // 31 버전이 아닌 경우, SplashScreen이 없으므로 Splash Activity를 실행시키는 등 별도의 처리.
//            startMainActivity()
//        }
//    }

    private fun initData() {

        thread(start = true) {
            for (i in 1..3) {
                Thread.sleep(1000)
                Log.d("SleepLog", "Sleep .. i = $i")
            }
            // remove Splash Screen after 3 sec
            isReady = true
        }
    }

//    /**
//     * 최초 빌드 시, setOnExitAnimationListener 가 호출이 안됨.
//     * 따라서 해당 경우에 MainActivity로 넘어가기 위하여 코드 추가.
//     */
//    private fun errorGuard() {
//        thread(start = true) {
//            Thread.sleep(5000)
//
//            if (!isStart) {
//                Log.d("loggingTest" , "start $isStart :: startActivity")
//                startMainActivity()
//            }
//        }
//    }

    private fun startMainActivity() {
        thread(start = true) {
            Thread.sleep(1000)
            startActivity(Intent(this, MovieSearchActivity::class.java))
            finish()
        }
    }
}