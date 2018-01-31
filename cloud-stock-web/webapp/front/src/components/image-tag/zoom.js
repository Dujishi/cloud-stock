export default {
    data() {
        return {
            sHeight: 0,
            zoomObj: {
                // 每次缩放大小比例
                rateLarge: 1.1,
                rateNarrow: 0.9,

                // 实时宽度
                w: 0,
                h: 0,

                // 图片原始宽高
                rawPicWidth: 500,
                rawPicHeight: 500,

                // 初始化宽高
                iw: 0,
                ih: 0,
            },

            enlargeDisabled: false,
            narrowDisabled: false,

            loaded: false,
        };
    },

    mounted() {
        this.loadComputedStyle();
    },

    methods: {
        /**
         * 还原初始化
         */
        initZoom() {
            this.zoomObj.rawPicWidth = this.currentImage.rawPicWidth;
            this.zoomObj.rawPicHeight = this.currentImage.rawPicHeight;
            this.enlargeDisabled = false;
            this.narrowDisabled = false;
            window.setTimeout(() => {
                this.loadComputedStyle();
            }, 20);
            this.setSImageSize();
        },
        /**
         * 设置左侧图片高度
         */
        setSImageSize() {
            // window.setTimeout(() => {
            //     const dom = document.getElementById('image_tag_container');
            //     const { w } = this.getComputedStyle(dom);
            //     this.sHeight = (w * 0.18) - 36;
            // }, 200);
        },

        /**
         * 设置原始高度
         * @param {number} w
         * @param {number} h
         */
        setDefaultWH(w, h) {
            this.zoomObj.rawPicWidth = w;
            this.zoomObj.rawPicHeight = h;
        },

        /**
         * 获取宽高
         */
        getComputedStyle(dom) {
            const style = window.getComputedStyle(dom, null);
            return {
                w: style.getPropertyValue('width').replace('px', '') - 0,
                h: style.getPropertyValue('height').replace('px', '') - 0,
            };
        },

        /**
         * 图片大小初始化
         */
        loadComputedStyle() {
            const thisDom = document.getElementById('image_container');
            if (!thisDom) {
                return;
            }
            const img = thisDom.querySelector('img');
            const imgPath = img.getAttribute('src');
            const parent = thisDom.parentNode;
            const that = this;
            const imgObj = new Image();
            this.zoomObj.w = 0;
            this.zoomObj.h = 0;
            imgObj.onload = function () {
                let { w, h } = that.getComputedStyle(parent); // 外框高度
                if (!h) {
                    window.setTimeout(() => {
                        that.loadComputedStyle();
                    }, 20);
                    return;
                }
                w *= 1.4;
                h = (w * imgObj.height) / imgObj.width;
                that.setInitWH(w, h);
                that.updateTagSize({ iw: w, ih: h });

                that.setSImageSize();
            };
            imgObj.src = imgPath;
        },

        /**
         * 设置初始化
         * @param {number} w
         * @param {number} h
         */
        setInitWH(w, h) {
            this.zoomObj.iw = w;
            this.zoomObj.ih = h;
            this.zoomObj.w = w;
            this.zoomObj.h = h;
        },

        /**
         * @param {String} type 放大还是缩小 enlarge/narrow
         */
        zoom(type) {
            const thisDom = document.getElementById('image_container');
            const style = window.getComputedStyle(thisDom, null);
            let w = style.getPropertyValue('width');
            let h = style.getPropertyValue('height');

            if (w && h) {
                w = w.replace('px', '') - 0;
                h = h.replace('px', '') - 0;

                if (type === 'enlarge') {
                    this.narrowDisabled = false;
                    if (this.enlargeDisabled) {
                        return;
                    }

                    if (w * this.zoomObj.rateLarge >= this.zoomObj.rawPicWidth * 4) {
                        this.enlargeDisabled = true;

                        this.zoomObj.w = this.zoomObj.rawPicWidth * 4;
                        this.zoomObj.h = this.zoomObj.rawPicHeight * 4;
                    } else {
                        this.enlargeDisabled = false;

                        this.zoomObj.w = w * this.zoomObj.rateLarge;
                        this.zoomObj.h = h * this.zoomObj.rateLarge;
                    }
                } else if (w * this.zoomObj.rateNarrow <= this.zoomObj.iw / 2) {
                    if (this.narrowDisabled) {
                        return;
                    }

                    this.enlargeDisabled = false;
                    this.narrowDisabled = true;

                    this.zoomObj.w = this.zoomObj.iw / 2;
                    this.zoomObj.h = this.zoomObj.ih / 2;
                } else {
                    this.enlargeDisabled = false;
                    this.narrowDisabled = false;

                    this.zoomObj.w = w * this.zoomObj.rateNarrow;
                    this.zoomObj.h = h * this.zoomObj.rateNarrow;
                }

                this.updateTagSize({ iw: this.zoomObj.w, ih: this.zoomObj.h });
            }
        },
    },
}
;
