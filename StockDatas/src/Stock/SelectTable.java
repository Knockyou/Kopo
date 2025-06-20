package Stock;
import java.sql.*;
import java.text.SimpleDateFormat;

public class SelectTable {
	public static void main(String[] args) {
		
		Connection kp29_con = null; // 커넥션 객체 생성
		Statement kp29_stmt = null; // 스테이트 먼트 객체 생성
		ResultSet kp29_rset = null; // 리절트 셋 객체 생성
		
		// 프로그램 시작 시간 비교용 시간
		long kp29_startTime = System.currentTimeMillis();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss"); // 데이트 포맷 형식 객체 생성
		
		try {
			// mysql jdbc 드라이버 가져오기
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// 디비와 커넥션을 생성해준다
			// 커넥트 정보에는 디비 주소와 접속할 디비명, 접속할 계정, 패스워드를 넣어준다
			kp29_con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:33062/kopoctc", "root", "0000");
					
			// statement를 생성해준다
			// 커넥션으로 연결한 객체에게 쿼리 작업을 실행하기 위한 객체이다
			// statement는 정적인 쿼리를 처리할 수 있다
			kp29_stmt = kp29_con.createStatement();
			
			// 쿼리 문자열 변수
			String QueryStr;			
			
			// 특정 조건 쿼리
			QueryStr = "SELECT * FROM kopoctc.stockinfo where shrn_iscd = 'A005930'"; // 삼전 코드
			QueryStr = "SELECT * FROM kopoctc.stockinfo where bsop_date between 20160401 and 20160402;"; // 특정 날짜 사이 조회			
			QueryStr = "SELECT * FROM kopoctc.stockinfo where bsop_date = 20140404;"; // 특정 날짜만 조회
			
			// select 쿼리 문자열 담아서 
			// executeQuery인자로 넘겨 리절트 셋 리턴
			kp29_rset = kp29_stmt.executeQuery(QueryStr);
						
			int kp29_selectRowCnt = 1; // 조회 row 카운트용 변수
			while(kp29_rset.next()){ // 리절트 셋으로 계속 다음을 읽어 오는것, 읽어 올것이 없으면 반복문 종료
				// getstring으로 리절트 셋에 있는 값을 순차적으로 출력해준다			
				System.out.printf("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,"
					+"%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,"
					+"%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,"
					+"%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,"
					+"%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s\n",
					kp29_rset.getString("stnd_iscd"),kp29_rset.getString("bsop_date"), //NOT_NULL 표준 종목코드 //NOT_NULL 주식 영업 일자 
					kp29_rset.getString("shrn_iscd"),kp29_rset.getString("stck_prpr"), //NOT_NULL 유가증권 단축 종목코드 //주식 종가 
					kp29_rset.getString("stck_oprc"),kp29_rset.getString("stck_hgpr"), //주식 시가 //주식 최고가 
					kp29_rset.getString("stck_lwpr"),kp29_rset.getString("prdy_vrss_sign"),//주식 최저가 //전일 대비 부호 
					kp29_rset.getString("prdy_vrss"),kp29_rset.getString("prdy_ctrt"),//전일 대비 //전일 대비율 
					kp29_rset.getString("prdy_vol"),kp29_rset.getString("acml_vol"),//전일 거래량 //누적 거래량
					kp29_rset.getString("acml_tr_pbmn"),kp29_rset.getString("askp1"),//누적 거래 대금 //매도호가1 
					kp29_rset.getString("bidp1"),kp29_rset.getString("total_askp_rsqn"),//매수호가1 //총 매도호가 잔량 
					kp29_rset.getString("total_bidp_rsqn"),kp29_rset.getString("seln_cntg_smtn"),//총 매수호가 잔량//매도 체결 합계 
					kp29_rset.getString("shnu_cntg_smtn"),kp29_rset.getString("seln_tr_pbmn"),//매수 체결 합계 //매도 거래 대금(누적) 
					kp29_rset.getString("shnu_tr_pbmn"),kp29_rset.getString("seln_cntg_csnu"),//매수 거래 대금(누적) //매도 체결 건수
					kp29_rset.getString("shnu_cntg_csnu"),kp29_rset.getString("w52_hgpr"),//매수 체결 건수 //52주일 최고가
					kp29_rset.getString("w52_lwpr"),kp29_rset.getString("w52_hgpr_date"),//52주일 최저가 //52주일 최고가 일자 
					kp29_rset.getString("w52_lwpr_date"),kp29_rset.getString("ovtm_untp_bsop_hour"),//52주일 최저가 일자 //시간외 단일가 최종 시간 
					kp29_rset.getString("ovtm_untp_prpr"),kp29_rset.getString("ovtm_untp_prdy_vrss"),//시간외 단일가 현재가 //시간외 단일가 전일 대비 
					kp29_rset.getString("ovtm_untp_prdy_vrss_sign"),kp29_rset.getString("ovtm_untp_askp1"),//시간외 단일가 전일 대비 부호 //시간외 단일가 매도호가1 
					kp29_rset.getString("ovtm_untp_bidp1"),kp29_rset.getString("ovtm_untp_vol"),//시간외 단일가 매수호가1 //시간외 단일가 거래량 
					kp29_rset.getString("ovtm_untp_tr_pbmn"),kp29_rset.getString("ovtm_untp_oprc"),//시간외 단일가 거래 대금 //시간외 단일가 시가 
					kp29_rset.getString("ovtm_untp_hgpr"),kp29_rset.getString("ovtm_untp_lwpr"),//시간외 단일가 최고가 //시간외 단일가 최저가 
					kp29_rset.getString("mkob_otcp_vol"),kp29_rset.getString("mkob_otcp_tr_pbmn"),//장개시전 시간외종가 거래량 //장개시전 시간외종가 거래 대금 
					kp29_rset.getString("mkfa_otcp_vol"),kp29_rset.getString("mkfa_otcp_tr_pbmn"),//장종료후 시간외종가 거래량 //장종료후 시간외종가 거래 대금 
					kp29_rset.getString("mrkt_div_cls_code"),kp29_rset.getString("pstc_dvdn_amt"),//시장 분류 구분 코드 //주당 배당 금액 
					kp29_rset.getString("lstn_stcn"),kp29_rset.getString("stck_sdpr"),//상장 주수 //주식 기준가 
					kp29_rset.getString("stck_fcam"),kp29_rset.getString("wghn_avrg_stck_prc"),//주식 액면가 //가중 평균 주식 가격
					kp29_rset.getString("issu_limt_rate"),kp29_rset.getString("frgn_limt_qty"),//종목 한도 비율 //외국인 한도 수량 
					kp29_rset.getString("oder_able_qty"),kp29_rset.getString("frgn_limt_exhs_cls_code"),//주문 가능 수량 //외국인 한도 소진 구분 코드
					kp29_rset.getString("frgn_hldn_qty"),kp29_rset.getString("frgn_hldn_rate"),//외국인 보유 수량 //외국인 보유 비율
					kp29_rset.getString("hts_frgn_ehrt"),kp29_rset.getString("itmt_last_nav"),//HTS 외국인 소진율 //장중 최종 NAV 
					kp29_rset.getString("prdy_last_nav"),kp29_rset.getString("trc_errt"),//전일 최종 NAV //추적 오차율 //괴리율 
					kp29_rset.getString("dprt"),kp29_rset.getString("ssts_cntg_qty"),//공매도차입증권매도체결수량 //공매도차입증권매도거래대금 
					kp29_rset.getString("ssts_tr_pbmn"),kp29_rset.getString("frgn_ntby_qty"),kp29_rset.getString("flng_cls_code"),//외국인 순매수 //락구분 코드 //분할 비율
					kp29_rset.getString("prtt_rate"),kp29_rset.getString("acml_prtt_rate"),kp29_rset.getString("stdv"),//누적 분할 비율 //전체융자잔고비율 //베타 계수(90일) 
					kp29_rset.getString("beta_cfcn"),kp29_rset.getString("crlt_cfcn"),kp29_rset.getString("bull_beta"),//DEL 상관 계수 //DEL 강세 계수 //DEL 약세 계수
					kp29_rset.getString("bear_beta"),kp29_rset.getString("bull_dvtn"),kp29_rset.getString("bear_dvtn"),//DEL 강세 편차 //DEL 약세 편차 //DEL 강세 상관계수
					kp29_rset.getString("bull_crlt"),kp29_rset.getString("bear_crlt"),kp29_rset.getString("stck_mxpr"),//DEL 약세 상관계수 //주식 상한가 //주식 하한가
					kp29_rset.getString("stck_llam"),kp29_rset.getString("icic_cls_code"),kp29_rset.getString("itmt_vol"),//증자 구분 코드 //장중 거래량 //장중 거래대금
					kp29_rset.getString("itmt_tr_pbmn"),kp29_rset.getString("fcam_mod_cls_code"),kp29_rset.getString("revl_issu_reas_code"),//액면가 변경 구분 코드 //재평가 종목 사유 코드//기관계 순매수
					kp29_rset.getString("orgn_ntby_qty"),kp29_rset.getString("adj_prpr"),kp29_rset.getString("fn_oprc"),//수정주가 (교보:fnguide때문에 추가) //주식 시가 //주식 최고가
					kp29_rset.getString("fn_hgpr"),kp29_rset.getString("fn_lwpr"),kp29_rset.getString("fn_prpr"),//주식 최저가 //주식 종가 //누적 거래량
					kp29_rset.getString("fn_acml_vol"),kp29_rset.getString("fn_acml_tr_pbmn"),kp29_rset.getString("fn_prtt_rate"),//누적 거래 대금 //분할 비율 //락구분 코드
					kp29_rset.getString("fn_flng_cls_code"),kp29_rset.getString("buyin_nor_prpr"),kp29_rset.getString("buyin_nor_prdy_vrss"),//uy-in 일반 체결가 //uy-in 일반 종가 대비 //uy-in 일반 체결량 
					kp29_rset.getString("buyin_nor_vol"),kp29_rset.getString("buyin_nor_tr_pbmn"),kp29_rset.getString("buyin_tod_prpr"),//uy-in 일반 체결 대금 //uy-in 당일 체결가 //uy-in 당일 종가 대비 
				    kp29_rset.getString("buyin_tod_prdy_vrss"),kp29_rset.getString("buyin_tod_vol"),kp29_rset.getString("buyin_tod_tr_pbmn"));//uy-in 당일 체결량 //uy-in 당일 체결 대금
				++kp29_selectRowCnt; // 출력후 row 카운트 + 1
			}
								
			// 조회 결과 row 카운트 출력
			System.out.println("row Count : " + kp29_selectRowCnt); 
			
					
			// 받아온 resultset 출력			
		} catch (SQLException e) { // Sql관련 예외 블럭을 잡아준다
			// sql관련 예외블럭 메세지 출력
			System.out.println("데이터베이스 오류 : " + e.getMessage()); // DB 예외 발생 프린트 
			System.out.println("데이터베이스 오류번호 : " + e.getErrorCode());// sql관련 예외블럭 에러 코드 출력 1146 출력
		}catch (ClassNotFoundException e) {
			// 클래스를 찾을 수 없는 예외 블럭
			System.out.println("드라이버를 찾을 수 없습니다: " + e.getMessage()); // jdbc 찾지 못할 때 출력
		}finally {
			try {
				// 닫기는 역순으로 진행.
				
				// 리절트 셋이 있으면 닫아준다
				if(kp29_rset != null)kp29_rset.close();				
				// statement가 있으면 닫아준다
				if(kp29_stmt != null)kp29_stmt.close();
				// 커넥션이 있으면 닫아 준다
				if(kp29_con != null)kp29_con.close();		
				
			} catch (SQLException e) {
				// 사용한 sql연결 객체들을 닫을때 발생하는 예외 블럭을 잡아준다
				System.out.println("리소스를 닫는 도중 오류 발생 : " + e.getMessage()); // 예외 메세지 출력
			}
		}						
		// 조회 쿼리 출력후 시간 출력 확인
		System.out.println("조회 쿼리 소요 시간 : " + simpleDateFormat.format((System.currentTimeMillis() - kp29_startTime) - 32400000));	
		
	}
}
