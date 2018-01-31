import { getUserList, deleteUserInfo, saveUserInfo } from '@/api/index';
import VAccountDialog from '@/components/account-dialog/app.vue';
import VSelectBrand from '../../components/select-brand/app.vue';
import { mapGetters, mapActions } from 'vuex';

export default {
    data() {
        return {
            accountDialogVisible: false,
            deleteDialogVisible: false,
            userList: [],
            editedUserInfo: null,
            deletedUserInfo: {},
            manageDialogVisible: false,
            userId: '',
        };
    },
    created() {
        this.loadData();
    },
    computed: {
        ...mapGetters([
            'myUserInfo',
        ]),
    },
    methods: {
        ...mapActions([
            'getMyUserInfoAction',
        ]),
        loadData() {
            getUserList({}).then((res) => {
                if (res.success) {
                    this.userList = res.data || [];
                } else {
                    this.$message.error(res.message);
                }
            });
        },
        addAccount() {
            this.editedUserInfo = null;
            this.accountDialogVisible = true;
        },
        saveAccount() {
            if (this.editedUserInfo.isloginUser) {
                this.getMyUserInfoAction();
            }
            this.loadData();
        },
        showDeleteDialog(info) {
            this.deletedUserInfo = info;
            this.deleteDialogVisible = true;
        },
        showManageDialog(item) {
            this.userId = item.id;
            this.manageDialogVisible = true;
        },
        deleteAccount() {
            deleteUserInfo({
                accountId: this.deletedUserInfo.id,
            }).then((res) => {
                if (res.success) {
                    this.deleteDialogVisible = false;
                    this.$message.success('删除成功');
                    this.loadData();
                } else {
                    this.$message.error(res.message);
                }
            });
        },
        editAccount(info) {
            this.editedUserInfo = info;
            this.accountDialogVisible = true;
        },
        updateAuth(info, roleId, message) {
            saveUserInfo({
                id: info.id,
                phone: info.phone,
                name: info.name,
                roleId,
            }).then((res) => {
                if (res.success) {
                    this.loadData();
                    this.$message.success(message);
                } else {
                    this.$message.error(res.message);
                }
            });
        },
    },
    components: {
        VAccountDialog,
        VSelectBrand,
    },
};
