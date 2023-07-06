package com.dicoding.habitapp.ui.countdown

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.dicoding.habitapp.R
import com.dicoding.habitapp.data.Habit
import com.dicoding.habitapp.notification.NotificationWorker
import com.dicoding.habitapp.ui.list.HabitListViewModel
import com.dicoding.habitapp.utils.HABIT
import com.dicoding.habitapp.utils.HABIT_ID
import com.dicoding.habitapp.utils.HABIT_TITLE
import java.util.concurrent.TimeUnit

class CountDownActivity : AppCompatActivity() {
    private lateinit var habit: Habit
    private lateinit var workManager: WorkManager
    private lateinit var countDownRequest: OneTimeWorkRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_count_down)
        supportActionBar?.title = "Count Down"


        habit = intent.getParcelableExtra<Habit>(HABIT) as Habit
        workManager = WorkManager.getInstance(applicationContext)

        findViewById<TextView>(R.id.tv_count_down_title).text = habit.title

        val viewModel = ViewModelProvider(this).get(CountDownViewModel::class.java)

        viewModel.setInitialTime(habit.minutesFocus)

        viewModel.currentTimeString.observe(this, { timeString ->
            findViewById<TextView>(R.id.tv_count_down).text = timeString
        })

        viewModel.eventCountDownFinish.observe(this, { isFinished ->
            updateButtonState(!isFinished)
        })

        findViewById<Button>(R.id.btn_start).setOnClickListener {
            viewModel.startTimer()
            updateButtonState(true)
            countDownRequest = buildCountDownRequest(habit.minutesFocus)
            workManager.enqueue(countDownRequest)
        }

        findViewById<Button>(R.id.btn_stop).setOnClickListener {
            viewModel.resetTimer()
            updateButtonState(false)
            workManager.cancelWorkById(countDownRequest.id)
        }
    }

    private fun updateButtonState(isRunning: Boolean) {
        findViewById<Button>(R.id.btn_start).isEnabled = !isRunning
        findViewById<Button>(R.id.btn_stop).isEnabled = isRunning
    }

    private fun buildCountDownRequest(minutes: Long): OneTimeWorkRequest {
        return OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInitialDelay(minutes, TimeUnit.MINUTES)
            .setInputData(workDataOf(HABIT_ID to habit.id, HABIT_TITLE to habit.title))
            .build()
    }
}