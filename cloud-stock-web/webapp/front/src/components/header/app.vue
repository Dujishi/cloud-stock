<template>
    <div class="v-header no-print" style="-webkit-app-region: drag">
        <img @click="gotoIndexPage" class="v-header__logo" src="../../assets/images/new-logo.svg" alt="典典云仓">
        <div class="v-header__nav">
            <span class="el-dropdown-link" @click="gotoPage('/cargo')" :class="{active:currentPage && currentPage.indexOf('cargo')>-1}">
                <i class="iconfont icon-peihuodan"></i>配货单
            </span>
             <span class="el-dropdown-link" @click="gotoPage('/order/list')" :class="{active:currentPage &&currentPage.indexOf('order')>-1}">
                <i class="iconfont icon-dingdan"></i>订单  
            </span>
            <el-dropdown v-if="isLogin">
                <span class="el-dropdown-link person">
                    <i class="iconfont icon-geren"></i>个人中心
                </span>
                <el-dropdown-menu class="v-header__menu" slot="dropdown">
                    <el-dropdown-item><div tag="div" @click="gotoPage('/account/my')">我的账号</div></el-dropdown-item>
                    <!-- <el-dropdown-item v-if="myUserInfo.roleId < 3"><router-link tag="div" to="/account/management">账号管理</router-link></el-dropdown-item> -->
                    <el-dropdown-item v-if="roleId<3"><div tag="div" @click="gotoPage('/account/management')">账号管理</div></el-dropdown-item>
                    <el-dropdown-item v-if="roleId==1"><div tag="div" @click="gotoPage('/account/balance')">我的余额</div></el-dropdown-item>
                    <el-dropdown-item @click.native="logout">退出</el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
            <span @click="showLoginDialog" v-else class="el-dropdown-link person">
                <i class="iconfont icon-geren"></i>登录
            </span>
            <!-- <li><i class="iconfont icon-xiaoxi"></i>消息中心</li> -->
        </div>
    </div>
</template>

<script>
import AppMod from "./app";

export default AppMod;
</script>

<style lang="less">
@import "./app.less";
</style>

