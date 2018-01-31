<template>
    <div class="page-series">
        <p class="title">{{brandName}}</p>
        <div class="series-box">
            <ul>
                <li v-for="item in seriesList" @click="selectSeries(item)">{{item.seriesName}}</li>
            </ul>
        </div>
    </div>
    
</template>

<script>
import {getSeriesByBrand} from '@/api/index';
import { mapGetters, mapMutations } from 'vuex';

export default {
    data () {
        return {
            brandId: this.$route.query.brandId,
            brandName: this.$route.query.brandName,
            seriesList: [],
        }
    },
    computed: {
        ...mapGetters([
            'seriesName',
        ]),
    },
    mounted () {
        this.getSeriesByBrand();
    },
    methods: {
        ...mapMutations([
            'setSeriesName',
        ]),

        getSeriesByBrand() {
            const brandId = this.brandId;
            getSeriesByBrand({brandId}).then(res => {
                if (res.success) {
                    this.seriesList = res.data;
                } else {
                    this.$message.error(res.message);
                }   
            })
        },

        selectSeries(item) {
            this.$router.push({
                path: '/model/modelYear',
                query: {
                    brandId: this.brandId,
                    brandName: this.brandName,
                    firstLetter: this.$route.query.firstLetter,
                    series: item.series,
                }
            })
        }
    }
}
</script>

<style lang="less">
    .page-series {
        padding: 30px 0 30px 30px;

        .title {
            padding-bottom: 10px;
            font-size: 18px;
            color: #222;
            border-bottom: 1px solid #efefef;
        }

        .series-box {

            li {
                margin-top: 30px;
                display: inline-block;
                margin-right: 20px;
                min-width: 170px;
                padding: 0 15px;
                height: 40px;
                line-height: 40px;
                text-align: center;
                background: #FFFFFF;
                border: 1px solid #888D98;
                border-radius: 26px;
                font-size: 14px;
                color: #333;
                cursor: pointer;

                &:hover {
                    color: #fff;
                    background-color: #e05e59;
                    border-color: transparent;
                }
            }
        }
        .car-box {

            .year-list {

                .el-tabs__item.is-active {
                    color: #e05e59;
                }

                .el-tabs__active-bar {
                    background-color: #e05e59;
                }
            }
        }
    }
</style>


