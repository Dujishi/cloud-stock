<template>
    <div class="v-clearable-input">
        <el-input 
            v-model.trim="inputValue"
            :type="type"
            :resize="resize"
            :rows="rows"
            :placeholder="placeholder">
        </el-input>
        <i @click="clearInput" v-show="clearIconVisible" class="iconfont icon-guanbi"></i>
    </div>
</template>

<script>
    import AppMod from './app';

    export default AppMod;
</script>

<style lang="less">
    @import './app.less';
</style>

