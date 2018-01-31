import { getCode, modifyPassword } from '@/api/index';
import validator from '@/utils/validator';
import { clearInterval } from 'timers';

export default {
    props: {
        show: {
            type: Boolean,
            default: false,
        },
        userPhone: {
            type: [String, Number],
            default: '',
        },
    },
    data() {
        return {
            dialogVisible: true,
            phone: '',
            password: '',
            confirmPassword: '',
            vcode: '',
            disabledPhone: false,
            vcodeText: '获取验证码',
            vcodeBtnDisabled: false,
            timer: null,
        };
    },
    watch: {
        show(val) {
            this.dialogVisible = val;
            if (this.userPhone) {
                this.phone = this.userPhone;
                this.disabledPhone = true;
            }
        },
    },
    created() {
        this.dialogVisible = this.show;
    },
    methods: {
        validate() {
            if (!validator.isPhone(this.phone)) {
                this.$message.error('手机号格式不正确');
                return false;
            }
            if (!validator.isPassword(this.password)) {
                this.$message.error('请输入至少6位密码');
                return false;
            }
            if (this.password !== this.confirmPassword) {
                this.$message.error('两次密码不一致');
                return false;
            }
            return true;
        },
        countDown() {
            if (this.vcodeBtnDisabled) return;
            this.vcodeBtnDisabled = true;
            let seconds = 30;
            const count = () => {
                if (seconds > 0) {
                    this.vcodeText = `${seconds}s后重新获取`;
                    seconds -= 1;
                } else {
                    this.vcodeText = '获取验证码';
                    this.vcodeBtnDisabled = false;
                    window.clearInterval(this.timer);
                }
            };
            count();
            this.timer = window.setInterval(count, 1000);
        },
        getVcode() {
            if (!this.validate()) return;
            getCode({
                phone: this.phone,
            }).then((res) => {
                if (res.success) {
                    this.countDown();
                } else {
                    this.$message.error(res.message);
                }
            });
        },
        confirm() {
            if (!this.validate()) return;
            if (!this.vcode) {
                this.$message.error('请输入验证码');
                return;
            }
            modifyPassword({
                phone: this.phone,
                password: this.password,
                code: this.vcode,
            }).then((res) => {
                if (res.success) {
                    this.closeDialog();
                    this.$message.success('密码修改成功');
                } else {
                    this.$message.error(res.message);
                }
            });
        },
        closeDialog() {
            this.dialogVisible = false;
            this.$emit('update:show', this.dialogVisible);
        },
    },
};
