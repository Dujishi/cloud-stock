<template>
    <div class="vin-part-list-page">
        <div class="model-result">
            查询车型结果：<strong>{{carModel.carModelName}}</strong>
            <v-change-model></v-change-model>
        </div>
        <div class="list v-table">
            <el-table
                :data="partList"
                style="width: 100%"
                @selection-change="handleSelectionChange">
                <el-table-column
                    type="selection"
                    width="55">
                </el-table-column>
                <el-table-column
                    prop="partCode"
                    label="零件号"
                    width="140">
                </el-table-column>
                <el-table-column
                    prop="partName"
                    label="零件名">
                </el-table-column>
                <el-table-column
                    prop="address"
                    width="230"
                    label="操作">
                    <template slot-scope="scope">
                        <el-button @click="gotoDetail(scope.row)" type="v-info" size="small">详情</el-button>
                        <el-button @click="gotoStock(scope.row)" type="v-info" size="small">查看库存</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>

        <div class="actions">
            <el-button @click="gotoStockMultiple" type="v-primary">一键查库存报价</el-button>
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

