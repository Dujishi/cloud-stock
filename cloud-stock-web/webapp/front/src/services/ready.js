let handlerTimeout = null;

let count = 0;
const checkJSBridge = (fn) => {
    if (window.JSBridge || count === 3) {
        if (handlerTimeout) {
            window.clearTimeout(handlerTimeout);
        }
        if (fn) {
            fn(window.JSBridge);
        }
        return;
    }
    count += 1;
    handlerTimeout = setTimeout(() => {
        checkJSBridge(fn);
    }, 50);
};

const ready = () => {
    return new Promise((resolve, reject) => {
        if (window.JSBridge) {
            resolve(window.JSBridge);
        } else {
            checkJSBridge((bridge) => {
                if (bridge) {
                    resolve(bridge);
                } else if (reject) {
                    reject();
                }
            });
        }
    });
}
;

export default ready;
