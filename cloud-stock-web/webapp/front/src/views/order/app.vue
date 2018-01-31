<template>
  <div class="v-wrapper v-order-page">
        <div class="header-bg no-print">
            <div class="title-wrap">   
                <div class="title-text">
                    <p>订单</p>
                    <p>DINGDAN</p>
                </div>
                <div class="title-img">
                    <img src="../../assets/images/title-img.png">
                </div>
            </div>
        </div>
        <div class="v-main-list">
            <router-view></router-view>
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
