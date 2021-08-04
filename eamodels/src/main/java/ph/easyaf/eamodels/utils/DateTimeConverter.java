package ph.easyaf.eamodels.utils;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateTimeConverter {

    public static final String ISO_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    public static String toDateText(Date date, String format) {
        if (date == null) return "";
        SimpleDateFormat timeFormatter = new SimpleDateFormat(format, Locale.getDefault());
        DateFormatSymbols symbols = new DateFormatSymbols(Locale.getDefault());
        symbols.setAmPmStrings(new String[] { "AM", "PM" });
        timeFormatter.setDateFormatSymbols(symbols);
        return String.format("%s", timeFormatter.format(date));
    }

    public static String toISODate(Date date) {
        if (date == null) return "";
        SimpleDateFormat formatter = new SimpleDateFormat(ISO_FORMAT, Locale.getDefault());
        return String.format("%s", formatter.format(date));
    }

    public static String toISODateUtc(Date date) {
        if (date == null) return "";
        SimpleDateFormat formatter = new SimpleDateFormat(ISO_FORMAT, Locale.getDefault());
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        return String.format("%s", formatter.format(date));
    }

    public static Date toDateUtc(String date) {
        Date utcDate = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(ISO_FORMAT, Locale.getDefault());
            formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
            utcDate = formatter.parse(date);
        } catch (ParseException | NullPointerException e) {
            e.printStackTrace();
        }
        return utcDate;
    }

    public static Date toDateUtc(String date, String format) {
        Date utcDate = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());
            formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
            utcDate = formatter.parse(date);
        } catch (ParseException | NullPointerException e) {
            e.printStackTrace();
        }
        return utcDate;
    }

    public static Date toDate(String date) {
        Date fDate = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(ISO_FORMAT, Locale.getDefault());
            fDate = formatter.parse(date);
        } catch (ParseException | NullPointerException e) {
            e.printStackTrace();
        }
        return fDate;
    }

    public static Date toDate(String date, String format) {
        Date fDate = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());
            fDate = formatter.parse(date);
        } catch (ParseException | NullPointerException e) {
            e.printStackTrace();
        }
        return fDate;
    }
}
