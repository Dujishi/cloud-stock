<template>
<div class="order-page-box">
    <div class="order-detail-page circle-box clearfix table-box">
        <div class="detail-header">
            <div class="qrcode-box">
                <div id="qrcode">
                </div>
                <p class="qr-text">收款二维码</p>
                <p class="no-print">
                    <!-- <button @click="copyImg" id="img-btn">复制图片</button> -->
                    <button @click="copyUrl">复制链接</button>
                </p>
            </div>
            <div class="order-info">
                <h1>商家订单</h1>
                <p class="order-no">订单编号：
                    <span>{{orderDetail.orderNo}}</span>
                </p>
                <p class="order-status no-print">
                    <span class="status-no status" v-if="orderDetail.status==2000">
                        <span class="circle circle-no"></span>
                        <span class="text">未支付</span>
                        </span>
                        <span class="status-no status" v-if="orderDetail.status==3000">
                            <span class="circle circle-success"></span>
                            <span class="text">已支付</span>
                    </span>
                </p>
            </div>
            <div class="print-btn no-print" @click="print">
                <span>打印</span>
            </div>
            <span class="print">
            </span>
        </div>
        <div class="order-buy-info">
            <div>
                <p class="buy-info-item">
                    <span>联系人:</span>
                    <span>{{orderDetail.contact}}</span>
                </p>
                <p class="buy-info-item">
                    <span>联系电话:</span>
                    <span>{{orderDetail.contactPhone}}</span>
                </p>
            </div>
            <div>
                <p class="buy-info-item">
                    <span>公司名称:</span>
                    <span>{{orderDetail.customerName}}</span>
                </p>
                <p class="buy-info-item">
                    <span>配送地址:</span>
                    <span>{{orderDetail.province}} {{orderDetail.city}} {{orderDetail.district}} {{orderDetail.address}}</span>
                </p>
            </div>
        </div>
        <div class="clearfix">
            <el-table 
                :data="orderDetail.orderPartDtoList" 
                stripe class="order-detail-table">
                <el-table-column label="零件码" prop="oeNo" min-width="150">
                </el-table-column>
                <el-table-column label="零件名称" prop="partName" min-width="150"></el-table-column>
                <el-table-column label="产地" prop="originPlace" ></el-table-column>
                <el-table-column label="供应商" prop="manufacturer" min-width="200"></el-table-column>
                <el-table-column label="仓库" prop="partDepot" min-width="200"></el-table-column>
                <el-table-column label="单价" width="100">
                    <template scope="scope">
                        ￥{{scope.row.unitPrice}}
                    </template>
                </el-table-column>
                <el-table-column label="数量" prop="amount" width="100"></el-table-column>
                <el-table-column label="小计" width="100">
                    <template scope="scope">
                        <span class="color-bold">￥{{scope.row.subtotal}}</span>
                    </template>
                </el-table-column>
            </el-table>
            <div class="order-price pull-right">
                <p>
                    <span>合计：</span>
                    <span>￥{{orderDetail.total||0}}</span>
                </p>
                <p>
                    <span>优惠：</span>
                    <span>-￥{{orderDetail.discountPrice||0}}</span>
                </p>
                <p>
                    <span>总计：</span>
                    <span class="color-primary">￥{{orderDetail.payPrice||0}}</span>
                </p>
            </div>
        </div>
    </div>
    <footer class="order-detail-btn no-print table-box">
        <div class="return" @click="goBack">
            <i class="iconfont icon-zuofanyeunclick"></i>
            <span>返回上一级</span>
        </div>
        <div class='btn-box'>
            <button v-if="orderDetail.status!=3000" @click="updateIndent">修改订单</button>
            <button @click="deleteOrder(orderDetail.orderNo)">删除订单</button>
        </div>
    </footer>
</div>    
</template>

<script>
import AppMod from './app';
export default AppMod;
</script>

<style lang="less">
 @import './app.less';
</style>

