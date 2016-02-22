<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>播放视频</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery/jquery.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/static/ckplayer/ckplayer.js" charset="utf-8"></script>
<script type="text/javascript">
$(function(){
        function loadedHandler(){
            if(CKobject.getObjectById('ckplayer_a1').getType()){
              CKobject.getObjectById('ckplayer_a1').addListener('time',timeHandler);
            }
            else{
              CKobject.getObjectById('ckplayer_a1').addListener('time','timeHandler');
            }
          }
          function timeHandler(t){
            if(t>-1){
                CKobject._K_('nowTime').innerHTML='当前播放的时间点是(此值精确到小数点后三位，即毫秒)：'+t;
            }
          }
          var flashvars={
        	f:'${pageContext.request.contextPath}/video/${fileName}',
            c:0,
            p:1,
            b:0,
            loaded:'loadedHandler',
          };
          var video=['${pageContext.request.contextPath}/video/${fileName}->video/mp4'];
          CKobject.embed('${pageContext.request.contextPath}/static/ckplayer/ckplayer.swf','a1','ckplayer_a1','600','400',false,flashvars,video);
});
</script>
</head>
<body>
	<h1>播放视频</h1>
	<div id="a1"></div>
	<br>
	<div id="nowTime">当前播放的时间点是(此值精确到小数点后三位，即毫秒)：0</div>
	<br/>
	<div><a href="${pageContext.request.contextPath}/upload">上传视频</a></div>
</body>
</html>
