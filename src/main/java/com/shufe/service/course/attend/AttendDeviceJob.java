package com.shufe.service.course.attend;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.shufe.service.BasicService;

public class AttendDeviceJob extends BasicService {    
    public void updateKqjzt() {
    	//SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
    	//System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
    	utilDao.executeUpdateNamedQuery("updateKqjztTo1", (Map)null);
    	utilDao.executeUpdateNamedQuery("updateKqjztTo0", (Map)null);
    }
}
