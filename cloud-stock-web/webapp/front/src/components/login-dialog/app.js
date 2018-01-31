import { login, getUserInfo } from '@/api/index';
import validator from '@/utils/validator';
import { registerStrategy } from '@/services/index';
import VPasswordDialog from '@/components/password-dialog/app.vue';
import { mapGetters, mapMutations } from 'vuex';
import Cookie from '../../utils/cook';

export default {
    props: {
        show: {
            type: Boolean,
            default: false,
        },
    },
    computed: {
        ...mapGetters([
            'isLogin',
            'roleId',
        ]),
    },
    data() {
        return {
            phone: '',
            password: '',
            loginDialogVisible: false,
            passwordDialogVisible: false,
            remeber: false,
            type: 'text',
        };
    },
    watch: {
        show(val) {
            this.loginDialogVisible = val;
        },
    },
    created() {
        this.loginDialogVisible = this.show;
    },
    mounted() {
        if (Cookie.getCookie('userPhone')) {
            this.type = 'password';
            this.phone = Cookie.getCookie('userPhone');
            this.password = Cookie.getCookie('userPsw');
            this.remeber = Boolean(Cookie.getCookie('isReme'));
        } else {
            this.remeber = false;
        }
    },
    methods: {
        ...mapMutations([
            'setIsLogin',
            'setRoleId',
        ]),
        closeDialog() {
            this.loginDialogVisible = false;
            this.$emit('update:show', false);
        },
        passFocus() {
            this.type = 'password';
        },
        validate() {
            if (!validator.isPhone(this.phone)) {
                this.$message.error('手机号格式不正确');
                return false;
            }
            if (!validator.isPassword(this.password)) {
                this.$message.error('请输入至少6位密码');
                return false;
            }
            return true;
        },
        login() {
            if (!this.validate()) return;
            const password = this.password;
            this.password = '';
            this.type = 'text';
            login({
                phone: this.phone,
                password,
            }).then((res) => {
                if (res.success) {
                    this.setIsLogin(true);
                    this.closeDialog();
                    this.getUserInfo();
                    this.remeberUser(password);
                    this.bridgeUser(res.data);
                } else {
                    this.$message.error(res.message);
                }
            });
        },
        forgetPassword() {
            this.passwordDialogVisible = true;
        },
        getUserInfo() {
            getUserInfo().then((res) => {
                localStorage.setItem('supplierName', res.data.companyName);
                localStorage.setItem('roleId', res.data.roleId);
                this.setRoleId(res.data.roleId);
                this.$emit('success');
            });
        },
        // 记住账号密码
        remeberUser(password) {
            if (this.remeber) {
                const expireDays = 1000 * 60 * 60 * 24 * 30;
                Cookie.setCookie('userPhone', this.phone, expireDays);

                Cookie.setCookie('userPsw', escape(password), expireDays);

                Cookie.setCookie('isReme', this.remeber, expireDays);
            } else {
                Cookie.delCookie('userPhone');
                Cookie.delCookie('userPsw');
                Cookie.delCookie('isReme');
            }
            this.phone = '';
            this.password = '';
            this.remeber = false;
        },
        bridgeUser(flagData) {
            if (flagData) {
                registerStrategy(flagData);
            }
        },
    },
    components: {
        VPasswordDialog,
    },
};
