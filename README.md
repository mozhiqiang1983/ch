ch
==

项目依赖oschina的开源库，需要连接到外网下载lib。settings.xml是我自己的maven配置文件,下载包有问题的话可以把我的直接覆盖到maven的conf目录下使用，

项目使用到的技术有：
springMVC
hibernate
freemarker
jquery
easyui(后台用的ui组件，官网www.jeasyui.com)
redis(缓存，可选)
qiniu.js(七牛云服务api,可选)

需要安装的软件有：
mysql
tomcat7
jdk1.7(必须1.7)
maven

项目可以用eclipse导入(新版自带git插件)，项目内搜索StartJetty6.java运行项目，默认访问路径是http://localhost:8888,需要先在mysql
建立一个名为ch_test的数据库，项目启动过程中会自动建表，成功访问首页后，在地址栏输入http://localhost:8888/init初始化数据，
程序会生成一些基础数据，生成后会自动回到首页。登陆账号密码为:admin/123456。数据库配置在项目内搜索conf.properties。

现只有ch-admin能运行，有一个基本的系统管理功能，能建立用户、角色和菜单。
ch-web是网站的前台项目，能运行但没有页面。

ch项目
