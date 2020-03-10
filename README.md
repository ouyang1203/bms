# bms
## 1、本项目采用springcloud开源框架搭建，其中springboot版本为：2.0.3.RELEASE，springcloud版本为：Finchley.RELEASE
## 2、目前使用的模块有：Eurzka,feign,springcloud gateway
## 3、bmsGateway中实现了动态路由（路由的增删改，路由数据存储于mysql中），同时在当前模块中将记录所有经过gateway网关的请求日志（目前输出到日志文件中，后续可根据实际需求存到mysql数据库中）,gateway服务返回均为JSON
## 4、bmsGateway中提供的服务
### 1、查询所有生效路由地址：http://localhost:5555/actuator/gateway/routes；
### 2、手动加载数据库中路由：http://localhost:5555/refreshRoute；
### 3、动态添加路由（无界面）：http://localhost:5555/addRoute；请求参数：{"gateway_routes":{"routeId":"test","routeUri":"lb://TEST","routeOrder":0,"predicates":[{"name":"Path","args":{"pattern":"/test/test/**"}}],"filters":[{"name":"StripPrefix","args":{"_genkey_0":"1"}}],"route_enable":"Y","created_by":"222","updated_by":"222"}}；请求方法：POST；
### 4、删除路由（无界面）：http://localhost:5555/delRoute，请求参数（参数中的id为mysql记录主键，route_id为定义的路由ID）：{"id":"5","route_id":"test"}，请求方法：POST；
### 5、更新路由（无界面）：http://localhost:5555/updateRoute，请求参数：{"gateway_routes":{"id":"6","routeId":"test_v2","routeUri":"lb://TEST2","routeOrder":0,"predicates":[{"name":"Path","args":{"pattern":"/test/test/**"}}],"filters":[{"name":"StripPrefix","args":{"_genkey_0":"1"}}],"route_enable":"Y","created_by":"222","updated_by":"222"}}，请求方法：POST
## 5、服务间调用均通过feign调用gateway中定义的路由实现：localhost:3333/daily/dailyAmountCheck（目前测试的地址，通过日常模块调用年度模块），http://localhost:4444/annual/checkAnnualAmount（通过年度调用日常模块）
