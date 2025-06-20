package Stock;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

import java.text.SimpleDateFormat;
import java.util.Date;

public class InsertTable {

	public static void main(String[] args){
		
		Connection kp29_con = null; // 커넥션 객체 생성
		PreparedStatement kp29_pstmt = null; // 동일 쿼리에 유용한 PreparedStateme 사용
		int kp29_tmp = 0; // row 카운트용 변수
		long kp29_startTime = System.currentTimeMillis(); // 시작 시작 출력용 시작 시간 기록
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss"); // 시간 형식 표현 객체 생성
		
		try {
			// mysql jdbc 드라이버 가져오기
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// 디비와 커넥션을 생성해준다
			// 커넥트 정보에는 디비 주소와 접속할 디비명, 접속할 계정, 패스워드를 넣어준다
			kp29_con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:33062/kopoctc?rewriteBatchedStatements=true", "root", "0000");
					
			// statement를 생성해준다
			// 커넥션으로 연결한 객체에게 쿼리 작업을 실행하기 위한 객체이다
			// ignore옵션으로 중복되지 않는 데이터만 삽입, 대량의 데이터를 핸들링 할때 고려 해야 하는 옵션이다
			// 변수값 대신 ? 로 99개 컬럼 형식 지정
			String kp29_QueryTxt = "insert ignore into stockinfo values (?,?,?,?,?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
			
			// statement는 정적인 쿼리를 처리할 수 있다
			kp29_pstmt = kp29_con.prepareStatement(kp29_QueryTxt); // 커넥션의 prepareStatement를 쿼리를 인자로 객체 생성
			kp29_con.setAutoCommit(false); // Transaction 실행시 커밋 옵션 일지 false, 밑에서 처리가 다 되었을때 다시 true로 변경
			try {// try 블록 시작
				// \은 특수문자이므로 \\두개를 써야한다
				// 해당 데이터는 주가10년치 csv파일 데이터
				File kp29_f = new File("C:\\Users\\USER\\eclipse-workspace\\Data\\StockDailyPrice.CSV");

				// 버퍼 리더 객체 생성
				BufferedReader kp29_br;
				// 큰 데이터를 읽어오기 때문에 버퍼로 읽어옴
				kp29_br = new BufferedReader(new FileReader(kp29_f));
				// 한줄 한줄 읽어올 문자열 데이터
				String kp29_readtxt;
										
				int kp29_LineCnt = 0; // row카운트용 0으로 초기화
				
				while((kp29_readtxt = kp29_br.readLine()) != null) { // 줄이 빈줄일때까지 while
					
					// 컬럼이 99이니까 강제 적으로 split 99로 해준다
					String[] kp29_field = kp29_readtxt.split(",", 99);			 		
					
					// 형식을 string으로 99필드를 전체적으로 insert
					// 컬럼 순서와 csv파일의 데이터 순서를 동일하게 맞춘뒤 실행
					kp29_pstmt.setString(1, kp29_field[0]);kp29_pstmt.setString(2, kp29_field[1]);kp29_pstmt.setString(3, kp29_field[2]);
					kp29_pstmt.setString(4, kp29_field[3]);kp29_pstmt.setString(5, kp29_field[4]);kp29_pstmt.setString(6, kp29_field[5]);
					kp29_pstmt.setString(7, kp29_field[6]);kp29_pstmt.setString(8, kp29_field[7]);kp29_pstmt.setString(9, kp29_field[8]);
					kp29_pstmt.setString(10, kp29_field[9]);kp29_pstmt.setString(11, kp29_field[10]);kp29_pstmt.setString(12, kp29_field[11]);
					kp29_pstmt.setString(13, kp29_field[12]);kp29_pstmt.setString(14, kp29_field[13]);kp29_pstmt.setString(15, kp29_field[14]);
					kp29_pstmt.setString(16, kp29_field[15]);kp29_pstmt.setString(17, kp29_field[16]);kp29_pstmt.setString(18, kp29_field[17]);
					kp29_pstmt.setString(19, kp29_field[18]);kp29_pstmt.setString(20, kp29_field[19]);kp29_pstmt.setString(21, kp29_field[20]);
					kp29_pstmt.setString(22, kp29_field[21]);kp29_pstmt.setString(23, kp29_field[22]);kp29_pstmt.setString(24, kp29_field[23]);
					kp29_pstmt.setString(25, kp29_field[24]);kp29_pstmt.setString(26, kp29_field[25]);kp29_pstmt.setString(27, kp29_field[26]);
					kp29_pstmt.setString(28, kp29_field[27]);kp29_pstmt.setString(29, kp29_field[28]);kp29_pstmt.setString(30, kp29_field[29]);
					kp29_pstmt.setString(31, kp29_field[30]);kp29_pstmt.setString(32, kp29_field[31]);kp29_pstmt.setString(33, kp29_field[32]);
					kp29_pstmt.setString(34, kp29_field[33]);kp29_pstmt.setString(35, kp29_field[34]);kp29_pstmt.setString(36, kp29_field[35]);
					kp29_pstmt.setString(37, kp29_field[36]);kp29_pstmt.setString(38, kp29_field[37]);kp29_pstmt.setString(39, kp29_field[38]);
					kp29_pstmt.setString(40, kp29_field[39]);kp29_pstmt.setString(41, kp29_field[40]);kp29_pstmt.setString(42, kp29_field[41]);
					kp29_pstmt.setString(43, kp29_field[42]);kp29_pstmt.setString(44, kp29_field[43]);kp29_pstmt.setString(45, kp29_field[44]);
					kp29_pstmt.setString(46, kp29_field[45]);kp29_pstmt.setString(47, kp29_field[46]);kp29_pstmt.setString(48, kp29_field[47]);
					kp29_pstmt.setString(49, kp29_field[48]);kp29_pstmt.setString(50, kp29_field[49]);kp29_pstmt.setString(51, kp29_field[50]);
					kp29_pstmt.setString(52, kp29_field[51]);kp29_pstmt.setString(53, kp29_field[52]);kp29_pstmt.setString(54, kp29_field[53]);
					kp29_pstmt.setString(55, kp29_field[54]);kp29_pstmt.setString(56, kp29_field[55]);kp29_pstmt.setString(57, kp29_field[56]);
					kp29_pstmt.setString(58, kp29_field[57]);kp29_pstmt.setString(59, kp29_field[58]);kp29_pstmt.setString(60, kp29_field[59]);
					kp29_pstmt.setString(61, kp29_field[60]);kp29_pstmt.setString(62, kp29_field[61]);kp29_pstmt.setString(63, kp29_field[62]);
					kp29_pstmt.setString(64, kp29_field[63]);kp29_pstmt.setString(65, kp29_field[64]);kp29_pstmt.setString(66, kp29_field[65]);
					kp29_pstmt.setString(67, kp29_field[66]);kp29_pstmt.setString(68, kp29_field[67]);kp29_pstmt.setString(69, kp29_field[68]);
					kp29_pstmt.setString(70, kp29_field[69]);kp29_pstmt.setString(71, kp29_field[70]);kp29_pstmt.setString(72, kp29_field[71]);
					kp29_pstmt.setString(73, kp29_field[72]);kp29_pstmt.setString(74, kp29_field[73]);kp29_pstmt.setString(75, kp29_field[74]);
					kp29_pstmt.setString(76, kp29_field[75]);kp29_pstmt.setString(77, kp29_field[76]);kp29_pstmt.setString(78, kp29_field[77]);
					kp29_pstmt.setString(79, kp29_field[78]);kp29_pstmt.setString(80, kp29_field[79]);kp29_pstmt.setString(81, kp29_field[80]);
					kp29_pstmt.setString(82, kp29_field[81]);kp29_pstmt.setString(83, kp29_field[82]);kp29_pstmt.setString(84, kp29_field[83]);
					kp29_pstmt.setString(85, kp29_field[84]);kp29_pstmt.setString(86, kp29_field[85]);kp29_pstmt.setString(87, kp29_field[86]);
					kp29_pstmt.setString(88, kp29_field[87]);kp29_pstmt.setString(89, kp29_field[88]);kp29_pstmt.setString(90, kp29_field[89]);
					kp29_pstmt.setString(91, kp29_field[90]);kp29_pstmt.setString(92, kp29_field[91]);kp29_pstmt.setString(93, kp29_field[92]);
					kp29_pstmt.setString(94, kp29_field[93]);kp29_pstmt.setString(95, kp29_field[94]);kp29_pstmt.setString(96, kp29_field[95]);
					kp29_pstmt.setString(97, kp29_field[96]);kp29_pstmt.setString(98, kp29_field[97]);kp29_pstmt.setString(99, kp29_field[98]);					
					
					kp29_pstmt.addBatch(); // 바로 쿼리를 실행하지 않고 작성된 쿼리를 메모리에 올려 둔다
					kp29_pstmt.clearParameters(); // 객체를 다시 사용해야 하기떄문에 파라미터를 한번 비워준다.
					
					kp29_LineCnt++; // 삽입 카운트 용 변수
					//System.out.println(kp29_QueryTxt); // 확인용 쿼리 출력
					if(kp29_LineCnt % 10000 == 0) {
						System.out.printf("%d : %s\n", kp29_LineCnt, simpleDateFormat.format(System.currentTimeMillis() - kp29_startTime));
						kp29_pstmt.executeBatch(); // addBatch로 올려둔 메모리에 있는 쿼리를 실행 한다
						kp29_con.commit(); // 커밋!
					}
				}								
				kp29_tmp = kp29_LineCnt; // 전체 데이터가 들어 가고 나서 row카운트를 출력할 용도
				kp29_br.close(); // 버퍼 리드 close
			}catch (FileNotFoundException e) {
				System.out.println("파읽 읽기시 파일 찾을 수 없음 예외 : " + e.getMessage()); // 예외 메세지 출력
			} catch (IOException e) {
				System.out.println("IO 예외 : " + e.getMessage()); // 예외 메세지 출력
			} catch (Exception e) {
				System.out.println("Exception 예외 : " + e.getMessage()); // 예외 메세지 출력
			}
			
			// 마지막으로 메모리 전체에 대헤 쿼리를 실행
			kp29_pstmt.executeBatch();
			kp29_con.commit(); // 커밋!
			kp29_con.setAutoCommit(true); // Transaction 커밋 옵션 다시 true로 변경
			
		}catch (SQLException e) { // Sql관련 예외 블럭을 잡아준다
			// sql관련 예외블럭 메세지 출력
			// Table 'kopoctc.mytable' doesn't exist
			System.out.println("데이터베이스 오류 : " + e.getMessage()); // DB 예외 발생시 출력문 출력
			System.out.println("데이터베이스 오류번호 : " + e.getErrorCode());// sql관련 예외블럭 에러 코드 출력 1146 출력
		}catch (ClassNotFoundException e) {
			// 클래스를 찾을 수 없는 예외 블럭
			System.out.println("드라이버를 찾을 수 없습니다: " + e.getMessage());
		}finally {
			try {
				// 닫기는 역순으로 진행해준다				
				// statement가 있으면 닫아준다
				if(kp29_pstmt != null)kp29_pstmt.close();
				// 커넥션이 있으면 닫아 준다
				if(kp29_con != null)kp29_con.close();		
				
			} catch (SQLException e) {
				// 사용한 sql연결 객체들을 닫을때 발생하는 예외 블럭을 잡아준다
				System.out.println("리소스를 닫는 도중 오류 발생 : " + e.getMessage()); // 예외 메세지 출력
			}
		}						
		System.out.printf("\nTotal br Count : %d\n", kp29_tmp);	 // 삽입된 row 카운트 출력
		System.out.println("총 소요 시간: "+ simpleDateFormat.format(System.currentTimeMillis() - kp29_startTime)); // 시작 했던 시간 출력				
		}			
}



