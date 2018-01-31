import { mapMutations, mapGetters, mapActions } from 'vuex';
import VHistory from '@/components/history/app.vue';

export default {
    data() {
        return {

        };
    },
    computed: {
        ...mapGetters([
            'carModelList',
        ]),
    },
    methods: {
        ...mapMutations([
            'setCarModelId',
        ]),
        ...mapActions([
            'getCategoryByModelAction',
            'getPartListByModelAction',
        ]),
        changeModel(carModelId) {
            this.setCarModelId(carModelId);
            if (this.$route.path.indexOf('/vin/vin-part-list') > -1) {
                this.$router.replace({ path: '/vin/vin-part-list' });
                this.getPartListByModelAction({ carModelId });
                // this.$router.push({ path: `/vin/vin-part-list?carModelId=${carModelId}` });
            } else {
                this.$router.replace({ path: '/vin/category' });
                this.getCategoryByModelAction({ carModelId });
                // this.$router.push({ path: `/vin/category?carModelId=${carModelId}` });
            }
            this.$emit('change', carModelId);
        },
    },
    components: {
        VHistory,
    },
};
