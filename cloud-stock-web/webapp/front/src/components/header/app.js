import { logout } from '@/api/index';
import { mapGetters, mapMutations } from 'vuex';
import Cookie from '../../utils/cook';

export default {
    data() {
        return {
            activePage: '',
            // roleId: localStorage.getItem('roleId'),
        };
    },
    created() {
        this.init();
    },
    computed: {
        ...mapGetters([
            'myUserInfo',
            'isLogin',
            'currentPage',
            'roleId',
        ]),
    },
    methods: {
        ...mapMutations([
            'setIsLogin',
            'setCurrentPage',
        ]),
        init() {
            // this.activePage = this.$route.path;
            this.setCurrentPage(this.$route.path);
        },
        gotoPage(url) {
            if (!this.isLogin) {
                // this.currentPage = '/';
                this.setCurrentPage('/');
                this.$emit('showLoginDialog');
                sessionStorage.setItem('goUrl', url);
                return;
            }
            // this.currentPage = url;
            this.setCurrentPage(url);
            this.$router.push({ path: url });
        },
        gotoIndexPage() {
            // this.currentPage = '/';
            this.setCurrentPage('/');
            this.$router.push({ path: '/' });
            if (!this.isLogin) {
                this.$emit('showLoginDialog');
            }
        },
        showLoginDialog() {
            this.$emit('showLoginDialog');
        },
        logout() {
            logout({}).then((res) => {
                if (res.success) {
                    this.setIsLogin(false);
                    Cookie.delCookie('userPhone');
                    Cookie.delCookie('userPsw');
                    Cookie.delCookie('isReme');
                    this.$router.replace({ path: '/' });
                } else {
                    this.$message.error(res.message);
                }
            });
        },
    },
};
