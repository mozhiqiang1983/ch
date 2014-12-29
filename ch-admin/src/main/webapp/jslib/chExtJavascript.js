var ch = ch || {};

/**
 * 去字符串空格
 * 
 * 
 */
ch.trim = function(str) {
	return str.replace(/(^\s*)|(\s*$)/g, '');
};
ch.ltrim = function(str) {
	return str.replace(/(^\s*)/g, '');
};
ch.rtrim = function(str) {
	return str.replace(/(\s*$)/g, '');
};

/**
 * 判断开始字符是否是XX
 * 
 * 
 */
ch.startWith = function(source, str) {
	var reg = new RegExp("^" + str);
	return reg.test(source);
};
/**
 * 判断结束字符是否是XX
 * 
 * 
 */
ch.endWith = function(source, str) {
	var reg = new RegExp(str + "$");
	return reg.test(source);
};

/**
 * iframe自适应高度
 * 
 * 
 * 
 * @param iframe
 */
ch.autoIframeHeight = function(iframe) {
	iframe.style.height = iframe.contentWindow.document.body.scrollHeight + "px";
};

/**
 * 设置iframe高度
 * 
 * 
 * 
 * @param iframe
 */
ch.setIframeHeight = function(iframe, height) {
	iframe.height = height;
};