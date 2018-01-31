<template>
    <div class="stock-page">
        <v-action-bar :showCarModel="false" v-if="showBar"></v-action-bar>
        <div class="panel" :class="{detailPanel:!showBar}">
            <div class="table-wrap" v-if="data.length">
                <div class="table-hd">
                    <i class="iconfont icon-cangku"></i>库存信息
                </div>
                <div class="table-bd">
                    <el-table
                        class="v-table-bordered"
                        :data="data"
                        stripe
                        v-if="data.length"
                        :default-expand-all="false"
                        :row-class-name="tableRowClassName"
                        :expand-row-keys="getExpandRowKeys()"
                        :row-key="getRowKey"
                        style="width: 100%">
                        <el-table-column 
                            width="20"
                            type="expand">
                            <template slot-scope="props">
                                <div class="expand-row">
                                    <div class="expand-row__hd">智能推荐</div>
                                    <div class="expand-row__bd">
                                        <div class="expand-row__item">
                                            供应商： {{props.row.recommendSupplierInfo ? props.row.recommendSupplierInfo.supplierName : '--'}}
                                        </div>
                                        <div class="vline"></div>
                                        <div class="expand-row__item">
                                            <template v-if="props.row.replaceCodeList && props.row.replaceCodeList.length">
                                                替换件：<span 
                                                    v-for="item in props.row.replaceCodeList"
                                                    :key="item"
                                                    class="expand-row__code">
                                                    {{item}}
                                                </span>
                                            </template>
                                            <template v-else>
                                                替换件：--
                                            </template>
                                        </div>
                                    </div>
                                </div>
                            </template>
                        </el-table-column>
                        <el-table-column v-if="!from"
                            label="序号"
                            width="60">
                            <template scope="scope">
                                <div class="serial-number">
                                    {{scope.$index+1}}
                                     <i v-if="scope.row.flag === 2" class="iconfont icon-shouqing"></i>
                                    <i v-if="scope.row.flag === 3" class="iconfont icon-zanwu"></i>
                                </div>
                            </template>
                        </el-table-column>
                        <el-table-column
                            prop="stockInfo.partCode" v-if="!from"
                            label="零件号"
                            min-width="100">
                        </el-table-column>
                        <el-table-column
                            v-if="!from"
                            prop="stockInfo.partName"
                            label="零件名"
                            min-width="120">
                        </el-table-column>
                        <el-table-column
                            label="产地"
                            width="80">
                            <template scope="scope">
                                <div v-if="!from">{{scope.row.stockInfo.produceArea}}</div>
                                <div class="serial-number" v-else>
                                    <span>{{scope.row.stockInfo.produceArea}}</span>
                                    <i v-if="scope.row.flag === 2" class="iconfont icon-shouqing"></i>
                                    <i v-if="scope.row.flag === 3" class="iconfont icon-zanwu"></i>
                                </div>
                            </template>
                        </el-table-column>
                        <el-table-column
                            prop="stockInfo.depot"
                            label="仓库"
                            min-width="210">
                        </el-table-column>
                        <el-table-column
                            label="库存"
                            prop="stockInfo.balanceCount"
                            min-width="70">
                        </el-table-column>
                        <el-table-column
                            label="成本价"
                            min-width="70">
                            <template scope="scope">
                                <span class="money-symble" v-if="scope.row.stockInfo.costPrice">{{scope.row.stockInfo.costPrice}}</span>
                            </template>
                        </el-table-column>
                        <el-table-column
                            prop="stockInfo.tradePrice"
                            label="同行价"
                            min-width="70">
                            <template scope="scope">
                                <span class="money-symble" v-if="scope.row.stockInfo.tradePrice">{{scope.row.stockInfo.tradePrice}}</span>
                            </template>
                        </el-table-column>
                        <el-table-column
                            prop="stockInfo.repairPrice"
                            label="修理厂价"
                            min-width="80">
                            <template scope="scope">
                                <span class="money-symble" v-if="scope.row.stockInfo.repairPrice">{{scope.row.stockInfo.repairPrice}}</span>
                            </template>
                        </el-table-column>
                        <el-table-column
                            prop="stockInfo.insurerPrice"
                            label="保险公司价"
                            min-width="90">
                            <template scope="scope">
                                <span class="money-symble" v-if="scope.row.stockInfo.insurerPrice">{{scope.row.stockInfo.insurerPrice}}</span>
                            </template>
                        </el-table-column>
                        <el-table-column
                            prop="stockInfo.maintainPrice"
                            min-width="80"
                            label="维修站价">
                            <template scope="scope">
                                <span class="money-symble" v-if="scope.row.stockInfo.maintainPrice">{{scope.row.stockInfo.maintainPrice}}</span>
                            </template>
                        </el-table-column>
                        <el-table-column
                            min-width="100"
                            label="操作">
                            <template scope="scope">
                                <span class="primary-color" @click="addCart(scope.row)">
                                    加入配货单
                                </span>
                            </template>
                        </el-table-column>
                    </el-table>
                </div>
            </div>
        </div>
        <!-- 加入购物车弹框 -->
        <v-cart-dialog v-if="dialogShow" @close="dialogShow=false" :part="part.stockInfo"></v-cart-dialog>
    </div>
</template>

<script>
    import AppMod from './app';

    export default AppMod;
</script>

<style lang="less">
    @import './app.less';
</style>

