<template>
    <div class="component-brand-select">
        <div class="all-frist-name">
            <ul>
                <li v-for="item in fristNameArr" :class="{selected: item.firstLetter===selected}" @click="selectBrandFrist(item)">
                    {{item.firstLetter}}
                </li>
            </ul>
        </div>
        <div class="brand-content-box">
            <section v-for="item in fristNameArr" :id="item.firstLetter">
                <p class="title">{{item.firstLetter}}</p>
                <div class="brand-box">
                    <ul>
                        <li v-for="brand in brandArr" v-if="brand.firstLetter === item.firstLetter">
                            <div class="brand-img">
                                <img :src="brand.brandIconUrl" v-if="brand.brandIconUrl">
                                <el-checkbox-group class="check-box" 
                                v-model="checkBrandList">
                                    <el-checkbox    :label="brand.brandId">&nbsp</el-checkbox>
                                </el-checkbox-group>    
                            </div>
                            <p>{{brand.brandName}}</p>
                        </li>
                    </ul>
                </div>
            </section>  
        </div>
        <div class="footer">
            <el-checkbox v-model="checkAll" @change="checkAllChange">全选</el-checkbox>
            <el-button type="primary" @click="setBranchAuth">保存</el-button>
            <el-button @click="closeModal">取消</el-button>
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


