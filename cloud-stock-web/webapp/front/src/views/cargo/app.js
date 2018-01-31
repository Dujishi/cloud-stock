import VHeader from '@/components/header/app.vue';
import VAddress from '@/components/address/app.vue';
import VPriceInput from '@/components/price-input/app.vue';
import VNumberInput from '@/components/number-input/app.vue';
import {
    getIndentList,
    getIndentDetail,
    addIndentPart,
    delIndent,
    submitIndentDetail,
    saveIndentDetail,
    getCustomerHintList,
    updateIndentName,
} from '@/api/index';
import { submitInfoRules, partRules, saveRules } from './ruleConfig';


export default {
    components: {
        VHeader,
        VAddress,
        VPriceInput,
        VNumberInput,
    },

    data() {
        return {
            // 菜单列表
            indentList: [],
            defaultIndent: {},
            // 是否可编辑配货单名称
            disabled: true,
            // 详情数据
            cargoDetail: {
                name: '',
                indentNo: '',
                customerName: '',
                contact: '',
                contactPhone: '',
                buyerCode: '',
                sellerCode: '',
                vin: '',
                province: '',
                city: '',
                district: '',
                address: '',
                carModel: '',
                partDepot: '',
                discountPrice: null,
                salesmanId: null,
                indentStatus: null,
                indentPartDtoList: [],
            },
            // 优惠金额
            couponPrice: null,
            labelWidth: '100px',

            // 显示添加零件弹框
            addPartFormModal: false,

            partForm: {
                oeNo: '',
                partName: '',
                originPlace: '',
                manufacturer: localStorage.getItem('supplierName') || '',
                partDepot: '',
                unitPrice: '',
                amount: 1,
            },
            partRules,
            cargoInfoRules: saveRules,
            companyProps: {
                value: 'customerName',
                label: 'customerName',
            },
        };
    },

    mounted() {
        this.getIndentList();
    },

    computed: {
        totalPrice() {
            let total = 0;
            this.cargoDetail.indentPartDtoList.forEach(ele => {
                if (ele.amount && ele.unitPrice) {
                    total += Number(ele.amount * ele.unitPrice);
                } 
            });
            return total.toFixed(2);
        },

        realPrice() {
            return this.totalPrice - this.couponPrice < 0
                ? 0
                : (this.totalPrice - this.couponPrice).toFixed(2);
        },

        activeIndent() {
            return this.defaultIndent ? this.defaultIndent : this.indentList[0];
        },
    },

    methods: {
        changeCargo(item) {
            this.defaultIndent = item;
            // this.cargoInfoRules = saveRules;
            this.getIndentDetail();
            this.disabled = true;
        },
        addPartBtn() {
            this.addPartFormModal = true;
        },

        canEdit() {
            this.disabled = false;
        },
        noEdit() {
            this.disabled = true;
            if (this.activeIndent.name !== this.cargoDetail.name) {
                updateIndentName({
                    indentNo: this.activeIndent.indentNo,
                    name: this.cargoDetail.name,
                }).then(res => {
                    if (res.success) {
                        this.activeIndent.name = this.cargoDetail.name;
                    } else {
                        this.$message.error(res.message);
                    }
                });
            }
        },

        // 删除配货单
        delIndent(index,item) {
            this.$confirm(`确认删除${item.name}吗？`, '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
            }).then(() => {
                delIndent({
                    indentNo: item.indentNo,
                }).then(res => {
                    if (res.success) {
                        this.$message.success('已成功删除');
                        if (this.defaultIndent.id===item.id) {
                            window.location.reload();
                        } else {
                            this.indentList.splice(index,1);
                        }
                    } else {
                        this.$message.error(res.message);
                    }
                });
            }).catch(() => {
                
            });
        },

        // 删除零件
        delPart(index,item) {
            this.$confirm(`确认删除${item.oeNo}吗？`, '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
            }).then(() => {

                this.cargoDetail.indentPartDtoList.splice(index, 1);
                this.$message.success('已成功删除');
            }).catch(() => {});
        },

        // 增加配件
        addPart() {
            this.$refs["partForm"].validate((valid) => {
                if (valid) {
                    const indentNo = {
                        indentNo: this.activeIndent.indentNo,
                    };
                    const form = { ...indentNo, ...this.partForm };
                    addIndentPart(form).then(res => {
                        if (res.success) {
                            this.cargoDetail.indentPartDtoList.push(res.data);
                            this.resetForm(this.partForm);
                            this.addPartFormModal = false;
                        } else {
                            this.$message.error(res.message);
                        }
                    });
                }
            });
        },

        // 重置表单数据
        resetForm(form) {
            const arr = Object.keys(form);
            arr.forEach(ele => {
                form[ele] = '';
            });
        },

        // 保存配单信息
        saveIndentDetail() {
            // this.cargoInfoRules = {};
            this.$refs["cargoInfo"].validate((valid) => {
                if (valid) {
                    saveIndentDetail(this.cargoDetail).then(res => {
                        if (res.success) {
                            this.$message.success('保存成功');
                            this.activeIndent.name = this.cargoDetail.name;
                        } else {
                            this.$message.error(res.message);
                        }
                    });
                } else {
                    this.$message.error('请将信息填写完整');
                }
            });
        },

        // 提交配货单信息
        submitIndentDetail() {
            // this.cargoInfoRules = submitInfoRules;
            setTimeout(() => {
                this.$refs["cargoInfo"].validate((valid) => {
                    if (valid) {
                        this.cargoDetail.discountPrice = Number(this.couponPrice);
                        // 检查配件价格和数量是否为空
                        if (!this.checkIsNull(this.cargoDetail.indentPartDtoList)) {
                            this.$message.error('请将零件的单价和数量填写完整');
                            return false;
                        }
                        submitIndentDetail(this.cargoDetail).then(res => {
                            if (res.success) {
                                this.$message.success('提交成功');
                                this.$router.push({
                                    path: '/order/list',
                                });
                            } else {
                                this.$message.error(res.message);
                            }
                        });
                    } else {
                        this.$message.error('请将信息填写完整');
                    }
                });
            }, 0);
        },

        // 检查配件价格和数量是否为空
        checkIsNull(list) {
            let isNull;
            for (let i = 0; i < list.length; i++) {
                if (!list[i].amount || !list[i].unitPrice) {
                    isNull = false;
                    break;
                } else {
                    isNull = true;
                }
            }
            return isNull;
        },

        // 获取配货单列表
        getIndentList() {
            getIndentList().then((res) => {
                if (res.success) {
                    if (res.data.length > 0) {
                        this.indentList = res.data;
                        this.defaultIndent = this.$route.query.indentNo?this.indentList.filter(i=>
                            i.indentNo === this.$route.query.indentNo
                        )[0]:'';
                        this.getIndentDetail();  
                    } else {
                        this.indentList = res.data;
                    }
                } else {
                    this.$message.error(res.message);
                }
            });
        },

        // 获取配货单详情
        getIndentDetail() {
            getIndentDetail({
                indentNo: this.activeIndent.indentNo,
            }).then(res => {
                if (res.success) {
                    if (!res.data.province || !res.data.city ||!res.data.district) {
                        res.data.province = '';
                        res.data.city = '';
                        res.data.district = '';
                    }
                    res.data.indentPartDtoList.forEach((i)=>{
                        if(!i.unitPrice) {
                            i.unitPrice='';
                        }
                        if(!i.amount) {
                            i.amount=1;
                        }
                    })
                    this.cargoDetail = res.data;
                } else {
                    this.$message.error(res.message);
                }
            });
        },

        // 根据公司名称获取数据
        querySearchAsync(queryStr, cb) {
            console.log(queryStr);
            getCustomerHintList({
                customerName: queryStr,
            }).then((res)=>{
                if (res.success) {
                    cb(res.data);
                }
            });
        },

        // 选中时触发
        companySelect(item) {
            this.cargoDetail.customerName = item.customerName;
            this.cargoDetail.address = item.address;
            this.cargoDetail.contactPhone = item.contactPhone;
            this.cargoDetail.contact = item.contact;
            this.cargoDetail.province = item.province;
            this.cargoDetail.city = item.city;
            this.cargoDetail.district = item.district;
        },

        changeVin(val) {
            this.cargoDetail.vin = val.toLocaleUpperCase();
        },
    },
};
