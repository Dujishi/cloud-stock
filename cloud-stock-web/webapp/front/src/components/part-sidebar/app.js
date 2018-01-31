import { mapMutations, mapGetters } from 'vuex';
import { searchHistory } from '@/api/index';
import VHistory from '@/components/history/app.vue';
import VClearableInput from '@/components/clearable-input/app.vue';

export default {
    data() {
        return {
            pageOeCode: '',
            historyList: [],
        };
    },
    computed: {
        ...mapGetters([
            'oeCode',
        ]),
    },
    created() {
        this.getHistory();
        const oeCode = this.$route.query.oeCode;
        if (oeCode) {
            this.pageOeCode = oeCode;
            this.search();
        } else {
            // this.$router.replace({ path: '/part' });
        }
    },
    methods: {
        ...mapMutations([
            'setOeCode',
        ]),
        search() {
            // console.log(this.pageOeCode.split('\n'));
            // if (this.pageOeCode.split('\n').length === 1) {
            //     this.setOeCode(this.pageOeCode);
            //     this.$emit('searchByOne');
            //     return;
            // }

            if (this.pageOeCode) {
                this.setOeCode(this.pageOeCode);
                this.$emit('search');
            } else {
                this.$message.error('请输入零件码');
            }
        },
        getHistory() {
            searchHistory({
                searchType: 2,
            }).then((res) => {
                if (res.success) {
                    this.historyList = res.data;
                } else {
                    this.$message.error(res.message);
                }
            });
        },
        searchByHistory(code) {
            this.setOeCode(code);
            this.pageOeCode = code;
            this.search();
        },
    },
    components: {
        VHistory,
        VClearableInput,
    },
};
