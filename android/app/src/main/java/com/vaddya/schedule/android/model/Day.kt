package com.vaddya.schedule.android.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.jetbrains.annotations.NotNull
import java.util.UUID

/**
 * com.vaddya.schedule.android.model at android
 *
 * @author vaddya
 */
class Day(@SerializedName("id")
          @Expose
          @NotNull
          var date: String,

          @SerializedName("dayOfWeek")
          @Expose
          @NotNull
          var dayOfWeek: DayOfWeek,

          @SerializedName("lessons")
          @Expose
          @NotNull
          var lessons: List<Lesson>
)