<template>
    <div class="v-wrapper">
        <v-account-sidebar></v-account-sidebar>
        <div class="v-main">
            <router-view></router-view>
        </div>
    </div>
</template>

<script>
    import AppMod from './app';

    export default AppMod;
</script>

<style lang="less">
    @import './app.less';
</style>

