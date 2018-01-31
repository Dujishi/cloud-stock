import { mapGetters, mapMutations } from 'vuex';
import { getUserInfo } from '@/api/index';
import VAccountDialog from '@/components/account-dialog/app.vue';
import VPasswordDialog from '@/components/password-dialog/app.vue';

export default {
    data() {
        return {
            accountDialogVisible: false,
            passwordDialogVisible: false,
        };
    },
    computed: {
        ...mapGetters([
            'myUserInfo',
        ]),
    },
    filters: {
        formatCompanyType(val) {
            const map = {
                1: '供应商',
                2: '其他',
            };
            return map[val];
        },
    },
    methods: {
        ...mapMutations([
            'setMyUserInfo',
        ]),
        modifyAccount() {
            this.accountDialogVisible = true;
        },
        modifyPassword() {
            this.passwordDialogVisible = true;
        },
        saveAccount() {
            getUserInfo({}).then((res) => {
                if (res.success) {
                    this.setMyUserInfo(res.data);
                } else {
                    this.$message.error(res.message);
                }
            });
        },
    },
    components: {
        VAccountDialog,
        VPasswordDialog,
    },
};
