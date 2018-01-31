export default {
    methods: {
        getAssemblyList(data) {
            let list = [];
            data.forEach((v) => {
                if (v.subAssemblyList && v.subAssemblyList.length) {
                    list = list.concat(v.subAssemblyList);
                }
            });
            data.unshift({
                assemblyName: '全部',
                subAssemblyList: list,
            });
            return data;
        },
    },
};
