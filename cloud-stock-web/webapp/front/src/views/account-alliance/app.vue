<template>
    <div class="account-alliance-page">
        <h1>我的联盟</h1>
        <ul class="list">
            <li>
                <img src="">
                <span>车配龙事业部</span>
            </li>
        </ul>

        <footer>
            <span v-for='color in colors' :style="{backgroundColor:color}"></span>
        </footer>
    </div>
</template>

<script>
import AppMod from "./app";
export default AppMod;
</script>

<style lang="less">
@import "./app.less";
</style>


