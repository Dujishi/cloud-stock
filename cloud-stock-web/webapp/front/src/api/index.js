/**
 * 接口文档请参考：http://doc.xiaokakeji.com:9090/index.php?s=/143&page_id=2199
 */
import { get, post } from './config';

/**
 * epc相关接口
 */
export const getCategory = params => get('epc/getCategory', params);
export const getPartByNames = params => post('epc/getPartByNames', params);
export const getPartPics = params => get('epc/getPartPics', params);
export const getPartsInfo = params => get('epc/getPartsInfo', params);
export const getPartDetail = params => get('epc/getPartDetail', params);
export const getPartStock = params => get('epc/getPartStock', params);
export const getBrandByCodes = params => post('epc/getBrandByCodes', params);
export const getPartByCodes = params => post('epc/getPartByCodes', params);
export const carBrand = params => get('epc/carBrand', params);
export const searchHistory = params => get('epc/searchHistory', params);
export const getPicUrl = params => get('epc/getPicUrl', params);
export const getAssembly = params => get('epc/getAssembly', params);

/**
 * 配货单
 */
export const getIndentList = params => get('indent/getIndentList', params);
export const getIndentDetail = params => get('indent/getIndentDetail', params);
export const addIndentPart = params => post('indent/addIndentPart', params);
export const addIndent = params => post('indent/addIndent', params);
export const saveIndentDetail = params => post('indent/saveIndentDetail', params);
// 提交配货单
export const submitIndentDetail = params => post('order/submitIndentDetail', params);
export const delIndent = params => post('indent/deleteIndent', params);
// 修改配货单名称
export const updateIndentName = params => post('indent/updateIndentName', params);


// customer
export const getCustomerHintList = params => get('customer/getCustomerHintList', params);

/**
 * 省市区
 */
export const getProvinceList = params => get('region/getProvinceList', params);
export const getCityList = params => get('region/getCityListByProvName', params);
export const getDistrictList = params => get('region/getCityDistrictListByProvAndCityName', params);


/**
 * 订单
 */
export const getOrderList = params => get('order/getOrderList', params);
export const deleteOrder = params => post('order/deleteOrder', params);
export const getOrderDetail = params => get('order/getOrderDetail', params);
export const updateIndent = params => post('order/modifyOrder', params);


/**
 * 登录
 */
export const login = params => post('user/login', params);
export const logout = params => get('user/logout', params);
export const modifyPassword = params => post('user/modify/pswd', params);
export const getCode = params => get('user/code/get', params);

/**
 * 个人中心
 */
export const getUserInfo = params => get('account/user', params);
export const getUserList = params => get('account/list', params);
export const saveUserInfo = params => post('account/save', params);
export const deleteUserInfo = params => get('account/delete', params);

/**
 * 我的余额
 */
export const getBalance = () => get('balance/myBalance');
export const getBalanceDetail = params => get('balance/myBalanceDetails', params);
// 发送提现验证码
export const sendCode = params => post('balance/sendSettlementCode', params);
// 发送银行卡修改验证码
export const sendBankSms = () => post('balance/sendBankEditSms');
// 修改银行卡信息
export const updateBankInfo = params => post('balance/updateBankInfo', params);
// 获取银行卡信息
export const getBankInfo = () => get('balance/getBankInfo');
// 获取银行卡支行和城市
export const getCityAndBank = () => get('balance/getCityAndBank');
// 提现
export const getMoney = params => post('balance/extract', params);

/**
 * 权限控制
 */
export const getAllBrand = params => get('epc/getAllBrand', params);
export const getAuthorisedBrand = params => get('epc/getAuthorisedBrand', params);
export const getBrandFrist = params => get('epc/getBrandFirstLetter', params);
export const setBrandAuth = params => post('account/setBrandAuth', params);
export const getAuthFirstLetter = params => get('epc/getAuthorisedByFirstLetter', params);

/**
 * 车型查询
 */

// 根据名称搜索车型品牌
export const getBrandByModelName = params => get('epc/getBrandByModelName', params);
// 根据首字母查询品牌
export const getBrandByFirstLetter = params => get('epc/getBrandByFirstLetter', params);
// 根据品牌查询车系
export const getSeriesByBrand = params => get('epc/getSeriesByBrand', params);
// 根据品牌车系查询车型年份
export const getModelYear = params => get('epc/getModelYearBySeries', params);
// 查询车型
export const getModelSeries = params => get('epc/getModelBySeries', params);

/**
 * 判断是否登录
 */
export const hasLogin = params => get('have/login', params);
