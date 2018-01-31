<template>
    <div class="picture-page" :class="{'picture-page--single': isSingleImage}" v-show="showPage">
        <v-action-bar :showCarModel="true" :from="from" :carModelName="carModelName"></v-action-bar>
        
        <div class="change-category" v-if="!isSingleImage">
            <el-dropdown @command="changeAssembly">
                <span class="el-dropdown-link">
                    <strong>{{assemblyObj.assemblyName}}</strong><i class="el-icon-caret-bottom el-icon--right"></i>
                </span>
                <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item 
                        v-for="item in assemblyList"
                        :key="item.assemblyName"
                        :command="item"
                        >
                        {{item.assemblyName}}
                    </el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
        </div>

        <div class="bd" id="bd">
            <div class="pic-container">
                <v-image-tag :images="images" :init-image-index="initImageIndex" :mark-image="markImage" @changeImage="changeImage" :current-show-tag="currentShowTag"></v-image-tag>
            </div>
            <div v-if="tableData.length" class="list v-table-bordered">
                <el-table
                    :data="tableData"
                    style="width: 100%"
                    :max-height="maxHeight"
                    @selection-change="handleSelectionChange">
                    <el-table-column
                        type="selection"
                        width="55">
                    </el-table-column>
                    <el-table-column
                        prop="picSequence"
                        label="位置"
                        width="100">
                    </el-table-column>
                    <el-table-column
                        label="零件号"
                        min-width="150">
                        <template slot-scope="scope">
                            <span>{{scope.row.partCode}}</span>
                            <v-copy :text="scope.row.partCode"></v-copy>
                        </template>
                    </el-table-column>
                    <el-table-column
                        prop="partName"
                        label="零件名"
                        min-width="170">
                    </el-table-column>
                    <el-table-column
                        prop="address"
                        width="180"
                        label="操作">
                        <template slot-scope="scope">
                            <el-button @click="gotoDetail(scope.row)" type="v-info" size="small">详情</el-button>
                            <!-- <el-button @click="gotoStock(scope.row)" type="v-info" size="small">查看库存</el-button> -->
                        </template>
                    </el-table-column>
                </el-table>
            </div>
            <!-- <v-no-content v-else></v-no-content> -->
        </div>

        <el-button @click="gotoStockMultiple" class="view-stock-btn" type="v-primary">一键查库存报价</el-button>
    </div>
</template>

<script>
    import AppMod from './app';

    export default AppMod;
</script>

<style lang="less">
    @import './app.less';
</style>

