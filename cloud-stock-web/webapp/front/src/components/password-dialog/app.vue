<template>
    <el-dialog class="v-password-dialog" title="修改密码" @close="closeDialog" v-model="dialogVisible" size="tiny">
        <el-form ref="form" label-width="80px">
            <el-form-item label="手机号码:">
                <el-input :disabled="disabledPhone" :maxlength="11" v-model="phone"></el-input>
            </el-form-item>
            <el-form-item label="新密码:">
                <el-input type="password" v-model="password"></el-input>
            </el-form-item>
            <el-form-item label="确认密码:">
                <el-input type="password" v-model="confirmPassword"></el-input>
            </el-form-item>
            <el-form-item label="验证码:">
                <el-input :maxlength="6" class="vcode" v-model="vcode"></el-input><el-button :disabled="vcodeBtnDisabled" @click="getVcode" type="v-primary" class="get-vcode-btn">{{vcodeText}}</el-button>
            </el-form-item>
        </el-form>

        <span slot="footer" class="dialog-footer">
            <el-button type="v-primary" @click="confirm">确定</el-button>
            <el-button type="v-default" @click="closeDialog">取消</el-button>
        </span>
    </el-dialog>
</template>

<script>
    import AppMod from './app';

    export default AppMod;
</script>

<style lang="less">
    @import './app.less';
</style>

