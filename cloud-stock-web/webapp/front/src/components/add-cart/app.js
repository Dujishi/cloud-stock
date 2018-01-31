import { addIndent, getIndentList, addIndentPart } from '@/api/index';

export default {
    props: ['part'],
    data() {
        return {
            isShow: true,
            indentList: [],
            selectItem: {},
        };
    },
    watch: {
        isShow(val) {
            if (!val) {
                this.$emit('close');
            }
        },
    },
    mounted() {
        this.getIndentList();
        console.log(this.part)
    },
    methods: {
        // 获取配货单列表
        getIndentList() {
            getIndentList().then(res => {
                if (res.success) {
                    this.indentList = res.data;
                } else {
                    this.$message.error(res.message);
                }
            });
        },
        // 增加配货单标题
        addIndent() {
            addIndent().then(res => {
                if (res.success) {
                    this.indentList.push(res.data);
                } else {
                    this.$message.error(res.message);
                }
            });
        },
        // 添加配件到配货单
        addIndentPart() {
            if (!this.selectItem.indentNo) {
                this.$message.error('请选择配货单');
                return;
            }
            const indentNo = this.selectItem.indentNo;
            const oeNo = this.part.partCode;
            const partName = this.part.partName || '';
            const partDepot = this.part.partDepot || '';
            const originPlace = this.part.produceArea || '';
            const unitPrice = this.part.repairPrice || '';
            const manufacturer = localStorage.getItem('supplierName') || '';
            addIndentPart({ indentNo, oeNo, partName, partDepot, originPlace, unitPrice, manufacturer }).then(res => {
                if (res.success) {
                    this.$message.success('添加成功');
                    this.$emit('close');
                } else {
                    this.$message.error(res.message);
                }
            });
        },
        selectIndent(item) {
            this.selectItem = item;
            this.addIndentPart();
        },
        cancel() {
            this.$emit('close');
        },
    },
};
