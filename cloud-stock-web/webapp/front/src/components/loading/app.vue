<template>
    <div class="v-loading" v-show="show">
        <el-progress 
            :show-text="false"
            :stroke-width="18"
            :percentage="percentage"
        />
        <p class="v-loading__text">{{text}}</p>
    </div>
</template>

<script>
    import AppMod from './app';

    export default AppMod;
</script>

<style lang="less">
    @import './app.less';
</style>

