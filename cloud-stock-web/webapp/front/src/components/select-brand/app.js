import {getAllBrand, getAuthorisedBrand, getBrandFrist, setBrandAuth } from '@/api/index';
import axios from 'axios';


export default {
    props: ['userId', 'isShow'],
    data() {
        return {
            fristNameArr: [],
            brandArr: [],
            checkBrandList: [],
            selected: 'A',
            checkAll: false,
            isIndeterminate: true,
        };
    },
    mounted() {
        this.getAllBrandFirst();
        this.getAllBrand();
    },
    watch: {
    //   userId(val) {
    //     this.getAuthorisedBrand();
    //     document.querySelector('.brand-content-box').scrollTop = 0;
    //   },
      isShow(val) {
        if (val) {
            this.checkAll = false;
            this.getAuthorisedBrand();
            document.querySelector('.brand-content-box').scrollTop = 0;
        }
      }
    },
    methods: {
        selectBrandFrist(item) {
            this.selected = item.firstLetter;
            let elem = document.querySelector('.brand-content-box');
            let selectId = item.firstLetter;
            elem.scrollTop = document.querySelector(`#${selectId}`).offsetTop-200;
        },
        closeModal() {

            this.$emit('close');
        },
        // 全选
        checkAllChange() {
            if (this.checkAll) {
                this.brandArr.map((i) => {
                    if (this.checkBrandList.indexOf(i.brandId) === -1) {
                        this.checkBrandList.push(i.brandId);
                    }
                });
            } else {
                this.checkBrandList = [];
            }
        },
        getAllBrand() {
            getAllBrand().then(res => {
                if (res.success) {
                    this.brandArr = res.data;
                    this.getAuthorisedBrand();
                } else {
                    this.$message.error(res.message);
                }
            });
        },
        getAllBrandFirst() {
            getBrandFrist().then(res => {
                if (res.success) {
                    this.fristNameArr = res.data;
                }else {
                    this.$message.error(res.message);
                }
            });
        },
        getAuthorisedBrand() {
            getAuthorisedBrand({userId:this.userId}).then(res => {
                if (res.success) {
                    this.checkBrandList=[];
                    if (res.data) {
                        if (res.data.length === this.brandArr.length) {
                            this.checkAll = true;
                        } 
                        res.data.map((i) => {
                            this.checkBrandList.push(i.brandId); 
                        });  
                    }
                } else {
                    this.$message.error(res.message);
                }
            });
        },
        setBranchAuth() {
            setBrandAuth({id: this.userId, brandIds: this.checkBrandList}).then(res => {
                if (res.success) {
                    this.$message.success('设置成功');
                    this.$emit('close');
                } else {
                    this.$message.error(res.message);
                }
            })
        }
        
    },
};
