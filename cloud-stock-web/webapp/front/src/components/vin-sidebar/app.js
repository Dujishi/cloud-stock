import { mapMutations, mapGetters, mapActions } from 'vuex';
import { searchHistory } from '@/api/index';
import VHistory from '@/components/history/app.vue';
import VClearableInput from '@/components/clearable-input/app.vue';

export default {
    data() {
        return {
            currentVinCode: '',
            currentPartName: '',
            historyList: [],
        };
    },
    computed: {
        ...mapGetters([
            'vinCode',
            'partNameList',
        ]),
    },
    created() {
        this.getHistory();
        const vinCode = this.$route.query.vinCode;
        if (vinCode) {
            this.currentVinCode = vinCode;
            this.search();
        }
    },
    methods: {
        ...mapMutations([
            'setVinCode',
            'setPartName',
        ]),
        ...mapActions([
            'getCategoryByVinAction',
            'getPartListByVinAction',
        ]),
        search() {
            const currentVinCode = this.currentVinCode;
            const currentPartName = this.currentPartName;

            if (currentVinCode) {
                this.setVinCode(currentVinCode);
                this.setPartName(currentPartName);
                if (currentPartName) {
                    this.getPartListByVinAction({
                        vin: this.currentVinCode,
                        partNameList: this.partNameList,
                    });
                    this.$router.replace({ path: '/vin/vin-part-list' });
                } else {
                    this.getCategoryByVinAction({ vin: this.currentVinCode });
                    this.$router.replace({ path: '/vin/category' });
                }
            } else {
                this.$message.error('请输入VIN码');
            }
        },
        getHistory() {
            searchHistory({
                searchType: 1,
            }).then((res) => {
                if (res.success) {
                    this.historyList = res.data;
                } else {
                    this.$message.error(res.message);
                }
            });
        },
        searchByHistory(vinCode) {
            this.currentVinCode = vinCode;
            this.currentPartName = '';
            this.search();
        },
    },
    components: {
        VHistory,
        VClearableInput,
    },
};
