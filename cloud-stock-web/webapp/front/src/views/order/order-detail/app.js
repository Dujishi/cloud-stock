import { getOrderDetail, updateIndent, deleteOrder} from '@/api/index';
import QRcode from 'qrcodejs2';
import clipboard from '../../../utils/clipboard';


export default {
    data() {
        return {
            orderDetail: {},
            orderNo: this.$route.query.orderNo || '',
            qrurl: '',
        };
    },
    mounted() {
        this.getOrderDetail();
    },

    methods: {
        getOrderDetail() {
            getOrderDetail({
                orderNo: this.orderNo,
            }).then(res=>{
                if (res.success) {
                    this.orderDetail = res.data;
                    this.qrCode(res.data.payUrlCode);
                } else {
                    this.$message.error(res.message);
                }
            });
        },
        // 返回上一级
        goBack() {
            this.$router.go(-1);
        },

        // 生成2微码
        qrCode(payUrlCode) {
            let baseUrl= 'https://m.ddyc.com/store';
            this.qrurl = `${baseUrl}?payUrlCode=${payUrlCode}`;
            new QRcode(document.getElementById('qrcode'), this.qrurl);
        },

        // 打印内容
        print() {
            window.print();
        },

        // 复制链接
        copyUrl() {
            
            if (clipboard(this.qrurl)) {
                this.$message.success('已复制到剪贴板中');
            } else {
                this.$message.error('请用手机扫码打开');
            }
        },

        // 复制图片
        copyImg() {
             
        },

        // 修改订单
        updateIndent() {
            updateIndent({
                orderNo: this.orderDetail.orderNo,
            }).then(res=>{
                if (res.success) {
                    this.$router.push({
                        path: '/cargo',
                        query: {
                            indentNo: this.orderDetail.indentNo,
                        },
                    });
                } else {
                    this.$message.error(res.message);
                }
            });
        },

        // 删除订单
        deleteOrder(orderNo) {
            this.$confirm(`确认删除${orderNo}号订单吗？`, '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
            }).then(() => {
                deleteOrder({
                    orderNo,
                }).then(res => {
                    if (res.success) {
                        this.$message.success('已成功删除');
                        this.$router.go(-1);
                    } else {
                        this.$message.error(res.message);
                    }
                });
            }).catch(() => {});
        },
    },

};

