package com.nuoxin.virtual.rep.api;

import bean.Departments;
import bean.Dept;
import com.nuoxin.virtual.rep.api.common.util.StringUtils;
import com.nuoxin.virtual.rep.api.dao.DoctorRepository;
import com.nuoxin.virtual.rep.api.entity.Doctor;
import com.nuoxin.virtual.rep.api.service.MasterDataService;
import com.nuoxin.virtual.rep.api.utils.excel.ExcelUtils;
import com.nuoxin.virtual.rep.api.web.controller.response.vo.Doc;
import com.nuoxin.virtual.rep.api.web.controller.response.vo.Hci;
import com.nuoxin.virtual.rep.api.web.controller.response.vo.Hcp;
import control.MatchDept;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fenggang on 12/14/17.
 *
 * @author fenggang
 * @date 12/14/17
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class DoctorCheckTest {

    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private MasterDataService masterDataService;

    @Test
    public void check() {
        List<Doctor> list = doctorRepository.findAll();
        ArrayList<Map<String, Object>> listMap = new ArrayList<>();
        for (Doctor doctor : list) {
////            map.put("eapp_id",doctor.getId());
//            map.put("eapp_name", doctor.getName());
//            map.put("eapp_hospital", doctor.getHospitalName());
//            map.put("eapp_dept", doctor.getDepartment());
//            Hcp hcp = masterDataService.getHcpByHciIdAndHcpName(doctor.getHospitalName(), doctor.getName());
//            if (hcp != null) {
//                map.put("hcp_name", hcp.getName());
//                map.put("hcp_id", hcp.getId());
//                map.put("hci_id", hcp.getHciId());
//            } else {
//                listMap.add(map);
//            }
            Hci hci = masterDataService.getHciByName(doctor.getHospitalName());
            if(hci!=null){
                doctor.setHospitalId(hci.getId());
                doctor.setHospitalName(hci.getName());
                doctorRepository.saveAndFlush(doctor);
            }else{
                HashMap<String, Object> map = new HashMap<>();
                map.put("eapp_id",doctor.getId());
                map.put("hospitalName",doctor.getHospitalName());
                listMap.add(map);
            }
        }
        String[] keys = {"hospitalName", "eapp_id"};
        String[] columns = {"hospitalName", "eapp_id"};
        try {
            String name = "eapp_hci";
            try {
                Workbook wb = ExcelUtils.createWorkBook(listMap, keys, columns);
                FileOutputStream fos = new FileOutputStream(new File("/Users/fenggang/Desktop/" + name + ".xls"));
                wb.write(fos);
                wb.close();
                fos.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deptTest() {

        List<Doctor> list = doctorRepository.findAll();
        ArrayList<Map<String, Object>> listMap = new ArrayList<>();
        for (Doctor doctor : list) {
            MatchDept matchDept = new MatchDept();
            if (StringUtils.isBlank(doctor.getDepartment())) {
                continue;
            }
            //matchDept.matchList(arrayList);//参数为list<Dept>,返回list<Department>
            Dept dept = new Dept();
            dept.setId(111);
            dept.setValue(doctor.getDepartment());
            Departments Departments = matchDept.matchOne(dept);//参数为Dept,返回Department
            doctor.setDepartment(Departments.getParent());
            doctorRepository.saveAndFlush(doctor);
        }

    }

    @Test
    public void mdmTest() {
        // 驱动程序名
        String driver = "com.mysql.jdbc.Driver";

        // URL指向要访问的数据库名scutcs
        String url = "jdbc:mysql://rm-2ze0233bsklz6e9p7o.mysql.rds.aliyuncs.com:3306/mdm_db";

        // MySQL配置时的用户名
        String user = "nuoxin_admin";

        // MySQL配置时的密码
        String password = "n@x%i0ns";
        List<Map<String,Object>> mapList = new ArrayList<>();

        try {
            // 加载驱动程序
            Class.forName(driver);

            // 连续数据库
            Connection conn = DriverManager.getConnection(url, user, password);

            if (!conn.isClosed()) {
                System.out.println("Succeeded connecting to the Database!");
            }

            // statement用来执行SQL语句
            Statement statement = conn.createStatement();

            List<Doctor> list = doctorRepository.findAll();
            ArrayList<Map<String, Object>> listMap = new ArrayList<>();
            for (Doctor doctor : list) {
                HashMap<String, Object> mapd = new HashMap<>();
                mapd.put("eapp_id",doctor.getId());
                mapd.put("eapp_name", doctor.getName());
                mapd.put("eapp_hospital", doctor.getHospitalName());
                mapd.put("eapp_dept", doctor.getDepartment());
                String hospitalSql = "select * from hci_alias where alias='"+doctor.getHospitalName()+"'";

                // 结果集
                ResultSet rsh = statement.executeQuery(hospitalSql);

                String hciId = "";
                while (rsh.next()) {
                    hciId = rsh.getString("hci_id");
                }
                rsh.close();
                // 要执行的SQL语句
                String sql = "select * from hcp where name='"+doctor.getName()+"'";
                if(StringUtils.isNotEmtity(hciId)){
                    sql = sql +" and hci_id = "+hciId;
                }

                // 结果集
                ResultSet rs = statement.executeQuery(sql);

                System.out.println("-----------------");
                System.out.println("执行结果如下所示:");
                System.out.println("-----------------");
                System.out.println(" id" + "\t" + " 姓名");
                System.out.println("-----------------");

                String name = null;

                while (rs.next()) {
                    Map<String,Object> map = new HashMap<>();
                    // 选择sname这列数据
                    map.putAll(mapd);
                    name = rs.getString("id");
                    map.put("hcp_id",rs.getString("id"));
                    map.put("hcp_name",rs.getString("name"));
                    map.put("hci_id",rs.getString("hci_id"));

                    // 首先使用ISO-8859-1字符集将name解码为字节序列并将结果存储新的字节数组中。
                    // 然后使用GB2312字符集解码指定的字节数组
                    name = new String(name.getBytes("ISO-8859-1"), "GB2312");

                    // 输出结果
                    System.out.println(rs.getString("name") + "\t" + name);



                    mapList.add(map);
                }

                rs.close();
            }
//            Doctor doctor = list.get(2);

            if(mapList!=null && !mapList.isEmpty()){
                for (Map<String, Object> map:mapList){
                    String dept = "";
                    String deptSql = "select * from hcp_social_info1 where id="+map.get("hcp_id");
                    ResultSet rsd = statement.executeQuery(deptSql);
                    while (rsd.next()) {
                        dept = rsd.getString("standard_dept");
                    }
                    rsd.close();
                    map.put("hcp_dept",dept);
                }
            }
            conn.close();

        } catch (ClassNotFoundException e) {


            System.out.println("Sorry,can`t find the Driver!");
            e.printStackTrace();


        } catch (SQLException e) {


            e.printStackTrace();


        } catch (Exception e) {


            e.printStackTrace();


        }



        String[] keys = {"eapp_name", "eapp_hospital", "eapp_dept", "hcp_name", "hcp_id", "hci_id","hcp_dept"};
        String[] columns = {"eapp_name", "eapp_hospital", "eapp_dept", "hcp_name", "hcp_id", "hci_id","hcp_dept"};
        try {
            String name = "eapp_hcp_dept1";
//            for (int i = 0; i < mapList.size(); i=i+1000) {
//                int size = 1000;
//                if(mapList.size()-i<1000){
//                    size = mapList.size()-i;
//
//                }
//            }
            List<Map<String,Object>> mList = mapList.subList(0,5000);
            try {
                Workbook wb = ExcelUtils.createWorkBook(mapList, keys, columns);
                FileOutputStream fos = new FileOutputStream(new File("/Users/fenggang/Desktop/" + name + ".xls"));
                wb.write(fos);
                wb.close();
                fos.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            name = "eapp_hcp_dept2";
            mList = mapList.subList(5000,mList.size());
            try {
                Workbook wb = ExcelUtils.createWorkBook(mList, keys, columns);
                FileOutputStream fos = new FileOutputStream(new File("/Users/fenggang/Desktop/" + name + ".xls"));
                wb.write(fos);
                wb.close();
                fos.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
