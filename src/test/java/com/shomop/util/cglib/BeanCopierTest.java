package com.shomop.util.cglib;

import java.util.Arrays;
import java.util.List;
import net.sf.cglib.beans.BeanCopier;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class BeanCopierTest {

	static class ClassA {
		private String username;
		private String password;
		private String score;

		private List<String> list;

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getScore() {
			return score;
		}

		public void setScore(String score) {
			this.score = score;
		}

		public List<String> getList() {
			return list;
		}

		public void setList(List<String> list) {
			this.list = list;
		}

		@Override
		public String toString() {
			return ToStringBuilder.reflectionToString(this,
					ToStringStyle.MULTI_LINE_STYLE);
		}
	}

	static class ClassB {
		private String username;
		private String password;
		private String address;
		private List<Integer> list;

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public List<Integer> getList() {
			return list;
		}

		public void setList(List<Integer> list) {
			this.list = list;
		}

		@Override
		public String toString() {
			return ToStringBuilder.reflectionToString(this,
					ToStringStyle.MULTI_LINE_STYLE);
		}
	}

	public static void main(String[] args) {

		BeanCopier beanCopier = BeanCopier.create(ClassA.class, ClassB.class,
				false);
		List<String> list = Arrays.asList(new String[] { "a", "b", "c" });
		ClassA a = new ClassA();
		a.setUsername("hello");
		a.setPassword("world");
		a.setScore("99");
		a.setList(list);

		ClassB b = new ClassB();
		b.setUsername("hello");
		b.setPassword("world");
		b.setAddress("beijing");

		beanCopier.copy(a, b, null);

		System.out.println(a);
		System.out.println(b);

	}

}