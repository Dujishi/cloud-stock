export default {
    props: {
        show: {
            type: Boolean,
            default: false,
        },
        text: {
            type: String,
            default: '数据加载中...',
        },
    },
    data() {
        return {
            maxPercentage: 99,
            percentage: 0,
            timer: null,
        };
    },
    watch: {
        show(val) {
            if (val) {
                this.showLoading();
            } else {
                this.hideLoading();
            }
        },
    },
    methods: {
        startProgress() {
            if (this.percentage >= this.maxPercentage) {
                window.clearTimeout(this.timer);
                return;
            }
            this.percentage += 1;
            this.timer = window.setTimeout(this.startProgress, 30);
        },
        showLoading() {
            window.clearTimeout(this.timer);
            this.percentage = 0;
            this.startProgress();
        },
        hideLoading() {
            window.clearTimeout(this.timer);
            this.percentage = 0;
        },
    },
};
