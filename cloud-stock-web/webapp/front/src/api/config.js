import axios from 'axios';
import qs from 'qs';
import getUrl from '../utils/get-url';

/**
 * 序列化数组参数
 */
axios.defaults.paramsSerializer = (params) => {
    return qs.stringify(params, { arrayFormat: 'repeat' });
};

/**
 * 请求拦截器
 */
axios.interceptors.request.use((config) => {
    window.$vm.$store.commit('setLoadingVisible', true);
    return config;
}, (error) => {
    return Promise.reject(error);
});

/**
 * 响应拦截器
 */
axios.interceptors.response.use((response) => {
    if (response.data.errCode === 'USER_NO_LOGIN') {
        window.$vm.$router.replace({ path: '/?needLogin=true' });
    }
    window.$vm.$store.commit('setLoadingVisible', false);
    return response;
}, (error) => {
    return Promise.reject(error);
});

/**
 * get请求
 * @param {*} url 请求url
 * @param {*} params 请求参数
 */
export function get(url, params) {
    return axios.get(getUrl(url), { params }).then((response) => {
        return response.data;
    });
}

/**
 * post请求
 * @param {*} url 请求url
 * @param {*} params 请求参数
 */
export function post(url, params) {
    return axios.post(getUrl(url), params).then((response) => {
        return response.data;
    });
}
