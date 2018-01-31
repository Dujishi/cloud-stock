<template>
    <div class="image-tag clearfix" id="image_tag_container">
        <div class="image-tag__list" v-if="images && images.length > 1">
            <ul id="image_tag_simgs_wrap">
                <li :style="{height: sHeight ? sHeight+'px' : 'auto', lineHeight: sHeight ? sHeight+'px' : 'normal'}" v-for="(image, index) in images" :key="index" :class="{curr: currentIndex == index}" @click="showImage(index)"><img v-if="image" :src="image.picLocalPath"/></li>
            </ul>
        </div>
        <div class="image-tag__container" :class="{full: (images && images.length <=1)}">
            <div class="image-tag__preview">
                <div class="image-tag__zoom">
                    <a href="javascript:;" :class="{disabled: enlargeDisabled}" @click="zoom('enlarge')"><i class="iconfont icon-fangda"></i></a>
                    <a href="javascript:;" :class="{disabled: narrowDisabled}" @click="zoom('narrow')"><i class="iconfont icon-reduce"></i></a>
                </div>
                <a href="javascript:;" v-if="images.length > 1" class="image-tag__fy prev" :class="{disabled: prevDisabled}" @click="prev" @mouseenter="prevMouse('enter')" @mouseleave="prevMouse('leave')"><i class="iconfont" :class="prevIcon? prevIcon: ''"></i></a>
                <a href="javascript:;" v-if="images.length > 1" class="image-tag__fy next" :class="{disabled: nextDisabled}" @click="next" @mouseenter="nextMouse('enter')" @mouseleave="nextMouse('leave')"><i class="iconfont" :class="nextIcon? nextIcon: ''"></i></a>
                <div class="image-container" id="image_container" :style="{left: moveObj.left+'px', top: moveObj.top+'px', width: zoomObj.w ? zoomObj.w + 'px': 'auto', height: zoomObj.h ? zoomObj.h + 'px': 'auto'}" v-on="{mousedown: imageMoveStart, mousemove: imageMoving, mouseup: imageMoveEnd, mouseout: mouseOut}">
                    <img draggable="false" :src="currentImage.picLocalPath" ismap="ismap" usemap="#planetmap"/>

                    <span class="image-tag__tag" v-for="(tag, index) in tagsObj.currentShowTags" :key="index" :style="{left: tag.l+'px', top: tag.t+'px', width: tag.w+'px', height: tag.h+'px'}"></span>
                </div>
                <map name="planetmap" id="planetmap">
                    <area shape="rect" v-for="(area, index) in currentImage.carModelPicMarkItemList" :key="index" @click="showMapTag(area)" :coords="area.l+','+area.t+','+(area.w + area.l)+','+(area.t + area.h)"/>
                </map>
            </div>

        </div>
    </div>
</template>

<script>
    import AppMod from './app';

    export default AppMod;
</script>

<style lang="less">
    @import './app.less';
</style>

