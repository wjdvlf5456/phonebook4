package com.javaex.vo;

public class PersonVo {
	// 필드
	private int personId;
	private String name;
	private String hp;
	private String company;

	// 생성자
	public PersonVo() {

	}

	public PersonVo(String name, String hp, String company) {
		this.name = name;
		this.hp = hp;
		this.company = company;
	}

	public PersonVo(int personId, String name, String hp, String company) {
		this.personId = personId;
		this.name = name;
		this.hp = hp;
		this.company = company;
	}

	// 메소드 - gs
	public int getPersonId() {
		return personId;
	}

	public String getName() {
		return name;
	}

	public String getHp() {
		return hp;
	}

	public String getCompany() {
		return company;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setHp(String hp) {
		this.hp = hp;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	// 메소드 - 일반
	public void showList() {
		System.out.println(personId + "\t" + name + "\t" + hp + "\t" + company);

	}

	@Override
	public String toString() {
		return "PersonVo [personId=" + personId + ", name=" + name + ", hp=" + hp + ", company=" + company + "]";
	}

}
