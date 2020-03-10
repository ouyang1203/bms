//package com.pccw.kernel.bmsGateway.aspect;
//
//import java.lang.reflect.Field;
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestAttributes;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//
///**
// * 定义一个日志切面记录请求日志(无法记录网关请求日志)
// * */
////@Aspect
////@Component
//public class LogRecodeAspect {
//	
//	private static Logger log_ = LoggerFactory.getLogger(LogRecodeAspect.class);
//	
//	// 定义切点Pointcut
//	@Pointcut("execution(public * com.pccw.kernel.bmsGateway.controller..*.*(..))")
//	public void excudeService() {
//	}
//	
//	@Around("excudeService()")
//	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
//	    RequestAttributes ra = RequestContextHolder.getRequestAttributes();
//	    ServletRequestAttributes sra = (ServletRequestAttributes) ra;
//	    HttpServletRequest request = sra.getRequest();
//	    String url = request.getRequestURI();
//	    String method = request.getMethod();
//	    String queryString = request.getQueryString();
//	    Object[] args = pjp.getArgs();
//	    String params = "";
//	    String invokeUser = "";
//	    int userFlag = 1;
//	    //获取请求参数集合并进行遍历拼接
//	    if(args.length>0){
//	        if("POST".equals(method)){
//	            Object object = args[0];
//	            Map map = getKeyAndValue(object);
//	            params = JSON.toJSONString(map);
//	        }else if("GET".equals(method)){
//	            if(null!=queryString&&""!=queryString){
//	                String[] paramArray = queryString.split("&");
//	                for(String param : paramArray){
//	                    String[] keyValue = param.split("=");
//	                    String key = keyValue[0];
//	                    if(keyValue[0].equals("user")){
//	                        invokeUser = keyValue[1];
//	                    }else if(keyValue[0].equals("userFlag"))
//	                        userFlag = Integer.parseInt(keyValue[1]);
//	                }
//	                params = queryString;
//	            }
//	
//	        }
//	    }
//	    log_.info("请求开始地址={},类型={},参数={}:",url,method,params);
//	    // result的值就是被拦截方法的返回值
//	    Object result = pjp.proceed();
//	    String response = JSONObject.toJSONString(result);
//	    log_.info("请求结束===返回值={}:" + response);
//	    return result;
//	}
//	
//	public static Map<String, Object> getKeyAndValue(Object obj) {
//	    Map<String, Object> map = new HashMap<>();
//	    // 得到类对象
//	    Class userCla = obj.getClass();
//	    /* 得到类中的所有属性集合 */
//	    Field[] fs = userCla.getDeclaredFields();
//	    for (int i = 0; i < fs.length; i++) {
//	        Field f = fs[i];
//	        f.setAccessible(true); // 设置些属性是可以访问的
//	        Object val ;
//	        try {
//	            val = f.get(obj);
//	            // 得到此属性的值
//	            map.put(f.getName(), val);// 设置键值
//	        } catch (IllegalArgumentException e) {
//	            log_.error("解析参数异常",e);
//	        } catch (IllegalAccessException e) {
//	            log_.error("解析参数异常",e);
//	        }
//	
//	    }
//	    return map;
//	}
//	
//}
