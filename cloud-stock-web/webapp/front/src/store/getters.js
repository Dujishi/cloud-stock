import state from "./state";

export const loadingVisible = state => state.loadingVisible;
export const sidebarVisible = state => state.sidebarVisible;
export const vinCode = state => state.vinCode;
export const partName = state => state.partName;
export const carModelId = state => state.carModelId;
export const carModelList = state => state.carModelList;
export const categoryObj = state => state.categoryObj;
export const partList = state => state.partList;
export const categoryList = state => state.categoryList;
export const assemblyList = state => state.assemblyList;
export const assemblyIndex = state => state.assemblyIndex;
export const subAssemblyList = state => state.subAssemblyList;
export const subAssemblyIndex = state => state.subAssemblyIndex;
export const picNum = state => state.picNum;
export const oeCode = state => state.oeCode;
export const oePartList = state => state.oePartList;
export const oeBrandName = state => state.oeBrandName;
export const myUserInfo = state => state.myUserInfo;
export const isLogin = state => state.isLogin;
export const loginDialogVisible = state => state.loginDialogVisible;
export const provinceList = state => state.provinceList;
export const currentPage = state => state.currentPage;

// 车系查询相关
export const brandList = state => state.brandList;
export const seriesList = state => state.seriesList;
export const firstLetter = state => state.firstLetter;
export const seriesName = state => state.seriesName;

export const roleId = (state) => {
    return state.roleId ? state.roleId : localStorage.getItem('roleId');
};

export const partNameList = (state) => {
    return state.partName ? state.partName.split('\n') : [];
};

export const oeCodeList = (state) => {
    return state.oeCode ? state.oeCode.split('\n') : [];
};

export const carModel = (state) => {
    let model = {};
    state.carModelList.forEach((v) => {
        if (v.carModelId === state.carModelId) {
            model = v;
        }
    });
    return model;
};
