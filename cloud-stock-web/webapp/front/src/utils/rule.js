export default {
    // 自定义校验规则
    checkAmount: (rule, value, callback) => {
        if (!/^\d+\.?\d{0,2}$/.test(value) && value !== '') {
            return callback(new Error('请输入正确的数量'));
        } else {
            callback();
        }
    },
    checkPhone: (rule, value, callback) => {
        if (/^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$/.test(value) ||
            (/^1(3|4|5|7|8)\d{9}$/.test(value))) {
            callback();
        } else {   
            return callback(new Error('请输入正确的电话号码'));
        }
    },
    checkPrice: (rule, value, callback) => {
        if (!/^\d+\.?\d{0,2}$/.test(value) && value !== '') {
            return callback(new Error('请输入正确的金额'));
        } else {
            callback();
        }
    },
};
