package com.nuoxin.virtual.rep.api;

import com.nuoxin.virtual.rep.api.utils.CSVUtils;
import com.nuoxin.virtual.rep.api.utils.DateUtil;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class ExcelUtilsTest {

    public static void main(String [] args) throws IOException {
        long time = new Date().getTime();
        System.out.println(time);

        Date date = new Date(time);

        System.out.println(DateUtil.getDateMillisecondString(date));

    }

}
