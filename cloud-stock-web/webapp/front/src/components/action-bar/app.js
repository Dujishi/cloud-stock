import { mapGetters } from 'vuex';
import VCarInfoDialog from '@/components/car-info-dialog/app.vue';
import VChangeModel from '../change-model/app.vue';

export default {
    props: {
        showGoBack: {
            type: Boolean,
            default: true,
        },
        showCarModel: {
            type: Boolean,
            default: false,
        },
        carModelConfig: {
            type: Object,
            default: null,
        },
        from: {
            type: String,
        },
        carModelName: {
            type: String,
        }
    },
    data() {
        return {
            carInfoDialogVisible: false,
        };
    },
    computed: {
        ...mapGetters([
            'carModel',
        ]),
    },
    methods: {
        goback() {
            this.$router.back();
        },
        showCarInfoDialog() {
            this.carInfoDialogVisible = true;
        }, 
    },
    components: {
        VChangeModel,
        VCarInfoDialog,
    },
};
