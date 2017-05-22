package com.vaddya.schedule.android.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.jetbrains.annotations.NotNull
import org.joda.time.LocalDate

/**
 * com.vaddya.schedule.android.model at android
 *
 * @author vaddya
 */
class Day(@SerializedName("id")
          @Expose
          var date: LocalDate,

          @SerializedName("dayOfWeek")
          @Expose
          var dayOfWeek: DayOfWeek,

          @NotNull
          @SerializedName("lessons")
          @Expose
          var lessons: List<Lesson>
)