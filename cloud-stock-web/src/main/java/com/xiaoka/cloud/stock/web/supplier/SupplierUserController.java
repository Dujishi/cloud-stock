package com.xiaoka.cloud.stock.web.supplier;

import com.xiaoka.cloud.stock.service.core.util.AssertUtil;
import com.xiaoka.cloud.stock.service.supplier.CloudSupplierUserService;
import com.xiaoka.cloud.stock.service.supplier.dto.CloudSupplierUserDto;
import com.xiaoka.cloud.stock.service.supplier.param.SupplierUserParam;
import com.xiaoka.cloud.stock.web.util.ApiResultWrapper;
import com.xiaoka.cloud.stock.web.util.SessionUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Objects;

/**
 * Do something
 *
 * @author gancao 2017/11/15 19:07
 * @see [相关类/方法]
 * @since [版本号]
 */
@Controller
public class SupplierUserController {

	@Resource
	private CloudSupplierUserService cloudSupplierUserService;

	@RequestMapping(value = "/account/user", method = RequestMethod.GET)
	@ResponseBody
	public String getSupplierUser(HttpServletRequest request) {
		return ApiResultWrapper.success(SessionUtil.getUser(request));
	}

	@RequestMapping(value = "/account/list", method = RequestMethod.GET)
	@ResponseBody
	public String getSupplierUserList(HttpServletRequest request) {
		CloudSupplierUserDto cloudSupplierUserDto = SessionUtil.getCurrentUser();
		AssertUtil.assertTrueWithApiException(Objects.nonNull(cloudSupplierUserDto), "请先登录");
		return ApiResultWrapper.success(cloudSupplierUserService.getCloudSupplierUserList(cloudSupplierUserDto.getSupplierId(), cloudSupplierUserDto));
	}

	@RequestMapping(value = "/account/save", method = RequestMethod.POST)
	@ResponseBody
	public String saveOrUpdate(@RequestBody @Valid SupplierUserParam param) {
		CloudSupplierUserDto cloudSupplierUserDto = SessionUtil.getCurrentUser();
		AssertUtil.assertTrueWithApiException(Objects.nonNull(cloudSupplierUserDto), "请先登录");
		cloudSupplierUserService.saveOrUpdateCloudSupplierUser(param, cloudSupplierUserDto);
		this.updateSession(param.getId());
		return ApiResultWrapper.success("");
	}

	@RequestMapping(value = "/account/delete", method = RequestMethod.GET)
	@ResponseBody
	public String deleteUser(@RequestParam Integer accountId) {
		CloudSupplierUserDto cloudSupplierUserDto = SessionUtil.getCurrentUser();
		AssertUtil.assertTrueWithApiException(Objects.nonNull(cloudSupplierUserDto), "请先登录");
		cloudSupplierUserService.deleteCloudSupplierUser(accountId, cloudSupplierUserDto);
		return ApiResultWrapper.success("");
	}

	@RequestMapping(value = "/account/setBrandAuth", method = RequestMethod.POST)
	@ResponseBody
	public String setBrandAuth(@RequestBody SupplierUserParam param) {
		AssertUtil.assertTrueWithApiException(param != null, "参数不能为空");

		cloudSupplierUserService
				.setBrandAuth(param.getId(), param.getBrandIds(), SessionUtil.getCurrentUser().getName());

		return ApiResultWrapper.success(null);
	}

	private void updateSession(Integer userId){
		CloudSupplierUserDto userDto = SessionUtil.getCurrentUser();
		if (Objects.nonNull(userDto)) {
			if (Objects.equals(userDto.getId(), userId)){//修改自己
				SessionUtil.login(cloudSupplierUserService.getCloudSupplierUserDtoByPhone(userDto.getPhone()));
			}
		}
	}

}
