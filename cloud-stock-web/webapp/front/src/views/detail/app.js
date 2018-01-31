import VActionBar from '@/components/action-bar/app.vue';
import VNoContent from '@/components/no-content/app.vue';
import VCartDialog from '@/components/add-cart/app.vue';
import { getPartDetail } from '@/api/index';
import queryUtils from '@/utils/query';
import StockPage from '../stock/app.vue';

export default {
    data() {
        return {
            partInfoList: [],
            suitCarList: [],
            replacePartList: [],
            urlParams: {},
            part: {},
            dialogShow: false,
        };
    },
    created() {
        this.loadData();
    },
    watch: {
        $route: 'loadData',
    },

    methods: {
        loadData() {
            this.urlParams = this.$route.query;
            getPartDetail({
                carModelId: this.urlParams.carModelId,
                partCode: this.urlParams.partCode,
                carBrandName: this.urlParams.carBrandName,
            }).then((res) => {
                if (res.success) {
                    this.partInfoList = res.data.partInfoList || [];
                    this.suitCarList = res.data.suitCarList || [];
                    this.replacePartList = res.data.replacePartList || [];
                } else {
                    this.$message.error(res.message);
                }
            });
        },
        showPicture(item) {
            const from = this.urlParams.from || 'vin';
            const params = queryUtils.serialize({
                from: this.urlParams.from,
                type: 'single',
                carModelId: item.carModelId,
                carBrandName: item.carBrandName,
                picNum: item.picNum,
                picName: item.picName,
                carModelName: item.carModelName,
                extraParam: encodeURIComponent(item.extraParam || ''),
            });
            this.$router.push({ path: `/${from}/picture?${params}` });
            // this.$router.push({ path: `/vin/picture?${params}` });
        },
        // 显示加入购物车弹框
        addCart(item) {
            console.log(item);
            this.dialogShow = true;
            this.part = item;
        },
    },
    components: {
        VActionBar,
        VNoContent,
        VCartDialog,
        StockPage,
    },
};
