package com.bootup.csvom.parser;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import com.bootup.csvom.annotations.CSVColumn;
import com.bootup.csvom.annotations.CSVModel;

/**
 * @author samarth
 *
 */
public class CSVParser{

	public CSVParser() {
		super();
	}

	/**
	 * Parses given csv into POJO and returns List of the the Objects
	 * 
	 * @param clazz
	 *            Class of POJO which needs to fetched from a CSV
	 * @return
	 *
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> List<T> parse(Class<T> clazz) throws Exception {
		Class theClass = Class.forName(clazz.getCanonicalName());
		List<T> csvRecordList = new ArrayList<T>();
		T csvModel = (T) theClass.newInstance();
		CSVModel csvModelData = csvModel.getClass().getAnnotation(
				CSVModel.class);
		org.apache.commons.csv.CSVParser csvParser = null;
		String filePath;
		char delimiter;
		if (csvModelData != null) {
			filePath = csvModelData.filePath();
			delimiter = csvModelData.delimiter();
			if (filePath != null && !filePath.startsWith("http://")) {
				csvParser = org.apache.commons.csv.CSVParser.parse(new File(
						filePath), Charset.defaultCharset(), CSVFormat.DEFAULT
						.withHeader().withDelimiter(delimiter));
			} else {
				URL fileUrl = new URL(filePath);
				csvParser = org.apache.commons.csv.CSVParser.parse(fileUrl,
						Charset.defaultCharset(), CSVFormat.DEFAULT
								.withHeader().withDelimiter(delimiter));
			}
			for (CSVRecord csvRecord : csvParser) {
				T model = (T) theClass.newInstance();
				Field[] fields = csvModel.getClass().getDeclaredFields();
				for (Field field : fields) {
					if (field.isAnnotationPresent(CSVColumn.class)) {
						CSVColumn csvColumn = field
								.getAnnotation(CSVColumn.class);
						field.setAccessible(true);
						if (!"".equals(csvColumn.title())) {
							field.set(model, csvRecord.get(csvColumn.title()));
						} else if (csvColumn.index() > -1) {
							field.set(model, csvRecord.get(csvColumn.index()));
						} else {
							field.set(model, csvColumn.value());
						}
					}
				}
				csvRecordList.add(model);
			}
			/*System.out.println("file to read : " + filePath + " ,delimiter = "
					+ delimiter + csvModel.toString());*/
		} else {
			throw new Exception("Failed to Find CSVModel annotation");
		}
		return csvRecordList;
	}

}