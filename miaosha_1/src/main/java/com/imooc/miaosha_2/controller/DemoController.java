package com.imooc.miaosha_2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.imooc.miaosha_2.result.CodeMsg;
import com.imooc.miaosha_2.result.Result;

@Controller
@RequestMapping("/demo")
public class DemoController {
		//1.rest api json输出， 2.页面模板
	 	@RequestMapping("/tset")
	    @ResponseBody
	    String test() {
	        return "Hello World!";
	    }
	 	//1.rest api json输出 2.页面
	 	@RequestMapping("/hello")
	    @ResponseBody
	    public Result<String> hello() {
	 		return Result.success("hello,imooc");
	       // return new Result(0, "success", "hello,imooc");
	    }
	 	
	 	@RequestMapping("/helloError")
	    @ResponseBody
	    public Result<String> helloError() {
	 		return Result.error(CodeMsg.SERVER_ERROR);
	 		//return new Result(500102, "XXX");
	    }

	    //页面模板
	 	@RequestMapping("/thymeleaf")
	    public String  thymeleaf(Model model) {
	 		model.addAttribute("name", "闻涛");//name占位符号，传过去的值为Joshua
	 		return "hello";///templates/hello.html
	    }
	 	
}
