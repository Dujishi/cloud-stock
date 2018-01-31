<template>
    <div class="v-wrapper">
        <div class="index-page">
            <div class="bd">
                <ul class="tab">
                    <li 
                        v-for="item in typeList"
                        :key="item.id"
                        @click="selectSearchType(item.id)"
                        :class="{selected: item.id === currentType}">
                        {{item.text}}
                    </li>
                </ul>
                <el-input 
                    v-show="currentType === 'vin'" 
                    @keyup.native.enter="searchVinCode"
                    placeholder="请输入VIN码" 
                    v-model.trim="vinCode">
                    <el-button @click="searchVinCode" slot="append">查询</el-button>
                </el-input>
                <el-input 
                    v-show="currentType === 'part'" 
                    @keyup.native.enter="searchPartCode"
                    placeholder="请输入零件码" 
                    v-model.trim="partCode">
                    <el-button @click="searchPartCode" slot="append">查询</el-button>
                </el-input>
                <el-input 
                    v-show="currentType === 'model'" 
                    @keyup.native.enter="searchModel"
                    placeholder="请输入车型" 
                    v-model.trim="model">
                    <el-button @click="searchModel" slot="append">查询</el-button>
                </el-input>
            </div>
            <!-- <div class="ft">
                <div class="ft__text">
                    <h2>专注服务全球汽配供应商</h2>
                    <p>提供最便捷、高效的货源查找及库存优化工具</p>
                </div>
                <ul class="ft__list">
                    <li>
                        <svg class="v-icon" aria-hidden="true">
                            <use xlink:href="#icon-shuju"></use>
                        </svg>
                        <span>车型数据</span>
                        <strong>13万</strong>
                    </li>
                    <li>
                        <svg class="v-icon" aria-hidden="true">
                            <use xlink:href="#icon-pinpai"></use>
                        </svg>
                        <span>覆盖品牌</span>
                        <strong>134</strong>
                    </li>
                    <li>
                        <i class="iconfont icon-shuju1"></i>
                        <span>零件数据</span>
                        <strong>3000万</strong>
                    </li>
                </ul>
            </div> -->
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

