var wechatUnionid; //用户全网唯一id

var shopId; //商户id

var wechatAppid; //微信公众号标识

var wechatOpenId; //微信用户标识

var wechatName; //微信名称

var wechatImg; //微信头像

var guizRecordPhone; //竞猜记录手机号

var guizRecordTicket = ""; //活动券id

var getVerificationCode = ""; //验证码

var activityId;

//= "ff808181560c395e01560c3aeb3b0001";
//var openId = "vvvdg4";
//var appId = "r77rrggg61121";

$(document).ready(function() {

	var str = request.QueryString("shopid"); //商户id
	if(str.length > 0) {

		var strArry = str.split('-');
		shopId = strArry[0];

		if(strArry.length > 1) {

			//微信公众号id
			wechatAppid = strArry[1];
		};

		if(strArry.length > 2) {

			//大转盘活动ID
			activityId = strArry[2];
		};
	};
	wechatCode = request.QueryString('code');
	var openid = ""
	if(location.hash) {

		openid = location.hash.replace("#", "");
	};

	//功能：获取微信用户信息
	GetWechatUserInfo(wechatAppid, wechatCode, openid, callback_wechatUserInfo);

	$("#inner").click(function() {

		controlBigTurntable(activityId, wechatOpenId, wechatAppid);

	});

	//获取验证码点击事件
	$(".getVerificationNum").click(function() {

		if(valityPhone()) {

			console.log("获取验证码点击事件手机号：" + $(".phoneNum").val());
			if($(".getVerificationNum").html() == "获取验证码") {

				$(".getVerificationNum").attr("disabled", "disabled");
				$(".getVerificationNum").css("background-color", "#ccc");
				var num = 60;
				var timer = setInterval(function() {

					$('.getVerificationNum').text("重新发送(" + num + "s)");
					if(num >= 1) {
						num--;
					} else {
						//停止setInterval循环
						clearInterval(timer);
						$(".getVerificationNum").removeAttr("disabled");
						$(".getVerificationNum").css("background-color", "#ffd324");
						$(".getVerificationNum").html("获取验证码");

					}
				}, 1000);
				//(注册)发送验证码
				sendVerCodeForReg($(".phoneNum").val());
			};

		};

	});

	var getNum = "";
	$("#popShow").find("button:eq(0)").click(function() { //领取按钮

		getNum = $(this).val();

		if(getNum == "1") {

			$("#popShow").hide();

			//如果没获取到手机号，要求验证，验证弹窗显示
			if(guizRecordPhone == undefined || guizRecordPhone == null || guizRecordPhone == "") {

				$("#verification_pop").show(); //验证弹窗

				//完成验证 按钮点击事件
				$(".verificationEnd").click(function() {

					if(valityYanZ()) { //如果验证码验证通过

						guizRecordPhone = $(".phoneNum").val();
						console.log("完成验证按钮点击事件手机号：" + guizRecordPhone);

						$("#overlay").hide(); //遮罩隐藏
						$("#verification_pop").hide(); //验证弹窗

						//调用卡券
						InsertOrderInfo(shopId, guizRecordTicket, guizRecordPhone);
					};

				});

			} else {

				$("#overlay").hide(); //遮罩隐藏
				$("#verification_pop").hide(); //验证弹窗

				//调用卡券
				InsertOrderInfo(shopId, guizRecordTicket, guizRecordPhone);

			};

		};
	});
	$("#popShow").find("button:eq(1)").click(function() {

		$("#overlay").hide();
		$("#popShow").hide();
	});

	//关闭验证弹窗
	$(".closeVerificationPop").click(function() {
		$("#overlay").hide(); //遮罩隐藏
		$("#verification_pop").hide();
	});
	//放弃
	$(".noTicket").click(function() {
		$("#popShow").hide();
	});
	$("#share").click(function() {
		$("#share").hide();
	});
	//分享
	$(".shareBtn").click(function() {
		$("#share").show();
	});
});

/*
 * 功能：获取微信用户信息回调函数
 * 创建人：liql
 * 创建时间：2016-5-11
 */
