<template>
    <div class="v-account-sidebar v-sidebar">
        <div class="v-account-sidebar__info">
            <div class="v-account-sidebar__avatar"><i class="iconfont icon-geren"></i></div>
            <div class="v-account-sidebar__name">{{myUserInfo.name}}</div>
            <div class="v-account-sidebar__role v-role" :class="['v-role--' + myUserInfo.roleId]">{{myUserInfo.roleName}}</div>
        </div>
        <ul class="v-account-sidebar__menu">
            <li v-if="myUserInfo.roleId ===1"><router-link to="/account/balance">我的余额</router-link></li>
            <li><router-link to="/account/my">我的账号</router-link></li>
            <li v-if="myUserInfo.roleId < 3"><router-link to="/account/management">账号管理</router-link></li>
             <!-- <li><router-link to="/account/alliance">我的联盟</router-link></li> -->
        </ul>
    </div>
</template>

<script>
import AppMod from "./app";

export default AppMod;
</script>

<style lang="less">
@import "./app.less";
</style>

