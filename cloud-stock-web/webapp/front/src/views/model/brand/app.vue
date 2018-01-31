<template>
    <div class="page-brand">
        <p class="title">{{firstLetter}}</p>

        <section class="brand-box">
            <ul>
                <li v-for="item in brandList" @click="selectBrand(item)">
                    <div class="img-box">
                        <img :src="item.brandIconUrl">
                    </div>
                    <p>{{item.brandName}}</p>
                </li>
            </ul>
        </section>
    </div>
</template>


<script>
import { mapGetters, mapMutations, mapActions } from 'vuex';
import { getBrandByFirstLetter,  getBrandByModelName, getAuthFirstLetter } from '@/api/index';

export default {
    data () {
        return {
            firstLetter: '',
            brandName: '',
        }
    },
    computed: {
        ...mapGetters([
            'brandList',
        ]),
    },
    watch: {
        '$route'() {
            const firstLetter = this.$route.query.firstLetter;
            if (firstLetter && !this.brandName) {
                this.getBrandByFirstLetter();
            } else {
                this.getBrandByModelName();
            }
            
        },
    },
    mounted () {
        const firstLetter = this.$route.query.firstLetter;
        const brandName = this.$route.query.brandName;
        if (brandName) {
            this.getBrandByModelName();
            return;
        }
        if (firstLetter && !brandName) {
            this.getBrandByFirstLetter();  
            return;
        }
    },
    methods: {
        ...mapMutations([
            'setBrandsList',
        ]),
        getBrandByFirstLetter() {
            const firstLetter = this.$route.query.firstLetter;
            this.firstLetter = firstLetter;
            getAuthFirstLetter({firstLetter}).then(res => {
                this.setBrandsList(res.data);
            })
        },
        getBrandByModelName() {
            const brandName = this.$route.query.brandName;
            getBrandByModelName({modelCondition:brandName}).then(res => {
                if (res.success) {
                    this.setBrandsList(res.data);
                } else {
                    this.$message.error(res.message);
                }
            })
        },
        selectBrand(item) {
            this.$router.push({
                path: '/model/series',
                query:{
                    brandId: item.brandId,
                    brandName: item.brandName,
                    firstLetter: this.firstLetter,
                }
            })
        },
    },
}
</script>

<style lang="less">
    .page-brand {
        padding: 30px 0 30px 30px;

        .title {
            padding-bottom: 10px;
            font-size: 18px;
            color: #e05e59;
            border-bottom: 1px solid #efefef;
        }

        .brand-box {

            li {
                margin-top: 30px;
                display: inline-block;
                margin-right: 60px;
                color: #333;
                cursor: pointer;

                &:hover {
                    color: #e05e59;
                    
                    .img-box {
                        border: 1px solid #e05e59;
                    }
                }

                .img-box {
                    display: table-cell;
                    text-align: center;
                    vertical-align: middle;
                    width: 120px;
                    height: 120px;
                    border: 1px solid #efefef;

                    &:hover {
                        border-color: 1px solid #e05e59;
                    }
                }

                img {
                    width: 80px;
                }

                p {
                    margin-top: 10px;
                    font-size: 16px;
                    text-align: center;
                }
            }
        }
    }
</style>

