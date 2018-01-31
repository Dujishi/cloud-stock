/**
 * 
 */
package com.xiaoka.cloud.stock.admin.core.permission;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaoka.cloud.stock.admin.common.CommonErrorMessage;
import com.xiaoka.cloud.stock.admin.core.permission.vo.SystemVO;
import com.xiaoka.cloud.stock.admin.core.permission.vo.UserVO;
import com.xiaoka.freework.help.api.ApiException;
import com.xiaoka.freework.help.api.ApiResult;
import com.xiaoka.freework.help.page.PageList;
import com.xiaoka.freework.utils.Utils;
import com.xiaoka.ups.client.UpsClient;
import com.xiaoka.ups.client.dto.SystemDto;
import com.xiaoka.ups.client.soa.dto.UserSoaDto;
import com.xiaoka.ups.client.soa.service.UserSoaService;

/**
 *
 */
@Controller
@RequestMapping(path = "/core/permission")
@PermissionFilter(auth = false)
public class PermissionController {

	@Resource(name = "upsUserSoaService")
	private UserSoaService upsUserSoaService;

	/**
	 * 获取系统菜单信息
	 */
	@RequestMapping(path = "/menu")
	@ResponseBody
	public ApiResult<SystemVO> menu(HttpServletRequest request) {
		SystemVO systemVO = new SystemVO();
		UserVO user = CSUserContext.getUser();
		String ctx = request.getContextPath();
		List<SystemDto> systems = Utils.get(UpsClient.class).getUser().getSystems();
		Optional<SystemDto> systemDtoOptional = systems.stream().filter(s -> s.getRootPath().equals(ctx)).findFirst();
		if (!systemDtoOptional.isPresent()) {
			throw new ApiException(PermissionErrorMessage.SYSTEM_DENY);
		}
		SystemDto systemDto = systemDtoOptional.get();
		systemVO.setUser(user);
		systemVO.setSystemName(systemDto.getName());
		systemVO.setDefaultPath(systemDto.getPath());
		systemVO.setChildren(systemDto.getChildren());
		systemVO.setSystemDtos(systems);
		return new ApiResult<>(systemVO);
	}

	/**
	 * 根据输入的用户名，模糊查询返回匹配到的用户列表数据
	 */
	@RequestMapping(path = "/users")
	@ResponseBody
	public ApiResult<List<UserVO>> users(@RequestParam("name") String name, HttpServletRequest request) {
		if (StringUtils.isBlank(name)) {
			throw new ApiException(CommonErrorMessage.PARAM_ERR);
		}

		UserSoaDto user = new UserSoaDto();
		user.setName(name);
		PageList<UserSoaDto> pageList = PageList.build(1, 8);
		pageList.setCondition(user);

		List<UserSoaDto> users = upsUserSoaService.findUserPageList(pageList).getData();
		if (!ObjectUtils.isEmpty(users)) {
			List<UserVO> userList = users.stream().map(u -> {
				UserVO userVO = new UserVO();
				userVO.setName(u.getName());
				userVO.setNickName(u.getNickName());
				return userVO;
			}).sorted((a, b) -> a.getName().compareTo(b.getName())).collect(Collectors.toCollection(LinkedList::new));
			return new ApiResult<>(userList);
		}
		return new ApiResult<>(new ArrayList<>());
	}


}
