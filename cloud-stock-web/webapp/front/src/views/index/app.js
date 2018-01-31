import { mapMutations, mapGetters } from 'vuex';

export default {
    data() {
        return {
            vinCode: '',
            partCode: '',
            model: '',
            // loginDialogVisible: false,
            typeList: [
                { id: 'vin', text: 'VIN码' },
                { id: 'part', text: '零件码' },
                { id: 'model', text: '车型' },
            ],
            currentType: 'vin',
        };
    },
    computed: {
        ...mapGetters([
            'isLogin',
        ]),
    },
    methods: {
        ...mapMutations([
            'setVinCode',
            // 'setPartCode',
            'setOeCode',
            'setLoginDialogVisible',
        ]),
        selectSearchType(id) {
            this.currentType = id;
        },
        searchVinCode() {
            if (!this.isLogin) {
                this.setLoginDialogVisible(true);
                return;
            }
            if (!this.vinCode) {
                this.$message.error('请输入VIN码');
                return;
            }
            // this.setVinCode(this.vinCode);
            this.$router.push({ path: `/vin?vinCode=${this.vinCode}` });
        },
        searchPartCode() {
            if (!this.isLogin) {
                this.setLoginDialogVisible(true);
                return;
            }
            if (!this.partCode) {
                this.$message.error('请输入零件码');
                return;
            }
            // this.setOeCode(this.partCode);
            this.$router.push({ path: `/part?oeCode=${this.partCode}` });
        },
        searchModel() {
            if (!this.isLogin) {
                this.setLoginDialogVisible(true);
                return;
            }
            if (!this.model) {
                this.$message.error('请输入车型');
                return;
            }
            // this.setOeCode(this.partCode);
            this.$router.push({
                    path: `/model/brand?brandName=${this.model}`,
                });
        },

    },
    components: {
    },
};
