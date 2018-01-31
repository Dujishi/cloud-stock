<template>
    <div>
        <el-select placeholder="省份" v-model="obj.province" @visible-change="changeProvince" @change="changeCity">
            <el-option v-for="item in provinceList" :label="item.name" :value="item.name"></el-option>
        </el-select>
        <el-select placeholder="城市" v-model="obj.city" @change="changeDistrict" @visible-change="cityShow">
            <el-option v-for="item in cityList" :label="item.name" :value="item.name"></el-option>
        </el-select>
        <el-select placeholder="县区" v-model="obj.district">
            <el-option v-for="item in districtList" :label="item.name" :value="item.name"></el-option>
        </el-select>
        <el-input placeholder="详细地址" class="address-input" v-model="obj.address"></el-input>
    </div>
</template>

<script>
import {
    getCityList,
    getDistrictList,
} from '@/api/index';
import { mapMutations, mapGetters, mapActions } from 'vuex';

export default {
    props:{
        obj:{
            type:Object,
        }
    },
    data(){
        return {
            cityList: [],
            districtList: [],
            isChangePro:false,
            isChangeCity:false,
        }
    },
    computed: {
        ...mapGetters([
            'provinceList',
        ]),
    },
    methods: {
        ...mapMutations([
            'setProvinceList',
        ]),
        ...mapActions([
            'getProvince',
        ]),
        changeProvince(flag) {
            if (flag) {
                if(this.provinceList.length===0){
                    this.getProvince();
                } 
                this.isChangePro=true;
                this.isChangeCity=true;
            }
        },
        changeCity() {
            if(this.obj.province){
                getCityList({
                    provinceName:this.obj.province,
                }).then(res=>{
                    if(res.success) {
                        this.cityList=res.data;
                        if(this.isChangePro) {
                            this.obj.city=res.data[0].name;
                        }
                    }
                })
            } else {
                this.cityList=[];
                this.districtList=[];
            }
        },
        cityShow(flag) {
            if(flag) {
                this.isChangeCity=true;
                this.isChangePro=true;
            }
        },
        changeDistrict() {
            if(this.obj.province&&this.obj.city) {
                getDistrictList({
                    provinceName: this.obj.province,
                    cityName: this.obj.city,
                }).then(res=>{
                    if(res.success) {
                        this.districtList=res.data;
                        if(this.isChangeCity) {
                            this.obj.district=res.data[0].name;
                        }
                    }
                })
            }
            
        }
    }
}
</script>

