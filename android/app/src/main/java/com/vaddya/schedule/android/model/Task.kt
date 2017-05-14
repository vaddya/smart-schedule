package com.vaddya.schedule.android.model

import java.util.Date
import java.util.UUID

/**
 * com.vaddya.schedule.android.model at android
 *
 * @author vaddya
 */
class Task(var id: UUID,
           var subject: String,
           var type: LessonType,
           var deadline: Date,
           var textTask: String,
           var isComplete: Boolean)