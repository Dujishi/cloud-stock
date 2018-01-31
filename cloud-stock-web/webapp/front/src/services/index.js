import ready from './ready';


/**
 * 登录成功之后调用
 * @param {Object} params
 */
export const registerStrategy = (params) => {
    ready().then((bridge) => {
        bridge.call('permissionBridgeServiceImpl.registerStrategy', JSON.parse(params));
    }, () => {});
}
;

/**
 * 获取配件库存
 * @param {Object} params
 */
export const getPartStockByPartCode = (params) => {
    return new Promise((resolve) => {
        ready().then((bridge) => {
            if (typeof params === 'string') {
                params = params.split(',');
            }
            console.log('参数=>', JSON.stringify(params));
            const res = bridge.call('businessBridgeServiceImpl.getStorageByCodes', params);
            resolve(res);
        }, () => {});
    });
}
;
