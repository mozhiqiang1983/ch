<#assign qiniuUtil = static["com.ch.base.util.QiniuUtil"]>

<!DOCTYPE html>
<html>
<head>
<title></title>
<#include "/base_inc.ftl">
<script type="text/javascript" src="${contextPath}/jslib/plupload/js/plupload.full.min.js"></script>
<script type="text/javascript" src="${contextPath}/jslib/plupload/js/i18n/zh_CN.js"></script>
<script type="text/javascript" src="${contextPath}/jslib/qiniu/qiniu.js"></script>
<script type="text/javascript">

	var submitNow = function($dialog, $grid, $pjq) {
		var url;
		if ($(':input[name="id"]').val().length > 0) {
			url = ch.contextPath + '/auth/user/update';
		} else {
			url = ch.contextPath + '/auth/user/save';
		}
		

		
		$.post(url, ch.serializeObject($('form')), function(result) {
			parent.ch.progressBar('close');//关闭上传进度条

			if (result.success) {
				$pjq.messager.show({
					title:'提示',
					msg:result.msg,
					timeout:2000,
					showType:'slide'
				});
				$grid.datagrid('load');
				$dialog.dialog('destroy');
			} else {
				$pjq.messager.alert('提示', result.msg, 'error');
			}
		}, 'json');
	};
	var submitForm = function($dialog, $grid, $pjq) {
		if ($('form').form('validate')) {
			submitNow($dialog, $grid, $pjq);
		}
	};

	$(function() {
		
		if ($(':input[name="id"]').val().length > 0) {
	
			parent.$.messager.progress({
				text : '数据加载中....'
			});
			$.post(ch.contextPath + '/auth/user/get',{
				id : $(':input[name="id"]').val()
			}, function(result) {
				if (result.id != undefined) {
					$('form').form('load', {
						'id' : result.id,
						'name' : result.name,
						'loginname' : result.loginname,
						'sex' : result.sex,
						'age' : result.age,
						'pwd' : result.pwd,
						'photo' : result.photo
					});
					if (result.photo) {
						$('#photo').attr('src', result.photo);
					}
				}
				parent.$.messager.progress('close');
			}, 'json');
		}
		<#--
		uploader = new plupload.Uploader({//上传插件定义
			browse_button : 'pickfiles',//选择文件的按钮
			container : 'container',//文件上传容器
			runtimes : 'html5,flash',//设置运行环境，会按设置的顺序，可以选择的值有html5,gears,flash,silverlight,browserplus,html4
			//flash_swf_url : ch.contextPath + '/jslib/plupload_1_5_7/plupload/js/plupload.flash.swf',// Flash环境路径设置
			url : ch.contextPath + '/plupload?fileFolder=/userPhoto',//上传文件路径
			max_file_size : '5mb',//100b, 10kb, 10mb, 1gb
			chunk_size : '10mb',//分块大小，小于这个大小的不分块
			unique_names : true,//生成唯一文件名
			// 如果可能的话，压缩图片大小
			/*resize : {
				width : 320,
				height : 240,
				quality : 90
			},*/
			// 指定要浏览的文件类型
			filters : [ {
				title : '图片文件',
				extensions : 'jpg,gif,png'
			} ]
		});
		uploader.bind('Init', function(up, params) {//初始化时
			//$('#filelist').html("<div>当前运行环境: " + params.runtime + "</div>");
			$('#filelist').html("");
		});
		uploader.bind('BeforeUpload', function(uploader, file) {//上传之前
			if (uploader.files.length > 1) {
				parent.$.messager.alert('提示', '只允许选择一张照片！', 'error');
				uploader.stop();
				return;
			}
			$('.ext-icon-cross').hide();
		});
		uploader.bind('FilesAdded', function(up, files) {//选择文件后
			$.each(files, function(i, file) {
				$('#filelist').append('<div id="' + file.id + '">' + file.name + '(' + plupload.formatSize(file.size) + ')<strong></strong>' + '<span onclick="uploader.removeFile(uploader.getFile($(this).parent().attr(\'id\')));$(this).parent().remove();" style="cursor:pointer;" class="ext-icon-cross" title="删除">&nbsp;&nbsp;&nbsp;&nbsp;</span></div>');
			});
			up.refresh();
		});
		uploader.bind('UploadProgress', function(up, file) {//上传进度改变
			var msg;
			if (file.percent == 100) {
				msg = '99';//因为某些大文件上传到服务器需要合并的过程，所以强制客户看到99%，等后台合并完成...
			} else {
				msg = file.percent;
			}
			$('#' + file.id + '>strong').html(msg + '%');

			parent.ch.progressBar({//显示文件上传滚动条
				title : '文件上传中...',
				value : msg
			});
		});
		uploader.bind('Error', function(up, err) {//出现错误
			$('#filelist').append("<div>错误代码: " + err.code + ", 描述信息: " + err.message + (err.file ? ", 文件名称: " + err.file.name : "") + "</div>");
			up.refresh();
		});
		uploader.bind('FileUploaded', function(up, file, info) {//上传完毕
			var response = $.parseJSON(info.response);
			if (response.status) {
				$('#' + file.id + '>strong').html("100%");
				//console.info(response.fileUrl);
				//console.info(file.name);
				//$('#f1').append('<input type="hidden" name="fileUrl" value="'+response.fileUrl+'"/>');
				//$('#f1').append('<input type="hidden" name="fileName" value="'+file.name+'"/><br/>');
				$(':input[name="photo"]').val(response.fileUrl);
			}
		});
		uploader.init();
		-->
	});
	
