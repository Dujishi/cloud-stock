<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%-- <%@ page import="com.xiaoka.ups.client.UpsClient"%> --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<jsp:useBean id="Jackson" class="com.xiaoka.freework.utils.json.Jackson" scope="page" />
<!DOCTYPE html>
<html>
<head>
    <title>典典云仓</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <meta http-equiv="x-dns-prefetch-control" content="on">
    <meta name="renderer" content="webkit" >
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <link rel="icon" href="//ndsm.ddyc.com/ndsm/favicon.ico" type="image/x-icon" />
    <link rel="dns-prefetch" href="//store.ddyc.com">
    <link rel="dns-prefetch" href="//ndsm.ddyc.com">

    <meta name="viewport" content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,initial-scale=1.0,width=device-width"/>

    <link href="//at.alicdn.com/t/font_470566_zb69jx67wtp4x6r.css" rel="stylesheet" type="text/css" >
    <link href="//localhost:9000/dist/commons.css" rel="stylesheet" type="text/css" >
    <link href="//localhost:9000/dist/assets/css/theme.css" rel="stylesheet" type="text/css" >

    <script>
        window.globalConfig = {
            permissions:{},
            ctx:'<%=request.getContextPath()%>',
            permissionArr:[],
            shopName: '',
            shopSystemVersion: '',
        };
    </script>

    <script src='//localhost:9000/dist/manifest.js'></script>
    <script src='//localhost:9000/dist/commons.js'></script>
</head>
<body>
<c:if test="bodyPage != null">
    <jsp:include page="${bodyPage}"></jsp:include>
</c:if>
<div class="g-progress-container hidden">
    <div class="progress" id="g_progress_outer" style="margin-bottom: 10px;">
        <div class="progress-bar progress-bar-success" id="g_progress_inner_bar" role="progressbar" style="width: 0%;"></div>
    </div>
    <p class="text-center">数据加载中...</p>
</div>
<script type='text/javascript'>
    //获取浏览器参数
    window.onload = function(){
        var _vds = _vds || [];
        window._vds = _vds;
        (function() {
            _vds.push(['setAccountId', 'e2f213a5f5164248817464925de8c1af']);
            (function() {
                var vds = document.createElement('script');
                vds.type = 'text/javascript';
                vds.async = true;
                vds.src = ('https:' == document.location.protocol ? 'https://' : 'http://') + 'dn-growing.qbox.me/vds.js';
                var s = document.getElementsByTagName('script')[0];
                s.parentNode.insertBefore(vds, s);
            })();
        })();
    };
</script>
</body>
</html>
