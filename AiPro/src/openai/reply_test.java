package openai;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class reply_test {
	// post 방식으로 요청
	public static String apiPost(String sendData) throws Exception
	{
		URL url = null;
		String readLine = null;
		StringBuilder buffer = null;
		OutputStream outputStream = null;
		BufferedReader bufferedReader = null;
		BufferedWriter bufferedWriter = null;
		HttpURLConnection urlConnection = null;
		
		int connTimeout = 30000; //접속기다리기 30초 제한
		int readTimeout = 30000; //json받기 시작해서 다 받기 30초 제한
		
		//String apiUrl = "https://api.openai.com/v1/chat/completions"; // openapi 사용 url
		//String API_KEY = "Bearer "+ System.getenv("OPENAI_API_KEY"); // openapi 방식일때 key값 필요 
		String apiUrl = "http://localhost:1234/v1/chat/completions"; // 로컬에 돌아가는 lm-studio 방식
				
		String retStr = "";
		try {
			url = new URL(apiUrl);
			
			urlConnection = (HttpURLConnection)url.openConnection(); // 커넥션 열어줌
			urlConnection.setRequestMethod("POST"); // post 메소드 방식
			urlConnection.setConnectTimeout(connTimeout); // 커넥션 타임아웃 30초로 지정 해둠
			urlConnection.setReadTimeout(readTimeout); // 커넥션 타임아웃 30초로 지정 해둠
			
			//두개의 헤더 보내기..
			//urlConnection.setRequestProperty("Authorization", API_KEY); // openapi 방식
			// 헤더셋팅
			urlConnection.setRequestProperty("Content-Type", "application/json");
			urlConnection.setDoOutput(true);
			urlConnection.setInstanceFollowRedirects(true);
			
			outputStream = urlConnection.getOutputStream();
			
			bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
			//여기 sendData가 보내딜 json
			bufferedWriter.write(sendData);
			bufferedWriter.flush();
			
			buffer = new StringBuilder();

			// 커넥션이 연결될으면 실행
			if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK)
			{
				bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"UTF-8"));
				while((readLine = bufferedReader.readLine()) != null)
					buffer.append(readLine).append("\n");
				
				//System.out.println("= 응답이 왔습니다. =========");
				// 어떤 형식의 json데이터 확인 필요

				// json 형식의 데이터이기 때문에 키값을 확인하여 필요한 리턴값 가져옴옴
				JSONParser parser = new JSONParser();
				JSONObject jsonObject = (JSONObject)parser.parse(buffer.toString());
				
				JSONArray choicesData = (JSONArray)jsonObject.get("choices");
				
				JSONObject firstbject = (JSONObject)choicesData.get(0);
				JSONObject msgObject = (JSONObject)firstbject.get("message");
											
				
				String content = msgObject.get("content").toString();
				if(content.contains("1")) // 답변이 1이면 긍정적 판단
					content = "1";
				else if(content.contains("2")) // 답변이 2이면 부정적 판단
					content = "2";
				else // 그 이외의 판단
					content = "3";
				
				retStr = content;				
			}
			else
			{
				retStr = String.format("Error Code : %s/n 내용",
						urlConnection.getResponseCode(),
						urlConnection.getResponseMessage());
			}
		}
		catch (Exception ex) 
		{
			// TODO: handle exception
			ex.printStackTrace();
			retStr = "Exception발생 1";
		}
		finally
		{
			try 
			{
				if(bufferedWriter != null) bufferedWriter.close();
				if(outputStream != null) outputStream.close();
				if(bufferedReader != null) bufferedReader.close();
			}
			catch (Exception e) 
			{
				// TODO: handle exception
				e.printStackTrace();
				retStr = "Exception발생 2";
			}
		}
		
		urlConnection.disconnect();
		return retStr;
	}
	public static String reqData(String msg) 
	{
		// ai에게 물어볼 데이터 셋팅팅
		String send_data = """
				{
					"model" : "llama-3.2-1b-instruct",
					"messages" :
						[
				        	{\"role\":\"system\", \"content\": \"You can speak Korean very well, 너는 한국어로 이야기하는 인공지능이야.\"},
				        	{\"role\":\"user\", \"content\": \"%s\"}
				    	],        
					"temperature" : 0.5
				}				
				""";
		
		msg = msg.replaceAll("\n"," ");
		return String.format(send_data, msg);
	}
	public static String[] replyDatas()
	{
		List<String> result =  new ArrayList<String>();
		Connection con = null; // 커넥션 객체 생성
		Statement stmt = null; // 스테이트 먼트 객체 생성
		ResultSet rset = null; // 리절트 셋 객체 생성
		try 
		{
			// mysql jdbc 드라이버 가져오기
			Class.forName("com.mysql.cj.jdbc.Driver");
						
			// 디비와 커넥션을 생성해준다
			// 커넥트 정보에는 디비 주소와 접속할 디비명, 접속할 계정, 패스워드를 넣어준다
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:33062/kopoctc?useSSL=false", "root", "0000");
								
			// statement를 생성해준다
			// 커넥션으로 연결한 객체에게 쿼리 작업을 실행하기 위한 객체이다
			// statement는 정적인 쿼리를 처리할 수 있다
			stmt = con.createStatement();
						
			// 쿼리 문자열 변수
			String QueryStr;			
						
			// 특정 조건 쿼리
			QueryStr = "SELECT * FROM kopoctc.reply_text";
						
			// select 쿼리 문자열 담아서 
			// executeQuery인자로 넘겨 리절트 셋 리턴
			rset = stmt.executeQuery(QueryStr);	
			
			int selectRowCnt = 1; // 조회 row 카운트용 변수
			String tmp = "";
			while(rset.next()){ // 리절트 셋으로 계속 다음을 읽어 오는것, 읽어 올것이 없으면 반복문 종료
				tmp = rset.getString("id") + "/" + rset.getString("reply");
				result.add(tmp);
				++selectRowCnt; // 출력후 row 카운트 + 1
			}
								
			// 조회 결과 row 카운트 출력
			System.out.println("row Count : " + selectRowCnt); 
		} catch (SQLException e) { // Sql관련 예외 블럭을 잡아준다
			// sql관련 예외블럭 메세지 출력
			System.out.println("select 데이터베이스 오류 : " + e.getMessage()); // DB 예외 발생 프린트 
			System.out.println("select 데이터베이스 오류번호 : " + e.getErrorCode());// sql관련 예외블럭 에러 코드 출력 1146 출력
		} catch (ClassNotFoundException e) {
			// 클래스를 찾을 수 없는 예외 블럭
			System.out.println("select 드라이버를 찾을 수 없습니다: " + e.getMessage()); // jdbc 찾지 못할 때 출력
		} finally {			
			try {
				// 닫기는 역순으로 진행.
				
				// 리절트 셋이 있으면 닫아준다
				if(rset != null)rset.close();				
				// statement가 있으면 닫아준다
				if(stmt != null)stmt.close();
				// 커넥션이 있으면 닫아 준다
				if(con != null)con.close();		
				
			} catch (Exception e) {
				// 사용한 sql연결 객체들을 닫을때 발생하는 예외 블럭을 잡아준다
				System.out.println("select 리소스를 닫는 도중 오류 발생 : " + e.getMessage()); // 예외 메세지 출력
			}									
		}		
		return result.toArray(new String[0]);
	}	
	public static void reply_update(String id, String answer)
	{
		Connection con = null; // 커넥션 객체 생성
		Statement stmt = null; // 스테이트 먼트 객체 생성
		try 
		{
			// mysql jdbc 드라이버 가져오기
			Class.forName("com.mysql.cj.jdbc.Driver");
						
			// 디비와 커넥션을 생성해준다
			// 커넥트 정보에는 디비 주소와 접속할 디비명, 접속할 계정, 패스워드를 넣어준다
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:33062/kopoctc?useSSL=false", "root", "0000");
								
			// statement를 생성해준다
			// 커넥션으로 연결한 객체에게 쿼리 작업을 실행하기 위한 객체이다
			// statement는 정적인 쿼리를 처리할 수 있다
			stmt = con.createStatement();
						
			// 쿼리 문자열 변수
			String[] answer_data = answer.split("/");
								
			if(answer_data[0] == "1")
			{
				stmt.executeUpdate("update kopoctc.reply_text set good = good + 1 where id = "+id);
				System.out.printf("긍정적 댓글로 판단 : %s\n", answer_data[0]);
			}				
			else if(answer_data[0] == "2")
			{
				stmt.executeUpdate("update kopoctc.reply_text set bad = bad + 1 where id = "+id);
				System.out.printf("부정적 댓글로 판단 : %s\n", answer_data[0]);
			}
			else
				System.out.printf("그 외 판단 : %s\n", answer_data[0]);
												
		} catch (SQLException e) { // Sql관련 예외 블럭을 잡아준다
			// sql관련 예외블럭 메세지 출력
			System.out.println("update 데이터베이스 오류 : " + e.getMessage()); // DB 예외 발생 프린트 
			System.out.println("update 데이터베이스 오류번호 : " + e.getErrorCode());// sql관련 예외블럭 에러 코드 출력 1146 출력
		} catch (ClassNotFoundException e) {
			// 클래스를 찾을 수 없는 예외 블럭
			System.out.println("update 드라이버를 찾을 수 없습니다: " + e.getMessage()); // jdbc 찾지 못할 때 출력
		} finally {			
			try {
				// 닫기는 역순으로 진행.
							
				// statement가 있으면 닫아준다
				if(stmt != null)stmt.close();
				// 커넥션이 있으면 닫아 준다
				if(con != null)con.close();		
				
			} catch (Exception e) {
				// 사용한 sql연결 객체들을 닫을때 발생하는 예외 블럭을 잡아준다
				System.out.println("update 리소스를 닫는 도중 오류 발생 : " + e.getMessage()); // 예외 메세지 출력
			}									
		}
	}
	
	public static void main(String[] args) {
		int cnt = 0;
		System.out.printf("= 요청중입니다. ====\n");
		
		String[] reply_datas = replyDatas();
		
		for(int i = 0; i < reply_datas.length; i++)
		{
			String send_data = reqData("["+reply_datas[i]+"]"+"""
					 이 문장이 좋은 감정이면 1로 대답, 나쁜 감정이면 2로 대답, 잘모르겠으면 3으로 대답해줘 그밖에 다른 대답은 필요 없어
					난 단답형을 좋아해
					"""					
					);
						 
			try {
				reply_update(reply_datas[i].split("/")[0], apiPost(send_data));		
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			cnt++;
		}				
		System.out.printf("= 처리 =" + cnt);		
	}

}
