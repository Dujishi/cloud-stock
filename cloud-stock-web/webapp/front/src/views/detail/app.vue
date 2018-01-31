<template>
    <div class="detail-page">
        <v-action-bar :showCarModel="false"></v-action-bar>
        <div class="panel">
            <div class="table-wrap" v-if="partInfoList.length">
                <div class="table-hd">
                    <i class="iconfont icon-peijianxinxi"></i>配件信息
                </div>
                <div class="table-bd">
                    <el-table
                        class="v-table-bordered"
                        :data="partInfoList"
                        style="width: 100%">
                        <el-table-column
                            prop="partCode"
                            label="零件号"
                            width="130">
                        </el-table-column>
                        <el-table-column
                            prop="partName"
                            label="零件名"
                            min-width="130">
                        </el-table-column>
                        <el-table-column
                            prop="oemName"
                            label="厂商"
                            width="100">
                        </el-table-column>
                        <el-table-column
                            prop="oemBrand"
                            label="品牌"
                            width="100">
                        </el-table-column>
                        <el-table-column
                            prop="source"
                            label="配件类型"
                            width="100">
                        </el-table-column>
                        <el-table-column
                            prop="price"
                            label="4S店报价"
                            width="100">
                        </el-table-column>
                        <el-table-column
                            prop="remark"
                            min-width="130"
                            label="备注">
                        </el-table-column>
                    </el-table>
                </div>
            </div>

            <stock-page from="detail"></stock-page>

            <div class="table-wrap" v-if="suitCarList.length">
                <div class="table-hd">
                    <i class="iconfont icon-shiyongchexing"></i>适用车型
                </div>
                <div class="table-bd">
                    <el-table
                        class="v-table-bordered"
                        :data="suitCarList"
                        style="width: 100%">
                        <el-table-column
                            prop="carBrandName"
                            label="品牌"
                            width="80">
                        </el-table-column>
                        <el-table-column
                            prop="carSeriesName"
                            label="车系"
                            width="150">
                        </el-table-column>
                        <el-table-column
                            prop="carModelName"
                            label="车型车款"
                            min-width="250">
                        </el-table-column>
                        <el-table-column
                            prop="subAssemblyName"
                            min-width="300"
                            label="零件组">
                            <template slot-scope="scope">
                                {{scope.row.subAssemblyName}}
                                <el-button 
                                    class="show-picture-btn" 
                                    @click="showPicture(scope.row)"
                                    v-if="urlParams.from !== 'detail'"
                                    type="v-default" 
                                    size="small">
                                    EPC图
                                </el-button>
                            </template>
                        </el-table-column>
                        
                    </el-table>
                </div>
            </div>

            <div class="table-wrap" v-if="replacePartList.length">
                <div class="table-hd">
                    <i class="iconfont icon-dongli"></i>通用零件
                </div>
                <div class="table-bd">
                    <el-table
                        class="v-table-bordered"
                        :data="replacePartList"
                        style="width: 100%">
                        <el-table-column
                            prop="partCode"
                            label="零件号"
                            width="130">
                        </el-table-column>
                        <el-table-column
                            prop="partName"
                            label="零件名"
                            min-width="130">
                        </el-table-column>
                        <el-table-column
                            prop="oemName"
                            label="厂商"
                            width="100">
                        </el-table-column>
                        <el-table-column
                            prop="oemBrand"
                            label="品牌"
                            width="100">
                        </el-table-column>
                        <el-table-column
                            prop="source"
                            label="配件类型"
                            width="100">
                        </el-table-column>
                        <el-table-column
                            prop="price"
                            width="100"
                            label="4S店报价">
                        </el-table-column>
                    </el-table>
                </div>
            </div>            
        </div>

         <!-- 加入购物车弹框 -->
        <v-cart-dialog v-if="dialogShow" @close="dialogShow=false" :part="part"></v-cart-dialog>
    </div>
</template>

<script>
    import AppMod from './app';

    export default AppMod;
</script>

<style lang="less">
    @import './app.less';
</style>

