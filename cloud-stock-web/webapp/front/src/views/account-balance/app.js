import { getBalance, getBalanceDetail, sendCode, sendBankSms, updateBankInfo, getBankInfo, getCityAndBank, getMoney } from '@/api/index';
import VPriceInput from '@/components/price-input/app.vue';
import FormatDate from '../../mixins/formateDate';
import { bankInfoRules } from '../cargo/ruleConfig';

export default {
    components: {
        VPriceInput,
    },
    data () {
        return {
            billList: [],
            showPage: true,
            otherBankInputShow: false, // 其他银行？
            companyName: localStorage.getItem('supplierName'),
            balanceInfo: {
                withdrawAmount: 0,
            },
            showMoneyDialog: false,
            showCardDialog: false,
            formLabelWidth: "100px",
            moneyForm: {
                phoneNumber: null,
                smsCode: '',
            },
            withdrawAmount: null,
            cardForm: {
                bankNo: '',
                bankName: '',
                bankType: '',
                bankCity: '',
                bankBranch: '',
                bankUser: '',
                phone: '',
                bankAccType: 1,
                bankCityCode: '',
                bankAreaCode: '',
                smsCode: '',
            },
            bankDto: [], // 开户行列表
            cityDto: [], // 开户城市列表
            bankInfoRules,
            pageNumber: 1,
            listSize: 1,
            maxLength: 49,
        };
    },
    computed: {
        editText() {
            return this.balanceInfo.bankNo ? '修改' : '增加';
        },
    },
    filters: {
        formateDate: FormatDate,
    },
    mounted() {
        this.init();
    },

    methods: {
        init() {
            this.getBalance();
            this.getBankInfo();
            this.getBalanceDetail();
        },
        moneyDialogShow() {
            this.showMoneyDialog = true;
        },
        bankDialogShow() {
            this.getCityAndBank();
            this.showCardDialog = true;
        },
        // 获取编辑银行卡验证码
        getBankCode() {
            sendBankSms().then( res => {
                if (res.success) {
                    // this.cardForm.smsCode = res.code;
                    this.$message.success('验证码已发送');
                } else {
                    this.$message.error(res.message);
                }
            });
        },
        // 改变开户城市获取城市码和地区码
        cityChange(val) {
            this.cityDto.forEach((item) => {
                if (item.name === val) {
                    this.cardForm.bankCityCode = item.code;
                    this.cardForm.bankAreaCode = item.areaCode;
                }
            });
        },
        // 改变开户银行获取银行吗
        bankChange(val) {
            this.bankDto.forEach((item) => {
                if (item.bankName === val) {
                    this.cardForm.bankType = item.bankCode;
                }
            });
        },
        // 获取提现验证码
        getMoneyCode() {
            sendCode().then(res => {
                if (res.success) {
                    this.$message.success('验证码已发送');
                } else {
                    this.$message.error(res.message);
                }
            });
        },
        // 填写其他银行
        otherBank() {
            this.cardForm.bankName = '';
            this.otherBankInputShow = !this.otherBankInputShow;
        },
        // 提现
        getMoney() {
            if (!this.moneyForm.withdrawAmount || this.moneyForm.withdrawAmount > this.balanceInfo.withdrawAmount) {
                this.$message.error('提现金额输入有误');
                return;
            }
            if(!this.moneyForm.smsCode) {
                this.$message.error('请输入验证码');
                return;
            }
            getMoney({withdrawAmount: this.moneyForm.withdrawAmount, smsCode: this.moneyForm.smsCode}).then(res => {
                if (res.success) {
                    this.$message.success('提现成功');
                    this.init();
                    this.showMoneyDialog = false;
                } else {
                    this.$message.error(res.message);
                }
            })
        },
        // 编辑银行卡信息
        updateBankInfo() {
            this.$refs["cardForm"].validate((valid) => {
                if (valid) {
                    // 如果其他银行，banktype = 1099
                    if (this.otherBankInputShow) {
                        this.cardForm.bankType = '1099';
                    }
                    updateBankInfo(this.cardForm).then( res => {
                        if (res.success) {
                            this.$message.success('修改成功');
                            this.init();
                            this.showCardDialog = false;
                        } else {
                            this.$message.error(res.message);
                        }
                    });
                } else {
                    this.$message.error('请将信息填写完整');
                }
            });
        },
        // 获取余额
        getBalance() {
            getBalance().then(res => {
                if (res.success) {
                    this.balanceInfo = res.data;
                } else {
                    if (res.errCode === "CLOUD_STOCK_INVALID") {
                        this.showPage = false;
                    }
                }

            });
        },
        // 分页按钮
        changePage(val) {
            this.pageNumber = val;
            this.getBalanceDetail();
        },
        // 获取账单明细（分页）
        getBalanceDetail() {
            getBalanceDetail({ pageNumber: this.pageNumber }).then(res => {
                if (res.success) {
                    this.billList = res.data.data;
                    this.listSize = res.data.total;
                }
            });
        },
        // 获取银行卡详情
        getBankInfo() {
            getBankInfo().then(res => {
                if (res.success) {
                    // this.cardForm = res.data;
                    for (let key in this.cardForm) {
                        if (res.data.hasOwnProperty(key)) {
                            this.cardForm[key] = res.data[key];
                        }
                    }
                } else {
                    this.$message.error(res.message);
                }
            });
        },
        // 获取银行卡支行和城市
        getCityAndBank() {
            if (this.bankDto.length === 0 || this.cityDto.length === 0) {
                getCityAndBank().then(res => {
                    if (res.success) {
                        this.bankDto = res.data.bankDtos;
                        this.cityDto = res.data.cityDtos;
                    } else {
                        this.$message.error(res.message);
                    }
                });
            }
        },
    },
};
