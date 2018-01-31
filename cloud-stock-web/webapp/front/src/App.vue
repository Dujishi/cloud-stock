<template>
    <div id="app" :class="{'v-hide-sidebar': !sidebarVisible}">
        <v-header @showLoginDialog="setLoginDialogVisible(true)"></v-header>
        <div class="v-main-container">
            <div class="v-main-sidebar">
                <v-l-header @showLoginDialog="setLoginDialogVisible(true)"></v-l-header>
            </div>
            <div class="v-main-page">
                <router-view></router-view>
            </div>
        </div>
        <v-loading :show="loadingVisible" @hide="hideLoading"></v-loading>
        <v-login-dialog 
            :show="loginDialogVisible"
            @success="loginSuccess"
            @update:show="setLoginDialogVisible(false)">
        </v-login-dialog>
    </div>
</template>

<script>
import { mapGetters, mapMutations } from 'vuex';
import VHeader from '@/components/header/app.vue';
import VLHeader from '@/components/left-header/app.vue';
import VLoading from '@/components/loading/app.vue';
import VLoginDialog from '@/components/login-dialog/app.vue';
import { hasLogin } from '@/api/index';

export default {
    name: 'app',
    computed: {
        ...mapGetters([
            'loadingVisible',
            'sidebarVisible',
            'isLogin',
            'loginDialogVisible',
        ]),
    },
    created() {
        this.judgeLogin();
    },
    watch: {
        $route: 'judgeLogin',
    },
    components: {
        VLoading,
        VHeader,
        VLHeader,
        VLoginDialog,
    },
    methods: {
        ...mapMutations([
            'setLoadingVisible',
            'setIsLogin',
            'setCurrentPage',
            'setLoginDialogVisible',
        ]),
        hideLoading() {
            this.setLoadingVisible(false);
        },
        loginSuccess() {
            if (this.currentType === 'vin' && this.vinCode) {
                this.setVinCode(this.vinCode);
                this.$router.push({ path: '/vin' });
            } else if (this.currentType === 'part' && this.partCode) {
                this.setOeCode(this.partCode);
                this.$router.push({ path: '/part' });
            } else {
                sessionStorage.getItem('goUrl') ? this.$router.push(sessionStorage.getItem('goUrl')) : this.$router.replace({ path: '/' });
                this.setCurrentPage(sessionStorage.getItem('goUrl'));
            }
        },
        judgeLogin() {
            hasLogin({}).then((res) => {
                this.setIsLogin(res.success);
                if (this.$route.query.needLogin === 'true') {
                    this.setLoginDialogVisible(!res.success);
                }
            });
        },
    },
};
</script>

<style lang="less">
    @import './assets/less/index.less';

    body,html,#app{
        height: 100%;
    }

    .v-main-container{
        position: relative;
        overflow: hidden;
        padding: 70px 0 0 0;
        min-height: 100%;

        .v-main-sidebar{
            position: fixed;
            left: 0;
            top: 80px;
            width: 100px;
            height: 100%;
            background: #3A3A48;
            z-index: 99;
        }

        .v-main-page{
            margin: 10px 0 0 100px;
            // position: relative;
            position: absolute;
            height: 100%;
            width: 95%;
            overflow: auto;
        }
    }
</style>
