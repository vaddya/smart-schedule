package com.vaddya.schedule.android.model

import java.util.UUID

/**
 * com.vaddya.schedule.android.model at android

 * @author vaddya
 */
class Lesson(var id: UUID,
             var startTime: String,
             var endTime: String,
             var subject: String,
             var type: LessonType,
             var place: String,
             var teacher: String)