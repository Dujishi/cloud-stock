import { mapGetters, mapMutations } from 'vuex';
import { getPartsInfo, getPartPics, getPicUrl } from '@/api/index';
import getAssemblyList from '@/mixins/getAssemblyList';
import VImageTag from '@/components/image-tag/app.vue';
import VActionBar from '@/components/action-bar/app.vue';
import VCopy from '@/components/copy/app.vue';
import queryUtils from '@/utils/query';

export default {
    mixins: [getAssemblyList],
    data() {
        return {
            showPage: false,
            tableData: [],
            selectedRows: [],

            assemblyObj: {},

            markImage: null,
            images: [],
            initImageIndex: 0,

            // 初始化标记
            currentShowTag: null,

            maxHeight: '',

            from: '',
            picNum: '',
            picName: '',
            carModelId: '',
            carBrandName: '',
            carModelName: '',
            extraParam: '',
            isSingleImage: false,
        };
    },
    computed: {
        ...mapGetters([
            'categoryObj',
            'carModel',
            'vinCode',
            'assemblyList',
            'assemblyIndex',
            'subAssemblyList',
        ]),
    },
    created() {
        const query = this.$route.query;
        this.carModelId = query.carModelId;
        this.picNum = query.picNum;
        this.picName = query.picName;
        this.carBrandName = query.carBrandName;
        this.carModelName = query.carModelName;
        this.from = query.from;
        this.extraParam = decodeURIComponent(query.extraParam || '');

        this.isSingleImage = query.type === 'single';

        if (this.from === 'vin') {
            if (!this.vinCode || !this.picNum) {
                this.$router.replace({ path: '/vin' });
                return;
            }
        }

        // 从EPC图进来只有单张图片
        if (this.isSingleImage) {
            this.loadData(() => {
                // this.getPicUrl();
            });
        } else {
            this.loadData();
            // this.initPicList();
            this.assemblyObj = this.assemblyList[this.assemblyIndex];
            console.log(this.assemblyObj);
            this.getImages();
        }

        this.setSidebarVisible(false);
    },
    beforeRouteLeave(to, from, next) {
        this.showPage = false;
        this.setSidebarVisible(true);
        next();
        // 导航离开该组件的对应路由时调用
        // 可以访问组件实例 `this`
    },
    methods: {
        ...mapMutations([
            'setSidebarVisible',
            'setSubAssemblyList',
        ]),
        loadData(callback, currentIndex) {
            getPartsInfo({
                carModelId: this.carModelId,
                picNum: this.picNum,
                picName: this.picName,
                extraParam: this.extraParam,
            }).then((res) => {
                if (res.success) {
                    this.tableData = res.data.partList || [];
                    if (this.isSingleImage && this.tableData.length) {
                        this.images = [{
                            picLocalPath: this.tableData[0].picUrl,
                        }];
                    }

                    this.markImage = {
                        carModelPicMarkItemList: res.data.picMark && res.data.picMark.carModelPicMarkItemList ? res.data.picMark.carModelPicMarkItemList : [],
                        picLocalPath: res.data.picMark && res.data.picMark.picLocalPath ? res.data.picMark.picLocalPath : this.images[0].picLocalPath,
                    };

                    this.showPage = true;
                    if (typeof callback === 'function') {
                        callback();
                    }
                    this.$nextTick(() => {
                        const bd = document.querySelector('#bd');
                        const bdHeight = bd.offsetHeight;
                        this.maxHeight = bdHeight;
                    });
                } else {
                    this.$message.error(res.message);
                }
            });
        },
        // getPicUrl() {
        //     getPicUrl({
        //         carModelId: this.carModelId,
        //         picNum: this.picNum,
        //         picName: this.picName,
        //     }).then((res) => {
        //         if (res.success) {
        //             if (!res.data) {
        //                 this.getImages({
        //                     subAssemblyList: [{
        //                         picUrl: this.tableData[0].picUrl,
        //                     }],
        //                 });
        //                 console.log(this.images);
        //             }
        //         } else {
        //             this.$message.error(res.message);
        //         }
        //     });
        // },
        // initPicList() {
        //     this.assemblyObj = this.assemblyList[this.assemblyIndex];
        //     this.getImages(this.assemblyObj);
        // },
        getImages() {
            const list = [];
            this.subAssemblyList.forEach((v, index) => {
                list.push({
                    picLocalPath: v.picUrl,
                    carModelPicMarkItemList: [],
                });
                if (v.picNum === this.picNum && v.picName === this.picName) {
                    this.initImageIndex = index;
                }
                // if (v.picMark) {
                //     list.push(v.picMark);
                // } else {
                //     list.push({
                //         rawPicWidth: 500,
                //         rawPicHeight: 500,
                //         picLocalPath: v.picUrl,
                //         carModelPicMarkItemList: [],
                //     });
                // }
            });
            this.images = list;
        },
        gotoDetail(row) {
            const params = queryUtils.serialize({
                from: 'detail',
                carModelId: this.carModelId,
                partCode: row.partCode,
                carBrandName: this.carBrandName,
            });
            this.$router.push({ path: `/${this.from}/detail?${params}` });
        },
        gotoStock(row) {
            this.$router.push({ path: `/${this.from}/stock?partCodeList=${row.partCode}` });
        },
        handleSelectionChange(val) {
            this.selectedRows = val;
        },
        gotoStockMultiple() {
            if (this.selectedRows.length === 0) {
                this.$message.error('请选择需要查库存报价的行');
                return;
            }
            const partCodeList = [];
            this.selectedRows.forEach((v) => {
                partCodeList.push(v.partCode);
            });
            this.$router.push({ path: `/${this.from}/stock?partCodeList=${partCodeList.join(',')}` });
        },
        changeAssembly(obj) {
            this.assemblyObj = obj;
            // this.getImages(obj);
            // this.changeImage(0);

            getPartPics({
                carModelId: this.carModelId,
                type: this.categoryObj.type,
                assemblyName: this.assemblyObj.assemblyName,
                assemblyId: this.assemblyObj.assemblyId,
                categoryName: this.categoryObj.categoryName,
                extraParam: decodeURIComponent(this.assemblyObj.extraParam || ''),
            }).then((res) => {
                if (res.success) {
                    this.setSubAssemblyList(res.data || []);
                    this.getImages();
                    this.changeImage(0);
                } else {
                    this.$message.error(res.message);
                }
            });
        },
        changeImage(currentIndex) {
            // const subAssemblyObj = this.assemblyObj.subAssemblyList[currentIndex];
            const subAssemblyObj = this.subAssemblyList[currentIndex];
            this.extraParam = subAssemblyObj.extraParam || '';
            this.picNum = subAssemblyObj.picNum;
            this.picName = subAssemblyObj.picName;
            this.loadData(null, currentIndex);
            this.changUrlState();
        },
        // 无刷新更改浏览器地址
        changUrlState() {
            const fullUrl = window.location.href;
            const baseUrl = fullUrl.split('?')[0];
            const newUrl = `${baseUrl}?from=${this.from}&carModelId=${this.carModelId}&carBrandName=${this.carBrandName}&picNum=${this.picNum}&picName=${this.picName}&extraParam=${encodeURIComponent(this.extraParam)}`;
            history.replaceState('', null, newUrl);
        },
    },
    components: {
        VActionBar,
        VImageTag,
        VCopy,
    },
};
