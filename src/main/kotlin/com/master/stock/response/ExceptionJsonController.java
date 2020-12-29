package com.master.stock.response;

import com.google.gson.reflect.TypeToken;
import com.master.stock.util.StrUtil;
import com.master.stock.util.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: ExceptionJsonController
 * @Description: 通用异常处理，全部返回json格式，屏蔽html
 * @author he
 * @date Mar 13, 2020
 * 
 */
@Controller
public class ExceptionJsonController extends BasicErrorController {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	public ExceptionJsonController(ServerProperties serverProperties) {
		super(new DefaultErrorAttributes(), serverProperties.getError());
	}

	@Override
	public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		ModelAndView modelAndView = new ModelAndView();
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		BaseResponse<Object> errorRes = new BaseResponse<>();
		Map<String, Object> resMap = new HashMap<>();
		if (statusCode.equals(400)) {
			errorRes.setMessage("用户登录信息已过期，请重新登录");
			errorRes.setStatus("failed");
			errorRes.setCode(statusCode.toString());
		} else if (statusCode.equals(401)) {
			errorRes.setMessage("该账号已在别处登录,是否重新登录?");
			errorRes.setStatus("failed");
			errorRes.setCode(statusCode.toString());
		} else if (statusCode.equals(403)) {
			errorRes.setMessage("无效的访问权限");
			errorRes.setStatus("failed");
			errorRes.setCode(statusCode.toString());
		} else if (statusCode.equals(404)) {
			errorRes.setMessage("无效的url");
			errorRes.setStatus("failed");
			errorRes.setCode(statusCode.toString());
		} else if (statusCode.equals(405)) {
			errorRes.setMessage("无效的" + request.getMethod());
			errorRes.setStatus("failed");
			errorRes.setCode(statusCode.toString());
		} else if (statusCode.equals(406)) {
			errorRes.setMessage("token令牌过期，需要重新刷新令牌服务");
			errorRes.setStatus("failed");
			errorRes.setCode(statusCode.toString());
		} else {
			errorRes.getInternalError();
			errorRes.setCode(statusCode.toString());
		}
		String res = JSON.toJSONString(errorRes);
		log.error(res);
		resMap = JSON.parse(res, new TypeToken<Map<String, Object>>() {
		});
		view.setAttributesMap(resMap);
		modelAndView.setView(view);
		modelAndView.setStatus(HttpStatus.valueOf(statusCode));
		return modelAndView;
	}

	@Override
	public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
		ResponseEntity<Map<String, Object>> entity = super.error(request);
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		String message = (String) request.getAttribute("javax.servlet.error.message");
		String url = (String) request.getAttribute("javax.servlet.error.request_uri");
		log.error("url:{},error-msg:{}", url, message);
		BaseResponse<Object> errorRes = new BaseResponse<>();
		Map<String, Object> resMap = new HashMap<>();
		if (statusCode.equals(400)) {
			if (entity.getBody() != null && entity.getBody().get("message") != null
					&& (StrUtil.startWith(entity.getBody().get("message").toString(), "Could not read JSON")
							|| StrUtil.startWith(entity.getBody().get("message").toString(), "Required"))) {
				errorRes.setMessage("请求参数类型错误!");
				errorRes.setStatus("failed");
				errorRes.setCode("500");
				statusCode = 500;
			} else {
				errorRes.setMessage("用户登录信息已过期，请重新登录");
				errorRes.setStatus("failed");
				errorRes.setCode(statusCode.toString());
			}
		} else if (statusCode.equals(401)) {
			errorRes.setMessage("该账号已在别处登录,是否重新登录?");
			errorRes.setStatus("failed");
			errorRes.setCode(statusCode.toString());
		} else if (statusCode.equals(404)) {
			errorRes.setMessage("无效的url");
			errorRes.setStatus("failed");
			errorRes.setCode(statusCode.toString());
		} else if (statusCode.equals(405)) {
			errorRes.setMessage("无效的" + request.getMethod());
			errorRes.setStatus("failed");
			errorRes.setCode(statusCode.toString());
		} else if (statusCode.equals(406)) {
			errorRes.setMessage("token令牌过期，需要重新刷新令牌服务");
			errorRes.setStatus("failed");
			errorRes.setCode(statusCode.toString());
		} else if (statusCode.equals(403)) {
			errorRes.setMessage("无效的访问权限");
			errorRes.setStatus("failed");
			errorRes.setCode(statusCode.toString());
		} else {
			errorRes.getInternalError();
			errorRes.setCode(statusCode.toString());
		}
		String res = JSON.toJSONString(errorRes);
		log.error(res);
		resMap = JSON.parse(res, new TypeToken<Map<String, Object>>() {
		});
		return new ResponseEntity<Map<String, Object>>(resMap, HttpStatus.valueOf(statusCode));
	}
}
