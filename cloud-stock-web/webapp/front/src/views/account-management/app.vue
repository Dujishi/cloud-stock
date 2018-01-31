<template>
    <div class="account-management-page">
        <el-button @click="addAccount" class="add-btn" type="v-warn"><i class="iconfont icon-jia"></i>添加登录账号</el-button>
        <ul class="list">
            <li v-for="item in userList" :key="item.id">
                <div class="hd">
                    <span class="name">{{item.name}}</span><span class="v-role" :class="['v-role--' + item.roleId]">{{item.roleName}}</span>
                </div>
                <div class="bd">
                    <span>登录账号:</span>{{item.phone}}
                </div>
                <div class="actions">
                    <el-button v-if="item.canDelete" @click="showDeleteDialog(item)" size="small" type="v-default" class="w1">删除</el-button>
                    <el-button v-if="item.canEdit" @click="editAccount(item)" size="small" type="v-default" class="w1">编辑</el-button>
                    <template  v-if="myUserInfo.roleId == 1">
                        <el-button
                            @click="updateAuth(item, 2, '员工' + item.name + '已经被设置为管理员！')" 
                            v-if="item.canEdit && item.roleId == 3"
                            size="small"
                            type="v-default"
                            class="w2">
                            设为管理员
                        </el-button>
                        <el-button 
                            @click="updateAuth(item, 3, '管理员' + item.name + '已经被设置为员工！')" 
                            v-if="item.canEdit && item.roleId == 2" 
                            size="small" 
                            type="v-default" 
                            class="w2">
                            取消管理员
                        </el-button>
                        <el-button 
                            size="small" 
                            type="v-default" 
                            @click="showManageDialog(item)"
                            v-if="item.roleId < 3">
                            查询权限管理
                        </el-button>
                    </template>
                </div>
            </li>
        </ul>

        <el-dialog class="delete-dialog" title="删除确认" v-model="deleteDialogVisible" size="tiny">
            <div class="delete-dialog-text">
                <i class="iconfont icon-gantan"></i>确认要删除{{deletedUserInfo.roleName}}{{deletedUserInfo.name}}吗？
            </div>
            <div class="delete-dialog-tips">这项操作一旦确认删除就不能恢复了，三思后行。</div>

            <span slot="footer" class="dialog-footer">
                <el-button type="v-primary" @click="deleteAccount">确定</el-button>
                <el-button type="v-default" @click="deleteDialogVisible = false">取消</el-button>
            </span>
        </el-dialog>

        <v-account-dialog :accountInfo="editedUserInfo" @saveAccount="saveAccount" :show.sync="accountDialogVisible"></v-account-dialog>

        <!-- 权限管理弹框 -->
        <el-dialog title="查询权限管理" :visible.sync="manageDialogVisible" class="manage-dialog">
            <v-select-brand :userId="userId"  @close="manageDialogVisible=false" :isShow="manageDialogVisible"></v-select-brand>
        </el-dialog>    

    </div>
</template>

<script>
    import AppMod from './app';

    export default AppMod;
</script>

<style lang="less">
    @import './app.less';
</style>

