export default {
    props: {
        list: {
            type: Array,
            default: [],
        },
    },
    methods: {
        search(value) {
            this.$emit('search', value);
        },
    },
    components: {

    },
};
