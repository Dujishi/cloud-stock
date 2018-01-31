<template>
    <div class="page-balance" v-if="showPage">
        <div class="card-info-box clearfix">
            <div class="company-info">
                <p class="color-bold">{{companyName}}</p>
                <p>{{balanceInfo.phone}}</p>
            </div>
            <div class="line"></div>
            <div class="card-info">
                <div class="card-info_balance">
                    <p>账户余额:</p>
                    <p>￥{{Number(balanceInfo.withdrawAmount).toFixed(2) || 0}}</p>
                    <p class="bank-info">银行卡号：</p>
                    <p class="bank-number">
                        <span>{{balanceInfo.bankNo || "请添加银行卡信息"}}</span>
                        <span>{{cardForm.bankName}}</span>
                    </p>
                </div>
                <div class="card-btn-box">
                    <p>
                        <el-button round @click="moneyDialogShow">提现</el-button>
                    </p>
                    <p>
                        <el-button round @click="bankDialogShow">{{editText}}</el-button>
                    </p> 
                </div>
            </div>
        </div>

        <div class="bill-list">
            <p class="title">账单明细</p>
            <el-table :data="billList">
                <el-table-column
                    prop="date"
                    label="时间"
                    width="180">
                    <template scope="scope">
                        {{scope.row.date | formateDate}}
                    </template>
                </el-table-column>
                <el-table-column
                    label="项目">
                    <template scope="scope">
                        <span class="color-bold">{{scope.row.serviceName}}</span>
                    </template>
                </el-table-column>
                <el-table-column
                    label="金额"
                    width="180">
                    <template scope="scope">
                        <span class="add-amount" v-if="scope.row.amount>0">+￥{{scope.row.amount}}</span>
                        <span v-else class="reduce-amount">-￥{{Math.abs(scope.row.amount).toFixed(2)}}</span>
                    </template>
                </el-table-column>
                <el-table-column
                    prop="statusDesc"
                    label="状态"
                    width="180">
                </el-table-column>
                <el-table-column
                    label="余额"
                    width="180">
                    <template scope="scope">
                        ￥{{scope.row.balanceAmount}}
                    </template>
                </el-table-column>
            </el-table>
            <el-pagination
            class="pagination"
            small
            layout="prev, pager, next"
            :total="listSize"
            :page-size="20"	
            @current-change="changePage">
            </el-pagination>
        </div>

        <!-- 提现弹框 -->
        <el-dialog title="提现" :visible.sync="showMoneyDialog" class="money-dialog">
            <p class="can-get">
                <span>可提现金额：</span>
                <span>￥{{balanceInfo.withdrawAmount}}</span>
            </p>
            <el-form>
                <el-form-item label="提现金额：" :label-width="formLabelWidth">
                    <!-- <el-input v-model="moneyForm.withdrawAmount" auto-complete="off" placeholder="请输入提现金额"></el-input> -->
                    <v-price-input v-model="moneyForm.withdrawAmount" placeholder="￥0.00">
                    </v-price-input>    
                </el-form-item>
                <el-form-item label="手机号码：" :label-width="formLabelWidth">
                    <el-input v-model="balanceInfo.phone" auto-complete="off" :disabled=true></el-input>
                </el-form-item>
                <el-form-item label="验证码：" :label-width="formLabelWidth">
                    <el-input v-model="moneyForm.smsCode" auto-complete="off" class="code-input" placeholder="请输入验证码"></el-input>
                    <span class="code-btn" @click="getMoneyCode">获取验证码</span>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="getMoney">确 定</el-button>
                <el-button @click="showMoneyDialog = false">取 消</el-button>
            </div>
        </el-dialog>

        <!-- 修改银行卡信息弹框 -->
         <el-dialog title="编辑银行卡信息" :visible.sync="showCardDialog" class="money-dialog">
            <el-form :model="cardForm" ref="cardForm" :rules="bankInfoRules">
                <el-form-item label="银行卡号：" :label-width="formLabelWidth" prop="bankNo">
                    <el-input v-model="cardForm.bankNo" auto-complete="off" type="number" placeholder="请填写银行卡号"></el-input>
                </el-form-item>
                <el-form-item label="开户银行：" :label-width="formLabelWidth" prop="bankName" class="bank-name-input" v-if="!otherBankInputShow">
                    <el-select v-model="cardForm.bankName" placeholder="请选择开户银行" @change="bankChange">
                        <el-option
                        v-for="item in bankDto"
                        :label="item.bankName"
                        :value="item.bankName">
                        </el-option>
                    </el-select>
                    <div class="other-bank" @click="otherBank">
                        其他银行
                    </div>
                </el-form-item>
                <el-form-item label="开户银行：" :label-width="formLabelWidth" prop="bankName" v-if="otherBankInputShow" class="bank-name-input">
                    <el-input v-model="cardForm.bankName" auto-complete="off" placeholder="请填写开户银行" :maxlength='maxLength'></el-input>
                    <div class="other-bank" @click="otherBank">
                        其他银行
                    </div>
                </el-form-item>
                <el-form-item label="开户城市：" :label-width="formLabelWidth" prop="bankCity">
                    <el-select v-model="cardForm.bankCity" placeholder="请选择开户城市" @change="cityChange">
                        <el-option
                        v-for="item in cityDto"
                        :label="item.name"
                        :value="item.name">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="所属支行：" :label-width="formLabelWidth" prop="bankBranch">
                    <el-input v-model="cardForm.bankBranch" auto-complete="off" placeholder="请填写所属支行" :maxlength='maxLength'></el-input>
                </el-form-item>
                <el-form-item label="开户姓名：" :label-width="formLabelWidth" prop="bankUser">
                    <el-input v-model="cardForm.bankUser" auto-complete="off" placeholder="请填写开户姓名" :maxlength="maxLength"></el-input>
                </el-form-item>
                <el-form-item label="手机号码：" :label-width="formLabelWidth" prop="phone">
                    <el-input v-model="cardForm.phone" auto-complete="off" :disabled=true></el-input>
                </el-form-item>
                <el-form-item label="账户类型：" :label-width="formLabelWidth">
                    <el-radio v-model="cardForm.bankAccType" :label=1>个人账户</el-radio>
                    <el-radio v-model="cardForm.bankAccType" :label=2>企业账户</el-radio>
                </el-form-item>
                <el-form-item label="验证码：" :label-width="formLabelWidth" prop="smsCode">
                    <el-input v-model="cardForm.smsCode" auto-complete="off" class="code-input" placeholder="请填写验证码"></el-input>
                    <span class="code-btn" @click="getBankCode">获取验证码</span>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="updateBankInfo">确 定</el-button>
                <el-button @click="showCardDialog = false">取 消</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import AppMod from './app';
    export default AppMod;
</script>

<style lang="less">
    @import "./app.less";
</style>

