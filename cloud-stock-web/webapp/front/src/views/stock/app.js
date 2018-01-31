import VActionBar from '@/components/action-bar/app.vue';
import VCartDialog from '@/components/add-cart/app.vue';
import { getPartStock } from '@/api/index';
import { getPartStockByPartCode } from '@/services/index';

export default {
    props: ['from'],
    data() {
        return {
            data: [],
            dialogShow: false,
            part: {},
            showBar: true,
        };
    },
    mounted() {
        this.loadData();
    },
    watch: {
        $route: 'loadData',
    },
    methods: {
        loadData() {
            let partCodeList;
            if (this.$route.query.partCodeList) {
                partCodeList = this.$route.query.partCodeList.split(',');
            } else {
                partCodeList = this.$route.query.partCode;
                this.showBar = false;
            }
            getPartStock({
                codes: partCodeList,
            }).then((res) => {
                console.log('已加载完库存信息=>', JSON.stringify(res));
                if (res.success) {
                    const data = res.data || [];
                    data.forEach((item, index) => {
                        item.index = index;
                        if (!item.stockInfo) {
                            item.stockInfo = {};
                        }
                        item.stockInfo.balanceCount = null;
                    });
                    this.data = data;
                    if (partCodeList.length) {
                        this.getPartStockService(partCodeList);
                    }
                } else {
                    this.$message.error(res.message);
                }
            });
        },
        getPartStockService(params) {
            getPartStockByPartCode(params).then((res) => {
                console.log('已加载桥接信息=>', JSON.stringify(res));
                if (res.success) {
                    let data = this.data;
                    const sData = res.data;

                    const addData = [];
                    if (sData && sData.length) {
                        sData.forEach((it1) => {
                            let isExist = false;
                            data.forEach((it2) => {
                                if ((!it2.stockInfo.cPartId && it2.stockInfo.partCode === it1.partCode) || (it1.cPartId === it2.stockInfo.cPartId && it1.depot === it2.stockInfo.depot)) {
                                    isExist = true;
                                    it2.flag = it1.flag;
                                    Object.assign(it2.stockInfo, it1);
                                }
                            });
                            if (!isExist) {
                                addData.push({
                                    stockInfo: it1,
                                });
                            }
                        });
                    }
                    data = data.concat(addData);
                    this.data = data;
                }
            });
        },
        getExpandRowKeys() {
            const keys = [];
            this.data.forEach((v) => {
                if (v.flag !== 1 && (v.recommendSupplierInfo || v.replaceCodeList)) {
                    keys.push(`${v.stockInfo.partCode}${v.stockInfo.depot}`);
                }
            });
            return keys;
        },
        getRowKey(row) {
            return `${row.stockInfo.partCode}${row.stockInfo.depot}`;
        },
        tableRowClassName(row) {
            if (row.flag === 2) {
                return 'row-sellout';
            }
            if (row.flag === 3) {
                return 'row-none';
            }
            return '';
        },
        // 显示加入购物车弹框
        addCart(item) {
            this.dialogShow = true;
            this.part = item;
        },
    },
    components: {
        VActionBar,
        VCartDialog,
    },
};
