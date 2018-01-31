import VActionBar from '@/components/action-bar/app.vue';
import { getAssembly, getPartPics } from '@/api/index';
import getAssemblyList from '@/mixins/getAssemblyList';
import { mapGetters, mapMutations } from 'vuex';
import queryUtils from '@/utils/query';

export default {
    mixins: [getAssemblyList],
    data() {
        return {
            showPage: false,
            currentIndex: 0,
            // subAssemblyList: [],
        };
    },
    computed: {
        ...mapGetters([
            'categoryObj',
            'categoryList',
            'carModel',
            'carModelId',
            'assemblyList',
            'subAssemblyList',
        ]),
    },
    created() {
        if (!(this.carModelId >= 0)) {
            this.$router.replace({ path: '/vin' });
            return;
        }
        this.loadData();
    },
    methods: {
        ...mapMutations([
            'setCategoryObj',
            'setAssemblyList',
            'setAssemblyIndex',
            'setSubAssemblyList',
        ]),
        loadData() {
            getAssembly({
                carModelId: this.carModelId,
                categoryId: this.categoryObj.categoryId,
                categoryName: this.categoryObj.categoryName,
                extraParam: this.categoryObj.extraParam || '',
                type: this.categoryObj.type,
            }).then((res) => {
                if (res.success) {
                    this.setAssemblyList(res.data || []);
                    if (this.assemblyList.length > 0) {
                        if (this.assemblyList.length > 1) {
                            this.currentIndex = 1;
                        } else {
                            this.currentIndex = 0;
                        }
                        this.getPartPics();
                    }
                } else {
                    this.$message.error(res.message);
                }
            });
        },
        getPartPics() {
            const assemblyObj = this.assemblyList[this.currentIndex];
            getPartPics({
                carModelId: this.carModelId,
                type: this.categoryObj.type,
                assemblyName: assemblyObj.assemblyName,
                assemblyId: assemblyObj.assemblyId,
                categoryName: this.categoryObj.categoryName,
                extraParam: assemblyObj.extraParam || '',
            }).then((res) => {
                if (res.success) {
                    // this.subAssemblyList = res.data || [];
                    // this.setAssemblyList(this.getAssemblyList(res.data || []));
                    this.setSubAssemblyList(res.data || []);
                    this.showPage = true;
                } else {
                    this.$message.error(res.message);
                }
            });
        },
        changeAssembly(index) {
            this.currentIndex = index;
            this.getPartPics();
        },
        gotoPicturePage(subAssemblyObj) {
            const params = queryUtils.serialize({
                from: 'vin',
                carModelId: this.carModelId,
                carBrandName: this.carModel.carBrandName,
                picNum: subAssemblyObj.picNum || '',
                picName: subAssemblyObj.picName || '',
                extraParam: subAssemblyObj.extraParam ? encodeURIComponent(subAssemblyObj.extraParam) : '',
            });

            this.setAssemblyIndex(this.currentIndex);
            this.$router.push({ path: `/vin/picture?${params}` });
        },
        changeCategory(obj) {
            this.setCategoryObj(obj);
            this.loadData();
        },
    },
    components: {
        VActionBar,
    },
};
