<template>
    <input type="number" class="el-input__inner price-input" v-model="price" :placeholder="placeholder">
</template>

<script>

export default {
    props: ['value', 'index', 'placeholder', 'max'],

    data () {
        return {
            price:this.value,    
        }
    },

    mounted () {
        // this.$emit('input', this.price,this.index,1);
        // console.log(this.value);
    },

    computed: {
       
    },

    watch: {
        value(val){
            this.price=val;
        },
        price(val) {
            if(!/^\d+\.?\d{0,2}$/.test(val) && val!=='') {
                this.price = Number(val).toFixed(2);
            }
            if(val<0){
                this.price=0;
            }
            if(val-this.max>0) {
                this.price=this.max;
            }
            this.$emit('input', this.price);
        },
    }
}
</script>

<style lang="less">
    .price-input {
        margin-top: 5px;
        min-width: 90px;
        padding: 0 3px 0 2px;

        &:focus {
            background: #bbbbbb;
            border: 1px solid #f5f5f5;
       }
    }
</style>


