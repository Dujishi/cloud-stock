<template>
    <el-dialog class="v-car-info-dialog" title="车辆配置" @close="closeDialog" v-model="dialogVisible" size="tiny">
        <template v-if="carModel">
            <div class="v-car-info-dialog__hd"><i class="iconfont icon-shiyongchexing"></i>{{carModel.carModelName}}</div>
            <ul class="v-car-info-dialog__bd" v-if="carModel.carModelConfig">
                <li v-for="(value, key) in carModel.carModelConfig" :key="key">
                    <div class="item1">{{key}}</div>
                    <div class="item2">{{value}}</div>
                </li>
            </ul>
        </template>
    </el-dialog>
</template>

<script>
    import AppMod from './app';

    export default AppMod;
</script>

<style lang="less">
    @import './app.less';
</style>

