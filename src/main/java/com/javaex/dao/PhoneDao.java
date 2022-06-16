package com.javaex.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.PersonVo;

@Repository
public class PhoneDao {

	@Autowired
	private SqlSession sqlSession;

	public List<PersonVo> personSelect() {

		List<PersonVo> personList = sqlSession.selectList("phonebook.selectList");
		System.out.println(personList);

		return personList;
	}

	// 사람 추가
	public int personInsert(PersonVo personVo) {
		System.out.println("PhoneDao > personInsert()");
		return sqlSession.insert("phonebook.personInsert", personVo);

	}

	// 사람 삭제
	public int personDelete(int personId) {
		System.out.println("PhoneDao > personDelete()");

		int count = sqlSession.delete("phonebook.personDelete", personId);
		return count;
	}

	// 사람 수정
	public int personUpdate(PersonVo personVo) {
		System.out.println("PhoneDao > personUpdate()");
		int count = sqlSession.update("phonebook.personUpdate", personVo);
		return count;
	}

	// 1명 정보 가져오기
	public PersonVo getPerson(int personId) {
		System.out.println("PhoneDao > getPerson()");
		PersonVo personVo = sqlSession.selectOne("phonebook.getPerson", personId);

		return personVo;
	}

}