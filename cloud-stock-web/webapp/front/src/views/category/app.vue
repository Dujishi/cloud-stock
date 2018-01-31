<template>
    <div class="category-page">
        <v-action-bar :showGoBack="false" :showCarModel="true"></v-action-bar>
        <ul v-if="categoryList && categoryList.length" class="list">
            <li 
                v-for="item in categoryList"
                :key="item.categoryId"
                @click="gotoSubCategory(item)"
                :class="['item-' + getCategoryIcon(item.type, item.categoryName).class]">
                <i class="iconfont" :class="[getCategoryIcon(item.type, item.categoryName).icon]"></i>
                <div>{{item.categoryName}}</div>
            </li>
        </ul>
        <v-no-content v-else></v-no-content>
    </div>
</template>

<script>
    import AppMod from './app';

    export default AppMod;
</script>

<style lang="less">
    @import './app.less';
</style>

