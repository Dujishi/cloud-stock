package com.xiaoka.cloud.stock.service.wrapper.superepc;

/**
 * Created by sabo on 16/11/2017.
 */
public class SuperEPCconstant {

	public static final String CREATE_BY = "SYS_ZS";

	static final String GRANT_CODE = "W9G6P21O";

	//static final String GRANT_CODE = "test";

	public static final int PIC_NUM_SIZE_FOR_GET_PART = 200;

	public static final Integer TYPE_ORIGINAL = 0; // 原厂

	public static final Integer TYPE_TIMER = 1; // 正时

	public static final String SEP_COMMA = ",";

	public static final String EPC_IMG_HOST = "http://101.37.20.126/NewPic/";

	/**
	 * 查找替换件接口返回的成功标示
	 */
	public static final String GET_REPLACE_PARTS_RESP_DESC = "查询成功！";
	/**
	 * 原厂替换件信息
	 */
	public static final String GET_REPLACE_TYPE_QP = "2";
	/**
	 * 品牌替换配件信息
	 */
	public static final String GET_REPLACE_TYPE_BP = "3";


	/**
	 * 根据Vin码查询TID(车款ID)
	 */
	public static final String URL_1_1 = "http://47.97.226.137/vtm/getTid";

	/**
	 * 查询厂商、品牌和首字母
	 */
	public static final String URL_1_2 = "http://47.97.226.137/carModel/getAllSeries";

	/**
	 * 根据厂商品牌及其他条件查询车款信息
	 */
	public static final String URL_1_3 = "http://47.97.226.137/carModel/getCarModelByVin";

	/**
	 * 查询车款配置信息
	 */
	public static final String URL_1_4 = "http://47.97.226.137/carModel/getCarModelInfoByTid";

	/**
	 * 车款查询（整表）
	 */
	public static final String URL_2_1 = "http://47.97.226.137/catalogue/getAllCarModel";

	/**
	 * 查询正时总成、分总成分类目录
	 */
	public static final String URL_2_2 = "http://47.97.226.137/catalogue/getTimerAccemblyList";

	/**
	 * 查询正时标准配件分类目录（整合）
	 */
	public static final String URL_2_3 = "http://47.97.226.137/catalogue/getTimerStandardParts";

	/**
	 * 查询正时标准配件分类目录
	 */
	public static final String URL_3_1 = "http://47.97.226.137/adaptation/getAssemblyCatalogue";

	/**
	 * 查询总成分总成下的图片信息
	 */
	public static final String URL_3_2 = "http://47.97.226.137/adaptation/getPicInfo";

	/**
	 * 查询总成下面的所有图片
	 */
	public static final String URL_3_2_1 = "http://47.97.226.137/adaptation/getPicInfoForAssembly";

	/**
	 * 查询配件信息
	 */
	public static final String URL_3_3 = "http://47.97.226.137/adaptation/getPartsInfo";

	/**
	 * 查询配件适配车款
	 */
	public static final String URL_3_6 = "http://47.97.226.137/part/getAdapterModels";

	/**
	 * 原厂4S零售价格
	 */
	public static final String URL_3_7 = "http://47.97.226.137/price/getOEPrice";

	/**
	 * 查找替换件
	 */
	public static final String URL_3_9 = "http://47.97.226.137/part/getReplaceParts";

	/**
	 * 件号检测
	 */
	public static final String URL_4_1 = "http://47.97.226.137/part/getReplaceParts";

	/**
	 * 参数所需字段
	 */
	static final String PARAM_grant_code = "grant_code";
	static final String PARAM_type = "type";
	static final String PARAM_offset = "offset";
	static final String PARAM_tid = "tid";
	static final String PARAM_timer_id = "timer_id";
	static final String PARAM_part_code = "part_code";
	static final String PARAM_pic_num = "pic_num";
	static final String PARAM_assembly = "assembly";
	static final String PARAM_assembly_id = "assembly_id";
	static final String PARAM_sub_assembly = "sub_assembly";
	static final String PARAM_sub_assembly_id = "sub_assembly_id";
	static final String PARAM_car_brand = "car_brand";
	static final String PARAM_car_oem = "car_oem";
	static final String PARAM_oem_brand= "oem_brand";
	static final String PARAM_oem_name = "oem_name";
	static final String PARAM_series= "series";
	static final String PARAM_make_year = "make_year";
	static final String PARAM_transmission = "transmission";
	static final String PARAM_displacement = "displacement";
	static final String PARAM_is_original= "is_original";
	static final String PARAM_vin = "vin";
	static final String PARAM_gp_id = "gp_id";
	static final String PARAM_bp_id = "bp_id";


}
