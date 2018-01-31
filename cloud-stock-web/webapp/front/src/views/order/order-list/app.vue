<template>
    <div class="order-list-page table-box">
        <div class="has-data">
            <div class="table-search-box clearfix">
                <el-input placeholder="输入单号或用户名称、联系电话" v-model="keyWord" class="pull-left search-input">
                    <template slot="append">
                        <span @click="getOrderList(1)">查询</span>
                    </template>
                </el-input>
                <el-form class="pull-right state-select">
                    <el-form-item label="订单状态:">
                        <el-select v-model="orderStatus" @change='getOrderList(1)'>
                            <el-option label="全部" value=""></el-option>
                            <el-option label="已支付" value="3000"></el-option>
                            <el-option label="未支付" value="2000"></el-option>
                        </el-select>
                    </el-form-item>  
                </el-form>      
            </div>
            <el-table 
                :data="orderList" 
                stripe class="order-table"
                v-if="orderList.length>0">
                <el-table-column label="单号" width="110">
                    <template scope="scope">
                        <span class="color-primary" @click='goDetail(scope.row.orderNo)'>{{scope.row.orderNo}}</span>
                    </template>
                </el-table-column>
                <el-table-column label="公司名称" prop="customerName" width="200"></el-table-column>
                <el-table-column label="联系人" prop="contact"></el-table-column>
                <el-table-column label="联系电话" prop="contactPhone" width="130"></el-table-column>
                <!-- <el-table-column label="VIN码" prop="vin" width="150"></el-table-column> -->
                <el-table-column label="数量(单位：件)" width="150">
                    <template scope="scope">
                        <span style="fontWeight:bold">{{scope.row.amount}}</span>
                    </template>
                </el-table-column>
                <el-table-column label="小计">
                    <template scope="scope">
                        <span class="color-primary">￥{{scope.row.total}}</span>
                    </template>
                </el-table-column>
                <el-table-column label="订单状态" width="120">
                    <template scope="scope">
                        <span class="status-no status" v-if="scope.row.status==2000">
                            <span class="circle circle-no"></span>
                            <span class="text">未支付</span>
                        </span>
                        <span class="status-no status" v-if="scope.row.status==3000">
                            <span class="circle circle-success"></span>
                            <span class="text">已支付</span>
                        </span>
                    </template>
                </el-table-column>
                <el-table-column label="操作" width="120">
                    <template scope="scope">
                        <p class="operate">
                            <span class="update"  v-if="scope.row.status==2000" @click="updateOrder(scope.row)">修改</span>
                            <span class="delete" @click="deleteOrder(scope.row)">删除</span>
                        </p>  
                    </template>
                </el-table-column>
            </el-table>
            <div class="no-data" v-else>
                <img src="../../../assets/images/no-content.png">
                <p>暂无订单</p>
            </div>
            <el-pagination
            v-if="orderList.length>0"
            @current-change="handleCurrentChange"
            layout="total, prev, pager, next"
            :current-page="pageNumber"
            :total="totalData"
            :pageSize=20
            class="pagination">
            </el-pagination>
        </div>

        
    </div>
</template>

<script>
import AppMod from "./app";
export default AppMod;
</script>

<style lang="less">
@import "./app.less";
</style>


