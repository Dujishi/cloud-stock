<template>
    <div class="part-list-page">
        <div v-if="oePartList.length" class="list v-table">
            <el-table
                :data="oePartList"
                style="width: 100%"
                :row-class-name="tableRowClassName"
                @selection-change="handleSelectionChange">
                <el-table-column
                    type="selection"
                    :selectable="setSelectable"
                    width="55">
                </el-table-column>
                <el-table-column
                    prop="partCode"
                    label="零件号"
                    width="180">
                </el-table-column>
                <el-table-column
                    prop="partName"
                    label="零件名">
                    <template slot-scope="scope">
                        <span v-if="scope.row.partName">{{scope.row.partName}}</span>
                        <span v-else>未找到相应零件</span>
                    </template>
                </el-table-column>
                <el-table-column
                    prop="address"
                    width="230"
                    label="操作">
                    <template v-if="scope.row.partName" slot-scope="scope">
                        <el-button @click="gotoDetail(scope.row)" type="v-info" size="small">详情</el-button>
                        <!-- <el-button @click="gotoStock(scope.row)" type="v-info" size="small">查看库存</el-button> -->
                    </template>
                </el-table-column>
            </el-table>

            <div class="actions">
                <el-button @click="gotoStockMultiple" type="v-primary">一键查库存报价</el-button>
            </div>
        </div>

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

