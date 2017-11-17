package com.zhou.cpu.serviceImp;

import com.zhou.cpu.action.cpu;
import com.zhou.cpu.service.CpuService;
import com.zhou.pojo.MonitorInfoBean;

public class CpuServiceImp implements CpuService{

	@Override
	public double getCupnum() throws Exception {
		cpu c =new cpu();
	    MonitorInfoBean monitorInfo = c.getMonitorInfoBean();
	    System.out.println("cpu占有率=" + monitorInfo.getCpuRatio());
	    Double num = monitorInfo.getCpuRatio();
	    return num;
	}
	
	
	
}
