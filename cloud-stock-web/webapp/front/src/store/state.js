const state = {
    // 是否显示加载进度条
    loadingVisible: false,
    // 是否显示侧边栏
    sidebarVisible: true,
    // vin码
    vinCode: '',
    // 零件名称
    partName: '',
    // 匹配到的车型列表
    carModelList: [],
    // 当前选择的车型
    carModelId: '',
    // 当前分类
    categoryObj: {},
    // 分类列表
    categoryList: [],
    // vin码+零件名查询出来的零件列表
    partList: [],
    // 总成列表
    assemblyList: [],
    // 选中的总成索引
    assemblyIndex: [],
    // 子总成列表（图片列表）
    subAssemblyList: [],
    // 子总成索引
    subAssemblyIndex: [],
    // 选中的图片
    picNum: '',
    // 零件码
    oeCode: '',
    // 品牌名称
    oeBrandName: '',
    // 通过零件码查出来的零件列表
    oePartList: [],
    // 当前用户信息
    myUserInfo: {},
    // 是否登录
    isLogin: true,
    // 是否显示登录弹窗
    loginDialogVisible: false,
    // 省份列表
    provinceList: [],

    // 当前页面url
    currentPage: '',

    // 车的品牌List
    brandList: [],
    // 车系List
    seriesList: [],
    // 当前选中的品牌首字母
    firstLetter: '',
    seriesName: '',
    roleId: '', // 用户角色
};

export default state;
