package com.vaddya.schedule.android.model

import org.joda.time.DateTime
import org.joda.time.LocalDate
import java.util.*

/**
 * com.vaddya.schedule.android.model at android
 *
 * @author vaddya
 */
class Task(var id: UUID,
           var subject: String,
           var type: LessonType,
           var deadline: LocalDate,
           var textTask: String,
           var isComplete: Boolean)