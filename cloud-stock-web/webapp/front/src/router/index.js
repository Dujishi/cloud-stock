import Vue from 'vue';
import Router from 'vue-router';
import Index from '@/views/index/app.vue';
import Vin from '@/views/vin/app.vue';
import Category from '@/views/category/app.vue';
import SubCategory from '@/views/sub-category/app.vue';
import Part from '@/views/part/app.vue';
import PartList from '@/views/part-list/app.vue';
import Detail from '@/views/detail/app.vue';
import Stock from '@/views/stock/app.vue';
import VinPartList from '@/views/vin-part-list/app.vue';
import InitialImage from '@/components/initial-image/app.vue';
import Test from '@/views/test/app.vue';
import Picture from '@/views/picture/app.vue';
import Account from '@/views/account/app.vue';
import MyAccount from '@/views/my-account/app.vue';
import AccountAlliance from '@/views/account-alliance/app.vue';
import AccountManagement from '@/views/account-management/app.vue';
import Order from '@/views/order/app.vue';
import OrderList from '@/views/order/order-list/app.vue';
import OrderDetail from '@/views/order/order-detail/app.vue';
import Cargo from '@/views/cargo/app.vue';
import AccountBalance from '@/views/account-balance/app.vue';
import Model from '@/views/model/app.vue';
import Brand from '@/views/model/brand/app.vue';
import Series from '@/views/model/series/app.vue';
import ModelYear from '@/views/model/model-year/app.vue';
import ModelWrite from '@/views/model/model-write/app.vue';

// import intercept from './intercept';

Vue.use(Router);

const router = new Router({
    // mode: 'history',
    routes: [
        {
            path: '/',
            component: Index,
        }, {
            path: '/test',
            component: Test,
        }, {
            path: '/vin',
            component: Vin,
            children: [
                {
                    path: '',
                    component: InitialImage,
                }, {
                    path: 'category',
                    component: Category,
                }, {
                    path: 'sub-category',
                    component: SubCategory,
                }, {
                    path: 'vin-part-list',
                    component: VinPartList,
                }, {
                    path: 'detail',
                    component: Detail,
                }, {
                    path: 'stock',
                    component: Stock,
                }, {
                    path: 'picture',
                    component: Picture,
                },
            ],
        }, {
            path: '/part',
            component: Part,
            children: [
                {
                    path: '',
                    component: InitialImage,
                }, {
                    path: 'part-list',
                    component: PartList,
                }, {
                    path: 'detail',
                    component: Detail,
                }, {
                    path: 'stock',
                    component: Stock,
                }, {
                    path: 'picture',
                    component: Picture,
                },
            ],
        }, {
            path: '/account',
            component: Account,
            children: [
                {
                    path: '',
                    redirect: 'my',
                }, {
                    path: 'my',
                    component: MyAccount,
                }, {
                    path: 'management',
                    component: AccountManagement,
                }, {
                    path: 'alliance',
                    component: AccountAlliance,
                }, {
                    path: 'balance',
                    component: AccountBalance,
                },
            ],
        }, {
            path: '/order',
            component: Order,
            children: [
                {
                    path: '',
                    redirect: 'list',
                }, {
                    path: 'list',
                    component: OrderList,
                }, {
                    path: 'detail',
                    component: OrderDetail,
                },
            ],
        }, {
            path: '/cargo',
            component: Cargo,
        }, {
            path: '/model',
            component: Model,
            children: [
                {
                    path: '',
                    component: InitialImage,
                }, {
                    path: 'brand',
                    component: Brand,
                }, {
                    path: 'series',
                    component: Series,
                }, {
                    path: 'modelYear',
                    component: ModelYear,
                }, {
                    path: 'modelWriteResult',
                    component: ModelWrite,
                }, {
                    path: 'category',
                    component: Category,
                },
            ],
        },
    ],
});

// intercept(router);

export default router;
