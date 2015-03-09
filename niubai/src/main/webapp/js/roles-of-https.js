function getRole(gameId, serverId, successFunc, errorFunc) {
	
	if(!window.$) {
		alert("plz load the jquery first");
		return;
	}
	$.ajax({ 
		"type":"get", 
		dataType:"jsonp",
        jsonp:"jsonpcallback",
		"url":"https://www.gamebox.com/webgame/role.jhtml", 
		"data":{"gameId":gameId, "serverId":serverId},
		"cache":false,
		"success": function(response) {
			try {
				var roles = new Array();
				var data = response.data;
				if (gameId == 1) {
					
				} else if (gameId == 2) {
					
				} else if (gameId == 3) {
					
				} else if (gameId == 4) {
					data = $.parseJSON(data);
					for (var i = 0; i < data.length; i++) {
						roles.push({'roleid':data[i].Guid, 'rolename':data[i].nickname});
					}
				} else if (gameId == 5) {
					var roleid = data.split('Item ID="')[1].split('"')[0];
					var rolename = data.split('NickName="')[1].split('"')[0];
					roles.push({'roleid':roleid, 'rolename':rolename});
				} else if (gameId == 6) {
					data = $.parseJSON(data);
					var roleid = data.name;
					var rolename = "tank";
					roles.push({'roleid':roleid, 'rolename':rolename});
				} else if (gameId == 7) {
					
				} else if (gameId == 8) {
					data = $.parseJSON(data);
					var roleid = data.name;
					var rolename = data.name;
					roles.push({'roleid':roleid, 'rolename':rolename});
				} else if (gameId == 9) {
					var roleid = data.split('</name>')[0].split('<name>')[1]
					var rolename = roleid;
					roles.push({'roleid':roleid, 'rolename':rolename});
				} else if (gameId == 10) {
					var roleid = data.split('\t')[0];
					var rolename = roleid;
					roles.push({'roleid':roleid, 'rolename':rolename});
				} else if (gameId == 11) {
					
				} else if (gameId == 12) {
					
				} else if (gameId == 13) {
					var roleid = $.parseJSON(data).name;
					var rolename = roleid;
					roles.push({'roleid':roleid, 'rolename':rolename});
				} else if (gameId == 14) {
					var roleid = $.parseJSON(data).name;
					var rolename = roleid;
					roles.push({'roleid':roleid, 'rolename':rolename});
				} else if (gameId == 15) {
					var roleid = $.parseJSON(data).name;
					var rolename = roleid;
					roles.push({'roleid':roleid, 'rolename':rolename});
				} else if (gameId == 16) {
					
				} else if (gameId == 17) {
					data = $.parseJSON(data).data;
					for (var i = 0; i < data.length; i++) {
						var roleid = data[i].cid;
						var rolename = data[i].rolename;
						roles.push({'roleid':roleid, 'rolename':rolename});
					}
				} else if (gameId == 18) {
					var roleid = data.split('"role_name":"')[1].split('",')[0];
					var rolename = roleid;
					roles.push({'roleid':roleid, 'rolename':rolename});
				} else if (gameId == 20) {
					data = $.parseJSON(data).list;
					for (var i = 0; i < data.length; i++) {
						var roleid = data[i].id;
						var rolename = data[i].name;
						roles.push({'roleid':roleid, 'rolename':rolename});
					}
				} else if (gameId == 21) {
					data = $.parseJSON(data);
					var roleid = data.name;
					var rolename = roleid;
					roles.push({'roleid':roleid, 'rolename':rolename});
				}
				
				if (roles.length == 0) {
					return;
				}
				if (!roles[0].roleid) {
					return;
				}
			} catch (e) {
				return;
			}
			
			successFunc(roles);
        },
        "error":function(jqXHR, textStatus, errorThrown) {
        	if(errorFunc)
        	errorFunc(jqXHR, textStatus, errorThrown);
        }
	});
}