import VModelSidebar from '@/components/model-sidebar/app.vue';

export default {
    components: {
        VModelSidebar,
    },
    data() {
        return {
            headerNavVisble: false,
            navMenu: [{ menuName: '品牌筛选', menuLevel: 1 }],
        };
    },
    mounted() {
        this.init();
    },
    watch: {
        '$route'() {
            this.init();
        },
    },
    methods: {
        init() {
            const query = this.$route.query;
            const brandName = query.brandName;
            const seriesName = query.series;
            this.navMenu.splice(1);
        
            this.headerNavVisble = this.$route.path !== '/model' ? true : false;
            if (brandName) {
                this.navMenu.push({menuName:brandName, menuLevel:2});
            }
            if (seriesName) {
                this.navMenu.push({menuName:seriesName,menuLevel:3});
            }
            
        },
        goBack() {
            this.$router.go(-1);
        },
        // 跳页
        changeRoute(item) {
            const query = this.$route.query;
            const brandName = query.brandName;
            const seriesName = query.series;
            const brandId = query.brandName;
            const firstLetter = query.firstLetter;
            const step = this.navMenu.length - item.menuLevel;
            this.$router.go(-step);
        },
    },
};
