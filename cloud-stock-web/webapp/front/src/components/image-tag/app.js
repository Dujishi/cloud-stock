import moveMixins from './move';
import tagsMixins from './tags';
import zoomMixins from './zoom';
import emitMixins from './emit';

export default {
    name: 'imageTag',

    mixins: [moveMixins, tagsMixins, zoomMixins, emitMixins],

    props: {
        // 图片列表
        images: {
            type: Array,
            required: true,
        },

        // 当前图片需要高亮的tag
        currentShowTag: {
            type: Object,
        },

        // 初始化图片索引
        initImageIndex: {
            type: Number,
        },

        // 当前展示的图片标记
        markImage: {
            type: Object,
        },
    },

    data() {
        return {
            // 当前图片索引
            currentIndex: this.initImageIndex || 0,

            // 当前图片信息
            currentImage: {
                picLocalPath: '',
                carModelPicMarkItemList: [],
            },

            prevHover: false,
            nextHover: false,

            prevIcon: 'icon-zuofanye',
            nextIcon: 'icon-youfanye',

            prevDisabled: false,
            nextDisabled: false,
        };
    },

    mounted() {
        this.resetData(this.currentIndex, this.images);
        this.$watch('images', (newValue) => {
            this.resetData(this.currentIndex, newValue);
        });

        this.$watch('initImageIndex', (newValue) => {
            this.currentIndex = newValue;
            this.resetData(this.currentIndex, this.images);
        });

        this.$watch('markImage', (newValue) => {
            if (newValue) {
                this.currentImage.carModelPicMarkItemList = newValue.carModelPicMarkItemList;
                this.currentImage.picLocalPath = newValue.picLocalPath;
            } else {
                this.currentImage.carModelPicMarkItemList = [];
                this.getImageOrgWH(this.currentImage.picLocalPath);
            }
            this.initTags();
            this.initZoom();
            this.updateTags();
        });
    },

    methods: {
        prevMouse(type) {
            if (this.prevDisabled) {
                return;
            }
            if (type === 'enter') {
                this.prevIcon = 'icon-zuofanyehover';
            } else {
                this.prevIcon = 'icon-zuofanye';
            }
        },
        nextMouse(type) {
            if (this.nextDisabled) {
                return;
            }
            if (type === 'enter') {
                this.nextIcon = 'icon-youfanyehover';
            } else {
                this.nextIcon = 'icon-youfanye';
            }
        },
        /**
         * 获取图片的原始尺寸
         */
        getImageOrgWH(path) {
            const img = new Image();
            img.onload = () => {
                this.setDefaultWH(img.width, img.height);
            };
            img.src = path;
        },

        /**
         * 更新
         */
        updateFyIcon() {
            if (this.currentIndex === 0) {
                this.prevDisabled = true;
                this.nextDisabled = false;
                this.prevIcon = 'icon-zuofanye';
                return;
            }
            if (this.currentIndex === this.images.length - 1) {
                this.prevDisabled = false;
                this.nextDisabled = true;
                this.nextIcon = 'icon-youfanye';
                return;
            }
            this.nextDisabled = false;
            this.prevDisabled = false;
        },

        // 显示
        showImage(index) {
            if (index === this.currentIndex) {
                return;
            }
            this.resetData(index, this.images);


            // 触发更换图片事件
            this.emitChangeImage(this.images[index]);
        },

        /**
         * 重试数据
         */
        resetData(index, images) {
            if (!images || images.length === 0) {
                return;
            }
            this.currentIndex = index;
            // this.currentImage = images[this.currentIndex];
            this.updateFyIcon();
            // this.resetTagData();
        },

        resetTagData() {
            this.initTags();
            this.initZoom();
            this.initMove();
            this.updateTags();
        },

        // 上一张
        next() {
            if (this.currentIndex < this.images.length - 1) {
                this.showImage(this.currentIndex + 1);
            }
        },

        // 下一张
        prev() {
            if (this.currentIndex > 0) {
                this.showImage(this.currentIndex - 1);
            }
        },

    },
}
;
