package com.lhh.z.weichart;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

/**页面类中需要用到的工具方法
 */
public class CtrlUtils {
    
    public static final int NUM_PER_PAGE = 20;

    private static final String REQUEST_MESSAGE = "REQUEST_MESSAGE";
    
    public static ModelAndView getModelAndView(String subPath, String fileName) {
        return getModelAndView(subPath, fileName, null);
    }
    
    /**取得必需的参数，如果req中参数不存在抛异常，
     * @param req HttpServletRequest
     * @param name 参数名
     * @see javax.servlet.http.HttpServletRequest#getParameter(String)
     * @return String
     */
    public static String getRequiredStringParameter(HttpServletRequest req, String name) {
        String parameter = req.getParameter(name);
        Assert.hasLength(parameter);
        return parameter;
    }
    
    public static ModelAndView getModelAndView(String subPath, String fileName, Map<String, Object> map) {
        if (map == null) {
            map = new HashMap<String, Object>(1);
        }
        map.put("subPath", subPath);
        map.put("fileName", fileName);
        return new ModelAndView(subPath + "/" + fileName, map);
    }
   
    
    public static void putJSONArray(int totalProperty, JSONArray jsonArray, HttpServletResponse res) {
        putJSONArray(totalProperty, jsonArray, res, null);
    }
    
    public static void putJSONArray(int totalProperty, JSONArray jsonArray, HttpServletResponse res, Map map) {
        if (map == null) {
            map = new HashMap(2);
        }
        map.put("total", "" + totalProperty);
        map.put("rows", jsonArray);
        putJSON(map, res);
    }
    
    
    public static void putJSONModel(JSONArray jsonArray, HttpServletResponse res, Map map) {
        if (map == null) {
            map = new HashMap(2);
        }
        map.put("success", new Boolean(true));
        map.put("data", jsonArray);
        putJSON(map, res);
    }
    
    public static void putJSONResult(boolean isSuccess, Object data, HttpServletResponse res) {
        Map map = new HashMap(2);
        if (isSuccess) {
            map.put("success", "true");
        } else {
            map.put("failure", "true");
        }
        map.put("data", data); 
        putJSON(map, res);
    }
    
    public static void putJSON(Map map, HttpServletResponse res) {
        putJSON(JSONObject.fromObject(map), res);
    }
    
    public static void putJsonp(String callback,Map map, HttpServletResponse res){
    	 putJSONP(callback,JSONObject.fromObject(map), res);
    }
    /**
     * 去除字包含，防止hibernate的延迟加载报错
     * 实际是对model处理多层次包含处理
     * 解决There is a cycle in the hierarchy!问题
     * @param callback
     * @param map
     * @param res
     * @author huage
     */
    public static void putJsonpCDSY(String callback,Map map, HttpServletResponse res){
    	JsonConfig jsonConfig = new JsonConfig();
    	jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
    	putJSONP(callback,JSONObject.fromObject(map,jsonConfig), res);
    }
    
    public static void putJSON(JSONObject jsonObject, HttpServletResponse res) {
        writeStr2Res(jsonObject.toString(), res);
    }
    
    public static void putJSONP(String callback,JSONObject jsonObject, HttpServletResponse res) {
        writejsonp(callback,jsonObject.toString(), res);
    }
    
    public static void putJSON(JSONArray jsonArray, HttpServletResponse res) {
        writeStr2Res(jsonArray.toString(), res);
    }
    
