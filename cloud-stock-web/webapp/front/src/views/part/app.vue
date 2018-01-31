<template>
    <div class="v-wrapper part-page">
        <v-part-sidebar @search="search"></v-part-sidebar>
        <div class="v-main">
            <transition name="move" mode="out-in"><router-view></router-view></transition>
        </div>

        <el-dialog class="brand-dialog" title="品牌选择" v-model="brandDialogVisible">
            <ul class="clearfix">
                <li
                    v-for="item in brandList"
                    :key="item.brandName"
                    @click="selectBrand(item.brandName)"
                    >
                    <img :src="item.brandIconUrl" alt="">
                </li>
            </ul>
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

