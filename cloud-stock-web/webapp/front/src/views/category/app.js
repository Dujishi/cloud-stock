import { mapGetters, mapMutations } from 'vuex';
import VChangeModel from '@/components/change-model/app.vue';
import VNoContent from '@/components/no-content/app.vue';
import getCategoryIcon from '@/mixins/getCategoryIcon';
import VActionBar from '@/components/action-bar/app.vue';

export default {
    mixins: [getCategoryIcon],
    data() {
        return {
            inited: false,
            carModelId: this.$route.query.carModelId,
        };
    },
    computed: {
        ...mapGetters([
            'vinCode',
            'categoryList',
        ]),
    },
    created() {
        if (!this.vinCode && !this.carModelId) {
            this.$router.replace({ path: '/vin' });
        }
    },
    methods: {
        ...mapMutations([
            'setCategoryObj',
        ]),
        gotoSubCategory(obj) {
            this.setCategoryObj(obj);
            this.$router.push({ path: '/vin/sub-category' });
        },
    },
    components: {
        VChangeModel,
        VNoContent,
        VActionBar,
    },
};
