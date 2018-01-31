import copyToClipboard from '@/utils/clipboard';

export default {
    props: ['text'],
    data() {
        return {

        };
    },
    methods: {
        onCopyText() {
            const success = copyToClipboard(this.text);
            if (success) {
                this.$message.success('复制成功');
            } else {
                this.$message.error('浏览器不支持点击复制到剪贴板，请手动复制');
            }
        },
    },
};
