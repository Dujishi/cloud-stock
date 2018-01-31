import VClearableInput from '@/components/clearable-input/app.vue';
import {getBrandFrist} from '@/api/index';
import {mapGetters, mapMutations} from 'vuex';

export default {
    components: {
        VClearableInput,
    },
    data() {
        return {
            modelName: '',
            firstLetterShow: false,
            firstLetterList: [],
        };
    },
    computed: {
        ...mapGetters([
            'firstLetter',
        ]),
    },
    mounted () {
        this.getBrandFrist();
        this.isShowBrand();
    },
    methods: {
        ...mapMutations([
            'setFirstLetter',
        ]),
        search() {
            this.$router.replace({
                path: '/model/modelWriteResult',
                query: {
                    brandName: this.modelName,
                },
            });
        },
        searchBrand(item) {
            this.setFirstLetter(item.firstLetter);
            this.$router.push({
                path: '/model/brand',
                query: {
                    firstLetter: item.firstLetter,
                },
            });
        },
        modelQuery() {
            this.firstLetterShow = true;

        },
        writeQuery() {
            this.firstLetterShow = false;
            this.setFirstLetter('');
        },
        getBrandFrist() {
            getBrandFrist().then(res => {
                if (res.success) {
                    this.firstLetterList = res.data;
                } else {
                    this.$message.success(res.message);
                }
            });
        },
        isShowBrand() {
            const route = this.$route;
            if (route.query.firstLetter) {
                this.setFirstLetter(route.query.firstLetter);
                this.firstLetterShow = true;
            }
        },
    },
};
