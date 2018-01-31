<template>
    <el-dialog class="v-account-dialog" title="编辑账号" @close="closeDialog" v-model="dialogVisible" size="tiny">
        <el-form ref="form" label-width="80px">
            <el-form-item label="用户姓名:">
                <el-input v-model="name" placeholder="请输入用户姓名"></el-input>
            </el-form-item>
            <el-form-item label="用户角色:">
                <el-select v-model="roleId" placeholder="请选择" :disabled="accountInfo && Boolean(accountInfo.isloginUser)">
                    <el-option 
                        v-for="(value, key) in roleMap"
                        :key="key"
                        :label="value" 
                        :value="Number(key)">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="用户账号:">
                <el-input 
                    :maxlength="11" 
                    placeholder="请输入手机号"
                    v-model="phone" 
                    :disabled="accountInfo && Boolean(accountInfo.isloginUser)">
                </el-input>
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

