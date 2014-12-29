<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.ch.base.model.SessionInfo"%>
<%@ page import="com.ch.base.util.QiniuUtil"%>
<%
	String contextPath = request.getContextPath();
%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title></title>
		<script type="text/javascript" src="<%=contextPath%>/jslib/jquery-1.11.2.min.js" ></script>
		<script type="text/javascript" src="<%=contextPath%>/jslib/plupload/js/plupload.full.min.js" ></script>
		<script type="text/javascript" src="<%=contextPath%>/jslib/plupload/js/i18n/zh_CN.js" ></script>
		<script type="text/javascript" src="<%=contextPath%>/jslib/qiniu/qiniu.js" ></script>
		<script type="text/javascript" src="<%=contextPath%>/jslib/fileProgress.js" ></script>
	</head>
	<script>
		var uploader;
		$(function(){
			uploader = Qiniu.uploader({
                runtimes: 'html5,flash,html4',    //上传模式,依次退化
                browse_button: 'pickfiles',       //上传选择的点选按钮，**必需**
                uptoken_url: '<%=contextPath%>/qiniu/uptoken',            //Ajax请求upToken的Url，**强烈建议设置**（服务端提供）
                // uptoken : '', //若未指定uptoken_url,则必须指定 uptoken ,uptoken由其他程序生成
                unique_names: true, // 默认 false，key为文件名。若开启该选项，SDK为自动生成上传成功后的key（文件名）。
                // save_key: true,   // 默认 false。若在服务端生成uptoken的上传策略中指定了 `sava_key`，则开启，SDK会忽略对key的处理
                domain: '<%= QiniuUtil.getDomain()%>',   //bucket 域名，下载资源时用到，**必需**
                container: 'container',           //上传区域DOM ID，默认是browser_button的父元素，
                multi_selection: false,//为true时能同时上传多个文件
                max_file_size: '100mb',           //最大文件体积限制
                flash_swf_url: '<%=contextPath%>/jslib/plupload/js/Moxie.swf',  //引入flash,相对路径
                max_retries: 3,                   //上传失败最大重试次数
                dragdrop: true,                   //开启可拖曳上传
                drop_element: 'container',        //拖曳上传区域元素的ID，拖曳文件或文件夹后可触发上传
                chunk_size: '4mb',                //分块上传时，每片的体积
                auto_start: true,                 //选择文件后自动上传，若关闭需要自己绑定事件触发上传
                init: {
                    'FilesAdded': function(up, files) {
                        $('table').show();
                        $('#success').hide();
                        plupload.each(files, function(file) {
                            var progress = new FileProgress(file, 'fsUploadProgress');
                            progress.setStatus("等待...");
                        });
                    },
                    'BeforeUpload': function(up, file) {
                        var progress = new FileProgress(file, 'fsUploadProgress');
                        var chunk_size = plupload.parseSize(this.getOption('chunk_size'));
                        if (up.runtime === 'html5' && chunk_size) {
                            progress.setChunkProgess(chunk_size);
                        }
                    },
                    'UploadProgress': function(up, file) {
                        var progress = new FileProgress(file, 'fsUploadProgress');
                        var chunk_size = plupload.parseSize(this.getOption('chunk_size'));
                        progress.setProgress(file.percent + "%", up.total.bytesPerSec, chunk_size);

                    },
                    'UploadComplete': function() {
                        $('#success').show();
                    },
                    'FileUploaded': function(up, file, info) {
                    	var domain = up.getOption('domain');
                        var res = $.parseJSON(info);
                        var sourceLink = domain + res.key;
                        console.info(sourceLink);
                        $("#uploadPic").attr("src",sourceLink);
                        
                        $.post("<%=contextPath%>/qiniu/delete",{key:res.key},
                      		  function(data){
                      		    console.info(data);
                      		  },
                      		  "text")
                        var progress = new FileProgress(file, 'fsUploadProgress');
                        progress.setComplete(up, info);
                    },
                    'Error': function(up, err, errTip) {
                        $('table').show();
                        var progress = new FileProgress(err.file, 'fsUploadProgress');
                        progress.setError();
                        progress.setStatus(errTip);
                    }
                    /*
                     ,
                     'Key': function(up, file) {
                         var key = "";
                         // do something with key
                        return key
                    }
                    */
                }
            });
			
			/*
			uploader.bind('FileUploaded', function() {
		        console.log('hello man,a file is uploaded');
		    });
			*/
		    $('#container').on(
		        'dragenter',
		        function(e) {
		            e.preventDefault();
		            $('#container').addClass('draging');
		            e.stopPropagation();
		        }
		    ).on('drop', function(e) {
		        e.preventDefault();
		        $('#container').removeClass('draging');
		        e.stopPropagation();
		    }).on('dragleave', function(e) {
		        e.preventDefault();
		        $('#container').removeClass('draging');
		        e.stopPropagation();
		    }).on('dragover', function(e) {
		        e.preventDefault();
		        $('#container').addClass('draging');
		        e.stopPropagation();
		    });

		});
	</script>
	<body>
			<input id="pickfiles" type="button" value="选择"/>
			<div id="container" style="background-color:#eee;border:1px;height: 500px;">
				<img id="uploadPic" src=""></img>
			</div>
	</body>
</html>
