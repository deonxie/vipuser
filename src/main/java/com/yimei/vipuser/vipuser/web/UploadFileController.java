package com.yimei.vipuser.vipuser.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.yimei.vipuser.vipuser.service.account.ShiroDbRealm.ShiroUser;


@Controller
@RequestMapping("/updload")
public class UploadFileController {
	private static Logger logger = LoggerFactory.getLogger(UploadFileController.class);
	
	@RequiresAuthentication
	@RequestMapping(value="",method = RequestMethod.POST,produces={"application/json; charset=UTF-8"})
	@ResponseBody
	public String[] saveFile(@RequestParam(value="file",required=true)CommonsMultipartFile file,
			HttpServletRequest request){
		InputStream input = null;
		FileOutputStream output = null;
		if(null == file ||file.getSize()<=0)
			return new String[]{"false","无上传文件"};
		
		String fileType = file.getFileItem().getName().substring(file.getFileItem().getName().lastIndexOf("."));
		String acceptType = ".png;.jpg;.gif;.jepg";
		if(!acceptType.toUpperCase().contains(fileType.toUpperCase()+";")){
			return new String[]{"false","上传文件类型不支持."};
		}
		
		long fileSize = file.getSize();
		int maxSize = 5;
		if((fileSize>>20)>maxSize){
			return new String[]{"false","上传文件太大，超过"+maxSize+"M."};
		}
		String path = null;
		/**获取登录用户*/
//	    Subject subject = SecurityUtils.getSubject();
//	    if(subject !=null){
//	    	Object obj = subject.getPrincipal();
//	    	if(obj !=null && obj instanceof ShiroUser)
//	    		path = ((ShiroUser)obj).loginName;
//	    }
		path = path==null ? UUID.randomUUID().toString()+fileType : path+fileType;
		System.out.println(path);
		try {
			output = new FileOutputStream(getRootPath(request)+path);
			input = file.getInputStream();
			byte[] data = new byte[1024];
			int length = 0;
			while((length = input.read(data))>-1){
				output.write(data, 0, length);
			}
			return new String[]{"true",path};
		} catch (IOException e) {
			logger.error("文件保存失败", e);
		}finally{
			if(null != input){
				try {
					input.close();
				} catch (IOException e) {
					logger.error("input文件关闭异常", e);
				}
			}if(null != output){
				try {
					output.close();
				} catch (IOException e) {
					logger.error("output文件关闭异常", e);
				}
			}
		}
		return new String[]{"false","上传文件失败"};
	}
	
	@RequiresAuthentication
	@RequestMapping(value="frm",method = RequestMethod.POST,produces={"application/json; charset=UTF-8"})
	public String saveFileFrm(@RequestParam(value="file",required=true)CommonsMultipartFile file,
			HttpServletRequest request){
		InputStream input = null;
		FileOutputStream output = null;
		if(null == file ||file.getSize()<=0){
			request.setAttribute("result", false);
			request.setAttribute("message", "无上传文件");
			return "uploadForm"; 
		}
		
		String fileType = file.getFileItem().getName().substring(file.getFileItem().getName().lastIndexOf("."));
		String acceptType = ".png;.jpg;.gif;.jpeg;";
		if(!acceptType.toUpperCase().contains(fileType.toUpperCase()+";")){
			request.setAttribute("result", false);
			request.setAttribute("message", "上传文件类型不支持.");
			return "uploadForm"; 
		}
		
		long fileSize = file.getSize();
		int maxSize = 5;
		if((fileSize>>20)>maxSize){
			request.setAttribute("result", false);
			request.setAttribute("message", "上传文件太大，超过"+maxSize+"M.");
			return "uploadForm"; 
		}
		String path = null;
		/**获取登录用户*/
//	    Subject subject = SecurityUtils.getSubject();
//	    if(subject !=null){
//	    	Object obj = subject.getPrincipal();
//	    	if(obj !=null && obj instanceof ShiroUser)
//	    		path = ((ShiroUser)obj).loginName;
//	    }
		path = path==null ? UUID.randomUUID().toString()+fileType : path+fileType;
		try {
			output = new FileOutputStream(getRootPath(request)+path);
			input = file.getInputStream();
			byte[] data = new byte[1024];
			int length = 0;
			while((length = input.read(data))>-1){
				output.write(data, 0, length);
			}
			request.setAttribute("result", true);
			request.setAttribute("message", path);
			return "uploadForm"; 
		} catch (IOException e) {
			logger.error("文件保存失败", e);
		}finally{
			if(null != input){
				try {
					input.close();
				} catch (IOException e) {
					logger.error("input文件关闭异常", e);
				}
			}if(null != output){
				try {
					output.close();
				} catch (IOException e) {
					logger.error("output文件关闭异常", e);
				}
			}
		}
		return "uploadForm";
	}
	
	@RequestMapping(value="/frm",method = RequestMethod.GET)
	public String frm(){
		return "uploadForm";
	}
	
	private String getRootPath(HttpServletRequest request){
		return request.getServletContext().getRealPath("/static/upload/head/")+File.separator;
	} 
}
