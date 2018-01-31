import selfRules from '../../utils/rule';

export const submitInfoRules = {
    customerName: [
        {
            required: true,
            message: '请输入公司名称',
            trigger: 'blur',
        },
    ],
    contact: [
        {
            required: true,
            message: '请输入联系人',
            trigger: 'blur',
        },
    ],
    contactPhone: [
        {
            validator: selfRules.checkPhone,
            required: true,
        },
    ],
    vin: [
        {
            required: true,
            message: '请输入VIN码',
        }, {
            min: 17,
            max: 17,
            message: '长度应该在17个字符',
        },
    ],
    carModel: [
        {
            required: true,
            message: '请输入车型',
        },
    ],
    address: [
        {
            required: true,
            message: '请输入地址信息',
        },
    ],
};

export const saveRules = {
    customerName: [
        {
            required: true,
            message: '请输入公司名称',
            trigger: 'blur',
        },
    ],
    vin: [
        {
            min: 17,
            max: 17,
            message: '长度应该在17个字符',
        },
    ],
};

export const partRules = {
    oeNo: [
        {
            required: true,
            message: '请输入零件/OE码',
            trigger: 'blur',
        },
    ],
    partName: [
        {
            required: true,
            message: '请输入零件名称',
            trigger: 'blur',
        },
    ],
    unitPrice: [
        {
            validator: selfRules.checkPrice,
        },
    ],
    amount: [
        {
            validator: selfRules.checkAmount,
        },
    ],
};

export const bankInfoRules = {
    bankNo: [
        {
            required: true,
            message: '请输入银行卡号',
            trigger: 'blur',
        },
    ],
    bankName: [
        {
            required: true,
            message: '请选择开户银行',
            trigger: 'blur',
        },
    ],
    bankCity: [
        {
            required: true,
            message: '请选择开户城市',
            trigger: 'blur',
        },
    ],
    bankBranch: [
        {
            required: true,
            message: '请输入开户支行',
            trigger: 'blur',
        },
    ],
    bankUser: [
        {
            required: true,
            message: '请输入开户姓名',
            trigger: 'blur',
        },
    ],
    phone: [
        {
            validator: selfRules.checkPhone,
            required: true,
        },
    ],
    bankAccType: [
        {
            required: true,
            message: '请选择账户类型',
            trigger: 'blur',
        },
    ],
    smsCode: [
        {
            required: true,
            message: '请输入验证码',
            trigger: 'blur',
        },
    ],
};
