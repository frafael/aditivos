package util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	public static final Date convertTimestampToDate( Long time ) {
		Date date = new Date();
			date.setTime(time);
		return date;
	}

	public static final Calendar dataDeHojeComHoraZerada() {
		Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
		return calendar;
	}

	public static final Calendar dataDeHojeComHoraDoFimDoDia() {
		Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
		return calendar;
	}
}