function callback_wechatUserInfo(data) {
	console.log(data);
	if(data != null && data != undefined && data.rspCode != "000") {
		comm.alert("获取微信信息失败" + data.rspDesc != '' ? data.rspDesc : '');
	} else {
		wechatOpenId = data.userinfo.openid; //微信用户id
		location.hash = data.userinfo.openid;
		wechatName = data.userinfo.nickname; //微信昵称
		wechatImg = data.userinfo.headimgurl; //微信头像
		wechatUnionid = data.userinfo.unionid; //全网唯一id

	};

	//注册微信调用接口列表
	Get_WinXinCfg(wechatAppid);

};
//注册微信调用接口列表
function Get_WinXinCfg(Appid) { //添加json数据 
	var myjsonStr = {};
	var configUrl = wechatUrl + "/getSignPackage?appid=" + Appid
	jQuery.axjsonp(configUrl, myjsonStr, successGetWinxinCfg_jsonpCallback);
};
//获取微信配置成功后的回调函数
function successGetWinxinCfg_jsonpCallback(cfg) {
	shareLink = shareLink.replace('appid=$', 'appid=' + wechatAppid);
	shareLink = shareLink.replace('page.html', 'bigTurntablePhone.html');
	shareLink = shareLink.replace('shopid=$', 'shopid=' + request.QueryString("shopid"));
	title = '七夕幸运转盘转！转！转！转出豪华情侣餐！';
	//调用weixin.js中的初始配置函数
	var config = {
		debug: false,
		appId: cfg.appId, // 必填，公众号的唯一标识
		timestamp: cfg.timestamp, // 必填，生成签名的时间戳
		nonceStr: cfg.nonceStr, // 必填，生成签名的随机串
		signature: cfg.signature, // 必填，签名，见附录1
		jsApiList: ["onMenuShareTimeline", "onMenuShareAppMessage", "hideMenuItems"] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	};
	wx.config(config);
	wx.ready(function() {
		wx.hideMenuItems({
			menuList: ["menuItem:share:qq", "menuItem:share:weiboApp", "menuItem:share:QZone", "menuItem:openWithSafari"] // 要隐藏的菜单项，只能隐藏“传播类”和“保护类”按钮，所有menu项见附录3
		});
		wx.onMenuShareTimeline({
			debug: false,
			title: title,
			desc: desc,
			link: shareLink,
			imgUrl: shareImgUrl,
			success: function() {
				// 用户确认分享后执行的回调函数
				$("#share").hide();
				comm.alert('分享成功');
			},
			cancel: function() {
				// 用户取消分享后执行的回调函数
			}
		});
		wx.onMenuShareAppMessage({
			debug: false,
			title: title,
			desc: desc,
			link: shareLink,
			imgUrl: shareImgUrl,
			success: function() {
				// 用户确认分享后执行的回调函数
				$("#share").hide();
				comm.alert('分享成功');
			},
			cancel: function() {
				// 用户取消分享后执行的回调函数
			}
		});

		wx.error(function(res) {
			// config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
			comm.alert("errorMSG:" + res);
		});
	});

	//根据wechatOpenId获取手机号
	getUserPhoneNum(wechatOpenId);
};

/*
 * 功能：控制转盘
 * 创建时间：07月20
 * 创建人：xuBaoLing
 */
function controlBigTurntable(activityId, wechatOpenId, wechatAppid) {

	var url = qixiWeChatUrl + "fortuneWheel/controll";
	var jsonParam = {
		'marked': '',
		'code': '10002',
		'version': '1.0',
		'data': []
	};

	var myjsonStr = setJson(null, "activityId", activityId);
	myjsonStr = setJson(myjsonStr, "openId", wechatOpenId);
	myjsonStr = setJson(myjsonStr, "appId", wechatAppid);
	jsonParam.data.push($.parseJSON(myjsonStr));
	console.log(JSON.stringify(jsonParam));
	jQuery.axjsonpPost(url, jsonParam, callback_controlBigTurntable);
};

function callback_controlBigTurntable(data) {

	console.log("控制大转盘回调");
	console.log(data);

	if(data == undefined || data == null) {

		comm.alert('无数据返回');

	} else if(data.rspCode != "000") {

		comm.alert(data.rspDesc == undefined ? '无数据返回' : data.rspDesc);

	} else {

		if(data.remainNumber == "0") {

			comm.alert("亲，您的机会已用完~");
			$(".timerShow").show();
			$(".times").hide();

		} else {

			$("#inner").unbind('click').css("cursor", "default");
			//var angle = parseInt(data.angle); //角度
			var msg = data.awardExplain; //提示信息			
			$("#outer").rotate({ //inner内部指针转动，outer外部转盘转动
				duration: 5000, //转动时间 
				angle: 0, //开始角度 
				animateTo: 360 + parseInt(data.angle), //转动角度 
				easing: $.easing.easeOutSine, //动画扩展 
				callback: function() {
					var number = parseInt(data.remainNumber); //剩余次数

					if(data.awardId != "0") {

						//不等于0说明中奖，中奖后弹出提示信息
						//alert(data.awardExplain);
						//拿扩展字段调用其他接口
						var awardExplain = data.awardExplain; //奖励说明	
						//						$(".awardExplain").html("");
						$(".awardExplain").html(awardExplain);

						guizRecordTicket = data.expandParam; //扩展字段	

						$("#overlay").show();
						$("#popShow").show();

					} else {

						comm.alert("没有中奖，再接再厉哦！");
					};

					//页面显示的次数判断
					if(data.remainNumber == "1") {

						$(".times").hide();
						$(".timerShow").show();
						$("#inner").click(function() {

							controlBigTurntable(activityId, wechatOpenId, wechatAppid);
						});

					} else {

						$("#num").text(number / 1 - 1);
						$("#inner").click(function() {

							controlBigTurntable(activityId, wechatOpenId, wechatAppid);
						});

					};

				}
			});

		};

	};
};

