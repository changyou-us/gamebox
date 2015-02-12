<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<title></title>
	<style type="text/css">
	
	body { min-width:1000px; min-height:1000px; width:100%; height:100%; background: #000 url(http://dzkruhbej0os7.cloudfront.net/hz/hz_fb1.jpg) no-repeat center top; font-family:'Open Sans';}
	
	</style>
    <link type="text/css" rel="stylesheet" href="/css/base.css"  />
	<link type="text/css" rel="stylesheet" href="/css/tt.css"  />
	<script type="text/javascript" src="/js/jquery-1.11.1.min.js" charset="utf-8"></script>
</head>
<body>
	<div class="container">
        <!--<div class="likeBtn" style="position: absolute;  top: 100px; right: 0px;">
            <iframe scrolling="no" frameborder="0" src="//www.facebook.com/plugins/like.php?href=https%3A%2F%2Fwww.facebook.com%2Finfernolegendofficial+&amp;width&amp;layout=button_count&amp;action=like&amp;show_faces=false&amp;share=false&amp;height=21" style="border:none; overflow:hidden; width: 120px; height:21px;" allowtransparency="true"></iframe>
        </div>-->
		<div class="nav">			
			<!--<a href="" class="fanpage">fanpage</a>
			<a href="" class="recharge">RECHARGE</a>-->
		</div>
		<!-- nav end -->
		<div class="main tabContents_1">
			<div class="tabsBox">
				<div class="tabs_text">
					<ul style="display:block" class="all_server">
						<c:forEach items="${serverList}" var="server">
						<li><a href="/games/playpage?gameId=${gameId}&serverId=${server.serverId}">${server.name}</a></li>
						</c:forEach>
					</ul>
				</div>
				<!-- tabsBox end -->
			</div>
		</div>
		<!-- main end -->
		<div class="download_box tabContents_1" style="display:none">
			<ul>
				<li>
					<a href="javascript:(alert('coming soon...'))">
						DOWNLOAD
						<br> <i>micro-clien</i>
					</a>
				</li>
				<li>
					<a href="javascript:(alert('coming soon...'))">
						DOWNLOAD
						<br> <i>APP</i>
					</a>
				</li>
				<li class="or_code">
                    <br /><br />
					QR CODE<br/> is coming soon
				</li>
			</ul>
		</div>		
	</div>
    <script type="text/javascript">
        $(function() {

            $(".tabsBox .tabs_title").children("li").each(function(index, element) {
                $(this).click(function(){
                    $(this).addClass("cur").siblings("li").removeClass("cur");
                    $(".tabs_text").children("ul").eq(index).siblings("ul").hide().end().show();
                });
            });

            $(".nav .tabBtns_1").each(function(index){
                $(this).click(function(){
                    $(this).siblings(".tabBtns_1").removeClass("cur").end().addClass("cur");
                    $(".container .tabContents_1").eq(index).show().siblings(".tabContents_1").hide();
                });
            })
        })
    </script>
<%@ include file="../google_tag_manager.jsp" %> 
</body>
</html>