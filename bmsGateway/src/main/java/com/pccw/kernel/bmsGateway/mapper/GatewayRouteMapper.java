package com.pccw.kernel.bmsGateway.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.pccw.kernel.bmsGateway.vo.GatewayRouteVo;

@Mapper
public interface GatewayRouteMapper {
	
	public List<GatewayRouteVo> findAll();
	
	public GatewayRouteVo findGatewayRouteById(Integer id);
	
	public void addGatewayRoute(GatewayRouteVo vo);
	
	public void updateGatewayRoute(GatewayRouteVo vo);
	
	public void deleteGatewayRoute(Integer id);
}