//获取用户竞猜手机号
function getUserPhoneNum(wechatOpenId) {

	var url = activityUrl;
	var jsonParam = {
		"marked": "getUserPhone",
		"code": "10002",
		"version": "1.0",
		"jsonStr": {}
	};

	var myjsonStr = setJson(null, "wechatOpenId", wechatOpenId);
	jsonParam.jsonStr = myjsonStr;
	console.log(myjsonStr);
	jQuery.axjsonp(url, jsonParam, callBack_getUserPhoneNum);
};
//获取用户竞猜手机号回调函数
function callBack_getUserPhoneNum(data) {

	console.log("获取手机号的返回数据");
	console.log(data);

	if(data == undefined || data == null) {
		comm.alert('无数据返回');
	} else if(data.rspCode != "000") {
		comm.alert(data.rspDesc == undefined ? '无数据返回' : data.rspDesc);
	} else {

		guizRecordPhone = data.guizRecordPhone;
	};

	//获取大转盘活动信息
	GetWheelConfigureInfoById(activityId);
};

/*
 *创建日期：07月07
 *功能：2.1.4 获取大转盘活动信息
 *创建人：xuBaoLing
 */
function GetWheelConfigureInfoById(activityId) {

	var url = qixiWeChatUrl + "fortuneWheelConfigure/get";
	var jsonParam = {
		'marked': '',
		'code': '10002',
		'version': '1.0',
		'data': []
	};

	var myjsonStr = setJson(null, "activityId", activityId);
	jsonParam.data.push($.parseJSON(myjsonStr));
	console.log(JSON.stringify(jsonParam));
	jQuery.axjsonpPost(url, jsonParam, callback_GetWheelConfigureInfoById);

};

function callback_GetWheelConfigureInfoById(data) {

	console.log("获取大转盘活动信息");
	console.log(data);

	if(data == undefined || data == null) {

		comm.alert('无数据返回');

	} else if(data.rspCode != "000") {

		comm.alert(data.rspDesc == undefined ? '无数据返回' : data.rspDesc);

	} else {

		if(data.activityExplain != "" || data.activityExplain != null || data.activityExplain != undefined) {

			$(".activityExplain").text(data.activityExplain);
			//活动说明

		};
		if(data.awardSetup != "" || data.awardSetup != null || data.awardSetup != undefined) {

			$(".awardSetup").text(data.awardSetup);
			//活动说明
		};

	};

	//获取剩余次数
	remainNumberFun(wechatAppid, wechatOpenId, activityId);
};
/*
 * 获取剩余次数
 */

function remainNumberFun(wechatAppid, wechatOpenId, activityId) {

	var url = qixiWeChatUrl + "fortuneWheel/get";
	var jsonParam = {
		'marked': '',
		'code': '10002',
		'version': '1.0',
		'data': []
	};

	var myjsonStr = setJson(null, "activityId", activityId);
	myjsonStr = setJson(myjsonStr, "appId", wechatAppid);
	myjsonStr = setJson(myjsonStr, "openId", wechatOpenId);
	jsonParam.data.push($.parseJSON(myjsonStr));
	console.log(JSON.stringify(jsonParam));
	jQuery.axjsonpPost(url, jsonParam, callback_remainNumberFun);
};

function callback_remainNumberFun(data) {

	console.log("获取剩余次数");
	console.log(data);

	if(data == undefined || data == null) {

		comm.alert('无数据返回');

	} else if(data.rspCode != "000") {

		comm.alert(data.rspDesc == undefined ? '无数据返回' : data.rspDesc);

	} else {

		if(data.remainNumber != "" || data.remainNumber != undefined || data.remainNumber != null) {

			$("#num").text(data.remainNumber);
		};

	};

};
/*
 * 功能：订单新增
 * 创建人：liql
 * 创建时间：2016-7-23
 */