    public static void writeStr2Res(String jsonStr, HttpServletResponse res) {
        res.setContentType("text/html");
        res.setContentType("text/html; charset=UTF-8");
        try {
            res.getWriter().write(jsonStr);  
        } catch (IOException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }
    
    public static void writejsonp(String callback,String jsonStr, HttpServletResponse res) {
        res.setContentType("text/html; charset=UTF-8");
        try {
        	if(callback!=null && !"".equals(callback.trim())){
        		res.setContentType("text/javascript");
        		StringBuffer sb = new StringBuffer(callback);
        		sb.append("(");
        		sb.append(jsonStr);
        		sb.append(");");
        		res.getWriter().write(sb.toString());  
        	}else{
        		res.getWriter().write(jsonStr);  
        	}
        } catch (IOException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }
    
    
    public static void putMsgMap(String msg, Map map) {
        map.put(REQUEST_MESSAGE, msg);
    }
    
    public static void putMsgMap(Exception ex, Map map) {
        map.putAll(getMsgMap(ex.getMessage()));
    }
    
    public static Map getMsgMap(String msg) {
        Map map = new HashMap(1);
        map.put(REQUEST_MESSAGE, msg);
        return map;
    }
    
    /**
     * 页面显示年份下拉框，默认值为当前年，页面显示起始年也是当前年，页面元素名称year
     * 用法:见my_result.ftl
     * @param req
     * @param map
     * @return int
     */
    public static int getAndPutSelectYear(HttpServletRequest req, Map map) {
        int nowYear = Calendar.getInstance().get(Calendar.YEAR);
        map.put("startYear", "" + nowYear);
        return Integer.parseInt(getStrAndPutInMapSessionAutoPrefix(req, "year", "" + nowYear, map));
    }
    
    /**
     * 页面显示月份下拉框，默认值是当前月份，起始月也是当前月，selected=month;
     * @param request
     * @param map
     * @return
     */
    public static int getAndPutSelectMonth(HttpServletRequest request, Map map) {
        int nowMonth = Calendar.getInstance().get(Calendar.MONTH);
        return Integer.parseInt(getStrAndPutInMapSessionAutoPrefix(request, "month", "" + nowMonth, map));
    }
    
    /**
     * 根据名称从request取出变量，如果request中没有数据，则从session中找，如果session中也没有，取默认值
     * 返回的同时执行map.put(name, value)，session.put(className + "." + methodName + "." + name, value)
     * @param req
     * @param name
     * @param defaultVal
     * @param map
     * @return
     */
    public static String getStrAndPutInMapSessionAutoPrefix(HttpServletRequest req, String name, String defaultVal, Map map) {
        String sessionName = getPrefixPrivate() + name;
        String value = getStrFromSession(req, sessionName, defaultVal);
        value = getStrAndPutInMap(req, name, value, map);
        req.getSession().setAttribute(sessionName, value);
        return value;
    }
    
    public static String getStrFromSessionAutoPrefix(HttpServletRequest req, String name, String defaultVal) {
        String sessionName = getPrefixPrivate() + name;
        return getStrFromSession(req, sessionName, defaultVal);
    }
    
    public static void putInSessionAutoPrefix(HttpServletRequest req, String name, Object value) {
        String sessionName = getPrefixPrivate() + name;
        req.getSession().setAttribute(sessionName, value);
    }
    
    /**
     * 根据名称从request取出变量，返回的同时执行map.put(name, value)，有默认值
     * @param req
     * @param name
     * @param defaultValue
     * @param map
     * @return
     */
    public static String getStrAndPutInMap(HttpServletRequest req, String name, String defaultVal, Map map) {
        String value = ServletRequestUtils.getStringParameter(req, name, defaultVal);
        map.put(name, value);
        return value;
    }
    
    /**
     * 根据名称，从request取出变量，有默认值
     * @param req
     * @param name
     * @param defaultVal
     * @return
     */
    public static String getStrFromSession(HttpServletRequest req, String name, String defaultVal) {
        String value = (String) req.getSession().getAttribute(name);
        if (value == null) {
            return defaultVal;
        }
        return value;
    }
    

    
    /**
     * 得到当前所需前缀，例：
     * public class UserCtrl {
     *     public List findList() {
     *         System.out.println(Ctrl.getPrefix());
     *     };
     * }
     * 打印输出“UserCtrl.findList.”
     * @return 前缀
     * @deprecated
     */
    private static String getPrefixPrivate() {
        StackTraceElement ste = new Throwable().getStackTrace()[2];
        return ste.getClassName() + "." + ste.getMethodName() + ".";
    }
    
    public static String getPrefix() {
        StackTraceElement ste = new Throwable().getStackTrace()[1];
        return ste.getClassName() + "." + ste.getMethodName() + ".";
    }
    
    
    /**
     * 得到堆栈中第三个调用的类名，效果如下：
     * public class UserCtrl {
     *     public List findList() {
     *         System.out.println(Ctrl.getMethodName());
     *     };
     * }
     * 打印输出“UserCtrl”
     * @return
     */
    public static String getClassName() {
        return new Throwable().getStackTrace()[2].getClassName();
    }
    
    /**
     * 得到堆栈中第三个调用的方法名，效果如下：
     * public class UserCtrl {
     *     public List findList() {
     *         System.out.println(Ctrl.getMethodName());
     *     };
     * }
     * 打印输出“findList”
     * @return
     */
    public static String getMethodName() {
        return new Throwable().getStackTrace()[2].getMethodName();
    }
    
    public static boolean isFromMethod(String mothodName) {
        String methodName = new Throwable().getStackTrace()[2].getMethodName();
        return methodName.equals(mothodName);
    }
    
    public static boolean isNotFromMethod(String mothodName) {
        String methodName = new Throwable().getStackTrace()[2].getMethodName();
        return !methodName.equals(mothodName);
    }
    
    public static String testGetPrefix() {
        return getPrefixPrivate();
    }
    
    public static String testGetClassName() {
        return getClassName();
    }
    
    public static String testGetMethodName() {
        return getMethodName();
    }
    
    public static void main(String[] args) {
        System.out.println(testGetPrefix());
        System.out.println(testGetClassName());
        System.out.println(testGetMethodName());
    }
}
