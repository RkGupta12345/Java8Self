package com.nit.resource;

import javax.xml.bind.annotation.XmlRootElement;

public class Employee {
	
	private int empno;
	private String empname;
	private String empdesig;
	public int getEmpno() {
		return empno;
	}
	public void setEmpno(int empno) {
		this.empno = empno;
	}
	public String getEmpname() {
		return empname;
	}
	public void setEmpname(String empname) {
		this.empname = empname;
	}
	public String getEmpdesig() {
		return empdesig;
	}
	public void setEmpdesig(String empdesig) {
		this.empdesig = empdesig;
	}
	@Override
	public String toString() {
		return "Employee [empno=" + empno + ", empname=" + empname + ", empdesig=" + empdesig + "]";
	}
	
	
	



	}


