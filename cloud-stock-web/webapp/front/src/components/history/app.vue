<template>
    <div class="v-history">
        <div class="v-history__hd">历史记录</div>
        <div class="v-history__bd">
            <el-button 
                v-for="(item, index) in list"
                :key="index"
                v-if="item.content"
                @click="search(item.content)"
                size="small"
                type="v-assist">
                {{item.content}}
            </el-button>
        </div>
    </div>
</template>

<script>
    import AppMod from './app';

    export default AppMod;
</script>

<style lang="less">
    @import './app.less';
</style>

