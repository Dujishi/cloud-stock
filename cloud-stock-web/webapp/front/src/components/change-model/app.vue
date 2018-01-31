<template>
    <el-dropdown v-if="carModelList.length > 1" class="v-change-model" @command="changeModel">
        <el-button  class="v-change-model__btn" size="small" type="v-default">更换车型</el-button>
        <el-dropdown-menu slot="dropdown">
            <el-dropdown-item 
                v-for="(item, index) in carModelList"
                :key="index"
                :command="item.carModelId"
                >
                {{item.carModelName}}
            </el-dropdown-item>
        </el-dropdown-menu>
    </el-dropdown>
</template>

<script>
    import AppMod from './app';

    export default AppMod;
</script>

<style lang="less">
    @import './app.less';
</style>