function InsertOrderInfo(shopId, guizRecordTicket, guizRecordPhone) {
	var url = activityUrl;
	var jsonParam = {
		"marked": "insertOrderInfo",
		"code": "10002",
		"version": "1.0",
		"jsonStr": {}
	};
	var jsonStr = setJson(null, "orderShopId", shopId); //商户id
	jsonStr = setJson(jsonStr, "orderTicket", guizRecordTicket); //券id
	jsonStr = setJson(jsonStr, "orderWechatOpenid", wechatOpenId); //微信用户标识
	jsonStr = setJson(jsonStr, "orderWechatAppid", wechatAppid); //微信公众号id
	jsonStr = setJson(jsonStr, "orderWechatName", wechatName); //微信姓名
	jsonStr = setJson(jsonStr, "orderPhone", guizRecordPhone); //订单电话
	jsonStr = setJson(jsonStr, "orderPrice", '0'); //订单金额
	jsonStr = setJson(jsonStr, "orderPayResult", '1'); //支付结果
	jsonStr = setJson(jsonStr, "orderPayRemark", '支付备注'); //支付备注
	jsonStr = setJson(jsonStr, "orderIsReceive", '2'); //是否领取彩票
	var now = new Date();
	var nowtTimeStr = now.format("yyyy-MM-dd hh:mm:ss");
	jsonStr = setJson(jsonStr, "orderPayDate", nowtTimeStr); //订单支付时间
	jsonStr = setJson(jsonStr, "orderQuantity", '1'); //订单数量
	jsonStr = setJson(jsonStr, "orderIsAccept", '2'); //是否承兑
	jsonStr = setJson(jsonStr, "orderUnionId", wechatUnionid); //用户全网唯一id
	jsonStr = setJson(jsonStr, "orderCupPrice", '0'); //价格
	jsonStr = setJson(jsonStr, "orderDenomination", '0'); //面额
	jsonStr = setJson(jsonStr, "orderActivityType", '5'); //订单活动类型 读秒
	jsonParam.jsonStr = jsonStr;
	console.log(jsonParam);
	jQuery.axjsonp(url, jsonParam, callback_InsertOrderInfo);
}
/*
 * 功能：订单新增回调函数
 * 创建人：liql
 * 创建时间：2016-7-23
 */
function callback_InsertOrderInfo(data) {
	console.log(data);
	//	console.log(JSON.stringify(data));
	if(data != null && data != undefined && data.rspCode != "000") {

		comm.alert(data.rspDesc != '' ? data.rspDesc : '');
	} else {

		comm.alert('已领取，请在我的代金券中查看中奖券详情信息');
	}
	//	$("#popShow").hide();
};

//(注册)发送验证码
function sendVerCodeForReg(guizRecordPhone) {
	var url = activityUrl;
	var jsonParam = {
		"marked": "sendVerCodeForReg",
		"code": "10002",
		"version": "1.0",
		"jsonStr": {}
	};

	var myjsonStr = setJson(null, "userPhone", guizRecordPhone);
	jsonParam.jsonStr = myjsonStr;
	console.log(myjsonStr);
	jQuery.axjsonp(url, jsonParam, callBack_sendVerCodeForReg);
};
//(注册)发送验证码的回调
function callBack_sendVerCodeForReg(data) {
	console.log(data);
	if(data == undefined || data == null) {

		comm.alert('无数据返回');
	} else if(data.rspCode != "000") {

		comm.alert(data.rspDesc == undefined ? '无数据返回' : data.rspDesc);
	} else {

		comm.alert("验证码已发送至手机！");
		getVerificationCode = data.code;
	};
};
//验证验证码代码
function valityYanZ() {

	var code = $(".verificationNum").val();
	var strAlert = "";
	var isValidate = true;

	if(code == '' || code == null || code == undefined) {
		strAlert = strAlert + '验证码不能为空;\r\n';
		isValidate = false;
	} else if(code.length < 6) {
		strAlert = strAlert + '验证码不正确;\r\n';
		isValidate = false;
	} else if(!/^\S+$/gi.test(code)) {
		//		if (!/^\S+$/gi.test(code)) {
		strAlert = strAlert + "验证码不能包含空格;\r\n";
		isValidate = false;
		//		}
	} else if(code != getVerificationCode) {
		strAlert = strAlert + "验证码不正确;\r\n";
		isValidate = false;
	};
	if(strAlert != '') {
		comm.alert(strAlert);
	};
	return isValidate;
};
//验证手机代码
function valityPhone() {
	var reg = new RegExp("^[0-9]*$");
	var phone = $(".phoneNum").val();
	console.log(phone);
	var strAlert = "";
	var isValidate = true;

	if(phone == "" || phone == null || phone == undefined) {
		strAlert = "手机号不能为空;\r\n";
		isValidate = false;
		console.log("1");
	} else if(phone.length < 11 || phone.length > 11 || phone.substr(0, 1) != 1 || !reg.test(phone)) {

		strAlert = strAlert + '手机号格式不正确,请重新输入;\r\n';
		isValidate = false;

	};

	if(strAlert != '') {
		comm.alert(strAlert);
	};
	return isValidate;
};