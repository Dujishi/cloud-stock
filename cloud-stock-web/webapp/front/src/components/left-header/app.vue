<template>
    <div class="v-l-header no-print" style="-webkit-app-region: drag">
        <div class="v-l-header__tab">
            <a
                v-for="item in pages"
                :key="item.url"
                :class="{'selected': item.url === '/' ? currentPage === item.url : (currentPage.indexOf(item.url) > -1)}"
                @click="gotoPage(item.url)"
                href="javascript:;">
                <span :class="item.icon"></span>
                <span>{{item.text}}</span>
            </a>
        </div>
    </div>
</template>

<script>
import AppMod from "./app";

export default AppMod;
</script>

<style lang="less">
@import "./app.less";
</style>

