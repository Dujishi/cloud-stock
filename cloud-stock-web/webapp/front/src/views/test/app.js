// 图片组件
import VImageTag from '@/components/image-tag/app.vue';

export default {
    components: {
        VImageTag,
    },
    data() {
        return {
            // 图片列表
            images: [{
                picLocalPath: 'http://bpic.588ku.com/element_origin_min_pic/17/11/01/f284dc08d152079ee3add93d0abe4dcf.jpg',
                // 图片原始宽高
                rawPicWidth: 500,
                rawPicHeight: 500,
                carModelPicMarkItemList: [{
                    // 标签宽高
                    recWidth: 25,
                    recHeight: 25,

                    // 标记时图片宽高
                    markedPicWidth: 500,
                    markedPicHeight: 500,

                    // 图片相对距离
                    xAxis: 33.5,
                    yAxis: 44.5,

                    picSequence: '1',
                }, {
                    // 标签宽高
                    recWidth: 25,
                    recHeight: 25,

                    // 标记时图片宽高
                    markedPicWidth: 500,
                    markedPicHeight: 500,

                    // 图片相对距离
                    xAxis: 120.5,
                    yAxis: 150.5,

                    picSequence: '2',
                }, {
                    // 标签宽高
                    recWidth: 25,
                    recHeight: 25,

                    // 标记时图片宽高
                    markedPicWidth: 500,
                    markedPicHeight: 500,

                    // 图片相对距离
                    xAxis: 90.5,
                    yAxis: 85.5,

                    picSequence: '1',
                }],
            }, {
                picLocalPath: 'https://store.ddyc.com/commodity/stuff/thumb/2017/10/26/2017102607010188vg0s9qsql4o5vj.png',
                // 图片原始宽高
                rawPicWidth: 800,
                rawPicHeight: 800,

                tags: [{
                    // 标签宽高
                    recWidth: 25,
                    recHeight: 25,

                    // 标记时图片宽高
                    markedPicWidth: 500,
                    markedPicHeight: 500,

                    // 图片相对距离
                    xAxis: 33.5,
                    yAxis: 44.5,

                    picSequence: '1',
                }],
            }],

            // 初始化标记
            currentShowTag: null,
        };
    },
    mounted() {
        // 测试默认两秒钟自动出现
        window.setTimeout(() => {
            this.currentShowTag = this.images[0].tags[0];
        }, 2000);
    },
}
;
