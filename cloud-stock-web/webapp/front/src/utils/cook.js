const cookie = {

    setCookie(c_name, value, expireDays) {
        let exdate = new Date();
        exdate.setDate(exdate.getDate() + expireDays);
        document.cookie = `${c_name}=${escape(value)};expires=${exdate.toGMTString()}`;
    },

    getCookie(name) {
        let arr;
        let reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");

        if (arr = document.cookie.match(reg)) {
            return (arr[2]);
        } else {
            return null;
        }
    },

    delCookie(name) {
        let exp = new Date();
        exp.setTime(exp.getTime() - 1);
        let cval = this.getCookie(name);
        if(cval != null) {
            document.cookie = name + " = " + cval + ";expires=" + exp.toGMTString();
        }
    },
};


export default cookie;
