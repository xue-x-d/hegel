package com.shomop.crm.server;

import java.util.List;

public class TestInjection {

	private List<String> people;

	public List<String> getPeople() {
		return people;
	}

	public void setPeople(List<String> people) {
		this.people = people;
	}

}
