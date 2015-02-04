<%@ page language="java"  pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<title>mz</title>
<meta content="text/html; charset=utf-8" http-equiv=Content-Type/>
<meta name=GENERATOR content="MSHTML 9.00.8112.16448">
<style type=text/css>

* {margin:0; padding:0;}
html, body  { height:100%; }
body { margin:0; padding:0; overflow:auto; text-align:center;background-color: #000000; }
object:focus { outline:none; }
#flashContent { display:none; }
#fb_box{position: absolute;z-index: 9999;top: 90px;left: 350px;width: 100px; height: 41px; overflow: hidden;}

</style>
<script type="text/javascript" src="/js/jquery-1.11.1.min.js" ></script>
</head>
<body>
<div id="fb_box" >
</div>
<iframe width="100%" scrolling="no" height="100%" frameborder="0" marginheight="0" marginwidth="0" name="frm_main" id="frm_main" src="${href}"></iframe>
<script>
$(function() {
	$.getJSON("/games/login",{"gameId":"21","serverId":"${serverId}"}, function (data) {
		$('#frm_main').attr("src",data.url);
	});
})
</script>
</body>
</html>
