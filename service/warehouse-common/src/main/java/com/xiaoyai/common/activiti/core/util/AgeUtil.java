package com.xiaoyai.common.activiti.core.util;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.IntStream;

/**
 * @author xieyu
 * @date 2021-06-08
 */
public class AgeUtil {
    private static Logger logger = LoggerFactory.getLogger(Java110StringConvert.class);
    // 身份证校验码
    private static final int[] COEFFICIENT_ARRAY = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};

    // 身份证号的尾数规则
    private static final String[] IDENTITY_MANTISSA = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};

    private static final String IDENTITY_PATTERN = "^[0-9]{17}[0-9Xx]$";

    /**
     * 15位身份证号
     */
    private static final Integer FIFTEEN_ID_CARD=15;
    /**
     * 18位身份证号
     */
    private static final Integer EIGHTEEN_ID_CARD=18;

    public static int evaluate(String sfzjh) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int age = 0;

        try {
            Date date = new Date();
            if (!StringUtils.isEmpty(sfzjh) && isLegalPattern(sfzjh)) {
                //15位身份证号
                if (sfzjh.length() == 15) {
                    // 身份证上的年份(15位身份证为1980年前的)
                    String uyear = "19" + sfzjh.substring(6, 8);
                    // 身份证上的月份
                    String uyue = sfzjh.substring(8, 10);
                    // 当前年份
                    String fyear = sdf.format(date).substring(0, 4);
                    // 当前月份
                    String fyue = sdf.format(date).substring(5, 7);
                    if (Integer.parseInt(uyue) < Integer.parseInt(fyue)) {
                        age = Integer.parseInt(fyear) - Integer.parseInt(uyear) + 1;
                        // 当前用户还没过生
                    } else {
                        age = Integer.parseInt(fyear) - Integer.parseInt(uyear);
                    }
                    //18位身份证号
                } else if (sfzjh.length() == 18) {
                    // 身份证上的年份
                    String year = sfzjh.substring(6).substring(0, 4);
                    // 身份证上的月份
                    String yue = sfzjh.substring(10).substring(0, 2);
                    // 当前年份
                    String fyear = sdf.format(date).substring(0, 4);
                    // 当前月份
                    String fyue = sdf.format(date).substring(5, 7);
                    // 当前月份大于用户出身的月份表示已过生日
                    if (Integer.parseInt(yue) < Integer.parseInt(fyue)) {
                        age = Integer.parseInt(fyear) - Integer.parseInt(year) + 1;
                        // 当前用户还没过生日
                    } else {
                        age = Integer.parseInt(fyear) - Integer.parseInt(year);
                    }
                }
            }
        } catch (NumberFormatException e) {
            logger.error("身份证号错误,号码为：" + sfzjh);
        }

        return age;
    }

    /**
     * 获取出生日期  yyyy年MM月dd日
     * @param IDCard
     * @return
     */
    public static String getBirthday(String IDCard){
        String birthday="";
        String year="";
        String month="";
        String day="";
        if (StringUtils.isNotBlank(IDCard)){
            //15位身份证号
            if (IDCard.length() == FIFTEEN_ID_CARD){
                // 身份证上的年份(15位身份证为1980年前的)
                year = "19" + IDCard.substring(6, 8);
                //身份证上的月份
                month = IDCard.substring(8, 10);
                //身份证上的日期
                day= IDCard.substring(10, 12);
                //18位身份证号
            }else if(IDCard.length() == EIGHTEEN_ID_CARD){
                // 身份证上的年份
                year = IDCard.substring(6).substring(0, 4);
                // 身份证上的月份
                month = IDCard.substring(10).substring(0, 2);
                //身份证上的日期
                day=IDCard.substring(12).substring(0,2);
            }
            birthday=year+"-"+month+"-"+day;
        }
        return birthday;
    }

    public static boolean isLegalPattern(String identity) {
        if (identity == null) {
            return false;
        }

        if (identity.length() != 18) {
            return false;
        }

        if (!identity.matches(IDENTITY_PATTERN)) {
            return false;
        }

        char[] chars = identity.toCharArray();
        long sum = IntStream.range(0, 17).map(index -> {
            char ch = chars[index];
            int digit = Character.digit(ch, 10);
            int coefficient = COEFFICIENT_ARRAY[index];
            return digit * coefficient;
        }).summaryStatistics().getSum();

        // 计算出的尾数索引
        int mantissaIndex = (int) (sum % 11);
        String mantissa = IDENTITY_MANTISSA[mantissaIndex];

        String lastChar = identity.substring(17);
        if (lastChar.equalsIgnoreCase(mantissa)) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        try {
            String birthday = AgeUtil.getBirthday("110101197201143441") + " 23:59:59";

            Calendar calendar = Calendar.getInstance();

            Date birthdayDate = DateUtil.getDateFromString(birthday, DateUtil.DATE_FORMATE_STRING_A);
            calendar.setTime(birthdayDate);

            calendar.add(Calendar.YEAR, 50);
            Date date1 = calendar.getTime();

            // 提前 advanceDays 天数
            String datetime = DateUtil.getNeedDateByFormat(birthdayDate, DateUtil.DATE_FORMATE_STRING_A, -180);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            calendar.setTime(sdf.parse(datetime));
            calendar.add(Calendar.YEAR, 50);
            Date date2 = calendar.getTime();


            System.out.println(cn.hutool.core.date.DateUtil.isIn(new Date(), date2, date1));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
