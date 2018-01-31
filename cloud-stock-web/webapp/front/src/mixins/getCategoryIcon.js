export default {
    methods: {
        getCategoryIcon(type, id) {
            const zhengshiCategoryMap = {
                动力总成及组件: {
                    icon: 'icon-dongli',
                    class: 'dongli',
                },
                变速机构: {
                    icon: 'icon-biansujigou',
                    class: 'biansu',
                },
                其它: {
                    icon: 'icon-qita',
                    class: 'qita',
                },
                底盘: {
                    icon: 'icon-dipan',
                    class: 'dipan',
                },
                车身内部结构: {
                    icon: 'icon-cheshennei',
                    class: 'neibu',
                },
                车身外部结构: {
                    icon: 'icon-cheshenwai',
                    class: 'waibu',
                },
                服务信息: {
                    icon: 'icon-fuwuxinxi',
                    class: 'waibu',
                },
                电器: {
                    icon: 'icon-dianqi',
                    class: 'dianqi',
                },
            };

            const originalCategoryMap = {
                icon: 'icon-yuanchangzhibao',
                class: 'yuanchang',
            };

            return type === 1 ? zhengshiCategoryMap[id] : originalCategoryMap;
        },
    },
};
