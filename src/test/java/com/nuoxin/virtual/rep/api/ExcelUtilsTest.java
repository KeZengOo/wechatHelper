package com.nuoxin.virtual.rep.api;

import com.nuoxin.virtual.rep.api.utils.CSVUtils;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class ExcelUtilsTest {

    public static void main(String [] args) throws IOException {
//        File csv = new File("C:\\Users\\27168\\Desktop\\短信推送\\第4次补推.csv");  // CSV文件路径
//        BufferedReader br = null;
//        try {
//            br = new BufferedReader(new FileReader(csv));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        String line = "";
//        String everyLine = "";
//        try {
//            List<String> allString = new ArrayList<>();
//            while ((line = br.readLine()) != null){  //读取到的内容给line变量
//                everyLine = line;
//                System.out.println(everyLine);
//                allString.add(everyLine);
//            }
//
//            System.out.println("csv表格中所有行数："+allString.size());
//        } catch (IOException e){
//            e.printStackTrace();
//        }



        String[] headers = new String[]{"talker","content","createTime","imgPath","isSend","type"};

        String csvPath = "C:\\Users\\27168\\Desktop\\微信聊天消息导出\\parksihoo_message_file(1).csv";
        List<CSVRecord> records = CSVUtils.readCSV(csvPath,headers);

        System.out.println(records);

    }

}
