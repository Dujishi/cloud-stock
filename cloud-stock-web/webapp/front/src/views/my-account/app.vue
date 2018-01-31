<template>
    <div class="my-account-page">
        <h1>我的账号</h1>
        <ul class="list">
            <li>
                <label>登录账号</label>
                <span class="text follow-btn">{{myUserInfo.phone}}</span>
                <!-- <el-button @click="modifyAccount" size="small" type="v-default">修改</el-button> -->
            </li>
            <li>
                <label>登录密码</label>
                <span class="text follow-btn">********</span>
                <el-button @click="modifyPassword" size="small" type="v-default">修改</el-button>
            </li>
            <!-- <li>
                <label>微信绑定</label>
                <span class="text">{{myUserInfo.phone}}</span>
            </li> -->
            <li>
                <label>用户姓名</label>
                <span class="text">{{myUserInfo.name}}</span>
            </li>
            <li>
                <label>用户角色</label>
                <span class="text">{{myUserInfo.roleName}}</span>
            </li>
            <li>
                <label>公司名称</label>
                <span class="text">{{myUserInfo.companyName}}</span>
            </li>
            <li>
                <label>公司类型</label>
                <span class="text">{{myUserInfo.companyType | formatCompanyType}}</span>
            </li>
            <li>
                <label>所在城市</label>
                <span class="text">{{myUserInfo.supplierCity}}</span>
            </li>
            <li>
                <label>公司地址</label>
                <span class="text">{{myUserInfo.address}}</span>
            </li>
        </ul>

        <v-account-dialog :accountInfo="myUserInfo" @saveAccount="saveAccount" :show.sync="accountDialogVisible"></v-account-dialog>
        <v-password-dialog :userPhone="myUserInfo.phone" :show.sync="passwordDialogVisible"></v-password-dialog>

    </div>
</template>

<script>
    import AppMod from './app';

    export default AppMod;
</script>

<style lang="less">
    @import './app.less';
</style>

