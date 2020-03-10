package com.pccw.kernel.bmsGateway.vo;

import java.io.Serializable;
import java.sql.Timestamp;

public class GatewayRouteVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private String routeId;
	
	private String routeUri;
	
	private Integer routeOrder;
	
	private String predicates;
	
	private String filters;
	
	private String routeDesc;
	
	private String routeEnable;
	
	private Timestamp createTime;
	
	private Timestamp updateTime;
	
	private String createdBy;
	
	private String updatedBy;
	
	public GatewayRouteVo() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRouteId() {
		return routeId;
	}

	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}

	public String getRouteUri() {
		return routeUri;
	}

	public void setRouteUri(String routeUri) {
		this.routeUri = routeUri;
	}

	public Integer getRouteOrder() {
		return routeOrder;
	}

	public void setRouteOrder(Integer routeOrder) {
		this.routeOrder = routeOrder;
	}

	public String getPredicates() {
		return predicates;
	}

	public void setPredicates(String predicates) {
		this.predicates = predicates;
	}

	public String getFilters() {
		return filters;
	}

	public void setFilters(String filters) {
		this.filters = filters;
	}

	public String getRouteDesc() {
		return routeDesc;
	}

	public void setRouteDesc(String routeDesc) {
		this.routeDesc = routeDesc;
	}

	public String getRouteEnable() {
		return routeEnable;
	}

	public void setRouteEnable(String routeEnable) {
		this.routeEnable = routeEnable;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "GatewayRouteVo [id=" + id + ", routeId=" + routeId + ", routeUri=" + routeUri + ", routeOrder="
				+ routeOrder + ", predicates=" + predicates + ", filters=" + filters + ", routeDesc=" + routeDesc
				+ ", routeEnable=" + routeEnable + ", createTime=" + createTime + ", updateTime=" + updateTime
				+ ", createdBy=" + createdBy + ", updatedBy=" + updatedBy + "]";
	}

}
