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
@Target(ElementType.TYPE)
public @interface CSVModel {
	/**
	 * File path should be the path of the parseable csv file either local or remote(http)
	 */
	String filePath(); 
	/**
	 * Delimiter of the file by default delimiter will be comma seperated
	 */
	char delimiter() default com.bootup.csvom.constants.CSVConstants.COMMA;
}
