export default {
    isPhone(val) {
        return /^1\d{10}$/.test(val);
    },
    isPassword(val) {
        return /^\S{6,}$/.test(val);
    },
};

