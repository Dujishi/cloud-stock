/**
 * Created by yinhuadong on 2017/2/4.
 */
import keys from 'lodash/keys';

export default {
    /**
     * 获取当前url上所有参数
     * @return {Object}
     */
    getParams() {
        const arr = window.location.search.substring(1).split('&');
        const params = {};
        for (let i = 0; i < arr.length; i++) {
            const strs = arr[i].split('=');
            params[strs[0]] = decodeURIComponent(strs[1]);
        }
        return params;
    },

    /**
     * 通过name获取链接参数中对应的值
     * @param {string} name
     * @return {*}
     */
    getParameterByName(name) {
        const params = this.getParams();
        return params[name] || null;
    },

    /**
     * 更新页面链接参数
     * @param url
     * @param param
     * @param paramVal
     * @return {string}
     */
    updateURLParameter(url, param, paramVal) {
        let newAdditionalURL = '';
        let tempArray = url.split('?');
        const baseURL = tempArray[0];
        const additionalURL = tempArray[1];
        let temp = '';
        if (additionalURL) {
            tempArray = additionalURL.split('&');
            for (let i = 0; i < tempArray.length; i++) {
                if (tempArray[i].split('=')[0] !== param) {
                    newAdditionalURL += temp + tempArray[i];
                    temp = '&';
                }
            }
        }
        return `${baseURL}?${newAdditionalURL}${temp}${param}=${paramVal}`;
    },

    serialize(obj) {
        const str = [];
        keys(obj).forEach(key => {
            str.push(`${encodeURIComponent(key)}=${encodeURIComponent(obj[key])}`);
        });
        return str.join('&');
    },

    /**
     * 跳转页面
     * @param {String} url
     */
    goToPage(url) {
        if (!url) return;
        window.location.href = `${window.globalConfig.ctx}${url}`;
    },
};
