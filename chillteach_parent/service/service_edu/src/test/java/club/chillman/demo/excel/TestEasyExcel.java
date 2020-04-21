package club.chillman.demo.excel;

import com.alibaba.excel.EasyExcel;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestEasyExcel {
    @Test
    public void testWrite(){
        //实现excel写操作
        //设置写入的路径
        String filename = "D:/1/1.xls";
        //调用easyExcel的方法实现写操作
        EasyExcel.write(filename,DemoData.class).sheet("学生列表").doWrite(getData());
    }
    @Test
    public void testRead(){
        //实现excel读操作
        String filename = "D:/1/1.xls";
        EasyExcel.read(filename,DemoData.class,new ExcelListener()).sheet().doRead();
    }


    //创建方法返回list集合
    private static List<DemoData> getData(){
        List<DemoData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setSno(i);
            data.setSname("lucy"+i);
            list.add(data);
        }
        return list;
    }
}
