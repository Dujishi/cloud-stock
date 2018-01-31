import { getOrderList, deleteOrder, updateIndent } from '@/api/index';


export default {
    components: {
    },
    data() {
        return {
            orderList: [],
            pageNumber: Number(this.$route.query.pageNumber) || 1,
            keyWord: this.$route.query.queryStr || '',
            orderStatus: this.$route.query.orderStatus || '',
            totalData: 0,
        };
    },
    mounted() {
        this.getOrderList();
    },

    methods: {
        getOrderList(pageNumber) {
            getOrderList({
                pageNumber: pageNumber || this.pageNumber,
                orderStatus: this.orderStatus,
                queryStr: this.keyWord,
            }).then(res => {
                if (res.success) {
                    this.orderList = res.data.data;
                    this.totalData = res.data.total;
                    this.changeUrlState();
                } else {
                    this.$message.error(res.message);
                }
            });
        },
        // 修改订单
        updateOrder(item) {
            updateIndent({
                orderNo:item.orderNo,
            }).then(res => {
                if (res.success) {
                    this.$router.push({
                        path: '/cargo',
                        query: {
                            indentNo: res.data,
                        },
                    });
                } else {
                    this.$message.error(res.message);
                }
            });
            
        },

        // 删除订单
        deleteOrder(item) {
            this.$confirm(`确认删除${item.orderNo}号订单吗？`, '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
            }).then(() => {
                deleteOrder({
                    orderNo: item.orderNo,
                }).then(res => {
                    if (res.success) {
                        this.$message.success('已成功删除');
                        this.getOrderList();
                    } else {
                        this.$message.error(res.message);
                    }
                });
            }).catch(() => {
                
            });
        },
        // 详情页
        goDetail(orderNo) {
            this.$router.push({
                path: '/order/detail',
                query: {
                    orderNo,
                },
            });
        },

        // 分页
        handleCurrentChange(pageNumber) {
            this.pageNumber = pageNumber;
            this.getOrderList();
        },

        // 无刷新更改浏览器url
        changeUrlState() {
            const fullUrl = window.location.href;
            const baseUrl = fullUrl.split('?')[0];
            const newUrl = `${baseUrl}?queryStr=${this.keyWord}&orderStatus=${this.orderStatus}&pageNumber=${this.pageNumber}`;
            history.replaceState('', null, newUrl);
        },
    },
};
