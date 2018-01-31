import { saveUserInfo } from '@/api/index';
import validator from '@/utils/validator';
import { roleMap } from '@/utils/map';

export default {
    props: {
        show: {
            type: Boolean,
            default: false,
        },
        accountInfo: {
            type: Object,
            default: null,
        },
    },
    data() {
        return {
            dialogVisible: false,
            name: '',
            roleId: '',
            phone: '',
            id: '',
            roleMap,
        };
    },
    watch: {
        show(val) {
            this.dialogVisible = val;
            if (this.accountInfo) {
                this.name = this.accountInfo.name;
                this.roleId = this.accountInfo.roleId;
                this.phone = this.accountInfo.phone;
                this.id = this.accountInfo.id;
            } else {
                this.name = '';
                this.roleId = '';
                this.phone = '';
                this.id = '';
            }
        },
    },
    created() {
        this.dialogVisible = this.show;
    },
    methods: {
        validate() {
            if (!this.name) {
                this.$message.error('请填写用户姓名');
                return false;
            }
            if (!this.roleId) {
                this.$message.error('请选择用户角色');
                return false;
            }
            if (!validator.isPhone(this.phone)) {
                this.$message.error('手机号格式不正确');
                return false;
            }
            return true;
        },
        confirm() {
            if (!this.validate()) return;
            saveUserInfo({
                id: this.id,
                phone: this.phone,
                name: this.name,
                roleId: this.roleId,
            }).then((res) => {
                if (res.success) {
                    this.closeDialog();
                    this.$emit('saveAccount', this.dialogVisible);
                    this.$message.success('保存成功');
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
