export default {
    methods: {
        /**
         * 选择标记
         * @param {Object} tag
         */
        emitSelectTag(tag) {
            this.$emit('selectTag', this.currentIndex, tag);
        },

        /**
         * 更换图片
         * @param {Object} image
         */
        emitChangeImage(image) {
            this.$emit('changeImage', this.currentIndex, image);
        },
    },
}
;
