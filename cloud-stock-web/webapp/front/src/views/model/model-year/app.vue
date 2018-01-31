<template>
    <div class="page-car-box">
        <p class="title">{{brandName}}</p>
        <div class="year-list">
            <el-tabs v-model="modelYear" @tab-click="changeYear">
                <el-tab-pane v-for="item in modelYearList" :label="item.modelYear" :name="item.modelYear"></el-tab-pane>
            </el-tabs>

            <ul>
                <li v-for="item in seriesList" @click="getCategory(item.modelId)">
                    {{item.standardModelName}}
                </li>
            </ul>
        </div>
    </div>
</template>

<script>
import {getModelYear, getModelSeries} from '@/api/index';
import { mapActions } from 'vuex';

export default {
    data () {
        return {
            brandId: this.$route.query.brandId,
            brandName: this.$route.query.brandName,
            modelYearList: [],
            seriesList: [],
            modelYear: '',
            series: this.$route.query.series,
        }
    },
    mounted () {
        this.getModelYear();  
    },
    methods: {
        ...mapActions([
            'getCategoryByModelAction',
        ]),
        changeYear() {
            this.getModelSeries();
        },
        getModelYear() {
            getModelYear({brandId:this.brandId,series:this.series}).then(res => {
                if (res.success) {
                    this.modelYearList = res.data;
                    this.modelYear = res.data[0].modelYear;
                    this.getModelSeries();
                } else {
                    this.$message.error(res.message);
                }
            })
        },
        // 查看
        getCategory(id) {
            this.getCategoryByModelAction({carModelId: id});
            this.$router.push({
                path: `/model/category?carModelId=${id}`,
            });
        },
        getModelSeries() {
            getModelSeries({
                    brandId:this.brandId,
                    series:this.series,
                    modelYear:this.modelYear
                }).then(res => {
                if (res.success) {
                    this.seriesList = res.data;
                } else {
                    this.$message.error(res.message);
                }
                
            })
        }
    }
}
</script>

<style lang="less">
.page-car-box {
    padding: 30px 0 30px 30px;

    .title {
        padding-bottom: 10px;
        font-size: 18px;
        color: #222;
        border-bottom: 1px solid #efefef;
    }

    .year-list {

        .el-tabs__item.is-active {
            color: #e05e59;
        }

        .el-tabs__active-bar {
            background-color: #e05e59;
        }

        li {
            display: inline-block;
            margin-right: 20px;
            margin-bottom: 20px;
            min-width: 300px;
            padding: 0 15px;
            height: 40px;
            line-height: 40px;
            border:1px solid #888d98;
            border-radius: 26px;
            text-align: center;
            overflow: hidden;
            cursor: pointer;

            &:hover {
                color: #fff;
                background-color: #e05e59;
                border: transparent;
            }
        }
    }
}
</style>


