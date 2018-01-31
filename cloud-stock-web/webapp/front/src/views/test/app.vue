<template>
    <div class="test-image-tag">
        <v-image-tag :images="images" :current-show-tag="currentShowTag"></v-image-tag>
    </div>
</template>

<script>
    import AppMod from './app';

    export default AppMod;
</script>

<style lang="less">
    .test-image-tag{
        height: 500px;
    }
</style>

