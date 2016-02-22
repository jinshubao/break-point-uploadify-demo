<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>上传视频</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery/jquery.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/Huploadify/Huploadify.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/static/Huploadify/jquery.Huploadify.js"></script>
<script type="text/javascript">
$(function(){
	var itemTemp ='';
		itemTemp+=	'<div id="${fileID}" class="uploadify-queue-item">                  ';
		itemTemp+=	'	  <div class="uploadify-progress">                              ';
		itemTemp+=	'		<div class="uploadify-progress-bar"></div>                  ';
		itemTemp+=	'	  </div>                                                        ';
		itemTemp+=	'		<span class="up_filename">${fileName}</span>                ';
		itemTemp+=	'		<a href="javascript:void(0);" class="uploadbtn">上传</a>    	';
		itemTemp+=	'		<a href="javascript:void(0);" class="delfilebtn">删除</a>    ';
		itemTemp+=	' </div>                                                            ';
	var uploadUrl = '${pageContext.request.contextPath}/upload';
	var up = $('#upload').Huploadify({
		auto:false,
		fileTypeExts:'*.mp4;*.flv;*.f4v;*.m3u8;',//允许上传的文件类型，格式'*.jpg;*.doc'
		multi:true,						//是否允许选择多个文件
// 		itemTemplate:itemTemp,			//上传队列显示的模板
		fileSizeLimit:1024*1024 * 1024,	//允许上传的文件大小，单位KB， 1G
		showUploadedPercent:true,		//是否实时显示上传的百分比，如20%
		showUploadedSize:true,			//是否实时显示已上传的文件大小，如1M/2M
		removeTimeout:1000*60*60,		//上传完成后进度条的消失时间，单位毫秒
		uploader:uploadUrl,				//文件提交的地址
		breakPoints:true,				//是否开启断点续传
		fileSplitSize:1024*1024*5,		//断点续传的文件块大小，单位Byte，默认1M， 5M
		saveInfoLocal:false,			//用于开启断点续传模式，是否使用localStorage存储已上传文件大小
		onUploadStart : function(){
			$("#btn2").attr("disabled","disabled");
		},
		onUploadComplete:function(file){
	        console.log(file.name+'上传完成');
	    },
	    onCancel:function(file){
	        console.log(file.name+'删除成功');
	    },
	    onClearQueue:function(queueItemCount){
	        console.log('有'+queueItemCount+'个文件被删除了');
	    },
	    onDestroy:function(){
	        console.log('destroyed!');
	    },
	    onSelect:function(file){
	        console.log(file.name+'加入上传队列');
	    },
	    onQueueComplete:function(queueData){
	        console.log('队列中的文件全部上传完成',queueData);
	        $("#btn2").removeAttr("disabled");
	    },
		//类型：function，自定义保存已上传文件的大小函数，用于开启断点续传模式，可传入两个参数：file：当前上传的文件对象，value：已上传文件的大小，单位Byte
		saveUploadedSize:function(file){
			console.log('上传大小',file.size);
		},
		//类型：function，自定义获取已上传文件的大小函数，用于开启断点续传模式，可传入一个参数file，即当前上传的文件对象，需返回number类型
		getUploadedSize:function(file){
			var url = '${pageContext.request.contextPath}/getUploadedSize';
			
			var uploadedSize = 0;
			$.ajax({
				url : url,
				data : {
					fileName : file.name,
					size : file.size,
					lastModifiedDate : file.lastModifiedDate.getTime()
				},
				async : false,
				type : 'GET',
				success : function(data){
					uploadedSize = data;
				}
			});
			console.info("============"+uploadedSize);
			return uploadedSize;
		}
	});

	$('#btn1').click(function(){
		up.stop();
		 $("#btn2").removeAttr("disabled");
	});
	$('#btn2').click(function(){
		up.upload('*');
	});
	$('#btn3').click(function(){
		up.cancel('*');
	});
});
</script>

</head>
<body>
	<div>
		<h1>视频列表</h1>
		<c:forEach items="${videos}" var="item" varStatus="status">
			<div>${status.index+1}、&nbsp;&nbsp;${item }&nbsp;&nbsp;&nbsp;&nbsp;<a href="${pageContext.request.contextPath}/player?fileName=${item }" target="${item }_black">播放</a></div>
		</c:forEach>
	</div>
	<div>
		<h1>上传视频</h1>
		<div id="upload"></div>
		<div>
			<button id="btn2">全部上传</button>
			<button id="btn1">暂停上传</button>
			<button id="btn3">取消上传</button>
		</div>
	</div>
</body>
</html>