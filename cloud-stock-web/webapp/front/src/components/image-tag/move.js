export default {
    data() {
        return {
            // 移动对象
            moveObj: {
                left: 0,
                top: 0,
                sLeft: 0,
                sTop: 0,
                sx: 0,
                sy: 0,
                isStart: false,
            },
        };
    },
    methods: {
        /**
         * 还原初始化
         */
        initMove() {
            this.moveObj.left = 0;
            this.moveObj.top = 0;
            this.moveObj.isStart = false;
            this.moveObj.sLeft = 0;
            this.moveObj.sTop = 0;
        },

        /**
         * 点击开始
         * @param {Object} $event 原生事件对象
         */
        imageMoveStart($event) {
            this.moveObj.isStart = true;
            this.moveObj.sx = $event.clientX;
            this.moveObj.sy = $event.clientY;
            const thisDom = document.getElementById('image_container');
            if (thisDom.style.left || thisDom.style.top) {
                this.moveObj.sLeft = window.parseInt(thisDom.style.left.replace('px', ''));
                this.moveObj.sTop = window.parseInt(thisDom.style.top.replace('px', ''));
            }
        },
        mouseOut() {
            this.moveObj.isStart = false;
        },
        /**
         * 开始移动
         * @param {Object} $event 原生事件对象
         */
        imageMoving($event) {
            if (this.moveObj.isStart) {
                const offsetX = $event.clientX - this.moveObj.sx;
                const offsetY = $event.clientY - this.moveObj.sy;
                this.moveObj.left = this.moveObj.sLeft + offsetX;
                this.moveObj.top = this.moveObj.sTop + offsetY;
            }
        },
        /**
         * 结束移动
         */
        imageMoveEnd() {
            this.moveObj.isStart = false;
        },
    },
};
