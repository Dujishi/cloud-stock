<template>
    <div class="v-part-sidebar v-sidebar">
        <div class="v-part-sidebar__input">
            <label>零件码</label>
            <v-clearable-input
                v-model="pageOeCode"
                type="textarea"
                :rows="5"
                resize="none"
                placeholder="请输入单个或多个零件码">
            </v-clearable-input>
        </div>
        <el-button @click="search" class="v-part-sidebar__query-btn" type="v-warn">查询</el-button>

        <v-history :list="historyList" @search="searchByHistory"></v-history>
    </div>
</template>

<script>
    import AppMod from './app';

    export default AppMod;
</script>

<style lang="less">
    @import './app.less';
</style>

