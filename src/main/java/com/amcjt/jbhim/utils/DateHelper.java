package com.amcjt.jbhim.utils;

import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 日期工具类
 */
public class DateHelper {
    // 全日期时间格式
    public static final String FULL_PATTERN = "yyyy-MM-dd HH:mm:ss";
    // 日期格式
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String DATE_PATTERN_2 = "yyyyMMdd";
    public static final String DATE_PATTERN_3 = "yyMMddHHmmss";
    public static final String DATE_PATTERN_4 = "yyyyMMddHHmmss";
    public static final String YEAR_PATTERN = "yyyy";
    public static final String MONTH_PATTERN = "MM";
    public static final String DAY_PATTERN = "dd";
    // 年月格式
    public static final String YEAR_MONTH_PATTERN = "yyyyMM";
    public static final String YEAR_MONTH_PATTERN_2 = "yyyy-MM";
    // 月份格式
    public static final String MONTH_DAY_PATTERN = "MM-dd";
    // 时间格式
    public static final String TIME_PATTERN = "HH:mm:ss";

    public static final String LOCALE_PATTERN = "EEE MMM dd HH:mm:ss zzz yyyy";
    public static final SimpleDateFormat DAY_FORMAT = new SimpleDateFormat(DATE_PATTERN);
    public static final SimpleDateFormat DAY_FORMAT_2 = new SimpleDateFormat(DATE_PATTERN_2);
    public static final SimpleDateFormat MONTH_DAY_FORMAT = new SimpleDateFormat(MONTH_DAY_PATTERN);
    public static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat(FULL_PATTERN);
    public static final SimpleDateFormat ONLY_TIME_FORMAT = new SimpleDateFormat(TIME_PATTERN);
    public static final SimpleDateFormat YEAR_MONTH_FORMAT = new SimpleDateFormat(YEAR_MONTH_PATTERN);
    public static final SimpleDateFormat YEAR_MONTH_FORMAT_2 = new SimpleDateFormat(YEAR_MONTH_PATTERN_2);
    public static final SimpleDateFormat ONLY_YEAR_FORMAT = new SimpleDateFormat(YEAR_PATTERN);
    public static final SimpleDateFormat ONLY_MONTH_FORMAT = new SimpleDateFormat(MONTH_PATTERN);
    public static final SimpleDateFormat ONLY_DAY_FORMAT = new SimpleDateFormat(DAY_PATTERN);

    /**
     * 标准日期格式转字符串
     *
     * @param date 标准日期
     */
    public static String conventStandardDateToStr(String date) throws ParseException {
        SimpleDateFormat sdf1 = new SimpleDateFormat(LOCALE_PATTERN, Locale.UK);
        Date temp = sdf1.parse(date);
        SimpleDateFormat formatter = new SimpleDateFormat(FULL_PATTERN);
        return formatter.format(temp);
    }

    /**
     * 日期转字符串
     *
     * @param date    日期
     * @param pattern 转换后的日期格式
     */
    public static final String date2String(final Date date, final String pattern) {
        return getFormatter(pattern).format(date);
    }

    /**
     * 字符串转日期，此方法会根据 dateStr 的格式自行判断其 pattern，如果
     * 不需要这么做可以使用 ：
     * string2Date(final String dateStr, final String pattern)
     * 方法
     *
     * @param dateStr 日期串
     */
    public static Date string2Date(String dateStr) throws ParseException {
        Date date = null;
        if (dateStr != null && !"".equals(dateStr)) {
            final String pattern = getPatternByString(dateStr);
            date = string2Date(dateStr, pattern);
        }
        return date;
    }

    /**
     * 字符串转日期
     *
     * @param dateStr 日期字符串
     * @param pattern 日期格式器
     */
    public static Date string2Date(final String dateStr, final String pattern) throws ParseException {
        Date date = null;
        if (dateStr != null && !"".equals(dateStr)) {
            date = getFormatter(pattern).parse(dateStr);
        }
        return date;
    }

    /**
     * 根据字符串返回对应的日期格式
     *
     * @param timeStr 日期字符串
     */
    public static final String getPatternByString(final String timeStr) {
        final String pattern;
        if (timeStr.matches("^(\\d{4})-(0?\\d{1}|1[0-2])-(0?\\d{1}|[12]\\d{1}|3[01])$")) {
            pattern = DateHelper.DATE_PATTERN;
        } else if (timeStr.matches("^(\\d{4})-(0?\\d{1}|1[0-2])-(0?\\d{1}|[12]\\d{1}|3[01])"
                + " (0?\\d{1}|1\\d{1}|2[0123]):(0?\\d{1}|[12345]\\d{1}):(0?\\d{1}|[12345]\\d{1})$")) {
            pattern = DateHelper.FULL_PATTERN;
        } else if (timeStr.matches("^(0?\\d{1}|1\\d{1}|2[0123]):(0?\\d{1}|[12345]\\d{1}):(0?\\d{1}|[12345]\\d{1})$")) {
            pattern = DateHelper.TIME_PATTERN;
        } else {
            pattern = DateHelper.DATE_PATTERN;
        }
        return pattern;
    }

