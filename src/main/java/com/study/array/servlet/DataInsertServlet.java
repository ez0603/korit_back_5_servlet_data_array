package com.study.array.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.study.array.entity.DataObj;
import com.study.array.service.DataService;


@WebServlet("/data/addition")
public class DataInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DataService dataService;
	
	public DataInsertServlet() {
		dataService = DataService.getInstance();
	}
  
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
// 자바스크립트 객체 -> json -> 자바 객체 데이터
		StringBuilder json = new StringBuilder(); // StringBuilder 문자열을 합쳐주는것
		String requestData = null;
		
		/*
		 *  "{
		 *  	"id":1
		 *  	"content":"aaa"
		 *  }"
		 *  StringBuilder에 문자열이 쌓임
		 */ 
		
		// 문자열 -> 스트림(통로) json은 무조건 스트림
		BufferedReader reader = request.getReader(); 
		while((requestData = reader.readLine()) != null) { // requestData가 null이 되면 멈춤
			json.append(requestData); // 한줄씩 들고올때마다 
		}
		
		Gson gson = new Gson();
		
		DataObj dataObj = gson.fromJson(json.toString(), DataObj.class); // gson을 쓰면 json.toString()을 DataObj.class 형태로 만들어줌
		
		System.out.println(dataObj);
		
		System.out.println(gson.toJson(dataObj));
		int responseBody = dataService.addData(dataObj);
		
		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("data", responseBody);
		responseMap.put("responseMessage", "데이터 추가 완료");
		
		response.setContentType("application/json"); // Map의 key value 형태를 json으로 바꿔줌
		response.setStatus(201);
		response.getWriter().println(gson.toJson(responseMap)); // 자바의 객체를 json형식으로 바꿔줌
	}

}
