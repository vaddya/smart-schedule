package com.vaddya.schedule.android.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.UUID

/**
 * com.vaddya.schedule.android.model at android
 *
 * @author vaddya
 */
class Lesson(@SerializedName("id")
             @Expose
             var id: UUID,

             @SerializedName("startTime")
             @Expose
             var startTime: String,

             @SerializedName("endTime")
             @Expose
             var endTime: String,

             @SerializedName("subject")
             @Expose
             var subject: String,

             @SerializedName("type")
             @Expose
             var type: LessonType,

             @SerializedName("place")
             @Expose
             var place: String,

             @SerializedName("teacher")
             @Expose
             var teacher: String)