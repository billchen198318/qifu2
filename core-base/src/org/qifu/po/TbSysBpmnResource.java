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
		name="tb_sys_bpmn_resource", 
		uniqueConstraints = { 
				@UniqueConstraint( columnNames = {"ID"} ) 
		} 
)
public class TbSysBpmnResource extends BaseEntity<String> implements java.io.Serializable {
	private static final long serialVersionUID = -3273551962173293095L;
	private String oid;	
	private String id;
	private String deploymentId;
	private String name;
	private byte[] content;
	private String description;
	private String cuserid;
	private Date cdate;
	private String uuserid;
	private Date udate;	
	
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
	
	@EntityUK(name="id")
	@Column(name="ID")
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name="DEPLOYMENT_ID")
	public String getDeploymentId() {
		return deploymentId;
	}
	
	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}
	
	@Column(name="NAME")
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="CONTENT")
	public byte[] getContent() {
		return content;
	}
	
	public void setContent(byte[] content) {
		this.content = content;
	}
	
	@Column(name="DESCRIPTION")
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
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
