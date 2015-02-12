<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>fb_credits - Powered By GAMEBOX</title>
<meta name="author" content="GAMEBOX Team" />
<meta name="copyright" content="GAMEBOX" />
<link href="https://www.gamebox.com/resources/webgame/css/games/base.css" rel="stylesheet" type="text/css" />
<link href="https://www.gamebox.com/resources/webgame/css/games/style.css" rel="stylesheet" type="text/css" />
<script src="https://connect.facebook.net/en_US/all.js"></script>
<script type="text/javascript" src="/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="/js/roles-of-https.js"></script>
<script src="https://connect.facebook.net/en_US/all.js"></script>

<script type="text/javascript">

FB.init({appId: "${appId}", status: true, cookie: true});

function placeOrder(amount, currency) {
	var gameId = "${gameId}";
	var serverId = $('#GameServer').val();
	var rolesOption = $("#roles").find("option:selected");
	var roleId = encodeURIComponent(rolesOption.val());
	var roleName = encodeURIComponent(rolesOption.text());
    	
	if ($('#roles').val() == "-1") {
		alert("please select a server and choose your rolename :)");
		return;
	}
	$.getJSON("/facebook/issueOrder.jhtml",{'gameId':gameId, 'serverId':serverId, 'amount':amount, 'currency':currency, 'roleId':roleId, 'roleName':roleName}, function (response) {
		var ordersn = response.ordersn;
		var paramObj = {
			method: 'pay',
			action: 'purchaseitem',
			product: 'https://facebook.gamebox.com/fbcoins.html',
			quantity: 1,
			request_id: ordersn
		};
		
		var callback = function(response) {
			console.log(response);
		};
		FB.ui(paramObj,callback);
    });
    	
}
</script>
</head>
<body>
<div class="container">
	<a href="javascript:void(0);" class="oqlogo" onclick="return false;"><img src="/images/${identifier}.jpg" width="69" height="58" alt="gamebox_fb_credits" /></a>
    <div class="select_server">
    	<label class="lab">Game Server :</label>
        <select class="sel" id="GameServer" name="GameServer">
            <option selected="selected" value="-1">-Select-</option>
            <c:forEach items="${serverList}" var="server">
            <option value="${server.serverId}">S${server.serverId}-${server.name}</option>
            </c:forEach>
        </select>
    </div>
    <div class="select_char">
    	<label class="lab">Character Name :</label>
        <select class="sel" id="roles" name="roles">
           <option value="-1">---</option>
        </select>
    </div>
    <div class="buy_list">
    	<ul>
    		<c:forEach items="${priceList}" var="price">
            <li class="li_1" style="position:relative;">
                <a class="col_1" href="#" onclick="placeOrder('${price.amount}', '${price.currency}'); return false;">
                    <div class="price"><c:choose><c:when test="${price.currency eq 'USD'}">$</c:when><c:otherwise>B</c:otherwise></c:choose>${price.amount}</div>
                    <div class="icon_f"><img src="/resources/webgame/images/facebook/f2.png" width="35" height="15" /></div>
                    <div class="gold"><c:choose><c:when test="${price.coinBonus == 0}"><span class="span_1">${price.gameCoin}</span></c:when><c:otherwise><span class="span_5">${price.gameCoin}+${price.coinBonus}</span></c:otherwise></c:choose><span class="span_2">Gold</span></div>
                    <div class="descript"></div>
                    <div class="buy_btn">BUY</div>
                </a>
            </li>
            </c:forEach>
    		
        </ul>
    </div>
    
<input id="roleId" type="hidden" value="" />
<input id="roleName" type="hidden" value="" />
<script>
$(function() {
	$('#GameServer').change(function() {
		var rolesSelect = $("#roles");
		var serverId = $(this).val();
		if (serverId == -1) {
			rolesSelect.empty();
			rolesSelect.append('<option value="-1">' + '---' + '</option>');
			return;
		}
		var gameId = '${gameId}';
		
		rolesSelect.empty();
		$('#roleId').val("");
		$('#roleName').val("");
		rolesSelect.append('<option value="-1">---</option>');
		getRole(gameId, serverId, function (roles) {
			rolesSelect.empty();
			for(var i = 0; i < roles.length; i++) {
				rolesSelect.append('<option value="' + roles[i].roleid + '">' + roles[i].rolename + '</option>');
			}
		}, function() {})
	});
});
</script>
<%@ include file="../google_tag_manager.jsp" %> 
</body>
</html>
