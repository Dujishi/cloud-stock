import { mapGetters } from 'vuex';

export default {
    props: {
        show: {
            type: Boolean,
            default: false,
        },
    },
    data() {
        return {
            dialogVisible: false,
        };
    },
    computed: {
        ...mapGetters([
            'carModel',
        ]),
    },
    watch: {
        show(val) {
            this.dialogVisible = val;
        },
    },
    created() {
        this.dialogVisible = this.show;
    },
    methods: {
        closeDialog() {
            this.dialogVisible = false;
            this.$emit('update:show', this.dialogVisible);
        },
    },
};