    private static DateFormat getFormatter(final String pattern) {
        return new SimpleDateFormat(pattern);
    }

    /**
     * 获取YYYY格式
     *
     * @return
     */
    public static String getYear() {
        return ONLY_YEAR_FORMAT.format(new Date());
    }

    /**
     * 获取指定yyyy-MM-dd格式的YYYY格式
     *
     * @return
     */
    public static String getJustYear(String date) throws ParseException {
        Date temp = DAY_FORMAT.parse(date);
        return ONLY_YEAR_FORMAT.format(temp);
    }

    /**
     * 获取指定yyyy-MM-dd格式的MM格式
     *
     * @return
     */
    public static String getJustMonth(String date) throws ParseException {
        Date temp = DAY_FORMAT.parse(date);
        return ONLY_MONTH_FORMAT.format(temp);
    }

    /**
     * 获取指定yyyy-MM-dd格式的dd格式
     *
     * @return
     */
    public static String getJustDay(String date) throws ParseException {
        Date temp = DAY_FORMAT.parse(date);
        return ONLY_DAY_FORMAT.format(temp);
    }

    /**
     * 获取MM-DD格式
     *
     * @return
     */
    public static String getMonth(String date) throws ParseException {
        Date temp = DAY_FORMAT.parse(date);
        return MONTH_DAY_FORMAT.format(temp);
    }

    /**
     * 获取MM-DD格式
     *
     * @return
     */
    public static String getMonth() {
        return MONTH_DAY_FORMAT.format(new Date());
    }

    /**
     * 获取YYYY-MM-DD格式
     *
     * @return
     */
    public static String getDay() {
        return DAY_FORMAT.format(new Date());
    }

    /**
     * 获取指定日期YYYY-MM-DD格式
     *
     * @return
     */
    public static String getDay(Date date) {
        return DAY_FORMAT.format(date);
    }

    /**
     * 获取YYYYMMDD格式
     *
     * @return
     */
    public static String getDays() {
        return DAY_FORMAT_2.format(new Date());
    }

    /**
     * 获取YYYY-MM-DD HH:mm:ss格式
     *
     * @return
     */
    public static String getTime() {
        return TIME_FORMAT.format(new Date());
    }

    /**
     * 获取YYYY-MM-DD HH:mm:ss格式
     *
     * @return
     */
    public static String getTime(Date date) {
        return TIME_FORMAT.format(date);
    }

    /**
     * 获取HH:mm:ss格式
     */
    public static String getJustTime(Date date) {
        return ONLY_TIME_FORMAT.format(date);
    }

    /**
     * 获取yyyyMM格式
     */
    public static String getYearMonth(Date date) {
        return YEAR_MONTH_FORMAT.format(date);
    }

    /**
     * 获取yyyy-MM格式
     */
    public static String getYearMonth2(Date date) {
        return YEAR_MONTH_FORMAT_2.format(date);
    }

    /**
     * 获取HH:mm:ss时间的分钟差
     *
     * @param firstTime  第一个时间
     * @param secondTime 第二个时间
     * @return
     */
    public static int getDifferenceTimeMinute(String firstTime, String secondTime) {
        String[] firstStrArr = firstTime.split(":");
        // 精确到秒，还是因为有个09:00:01上班的标记为迟到。
        int firstSecond = Integer.parseInt(firstStrArr[0]) * 60 * 60
                + Integer.parseInt(firstStrArr[1]) * 60
                + Integer.parseInt(firstStrArr[2]);
        String[] secondStrArr = secondTime.split(":");
        int secondSecond = Integer.parseInt(secondStrArr[0]) * 60 * 60
                + Integer.parseInt(secondStrArr[1]) * 60
                + Integer.parseInt(secondStrArr[2]);
        double ceil = Math.ceil((secondSecond - firstSecond) / 60d);
        return new Double(ceil).intValue();
    }

    /**
     * 获取两个date的天数差
     *
     * @param firstDate  第一个时间
     * @param secondDate 第二个时间
     * @return
     */
    public static int calcDays(Date firstDate, Date secondDate) {
        return (int) (Math.abs(firstDate.getTime() - secondDate.getTime()) / (1000 * 60 * 60 * 24));
    }

    /**
     * 校验日期是否合法
     *
     * @param: sourceDate
     * @Date in 11:32 on 2017/12/28.
     * @version V1.0
     **/
    public static boolean validateDate(String sourceDate) {
        boolean validate = false;
        if (!StringUtils.isEmpty(sourceDate)) {
            DAY_FORMAT.setLenient(false);
            try {
                DAY_FORMAT.parse(sourceDate);
            } catch (ParseException parseEx) {
                parseEx.printStackTrace();
            }
            validate = true;
        }
        return validate;
    }

