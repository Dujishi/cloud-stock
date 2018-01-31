/**
 * Created by yinhuadong on 2017/11/27.
 */
function AddHtmlPlugin(options) {
}
AddHtmlPlugin.prototype.apply = function(compiler) {
    // ...
    compiler.plugin('compilation', function(compilation) {
        compilation.plugin('html-webpack-plugin-after-html-processing', function(htmlPluginData, callback) {
            htmlPluginData.html = '<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>' + htmlPluginData.html;
            callback(null, htmlPluginData);
        });
    });
};
module.exports = AddHtmlPlugin;
