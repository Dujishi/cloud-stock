import { mapGetters, mapMutations } from 'vuex';

export default {
    data() {
        return {
            activePage: '',
            pages: [
                { url: '/', text: '首页', icon: 'iconfont icon-shouye' },
                { url: '/vin', text: 'VIN码查询', icon: 'iconfont icon-vinmachaxun' },
                { url: '/part', text: '零件码查询', icon: 'iconfont icon-lingjianmachaxun' },
                { url: '/model', text: '车型查询', icon: 'iconfont icon-chexingchaxun' },
            ],
            roleId: localStorage.getItem('roleId'),
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
        ]),
    },
    methods: {
        ...mapMutations([
            'setIsLogin',
            'setCurrentPage',
        ]),
        init() {
            // this.pages.forEach((v) => {
            //     if (this.$route.path.indexOf(v.url) > -1) {
            //         // this.currentPage = v.url;
            //         this.setCurrentPage(v.url);
            //     }
            // });
            this.setCurrentPage(this.$route.path);
            console.log(this.$route.path);
        },
        gotoPage(url) {
            if (!this.isLogin) {
                this.currentPage = '/';
                this.$emit('showLoginDialog');
                sessionStorage.setItem('goUrl', url);
                return;
            } else {
                 // this.currentPage = url;
                this.setCurrentPage(url);
                this.$router.push({ path: url });
            }   
        },
        gotoIndexPage() {
            this.setCurrentPage('/');
            this.$router.push({ path: '/' });
            if (!this.isLogin) {
                this.$emit('showLoginDialog');
            }
        },
        showLoginDialog() {
            this.$emit('showLoginDialog');
        },
    },
};
