import { mapGetters } from 'vuex';
import VNoContent from '@/components/no-content/app.vue';
import queryUtils from '@/utils/query';

export default {
    data() {
        return {
            selectedRows: [],
        };
    },
    created() {
        if (!this.oeCode) {
            this.$router.replace({ path: '/part' });
        }
    },
    computed: {
        ...mapGetters([
            'oeCode',
            'oeCodeList',
            'oeBrandName',
            'oePartList',
        ]),
    },
    methods: {
        gotoDetail(row) {
            const params = queryUtils.serialize({
                from: 'part',
                partCode: row.partCode,
                carBrandName: this.oeBrandName,
            });
            this.$router.push({ path: `/part/detail?${params}` });
        },
        gotoStock(row) {
            this.$router.push({ path: `/part/stock?partCodeList=${row.partCode}` });
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
            this.$router.push({ path: `/part/stock?partCodeList=${partCodeList.join(',')}` });
        },
        setSelectable(row) {
            return Boolean(row.partName);
        },
        tableRowClassName(row) {
            if (!row.partName) {
                return 'row-disabled';
            }
            return '';
        },
    },
    components: {
        VNoContent,
    },
};
