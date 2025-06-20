package Stock;
import java.sql.*;

public class CreateDropTable {

	public static void main(String[] args){
		Connection kp29_con = null; // 커넥션 객체 생성
		Statement kp29_stmt = null; // 스태이트 먼트 개체 생성
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
			
			// delete 쿼리 실행
			// 쿼리를 문자열로 담아서 인자로 전달하여 쿼리를 실행한다
			kp29_stmt.execute("drop table kopoctc.stockinfo"); // 주식 테이블 삭제	
			kp29_stmt.execute(" CREATE TABLE stockinfo ( stnd_iscd VARCHAR(13) NOT NULL, bsop_date DATE NOT NULL,shrn_iscd VARCHAR(10) NOT NULL, " +
					" stck_prpr INT, stck_oprc INT, stck_hgpr INT, stck_lwpr INT, prdy_vrss_sign VARCHAR(2), prdy_vrss INT, prdy_ctrt FLOAT, " +
					" prdy_vol BIGINT, acml_vol BIGINT, acml_tr_pbmn BIGINT, askp1 INT, bidp1 INT, total_askp_rsqn BIGINT, total_bidp_rsqn BIGINT, " +
					" seln_cntg_smtn BIGINT, shnu_cntg_smtn BIGINT, seln_tr_pbmn BIGINT, shnu_tr_pbmn BIGINT, seln_cntg_csnu INT, shnu_cntg_csnu INT, " +
					" w52_hgpr INT, w52_lwpr INT, w52_hgpr_date INT, w52_lwpr_date INT, ovtm_untp_bsop_hour INT, ovtm_untp_prpr INT, " +
					" ovtm_untp_prdy_vrss INT, ovtm_untp_prdy_vrss_sign VARCHAR(2), ovtm_untp_askp1 INT, ovtm_untp_bidp1 INT, ovtm_untp_vol BIGINT, " +
					" ovtm_untp_tr_pbmn BIGINT, ovtm_untp_oprc INT, ovtm_untp_hgpr INT, ovtm_untp_lwpr INT, mkob_otcp_vol BIGINT, " +
					" mkob_otcp_tr_pbmn BIGINT, mkfa_otcp_vol BIGINT, mkfa_otcp_tr_pbmn BIGINT, mrkt_div_cls_code VARCHAR(2), pstc_dvdn_amt BIGINT, " +
					" lstn_stcn BIGINT, stck_sdpr INT, stck_fcam FLOAT, wghn_avrg_stck_prc DOUBLE, issu_limt_rate FLOAT, frgn_limt_qty BIGINT, " +
					" oder_able_qty BIGINT, frgn_limt_exhs_cls_code VARCHAR(2), frgn_hldn_qty BIGINT, frgn_hldn_rate FLOAT, hts_frgn_ehrt FLOAT, " +
					" itmt_last_nav FLOAT, prdy_last_nav FLOAT, trc_errt FLOAT, dprt FLOAT, ssts_cntg_qty BIGINT, ssts_tr_pbmn BIGINT, " +
					" frgn_ntby_qty BIGINT, flng_cls_code VARCHAR(3), prtt_rate FLOAT, acml_prtt_rate FLOAT, stdv FLOAT, beta_cfcn FLOAT, " +
					" crlt_cfcn FLOAT, bull_beta FLOAT, bear_beta FLOAT, bull_dvtn FLOAT, bear_dvtn FLOAT, bull_crlt FLOAT, bear_crlt FLOAT, " +
					" stck_mxpr INT, stck_llam INT, icic_cls_code VARCHAR(3), itmt_vol BIGINT, itmt_tr_pbmn BIGINT, fcam_mod_cls_code VARCHAR(3), " +
					" revl_issu_reas_code VARCHAR(3), orgn_ntby_qty BIGINT, adj_prpr INT, fn_oprc INT, fn_hgpr INT, fn_lwpr INT, fn_prpr INT, " +
					" fn_acml_vol BIGINT, fn_acml_tr_pbmn BIGINT, fn_prtt_rate FLOAT, fn_flng_cls_code VARCHAR(3), buyin_nor_prpr INT, " +
					" buyin_nor_prdy_vrss INT, buyin_nor_vol BIGINT, buyin_nor_tr_pbmn BIGINT, buyin_tod_prpr INT, " +// 복합키 잡아주고 character set utf8로 해줌
					" buyin_tod_prdy_vrss INT, buyin_tod_vol BIGINT, buyin_tod_tr_pbmn BIGINT, PRIMARY KEY (stnd_iscd, bsop_date)) DEFAULT CHARACTER SET = utf8;"); 	
			
		}catch (SQLException e) { // Sql관련 예외 블럭을 잡아준다
			// sql관련 예외블럭 메세지 출력
			// Table 'kopoctc.mytable' doesn't exist
			System.out.println("데이터베이스 오류 : " + e.getMessage());  // 디비 오류 메세지 출력
			System.out.println("데이터베이스 오류번호 : " + e.getErrorCode());// sql관련 예외블럭 에러 코드 출력 1146 출력
		}catch (ClassNotFoundException e) {
			// 클래스를 찾을 수 없는 예외 블럭
			System.out.println("드라이버를 찾을 수 없습니다: " + e.getMessage());
		}finally {
			try {
				// 닫기는 역순으로 진행해준다
				
				// statement가 있으면 닫아준다
				if(kp29_stmt != null)kp29_stmt.close();
				// 커넥션이 있으면 닫아 준다
				if(kp29_con != null)kp29_con.close();		
				
			} catch (SQLException e) {
				// 사용한 sql연결 객체들을 닫을때 발생하는 예외 블럭을 잡아준다
				System.out.println("리소스를 닫는 도중 오류 발생 : " + e.getMessage()); // 예외 메세지 출력
			}
		}										
		System.out.println("\nProgram Off");
	}
}
