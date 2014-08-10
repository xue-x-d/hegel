package com.shomop.crm.model.si;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="si_category",uniqueConstraints = {   
        @UniqueConstraint(columnNames = {"cid", "pid" })  
})
public class SI_Category {

	private Long cid;
	private Long pid; // 父类目,根节点父类目与子类目相同
	private String name;
	/**
	 * 类目所属层
	 *             0
	 *            /  \
	 *          1    1          
	 *         / \
	 *      2  2
	 *     /\
	 *   3  3
	 *     /  \
	 *   4    4
	 */
	private int comclass;
	@Id
	@Column(name = "cid")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getCid() {
		return cid;
	}
	public void setCid(Long cid) {
		this.cid = cid;
	}
	public Long getPid() {
		return pid;
	}
	public void setPid(Long pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getComclass() {
		return comclass;
	}
	public void setComclass(int comclass) {
		this.comclass = comclass;
	}
	
	
}
