package com.jfservice.sys.family.model;

import java.util.Date;

import com.jfservice.common.bean.weike.PageBean;

public class Family extends PageBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1741640349020794733L;
	/**
	 * 
	 */
	private Integer id;
	private String userId;
	private String serieNo;
	private String productModel;
	private String phoneNumber;
	private String shortNumber;
	private String relativeType;   //0表示亲人,1表示好友
	private String nickName;
	private String status;
	private Date addTime;
    private String belongProject;
    private Integer icon;
	
	public String getBelongProject() {
		return belongProject;
	}
	public void setBelongProject(String belongProject) {
		this.belongProject = belongProject;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public Integer getId() {
		return id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSerieNo() {
		return serieNo;
	}
	public void setSerieNo(String serieNo) {
		this.serieNo = serieNo;
	}
	public String getProductModel() {
		return productModel;
	}
	public void setProductModel(String productModel) {
		this.productModel = productModel;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getRelativeType() {
		return relativeType;
	}
	public void setRelativeType(String relativeType) {
		this.relativeType = relativeType;
	}
	public String getShortNumber() {
		return shortNumber;
	}
	public void setShortNumber(String shortNumber) {
		this.shortNumber = shortNumber;
	}
	public Integer getIcon() {
		return icon;
	}
	public void setIcon(Integer icon) {
		this.icon = icon;
	}
	
}
