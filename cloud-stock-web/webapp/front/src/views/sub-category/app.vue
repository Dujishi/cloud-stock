<template>
    <div class="sub-category-page" v-show="showPage">
        <v-action-bar :showCarModel="true"></v-action-bar>
        <div class="panel">
            <div class="change-category">
                <el-dropdown @command="changeCategory">
                    <span class="el-dropdown-link">
                        <i class="iconfont icon-dongli"></i><strong>{{categoryObj.categoryName}}</strong><i class="el-icon-caret-bottom el-icon--right"></i>
                    </span>
                    <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item 
                            v-for="item in categoryList"
                            :key="item.categoryId"
                            :command="item"
                            >
                            {{item.categoryName}}
                        </el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
            </div>

            <div class="tab" v-if="assemblyList.length">
                <span>种类分类:</span>
                <a 
                    v-for="(item, index) in assemblyList"
                    :class="{'selected': index == currentIndex, 'is-spe': item.type === 0}"
                    :key="item.assemblyName"
                    @click="changeAssembly(index)"
                    href="javascript:;">
                    {{item.assemblyName}}
                </a>
            </div>

            <ul class="list" v-if="subAssemblyList.length">
                <li
                    v-for="item in subAssemblyList"
                    :key="item.subAssemblyName"
                    @click="gotoPicturePage(item)"
                    >
                    <img :src="item.picUrl" onerror="this.src='https://store.ddyc.com/res/xkcdn/images/cloud-stock/default.png'">
                    <p>{{item.subAssemblyName}}</p>
                </li>
            </ul>
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

