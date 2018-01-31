const mutations = {
    setLoadingVisible(state, loadingVisible) {
        state.loadingVisible = loadingVisible;
    },
    setSidebarVisible(state, sidebarVisible) {
        state.sidebarVisible = sidebarVisible;
    },
    setVinCode(state, vinCode) {
        state.vinCode = vinCode;
    },
    setPartName(state, partName) {
        state.partName = partName;
    },
    setCarModelList(state, carModelList) {
        state.carModelList = carModelList;
    },
    setCarModelId(state, carModelId) {
        state.carModelId = carModelId;
    },
    setCategoryObj(state, categoryObj) {
        state.categoryObj = categoryObj;
    },
    setCategoryList(state, categoryList) {
        state.categoryList = categoryList;
    },
    setPartList(state, partList) {
        state.partList = partList;
    },
    setAssemblyList(state, assemblyList) {
        state.assemblyList = assemblyList;
    },
    setAssemblyIndex(state, assemblyIndex) {
        state.assemblyIndex = assemblyIndex;
    },
    setSubAssemblyList(state, subAssemblyList) {
        state.subAssemblyList = subAssemblyList;
    },
    setSubAssemblyIndex(state, subAssemblyIndex) {
        state.subAssemblyIndex = subAssemblyIndex;
    },
    setPicNum(state, picNum) {
        state.picNum = picNum;
    },
    setOeCode(state, oeCode) {
        state.oeCode = oeCode;
    },
    setOePartList(state, oePartList) {
        state.oePartList = oePartList;
    },
    setOeBrandName(state, oeBrandName) {
        state.oeBrandName = oeBrandName;
    },
    setMyUserInfo(state, myUserInfo) {
        state.myUserInfo = myUserInfo;
    },
    setIsLogin(state, isLogin) {
        state.isLogin = isLogin;
    },
    setLoginDialogVisible(state, loginDialogVisible) {
        state.loginDialogVisible = loginDialogVisible;
    },
    setProvinceList(state, provinceList) {
        state.provinceList = provinceList;
    },
    setCurrentPage(state, currentPage) {
        state.currentPage = currentPage;
    },
    setRoleId(state, roleId) {
        state.roleId = roleId;
    },

    // 车型查询相关
    setBrandsList(state, brandList) {
        state.brandList = brandList;
    },
    setSeriesList(state, seriesList) {
        state.seriesList = seriesList;
    },
    setFirstLetter(state, firstLetter) {
        state.firstLetter = firstLetter;
    },
    setSeriesName(state, seriesName) {
        this.seriesName = seriesName;
    },
};

export default mutations;

