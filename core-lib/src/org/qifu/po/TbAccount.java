package org.qifu.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.qifu.base.model.BaseEntity;
import org.qifu.base.model.EntityPK;
import org.qifu.base.model.EntityUK;

@Entity
@Table(
		name="tb_account", 
		uniqueConstraints = { 
				@UniqueConstraint( columnNames = {"ACCOUNT"} ) 
		} 
)
public class TbAccount extends BaseEntity<String> implements java.io.Serializable {
	private static final long serialVersionUID = -7166355146138460130L;
	
	private String oid;
	private String account;
	private String password;
	private String onJob;
	private String cuserid;
	private Date cdate;
	private String uuserid;
	private Date udate;
	
	public TbAccount() {
		
	}
	
	public TbAccount(String oid, String account, String password) {
		this.oid=oid;
		this.account=account;
		this.password=password;
	}
	
	public TbAccount(String oid, String account, String password, String onJob,
			String cuserid, Date cdate, String uuserid, Date udate) {
		this.oid=oid;
		this.account=account;
		this.password=password;
		this.onJob=onJob;
		this.cuserid=cuserid;
		this.cdate=cdate;
		this.uuserid=uuserid;
		this.udate=udate;
	}
	
	@Override
	@Id
	@EntityPK(name="oid")
	@Column(name="OID")
	public String getOid() {
		return oid;
	}
	@Override
	public void setOid(String oid) {
		this.oid = oid;
	}
	@EntityUK(name="account")
	@Column(name="ACCOUNT")
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	@Column(name="PASSWORD")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Column(name="ON_JOB")
	public String getOnJob() {
		return onJob;
	}
	public void setOnJob(String onJob) {
		this.onJob = onJob;
	}	
	@Override
	@Column(name="CUSERID")
	public String getCuserid() {
		return this.cuserid;
	}
	@Override
	public void setCuserid(String cuserid) {
		this.cuserid = cuserid;
	}
	@Override
	@Column(name="CDATE")
	public Date getCdate() {
		return this.cdate;
	}
	@Override
	public void setCdate(Date cdate) {
		this.cdate = cdate;
	}
	@Override
	@Column(name="UUSERID")
	public String getUuserid() {
		return this.uuserid;
	}
	@Override
	public void setUuserid(String uuserid) {
		this.uuserid = uuserid;
	}
	@Override
	@Column(name="UDATE")
	public Date getUdate() {
		return this.udate;
	}
	@Override
	public void setUdate(Date udate) {
		this.udate = udate;
	}
	
}
