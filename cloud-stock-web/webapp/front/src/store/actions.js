import {
    getCategory,
    getPartByNames,
    getUserInfo,
    getPartByCodes,
    getProvinceList,
} from '@/api/index';

/**
 * 根据vin码查询
 */
export const getCategoryByVinAction = ({ commit }, { vin }) => {
    getCategory({
        vin,
    }).then((res) => {
        if (res.success) {
            const carModelList = res.data.carModelList || [];
            const categoryList = res.data.partCategoryList || [];
            const carModelId = carModelList.length ? carModelList[0].carModelId : '';
            commit('setCarModelList', carModelList);
            commit('setCategoryList', categoryList);
            commit('setCarModelId', carModelId);
        } else {
            commit('setCarModelList', []);
            commit('setCategoryList', []);
            commit('setCarModelId', '');
            window.$vm.$message.error(res.message);
        }
    });
};

/**
 * 通过carModelId查询类目
 */
export const getCategoryByModelAction = ({ commit }, { carModelId }) => {
    getCategory({
        carModelId,
    }).then((res) => {
        if (res.success) {
            const categoryList = res.data.partCategoryList || [];
            const carModelList = res.data.carModelList || [];
            commit('setCarModelList', carModelList);
            commit('setCategoryList', categoryList);
        } else {
            commit('setCategoryList', []);
            commit('setCarModelList', []);
            window.$vm.$message.error(res.message);
        }
    });
};

/**
 * 根据vin码和零件名查询
 */
export const getPartListByVinAction = ({ commit, getters }, { vin }) => {
    getPartByNames({
        vin,
        partNameList: getters.partNameList,
    }).then((res) => {
        if (res.success) {
            const carModelList = res.data.carModelList || [];
            const newCarModelId = carModelList.length ? carModelList[0].carModelId : '';
            const partList = res.data.partList || [];
            commit('setCarModelList', carModelList);
            commit('setCarModelId', newCarModelId);
            commit('setPartList', partList);
        } else {
            window.$vm.$message.error(res.message);
        }
    });
};

/**
 * 通过carModelId查询类目
 */
export const getPartListByModelAction = ({ commit, getters }, { carModelId }) => {
    getPartByNames({
        carModelId,
        partNameList: getters.partNameList,
    }).then((res) => {
        if (res.success) {
            const partList = res.data.partList || [];
            commit('setPartList', partList);
        } else {
            window.$vm.$message.error(res.message);
        }
    });
};

/**
 * 获取当前用户信息
 */
export const getMyUserInfoAction = ({ commit }) => {
    getUserInfo({}).then((res) => {
        if (res.success) {
            commit('setMyUserInfo', res.data);
        } else {
            window.$vm.$message.error(res.message);
        }
    });
};

/**
 * 通过零件码和品牌名称获取零件列表
 */
export const getOePartListAction = ({ commit, getters }, { brandName }) => {
    getPartByCodes({
        partCodeList: getters.oeCodeList,
        brandName,
    }).then((res) => {
        if (res.success) {
            commit('setOePartList', res.data || []);
        } else {
            commit('setOePartList', []);
            window.$vm.$message.error(res.message);
        }
    });
};

/**
 * 获取省份列表
 */

export const getProvince = ({ commit }) => {
    getProvinceList().then((res) => {
        if (res.success) {
            commit('setProvinceList', res.data);
        } else {
            window.$vm.$message.error(res.message);
        }
    });
};
