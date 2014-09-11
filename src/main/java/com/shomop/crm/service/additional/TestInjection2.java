package com.shomop.crm.service.additional;

import java.util.List;

public class TestInjection2 {

	private List<String> people;
	
	private AutowireTest autowireTest;

	public List<String> getPeople() {
		return people;
	}

	public AutowireTest getAutowireTest() {
		return autowireTest;
	}

	public void setPeople(List<String> people) {
		this.people = people;
	}

	public void setAutowireTest(AutowireTest autowireTest) {
		this.autowireTest = autowireTest;
	}
	
	@SuppressWarnings("unused")
	private void initMethod() {

		System.out.println("autowireTest2 init invoking...");
	}
	
}
