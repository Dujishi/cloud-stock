import { hasLogin } from '@/api/index';

export default function (router) {
    router.beforeEach((to, from, next) => {
        if (to.path === '/') {
            next();
        } else {
            hasLogin().then((res) => {
                if (res.success) {
                    window.$vm.$store.commit('setIsLogin', true);
                    next();
                } else {
                    window.$vm.$store.commit('setIsLogin', false);
                    next('/?needLogin=true');
                }
            });
        }
    });
}

