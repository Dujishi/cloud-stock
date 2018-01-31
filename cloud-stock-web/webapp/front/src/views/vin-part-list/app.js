import { mapGetters, mapMutations } from 'vuex';
import VChangeModel from '@/components/change-model/app.vue';
import queryUtils from '@/utils/query';

export default {
    data() {
        return {
            selectedRows: [],
        };
    },
    created() {
        if (!this.vinCode) {
            this.$router.replace({ path: '/vin' });
        }
    },
    computed: {
        ...mapGetters([
            'vinCode',
            'partNameList',
            'carModelId',
            'carModel',
            'partList',
        ]),
    },
    watch: {
        $route: 'fetchData',
    },
    methods: {
        gotoDetail(row) {
            const params = queryUtils.serialize({
                from: 'vin',
                carModelId: this.carModelId,
                partCode: row.partCode,
                carBrandName: this.carModel.carBrandName,
            });
            this.$router.push({ path: `/vin/detail?${params}` });
        },
        gotoStock(row) {
            this.$router.push({ path: `/vin/stock?partCodeList=${row.partCode}` });
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
            this.$router.push({ path: `/vin/stock?partCodeList=${partCodeList.join(',')}` });
        },
    },
    components: {
        VChangeModel,
    },
};
