package com.vaddya.schedule.rest;

//TODO по моему этому классу более уместно быть в подпакете controllers, как это и было.
//
//TODO с другой стороны есть сомнения насчет надобности такого класса с константами.
//TODO почему бы каждую из констант не разместить непосредственно в контроллере, где она используется?
//Вроде бы в таком виде, в каком есть, красиво, что все вместе. Как API. Но для API маловато информации.

//TODO в javadoc осталося старый путь к классу
/**
 * com.vaddya.schedule.rest.controllers at smart-schedule
 *
 * @author vaddya
 * @since April 08, 2017
 */
//TODO нет аболютной уверенности в этом, но вроде since используют для версии, а не даты. Посмотрел даже в jdk
public final class Paths {

    public static final String TASKS = "/api/tasks";

    public static final String SCHEDULE = "/api/schedule";

    public static final String WEEKS = "/api/weeks";

    public static final String LESSONS = "/api/lessons";

    public static final String CHANGES = "/api/changes";

}
