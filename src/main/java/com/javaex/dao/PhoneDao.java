package com.javaex.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.PersonVo;

@Repository
public class PhoneDao {

	// 필드
	@Autowired
	private DataSource dataSource;

	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	/*
	 * private String driver = "oracle.jdbc.driver.OracleDriver"; private String url
	 * =
	 * "jdbc:oracle:thin:@webdb_high?TNS_ADMIN=/Users/choijungphil/jungphil/Wallet_webdb";
	 * private String id = "admin"; private String pw = "^^65Rhcemdtla";
	 */
	// 생성자

	// 메소드 - gs

	// DB구축 메소드
	public void getConnection() {
		try {
			/*
			 * // 1. 오라클 드라이버 로딩 Class.forName(driver); // 2. 데이터베이스 접속
			 */
			conn = dataSource.getConnection();

		} catch (SQLException e) {
			System.out.println("error: " + e);
		}

	}

	// 자원정리 메소드
	public void close() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}

		} catch (SQLException e) {
			System.out.println("erroe: " + e);
		}
	}

	// 메소드 - 일반
	// 사람 등록(insert) 메소드
	public int personinsert(PersonVo personVo) {

		int count = 0;

		getConnection();

		try {
			// 3.SQL문 시작
			String query = "";
			query += " insert into person";
			query += " values (seq_person_id.nextval, ?, ?, ?)";
			System.out.println(query);

			// 바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, personVo.getName());
			pstmt.setString(2, personVo.getHp());
			pstmt.setString(3, personVo.getCompany());

			// 실행
			// 성공 횟수 리턴
			count = pstmt.executeUpdate();

			// 4. 결과처리
			System.out.println(count + "건이 등록되었습니다.");

		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
		close();

		return count;

	}

	// 사람 검색(select) 메소드
	public List<PersonVo> personSelect() {

		// 리스트로 만들기
		List<PersonVo> personList = new ArrayList<PersonVo>();
		getConnection();

		try {
			// SQL문 준비
			String query = "";
			query += " select person_id,";
			query += " 		  name,";
			query += " 		  hp,";
			query += " 		  company";
			query += " from person ";
			query += " order by person_id asc ";

			// 바인딩
			pstmt = conn.prepareStatement(query);

			// 실행
			// resultSet 가져오기
			rs = pstmt.executeQuery();

			// 결과처리
			// 반복문으로 Vo만들어 List에 추가하기
			while (rs.next()) {
				int personId = rs.getInt("person_id");
				String name = rs.getString("name");
				String hp = rs.getString("hp");
				String company = rs.getString("company");

				PersonVo personVo = new PersonVo(personId, name, hp, company);

				personList.add(personVo);

			}

		} catch (SQLException e) {
			System.out.println("error: " + e);
		}

		close();

		return personList;

	}

	// 사람 수정(update) 메소드
	public int personUpdate(PersonVo personVo) {
		int count = 0;

		getConnection();

		try {
			///// 3. SQL문 준비 / 바인딩 / 실행 /////
			// SQL문 준비
			String query = "";
			query += " update person ";
			query += " set name = ?, ";
			query += "     hp = ?, ";
			query += "     company = ? ";
			query += " where person_id = ? ";

			// 바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, personVo.getName());
			pstmt.setString(2, personVo.getHp());
			pstmt.setString(3, personVo.getCompany());
			pstmt.setInt(4, personVo.getPersonId());

			// 실행
			count = pstmt.executeUpdate();

			///// 4.결과처리 /////
			System.out.println(count + "건 수정 되었습니다");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();

		return count;

	}

	// 사람 삭제(delete) 메소드
	public int persondelete(int personId) {
		int count = -1;

		getConnection();

		try {
			// SQL문 준비
			String query = "";
			query += " delete from person ";
			query += " where person_id = ? ";

			// 바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, personId);

			// 실행
			count = pstmt.executeUpdate();

			// 출력
			System.out.println(count + "건이 삭제되었습니다.");

		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
		close();

		return count;

	}

	public int personSearch(String search) {

		int count = 0;
		getConnection();
		try {
			// SQL문 준비
			String query = "";
			query += " select * ";
			query += " from person ";
			query += " where name Like ? ";
			query += " or hp like ? ";
			query += " or company like ? ";

			// 바인딩
			pstmt = conn.prepareStatement(query);

			search = "%" + search + "%";
			pstmt.setString(1, search);
			pstmt.setString(2, search);
			pstmt.setString(3, search);

			// 실행
			// resultSet 가져오기
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int personId = rs.getInt("person_id");
				String name = rs.getString("name");
				String hp = rs.getString("hp");
				String company = rs.getString("company");

				PersonVo personVo = new PersonVo(personId, name, hp, company);
				personVo.showList();
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();

		return count;

	}

	// 사람 가져오기
	public PersonVo getPerson(int personId) {
		PersonVo personVo = null;

		this.getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			// SQL문 준비
			String query = "";
			query += " select  person_id, ";
			query += "         name, ";
			query += "         hp, ";
			query += "         company ";
			query += " from person ";
			query += " where person_id = ? ";

			// 바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, personId);

			// 실행
			rs = pstmt.executeQuery();

			// 4.결과처리
			while (rs.next()) {

				int id = rs.getInt("person_id");
				String name = rs.getString("name");
				String hp = rs.getString("hp");
				String company = rs.getString("company");

				personVo = new PersonVo(id, name, hp, company);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();

		return personVo;
	}

}
