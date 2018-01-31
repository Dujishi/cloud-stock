<template>
    <div class="v-vin-sidebar v-sidebar">
        <div class="select-way">
            <span @click="modelQuery" :class="{primary: firstLetterShow}">按品牌查询</span>
            <span @click="writeQuery" :class="{primary: !firstLetterShow}">手动输入</span>
        </div>

        <div v-show="!firstLetterShow">
            <div class="v-vin-sidebar__input">
                <label>车型</label>
                <v-clearable-input
                    v-model="modelName"
                    placeholder="请输入车型名称">
                </v-clearable-input>
            </div>
        
            <el-button @click="search" class="v-vin-sidebar__query-btn" type="v-warn">查询</el-button>
        </div>
        
        <!-- 查询字母 -->
        <div class="first-letter-list" v-show="firstLetterShow">
            <ul>
                <li v-for="item in firstLetterList" @click="searchBrand(item)" :class="{selected: item.firstLetter === firstLetter}">
                    {{item.firstLetter}}
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

