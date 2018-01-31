<template>
    <div class="page-model-write-result">
        <el-tabs type="card" v-model="brandName" @tab-click="changeBrand">
            <el-tab-pane v-for="item in brandList" :label="item.brandName" :name="item.brandName"></el-tab-pane>
        </el-tabs>
        <el-tabs v-model="brandYear" @tab-click="changeYear">
            <el-tab-pane v-for="item in yearList" :label="item.year" :name="item.year"></el-tab-pane>
    </el-tabs>
     <ul>
        <li v-for="item in carModelList" @click="getCategory(item.modelId)">
            {{item.standardModelName}}
        </li>
    </ul>
    </div>
</template>

<script>
import { getBrandByModelName } from '@/api/index';
import { mapActions } from 'vuex';


export default {
    data () {
        return {
            brandName: '',
            brandList: [],
            yearList: [],
            brandYear: '',
            carModelList: [],
        }
    },
    watch: {
        '$route'() {
            this.getBrandByModelName();
        }
    },
    mounted () {
        // const brandName = this.$route.query.brandName;
        this.getBrandByModelName();
        
    },
    methods: {
        ...mapActions([
            'getCategoryByModelAction',
        ]),
        getBrandByModelName() {
            const brandName = this.$route.query.brandName;
            getBrandByModelName({modelCondition:brandName}).then(res => {
                if (res.success && res.data.length>0) {
                    this.brandList = res.data;
                    this.brandName = res.data[0].brandName;
                    this.yearList = res.data[0].yearList;
                    this.brandYear = this.yearList[0].year;
                    this.carModelList = this.yearList[0].carModelList;
                } else {
                    this.$message.error(res.message || "暂无数据");
                }
            })
        },
        // 改变车型
        changeBrand() {
            this.brandList.some((item) => {
                if (item.brandName === this.brandName) {
                    this.yearList = item.yearList;
                    this.brandYear = this.yearList[0].year;
                    this.carModelList = this.yearList[0].carModelList;
                }
            });
        },
        // 改变年份
        changeYear() {
            this.yearList.some((item) => {
                if (item.year === this.brandYear) {
                    this.carModelList = item.carModelList;
                    return true;
                }
            });
        },
         // 查看
        getCategory(id) {
            this.getCategoryByModelAction({carModelId: id});
            this.$router.push({
                path: `/model/category?carModelId=${id}`,
            });
        },
    },
}
</script>

<style lang="less">
    @import '../../../assets/less/variable.less';
    .page-model-write-result {
        margin-top: 30px;
        padding-left: 30px;

        .el-tabs__item.is-active {
            color: @color-primary;
        }

        .el-tabs__active-bar {
            background-color: @color-primary;
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
</style>