    /**
     * 字符串日期比较大小
     *
     * @param: startDate 开始日期
     * @param: endDate   结束日期
     **/
    public static boolean compareDate(SimpleDateFormat format, String startDate, String endDate) {
        boolean result = false;
        try {
            Date sDate = format.parse(startDate);
            Date eDate = format.parse(endDate);
            if (!eDate.before(sDate)) {
                result = true;
            }
        } catch (ParseException parseEx) {
            parseEx.printStackTrace();
        }
        return false;
    }

    /**
     * 获取指定日期是周几
     *
     * @param date  日期
     * @param isEng false表示英文星期，true表示中文星期
     */
    public static String getWeekOfDate(Date date, Boolean isEng) {
        String[] weekDays;
        if (isEng) {
            weekDays = new String[]{"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};
        } else {
            weekDays = new String[]{"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week < 0) {
            week = 0;
        }
        return weekDays[week];
    }

    /**
     * 获取具体指定的年月日
     *
     * @param type 年、月、日
     */
    public static String getCalendarValue(String type) {
        Calendar calendar = Calendar.getInstance();
        int result = "年".equals(type)
                ? calendar.get(Calendar.YEAR)
                : "月".equals(type)
                ? calendar.get(Calendar.MONTH) + 1 : "日".equals(type) ? calendar.get(Calendar.DAY_OF_MONTH) : -1;
        return String.valueOf(result > 9 ? result : "0".concat(String.valueOf(result)));
    }

    /**
     * 月初
     */
    public static String getFirstDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getMinimum(Calendar.DATE));
        return DAY_FORMAT.format(calendar.getTime());
    }

    /**
     * 指定月份最后一天
     */
    public static String getFirstDayOfMonth(int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getMinimum(Calendar.DATE));
        return DAY_FORMAT.format(calendar.getTime());
    }

    /**
     * 月末
     */
    public static String getLastDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DATE));
        return DAY_FORMAT.format(calendar.getTime());
    }

    /**
     * 周枚举
     **/
    public enum WeekEnum {
        周一("MON"),
        周二("TUE"),
        周三("WED"),
        周四("THU"),
        周五("FRI"),
        周六("SAT"),
        周日("SUN");
        private final String value;

        WeekEnum(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 判断某一时间点是否在某一时间段内
     *
     * @param time         System.currentTimeMillis()
     * @param beginDateStr 开始时间 yyyy-MM-dd hh:mm:ss
     * @param endDateStr   结束时间 yyyy-MM-dd hh:mm:ss
     * @return
     */
    public static boolean isInDate(String time, String beginDateStr, String endDateStr) throws ParseException {
        boolean result = false;
        // 需要比较的时间
        Date compareDate = TIME_FORMAT.parse(time);
        Date beginDate = TIME_FORMAT.parse(beginDateStr);
        Date endDate = TIME_FORMAT.parse(endDateStr);
        if (compareDate.getTime() >= beginDate.getTime()
                && compareDate.getTime() <= endDate.getTime()) {
            result = true;
        }
        return result;
    }

    /**
     * Date 转 Calendar
     *
     * @param date 时间
     * @return instance Calendar
     */
    public static Calendar data2Calendar(Date date) {
        Calendar instance = null;
        if (date != null) {
            instance = Calendar.getInstance();
            instance.setTime(date);
        }
        return instance;
    }

    /**
     * 时间 字符串 转 Calendar
     *
     * @param dateStr 字符串
     * @param pattern 时间格式
     * @return instance Calendar
     */
    public static Calendar string2Calendar(final String dateStr, final String pattern) throws ParseException {
        Date date = string2Date(dateStr, pattern);
        Calendar instance = null;
        if (date != null) {
            instance = Calendar.getInstance();
            instance.setTime(date);
        }
        return instance;
    }

    /**
     * 比教两个时间相差年份
     *
     * @param fromDate 开始时间
     * @param toDate   结束时间
     * @return 年份精确到两位数
     */
    public static String yearCompare(Date fromDate, Date toDate) {
        BigDecimal years = BigDecimal.ZERO;
        if (fromDate != null && toDate != null) {
            Calendar timeFrom = Calendar.getInstance();
            timeFrom.setTime(fromDate);
            Calendar timeTo = Calendar.getInstance();
            timeTo.setTime(toDate);
            //只要年月
            int fromYear = timeFrom.get(Calendar.YEAR);
            int fromMonth = timeFrom.get(Calendar.MONTH);
            int toYear = timeTo.get(Calendar.YEAR);
            int toMonth = timeTo.get(Calendar.MONTH);
            int year = toYear - fromYear;
            int month = toMonth - fromMonth;
            BigDecimal bigYear = new BigDecimal(year);
            BigDecimal bigMonth = new BigDecimal(month);
            //返回2位小数，并且四舍五入
            years = bigYear.add(bigMonth.divide(new BigDecimal(12), 1, RoundingMode.HALF_UP));
        }
        DecimalFormat decimalFormat = new DecimalFormat("######0.0");
        return decimalFormat.format(years);
    }

}
