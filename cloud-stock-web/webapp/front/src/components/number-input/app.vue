<template>
    <div class="input-number">
        <span class="input-number_dec" @click="minus">
            <i class="el-icon-minus"></i>
        </span>
        <input type="number" class="el-input__inner" v-model="amount" @blur="checkNumber">
        <span class="input-number_add" @click="plus">
            <i class="el-icon-plus"></i>
        </span>
    </div>
</template>

<script>

export default {
    props: ['value','index'],
    data () {
        return {
            amount:this.value,
        }
    },
    methods: {
        minus() {
            this.amount--;
        },
        plus() {
            this.amount++;
        },
        checkNumber() {
            if(this.amount===""||this.amount==0){
                this.amount=1;
            }
        },
    },
    watch: {
        value(val){
            this.amount=val;
        },
        amount(val) {
            if(!/^\d+\.?\d{0,2}$/.test(val) && val!=='') {
                this.amount = Number(val).toFixed(2);
            }
            if(val<0){
                this.amount=1;
            }
            this.$emit('input', this.amount);
        },
    }
}
</script>

<style lang="less">
    .input-number {
        position: absolute;
        left: 0;top: 30%;
        display: flex;
        justify-content: space-around;
        align-items: center;
        width: 120px;
        height: 36px;
        border: 1px solid #BBBBBB;
        border-radius: 3px;

        &:hover {
            border: 1px solid #DE5550;
        }

        span {
            padding: 0 7px;
            width: 36px;
            cursor: pointer;

            &.el-input-number_dec {
                border-right:1px solid #D8D8D8;
            }

            &.el-input-number_add {
                border-left: 1px solid #D8D8D8;
            }

            i:hover {
                color: #DE5550;
            }
        }

        .el-input__inner {
            padding: 0;
            border: 0;
            height: 80%;
            text-align: center;
            width: 46px;
        }

        .el-icon-minus {
            color: #888;    
        }

        .el-icon-plus {
            color: #222;  
        }
    }
</style>


