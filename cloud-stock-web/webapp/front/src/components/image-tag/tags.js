export default {
    data() {
        return {
            tagsObj: {
                // 当前点击显示的tag
                currentShowTags: [],
            },
        };
    },

    mounted() {
        this.$watch('currentShowTag', (newValue) => {
            if (newValue) {
                this.tagsObj.currentShowTags = this.filterShowTags(newValue);
            }
        });
    },

    methods: {
        initTags() {
            this.tagsObj.currentShowTags = [];
        },

        // 更新列表
        updateTags() {
            const tags = this.currentImage.carModelPicMarkItemList || [];
            for (let i = 0; i < tags.length; i++) {
                tags[i].w = tags[i].recWidth;
                tags[i].h = tags[i].recHeight;
                tags[i].l = tags[i].xAxis;
                tags[i].t = tags[i].yAxis;
                tags[i].wRate = tags[i].recWidth / tags[i].markedPicWidth;
                tags[i].hRate = tags[i].recHeight / tags[i].markedPicHeight;
                tags[i].lRate = tags[i].xAxis / tags[i].markedPicWidth;
                tags[i].tRate = tags[i].yAxis / tags[i].markedPicHeight;
                this.$set(this.currentImage.carModelPicMarkItemList, i, tags[i]);
            }
        },

        // 更新大小
        updateTagSize({ iw, ih }) {
            const tags = this.currentImage.carModelPicMarkItemList || [];
            for (let i = 0; i < tags.length; i++) {
                tags[i].w = tags[i].wRate * iw;
                tags[i].h = tags[i].hRate * ih;
                tags[i].l = tags[i].lRate * iw;
                tags[i].t = tags[i].tRate * ih;
                this.$set(this.currentImage.carModelPicMarkItemList, i, tags[i]);
            }

            const sTags = this.tagsObj.currentShowTags || [];
            for (let j = 0; j < sTags.length; j++) {
                sTags[j].w = sTags[j].wRate * iw;
                sTags[j].h = sTags[j].hRate * ih;
                sTags[j].l = sTags[j].lRate * iw;
                sTags[j].t = sTags[j].tRate * ih;
                this.$set(this.tagsObj.currentShowTags, j, sTags[j]);
            }
        },

        /**
         * 过滤要显示的tag
         * @param {Object} tag
         */
        filterShowTags(tag) {
            const nArr = [];
            const tags = this.currentImage.carModelPicMarkItemList || [];
            for (let i = 0; i < tags.length; i++) {
                if (tags[i].picSequence === tag.picSequence) {
                    nArr.push(tags[i]);
                }
            }
            return nArr;
        },

        // 显示tag
        showMapTag(tag) {
            this.tagsObj.currentShowTags = this.filterShowTags(tag);
            this.emitSelectTag(tag);
        },
    },
}
;
