export default {
    props: {
        placeholder: {
            type: String,
        },
        value: {
            type: String,
        },
        type: {
            type: String,
        },
        rows: {
            type: Number,
        },
        resize: {
            type: String,
        },
    },
    data() {
        return {
            inputValue: '',
            clearIconVisible: false,
        };
    },
    created() {
        this.inputValue = this.value;
    },
    watch: {
        inputValue(value) {
            this.$emit('input', value);
            this.clearIconVisible = Boolean(value);
        },
        value(value) {
            this.inputValue = value;
        },
    },
    methods: {
        clearInput() {
            this.inputValue = '';
        },
    },
    components: {

    },
};
