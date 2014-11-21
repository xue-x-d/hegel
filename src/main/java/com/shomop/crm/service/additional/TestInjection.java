package com.shomop.crm.service.additional;

import java.util.List;

public abstract class TestInjection {

	private List<String> people;

	public List<String> getPeople() {
		return people;
	}

	public void setPeople(List<String> people) {
		this.people = people;
	}
	

}
