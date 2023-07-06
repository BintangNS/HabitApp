//package com.dicoding.habitapp.ui.countdown
//
//import android.content.Context
//import androidx.work.CoroutineWorker
//import androidx.work.WorkerParameters
//import com.dicoding.habitapp.data.HabitRepository
//
//class CountDownWorker(
//    appContext: Context,
//    workerParams: WorkerParameters,
//    private val habitRepository: HabitRepository
//) : CoroutineWorker(appContext, workerParams) {
//
//    companion object {
//        const val KEY_HABIT_ID = "habit_id"
//        const val UNIQUE_WORK_NAME = "count_down_work"
//    }
//
//    override suspend fun doWork(): Result {
//        val habitId = inputData.getLong(KEY_HABIT_ID, 0L)
//        val habit = habitRepository.getHabitById(habitId)
//        habit?.let {
//            NotificationUtils.showNotification(
//                applicationContext,
//                it.id,
//                it.title
//            )
//        }
//        return Result.success()
//    }
//}