package com.javaex.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.PhoneDao;
import com.javaex.vo.PersonVo;

@Service
public class PhoneService {

	@Autowired
	private PhoneDao phoneDao;

	public List<PersonVo> personSelect() {
		return phoneDao.personSelect();
	}

	public int personInsert(PersonVo personVo) {
		return phoneDao.personInsert(personVo);
	}

	public int personDelete(int personId) {
		return phoneDao.personDelete(personId);
	}

	public PersonVo getPerson(int personId) {
		return phoneDao.getPerson(personId);
	}

	public int personUpdate(PersonVo personVo) {
		return phoneDao.personUpdate(personVo);
	}

}