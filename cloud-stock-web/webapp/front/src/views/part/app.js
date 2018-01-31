import VHeader from '@/components/header/app.vue';
import VPartSidebar from '@/components/part-sidebar/app.vue';
import { getBrandByCodes } from '@/api/index';
import { mapGetters, mapMutations, mapActions } from 'vuex';
import queryUtils from '@/utils/query';

export default {
    data() {
        return {
            brandDialogVisible: false,
            brandList: [],
        };
    },
    computed: {
        ...mapGetters([
            'oeCodeList',
            'oePartList',
        ]),
    },
    methods: {
        ...mapMutations([
            'setOeBrandName',
            'setOePartList',
        ]),
        ...mapActions([
            'getOePartListAction',
        ]),

        // 如果只有一个零件码，直接去详情页,否则去part-list
        search() {
            getBrandByCodes({
                partCodeList: this.oeCodeList,
            }).then((res) => {
                if (res.success) {
                    this.brandList = res.data || [];
                    if (this.brandList.length === 0) {
                        this.gotoPartList();
                    } else if (this.brandList.length === 1) {
                        // console.log(this.oeCodeList);
                        if (this.oeCodeList.length === 1) {
                            this.gotoDetail(this.oeCodeList[0], this.brandList[0].brandName);
                        } else {
                            this.gotoPartList(this.brandList[0].brandName);
                        }
                    } else if (this.brandList.length > 1) {
                        this.brandDialogVisible = true;
                    }
                } else {
                    this.gotoPartList();
                    this.$message.error(res.message);
                }
            });
        },

        // 直接去详情页
        gotoDetail(partCode, carBrandName) {
            const params = queryUtils.serialize({
                from: 'part',
                partCode,
                carBrandName,
            });
            this.$router.push({ path: `/part/detail?${params}` });
        },

        selectBrand(brandName) {
            this.brandDialogVisible = false;
            if (this.oeCodeList.length === 1) {
                this.gotoDetail(this.oeCodeList[0], brandName);
            } else {
                this.gotoPartList(brandName);
            }
            // this.gotoPartList(brandName);
        },
        gotoPartList(brandName) {
            if (brandName) {
                this.setOeBrandName(brandName);
                this.getOePartListAction({ brandName });
            } else {
                this.setOeBrandName('');
                this.setOePartList([]);
            }
            this.$router.push({ path: '/part/part-list' });
        },
    },
    components: {
        VHeader,
        VPartSidebar,
    },
};
