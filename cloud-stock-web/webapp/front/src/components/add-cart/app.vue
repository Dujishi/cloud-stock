<template>
    <el-dialog title="加入配货单" :visible.sync="isShow" class="add-cart-box">
        <ul class="clearfix">
            <li v-for='item in indentList' @click="selectIndent(item)" :class="{active:item.id==selectItem.id}">
                {{item.name}}
            </li>
            <li class="add-indent-btn" @click="addIndent">+添加配货单</li>
        </ul>
        <!-- <div class="btn-box">
            <button @click='addIndentPart'>确认</button>
            <button @click='cancel'>取消</button>
        </div> -->
    </el-dialog>
</template>

<script>
import AppMod from './app';
export default AppMod;
</script>

<style lang="less">
 @import './app.less';
</style>


