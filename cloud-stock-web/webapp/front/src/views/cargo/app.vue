<template>
    <div class="cargo-page">
        <div class="title-content">
            <div class="title-bg">
                <div class="title-wrap">   
                    <div class="title-text">
                        <p>配货单</p>
                        <p>PEIHUODAN</p>
                    </div>
                    <div class="title-img">
                        <img src="../../assets/images/title-img.png">
                    </div>
                </div>
            </div>
            <div class="cargo-menu-list table-box">
                <ul>
                    <li v-for="(item,index) in indentList">
                        <span :class="{active:item.indentNo==activeIndent.indentNo}" @click='changeCargo(item)'>
                            {{item.name}}
                        </span>
                        <img src="../../assets/images/del.svg" @click="delIndent(index,item)">
                    </li>
                </ul>
            </div>
        </div>    
        <div class="cargo-box circle-box table-box">
            <div class="has-data" v-if="indentList.length>0">
                <div class="cargo-title-box">
                    <input type="text" :disabled="disabled" v-model="cargoDetail.name" v-if="!disabled" class="cargo-title" @blur="noEdit">
                    <span class="cargo-title-span" v-else>{{cargoDetail.name}}</span>
                    <img src="../../assets/images/edit.png" class="edit-img" @click="canEdit">
                    <hr style="border:3px solid #888d98;marginTop:30px" />
                </div>
                
                <div class="cargo-info-box">
                    <el-form label-width="100px" :rules="cargoInfoRules" ref="cargoInfo" :model="cargoDetail">     
                        <el-form-item label="公司名称:" prop="customerName">
                            <el-autocomplete
                            v-model="cargoDetail.customerName"
                            :fetch-suggestions="querySearchAsync"
                            placeholder="请输入公司名称"
                            :trigger-on-focus="false"
                            @select="companySelect"
                            :props="companyProps"
                            ></el-autocomplete>
                        </el-form-item>
                        <el-form-item label="联系电话:" prop="contactPhone">
                            <el-input v-model="cargoDetail.contactPhone" placeholder="联系人电话号码" class="common-input"></el-input>
                        </el-form-item>
                        <el-form-item label="联系人:" prop="contact">
                            <el-input v-model="cargoDetail.contact" placeholder="联系人姓名" class="common-input"></el-input>
                        </el-form-item>
                    
                    
                        <el-form-item label="VIN码:" prop="vin">
                            <el-input v-model="cargoDetail.vin" placeholder="请输入VIN码"
                            class="common-input" 
                            @change="changeVin" :maxlength=17></el-input>
                        </el-form-item>
                        <el-form-item label="车型:" prop="carModel">
                            <el-input v-model="cargoDetail.carModel" placeholder="请输入车型" class="common-input"></el-input>
                        </el-form-item>    
                        <el-form-item label="配送地址:" prop="address">
                            <v-address :obj="cargoDetail"></v-address>
                        </el-form-item>
                    </el-form>
                </div>

                <div class="part-table">
                    <el-table :data="cargoDetail.indentPartDtoList" stripe>
                        <el-table-column label="零件号/OE码" prop="oeNo" width="150">
                        </el-table-column>
                        <el-table-column label="零件名称" prop="partName" width="120">
                        </el-table-column>
                        <el-table-column label="产地" prop="originPlace" width="80">
                        </el-table-column>
                        <el-table-column label="供应商" prop="manufacturer" min-width="150">
                        </el-table-column>
                        <el-table-column label="仓库" prop="partDepot" min-width="200">
                        </el-table-column>
                        <el-table-column label="单价" width="120" class-name="price-column">
                            <template scope="scope">
                                <!-- <span v-if="scope.row.unitPrice">￥</span> -->
                                <v-price-input v-model="scope.row.unitPrice" 
                                :index="scope.$index"></v-price-input>
                            </template>
                        </el-table-column>
                        <el-table-column label="数量" min-width="130">
                            <template scope="scope">
                                <v-number-input v-model="scope.row.amount" 
                                :index="scope.$index"></v-number-input>
                            </template>             
                        </el-table-column>
                        <el-table-column label="小计" prop="total" width="120">
                            <template scope="scope">
                                <span v-if="scope.row.unitPrice &&scope.row.amount" class="part-total-price">￥{{(scope.row.unitPrice * scope.row.amount).toFixed(2)}}</span>
                                <span v-else class="part-total-price">￥0.00</span>
                            </template>    
                        </el-table-column>
                        <el-table-column label="操作" width="80">
                            <template scope="scope">
                                <span class="del-part-btn" @click='delPart(scope.$index,scope.row)'>删除</span>
                            </template>
                        </el-table-column>
                    </el-table>
                </div>

                <div class="statistics-box">
                    <div class="add-part">
                        <h1>添加系统中未找到的零件</h1>
                        <span @click="addPartBtn">添加零件</span>
                    </div>
                    <div class="price-info-box">
                        <div>
                            <span>合计:</span>
                            <span class="total-price">￥{{totalPrice}}</span>
                        </div>
                        <div class="coupon-input">
                            <span>优惠减免请输入</span>
                            <v-price-input v-model="couponPrice" :max="totalPrice" placeholder="￥0.00"></v-price-input>
                        </div>
                        <div>
                            <span>总计:</span>
                            <span class="real-price">￥{{realPrice}}</span>
                        </div>
                    </div>
                </div>

                <div class="btn-box">
                    <button class="save-btn" @click="saveIndentDetail">保存</button>
                    <button class="submit-btn" @click="submitIndentDetail">提交</button>
                </div>

                <!-- 添加零件弹框 -->
                <el-dialog title="添加零件"  size="small" :visible.sync="addPartFormModal" class="add-part-modal">
                    <el-form :model="partForm" ref="partForm" :rules="partRules">
                        <el-form-item label="零件/OE码" :label-width="labelWidth" prop="oeNo">
                            <el-input v-model="partForm.oeNo" placeholder="请输入零件号或OE码"></el-input>
                        </el-form-item>
                        <el-form-item label="零件名称" prop="partName" :label-width="labelWidth">
                            <el-input v-model="partForm.partName" placeholder="请输入零件名称"></el-input>
                        </el-form-item>
                        <el-form-item label="产地" :label-width="labelWidth">
                            <el-input v-model="partForm.originPlace" placeholder="请输入产地"></el-input>
                        </el-form-item>
                        <el-form-item label="供应商" :label-width="labelWidth">
                            <el-input v-model="partForm.manufacturer" placeholder="请输入供应商名称"></el-input>
                        </el-form-item>
                        <el-form-item label="仓库" :label-width="labelWidth">
                            <el-input v-model="partForm.partDepot" placeholder="请输入仓库名称"></el-input>
                        </el-form-item>
                        <el-form-item label="单价" :label-width="labelWidth" prop="unitPrice">
                            <el-input v-model="partForm.unitPrice" placeholder="请输入单价"></el-input>
                        </el-form-item>
                        <el-form-item label="数量" :label-width="labelWidth" prop="amount">
                            <el-input v-model.number="partForm.amount"></el-input>
                        </el-form-item>
                    </el-form>
                    <div slot="footer" class="dialog-footer">
                        <el-button class="save-btn" @click="addPart">保存</el-button>
                        <el-button @click="addPartFormModal = false">取 消</el-button>
                    </div>
                </el-dialog>
            </div>
            <div v-else class="no-data">
                <img src="../../assets/images/no-content.png">
                <p>暂无配货单</p>
            </div>
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