</script>

<script>
	
		var uploader;
		$(function(){
			uploader = Qiniu.uploader({
                runtimes: 'html5,flash,html4',    //上传模式,依次退化
                browse_button: 'pickfiles',       //上传选择的点选按钮，**必需**
                uptoken_url: '${contextPath}/qiniu/uptoken',            //Ajax请求upToken的Url，**强烈建议设置**（服务端提供）
                // uptoken : '', //若未指定uptoken_url,则必须指定 uptoken ,uptoken由其他程序生成
                unique_names: true, // 默认 false，key为文件名。若开启该选项，SDK为自动生成上传成功后的key（文件名）。
                // save_key: true,   // 默认 false。若在服务端生成uptoken的上传策略中指定了 `sava_key`，则开启，SDK会忽略对key的处理
                domain: '${qiniuUtil.getDomain()}',   //bucket 域名，下载资源时用到，**必需**
                container: 'container',           //上传区域DOM ID，默认是browser_button的父元素，
                multi_selection: false,//为true时能同时上传多个文件
                max_file_size: '100mb',           //最大文件体积限制
                flash_swf_url: '${contextPath}/jslib/plupload/js/Moxie.swf',  //引入flash,相对路径
                max_retries: 3,                   //上传失败最大重试次数
                dragdrop: true,                   //开启可拖曳上传
                drop_element: 'container',        //拖曳上传区域元素的ID，拖曳文件或文件夹后可触发上传
                chunk_size: '4mb',                //分块上传时，每片的体积
                auto_start: true,                 //选择文件后自动上传，若关闭需要自己绑定事件触发上传
                init: {
                    'FilesAdded': function(up, files) {
                        $.each(files, function(i, file) {
							$('#filelist').append('<div id="' + file.id + '">' + file.name + '(' + plupload.formatSize(file.size) + ')<strong></strong>' + '<span onclick="uploader.removeFile(uploader.getFile($(this).parent().attr(\'id\')));$(this).parent().remove();" style="cursor:pointer;" class="ext-icon-cross" title="删除">&nbsp;&nbsp;&nbsp;&nbsp;</span></div>');
						});
						up.refresh();
                    },
                    'BeforeUpload': function(uploader, file) {
                        if (uploader.files.length > 1) {
							parent.$.messager.alert('提示', '只允许选择一张照片！', 'error');
							uploader.stop();
							return;
						}
						$('.ext-icon-cross').hide();
                    },
                    'UploadProgress': function(up, file) {

                       var msg;
						if (file.percent == 100) {
							msg = '99';//因为某些大文件上传到服务器需要合并的过程，所以强制客户看到99%，等后台合并完成...
						} else {
							msg = file.percent;
						}

						$('#' + file.id + '>strong').html(msg + '%');
					
						parent.ch.progressBar({//显示文件上传滚动条
							title : '文件上传中...',
							value : msg
						});
						
                    },
                    'UploadComplete': function() {
                        parent.ch.progressBar({
							value : 100
						});
                    	parent.ch.progressBar('close');
                    },
                    'FileUploaded': function(up, file, info) {
                    	
                    	
                    	
                    	var res = $.parseJSON(info);
						if (res) {
							$('#' + file.id + '>strong').html("100%");
							var domain = up.getOption('domain');
	                        var sourceLink = domain + res.key;
	                        $("#photo").attr("src",sourceLink);
							$(':input[name="photo"]').val(sourceLink);
						}
                    },
                    'Error': function(up, err, errTip) {
                        $('#filelist').append("<div>错误代码: " + err.code + ", 描述信息: " + err.message + (err.file ? ", 文件名称: " + err.file.name : "") + "</div>");
						up.refresh();
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
	
</head>
<body>
	<form method="post" class="form">
		<fieldset>
			<legend>用户基本信息</legend>
			<table class="table" style="width: 100%;">
				<tr>
					<th>编号</th>
					<td><input name="id" value="${tempvar["id"]?if_exists}" readonly="readonly" /><input name="pwd" type="hidden" /></td>
					<th>登陆名称</th>
					<td><input name="loginname" class="easyui-validatebox" data-options="required:true" /></td>
				</tr>
				<tr>
					<th>姓名</th>
					<td><input name="name" /></td>
					<th>性别</th>
					<td><select class="easyui-combobox" name="sex" data-options="panelHeight:'auto',editable:false" style="width: 155px;">
							<option value="1">男</option>
							<option value="0">女</option>
					</select></td>
				</tr>
				<tr>
					<th>照片上传</th>
					<td><div id="container">
							<a id="pickfiles" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-zoom'">选择文件</a>
							<div id="filelist"><#--您的浏览器没有安装Flash插件，或不支持HTML5！--></div>
						</div></td>
					<th></th>
					<td><input name="photo" readonly="readonly" style="display: none;" /> <img id="photo" src="" style="width: 200px; height: 200px;"></td>
				</tr>
			</table>
		</fieldset>
	</form>
</body>
</html>