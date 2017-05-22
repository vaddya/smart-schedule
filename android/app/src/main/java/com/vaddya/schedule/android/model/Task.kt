package com.vaddya.schedule.android.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.joda.time.LocalDate
import java.util.*

/**
 * com.vaddya.schedule.android.model at android
 *
 * @author vaddya
 */
class Task(@SerializedName("id")
           @Expose
           var id: UUID,

           @SerializedName("subject")
           @Expose
           var subject: String,

           @SerializedName("type")
           @Expose
           var type: LessonType,

           @SerializedName("deadline")
           @Expose
           var deadline: LocalDate,

           @SerializedName("textTask")
           @Expose
           var textTask: String,

           @SerializedName("isComplete")
           @Expose
           var isComplete: Boolean)