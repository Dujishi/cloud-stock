<template>
    <div class="v-vin-sidebar v-sidebar">
        <div class="v-vin-sidebar__input">
            <label>VIN码</label>
            <v-clearable-input
                v-model="currentVinCode"
                placeholder="请输入VIN码">
            </v-clearable-input>
        </div>
        <div class="v-vin-sidebar__input">
            <label>零件名</label>
            <v-clearable-input
                v-model="currentPartName"
                type="textarea"
                :rows="5"
                resize="none"
                placeholder="请输入单个或多个零件名称">
            </v-clearable-input>
        </div>
        <el-button @click="search" class="v-vin-sidebar__query-btn" type="v-warn">查询</el-button>

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

