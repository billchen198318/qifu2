package org.qifu.test;

import java.math.BigDecimal;

public class ProductVO implements java.io.Serializable {
	private static final long serialVersionUID = 1164417366837088204L;
	
	private String no;
	private String name;
	private BigDecimal amount;
	
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	@Override
	public String toString() {
		return "ProductVO [no=" + no + ", name=" + name + ", amount=" + amount + "]";
	}
	
}
