package com.f.mml.system;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

public class test {


    public static void main(String [] args) throws UnsupportedEncodingException {

        String string ="2019-10-11T10:51:10";
        LocalDateTime day1 = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.MAX);//当天零点

//        Random random = new Random();
//        Integer aa = random.nextInt();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
//        String code = LocalDateTime.now().format(formatter);



        System.out.println(UUID.randomUUID());

        String body = new String("");

        System.out.println(body);
//        ZonedDateTime parse = ZonedDateTime.parse(string);
//
//        DateUtil util = new DateUtil();
//        util.initialDateListNormol();
//        LocalDateTime date = LocalDateTime.parse(string);
//        System.out.println(date.toLocalDate());
//        Map<String,Integer> map = new HashMap<String,Integer>();
//        System.out.println(parse.toString());
//
//        string ="2019-10-10 13:59:55";
//        LocalDateTime date = LocalDateTime.parse(string);
//
//        //结果是2018-12-07T09:33:38Z
//        ZoneId zone = parse.getZone();
//        System.out.println(zone);



    }
}
