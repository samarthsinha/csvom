package com.bootup.csvom.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author samarth
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CSVColumn {
	/**
	 * title for the CSV column should be initialised here
	 */
	String title() default "";

	/**
	 * position index for the CSV column should be initialised here
	 */
	int index() default -1;

	String value() default "";
}
