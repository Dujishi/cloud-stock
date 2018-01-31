# 典典云仓


## 构建命令

``` bash
# install dependencies
npm install

# serve with hot reload at localhost:8080
npm run dev

# build for production with minification
npm run build

# build for production and view the bundle analyzer report
npm run build --report
```

## 兼容性

由于vuejs和ElementUI的1.x版本都兼容IE9+的现代浏览器，所以本项目也要求兼容IE9+

注意：flex布局、ElementUI2.0、vue-router的history模式都只支持IE10+，如果情况允许，后期还是建议最低兼容到IE10就可以了

## 编码规范

### CSS

1. 公共样式统一采用`v-`前缀，防止冲突，比如`v-sidebar`
2、给页面外层定一个唯一的class，页面级样式统一放在这个class里面，推荐使用`-page`结尾，比如`my-account-page`
3. 推荐使用BEM命名方式

### JS

1. 使用ESLint做代码检测，采用airbnb风格，四个空格缩进
2. 引入组件的时候，统一加上前缀`V`，比如`VSidebar`

