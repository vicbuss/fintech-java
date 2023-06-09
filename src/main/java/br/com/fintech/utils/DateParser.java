package br.com.fintech.utils;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateParser {
	
	private DateParser() {}
	
	public static Date localDateToSQL(LocalDate date) {
		Instant instant = date.atStartOfDay(ZoneId.systemDefault()).toInstant();
		return new Date(instant.toEpochMilli());
	}
	
	public static Date localDateTimeToSQL(LocalDateTime date) {
		Instant instant = date.atZone(ZoneId.systemDefault()).toInstant();
		return new Date(instant.toEpochMilli());
	}
	
	public static LocalDateTime SQLToLocalDateTime(Date date) {
		long millis = date.getTime();
		return Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDateTime();
	}
	
	public static LocalDate SQLToLocalDate(Date date) {
		return date.toLocalDate();
	}
	
	public static String LocalDateTimeToString(LocalDateTime date, String pattern) {
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern(pattern); 
		return date.format(fmt);
	}
	
	public static String LocalDateToString(LocalDate date, String pattern) {
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern(pattern);
		return date.format(fmt);
	}
	
	public static LocalDateTime StringToLocalDateTime(String date, String pattern) {
		if(date.length() < 11) {
			String dateConcat = date.concat(" 00:00");
			String patternConcat = pattern.concat(" HH:mm");
			date = dateConcat;
			pattern = patternConcat;
		}
		
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern(pattern);
		return LocalDateTime.parse(date, fmt);
	}
	
	public static LocalDate StringToLocalDate(String date, String pattern) {
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern(pattern);
		return LocalDate.parse(date, fmt);
	}
}
