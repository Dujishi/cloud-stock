<template>
    <div>
        <el-dialog class="v-login-dialog" @close="closeDialog" title="" v-model="loginDialogVisible">
            <i @click="closeDialog" class="v-login-dialog__close iconfont icon-turnoff"></i>
            <div class="hd clearfix">
                <h2>账号密码登录</h2>
            </div>
            <div class="bd">
                <div class="v-login-dialog__input">
                    <i class="iconfont icon-geren"></i>
                    <el-input :maxlength="11" type="number" v-model="phone" placeholder="手机号" name="phone"></el-input>
                </div>
                <div class="v-login-dialog__input">
                    <i class="iconfont icon-mima"></i>
                    <el-input :type="type" @keyup.native.enter="login" v-model="password" name="cloud-password" placeholder="密码" @focus="passFocus"></el-input>
                </div>
            </div>
            <div class="ft">
                <el-checkbox v-model="remeber">记住账号密码</el-checkbox>
                <a @click="forgetPassword" class="forget-pw pull-right" href="javascript:;">忘记密码</a>
                <el-button @click="login" type="v-primary">立即登录</el-button>
            </div>
        </el-dialog>

        <v-password-dialog :show.sync="passwordDialogVisible"></v-password-dialog>
    </div>
</template>

<script>
    import AppMod from './app';

    export default AppMod;
</script>

<style lang="less">
    @import './app.less';
</style>

