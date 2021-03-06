package com.usc.app.activiti.resource;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import com.usc.app.activiti.service.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author lwp
 */
@RestController
@RequestMapping(value = "/act/process", produces = "application/json;charset=UTF-8")
public class ProcessResource {

	@Autowired
	ProcessService processService;
	
	/**
	 * *获取流程定义(版本号，流程名称，流程图)
	 * @return 流程定义
	 */
	@GetMapping("/getProcdefProcess")
	public Object getProcdefProcess() {
		return processService.getProcdefProcess();
	}
	/**
	 * *获取流程定义(版本号，流程名称，流程图)
	 * @return 根据流程下绑定的对象筛选展示的流程
	 */
	@PostMapping("/getProcdefProcessByProcdefId")
	public Object getProcdefProcessByProcdefId(@RequestBody String queryParam) {
		return processService.getProcdefProcessByProcdefId(queryParam);
	}
	/**
	 **流程的挂起
	 */
	@PostMapping("/suspension/{id}")
	public Object suspension(@PathVariable("id") String id) {
		return processService.suspension(id);
	}
	/**
	 **流程的激活
	 */
	@PostMapping("/activation/{id}")
	public Object activation(@PathVariable("id") String id) {
		return processService.activation(id);
	}

	/**
	 * 获取流程定义的设计图
	 * @param id 流程定义id
	 * @param response 图片
	 */
	 @RequestMapping(value = "/dynamicPng/{id}", method=RequestMethod.GET)
	 public void getProcessPicture(@PathVariable("id") String id,final HttpServletResponse response) throws IOException{
		processService.getProcessPicture(id,response);
	}
	/**
	 * 删除部署的流程定义以及正在进行的流程
	 * @param deploymentId 流程定义id
	 * @return 状态
	 */
	@PostMapping("/deleteByDeploymentId/{deploymentId}")
	public Object deleteByDeploymentId(@PathVariable("deploymentId") String deploymentId) {
		return processService.deleteByDeploymentId(deploymentId);
	}

	/**
	 * 启动流程实例
	 * @param queryParam(id,userName,selectedRows) 流程id,用户名，选中的数据
	 */
	@PostMapping("/startProcess")
	public Object startProcess (@RequestBody String queryParam) throws IOException {
		return processService.startProcess(queryParam);
	}

	/**
	 * 获取运行中的流程
	 * @return 运行中流程集合
	 */
	@PostMapping("getRunProcess")
	public Object getRunProcess() {
		return processService.getRunProcess();
	}

    /**
     * 获取标红节点流程图
     * @param processInstanceId 流程实例id
     * @param response 图片流
     */
	@RequestMapping(value = "getActivityPng/{processInstanceId}", method=RequestMethod.GET)
	public void getActivityPng(@PathVariable("processInstanceId") String processInstanceId,final HttpServletResponse response) throws IOException {
		processService.getActivityPng(processInstanceId,response);
	}

	/**
	 * 获取运行流程详情中的流转信息
	 * @param queryParam （processInstanceId）流程实例id
	 * @return 流转信息集合
	 */
	@PostMapping("/getProcessReverseList")
	public Object getProcessReverseList(@RequestBody String queryParam) throws IOException {
		return processService.getProcessReverseList(queryParam);
	}


	/**
	 * 获取运行流程详情中的提交详情
	 * @param queryParam （processInstanceId）流程实例id
	 * @return 流转信息集合
	 */
	@PostMapping("/getProcessSubList")
	public Object getProcessSubList(@RequestBody String queryParam) throws Exception {
		return processService.getProcessSubList(queryParam);
	}

    /**
     * 终止（作废）流程
     * @param queryParam （processInstanceId） 流程实例id
     */
    @PostMapping("endProcess")
	public Object endProcess (@RequestBody String queryParam) throws IOException {
	    return processService.endProcess(queryParam);
    }

	/**
	 * 获取已结束的流程
	 * @return 结束流程集合
	 */
	@PostMapping("getEndProcess")
	public Object getEndProcess()  {
		return processService.getEndProcess();
	}

	/**
	 * 删除已结束流程
	 * @param queryParam（processInstanceId）流程实例id
	 */
	@PostMapping("deleteProcess")
	public Object deleteProcess(@RequestBody String queryParam) throws IOException {
		return processService.deleteProcess(queryParam);
	}

}
