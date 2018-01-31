<template>
    <div class="model-page v-wrapper">
        <v-model-sidebar></v-model-sidebar>
        <div class="v-main">
            <div class="header-nav" v-if="navMenu.length>1">
                <div>
                    <span class="go-back" @click="goBack">
                        <i class="iconfont icon-zuofanye"></i>
                        返回上一级
                        </span>
                        <span v-for="(item,index) in navMenu" class="menu-item" v-if="item.menuName" :class="{active: index<navMenu.length-1}" @click="changeRoute(item)">
                            <span>{{item.menuName}}</span>
                            <span v-if="index<navMenu.length-1">></span>
                    </span>
                </div>
            </div>
            <div class="header-nav" v-if="headerNavVisble && navMenu.length<=1">
                <span class="select-txt">品牌车系筛选</span>
            </div>
            <transition name="move" mode="out-in">
                <router-view></router-view> 
            </transition>
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


