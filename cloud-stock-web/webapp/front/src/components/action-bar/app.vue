<template>
    <div class="v-action-bar">
        <span @click="goback" v-if="showGoBack" class="goback">
            <i class="iconfont icon-zuofanye"></i>返回上一级
        </span>
        <span class="vline"></span>
        <template v-if="showCarModel&&from!=='part'">
            查询车型结果：<strong @click="showCarInfoDialog">{{carModel.carModelName}}</strong>
            <v-change-model></v-change-model>
        </template>
        <template v-if="showCarModel&&from==='part'">
            查询车型结果：<strong>{{carModelName}}</strong>
        </template>
        <v-car-info-dialog v-if="showCarModel" :show.sync="carInfoDialogVisible"></v-car-info-dialog>
    </div>
</template>

<script>
    import AppMod from './app';

    export default AppMod;
</script>

<style lang="less">
    @import './app.less';
</style>

