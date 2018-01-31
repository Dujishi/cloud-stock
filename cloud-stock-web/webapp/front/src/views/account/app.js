import { mapActions } from 'vuex';
import VHeader from '@/components/header/app.vue';
import VAccountSidebar from '@/components/account-sidebar/app.vue';

export default {
    created() {
        this.getMyUserInfoAction();
    },
    methods: {
        ...mapActions([
            'getMyUserInfoAction',
        ]),
    },
    components: {
        VHeader,
        VAccountSidebar,
    },
};
