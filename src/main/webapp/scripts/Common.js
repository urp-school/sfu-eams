function portableEvent(e){
   if(!e)return window.event;
}
function getEventTarget(e){
  if(document.all){
     return portableEvent(e).srcElement;
  }else{
     return e.target;
  }
}
function MakeFull(){
	var args = MakeFull.arguments;
	var url = args[0];
	var name = "";
	if (args.length > 1){
		name = args[1];
	}
	var closeSelf = false;
	if (args.length > 2){
		closeSelf = args[2];
	}

    var width=1024;
    var height=735;
    if (screen.width == 800){
        width = 800;
        height = 570;
    }

    var win = window.open(url,name,'toolbar=no,location=no,directories=no,status=no,menubar=no,resizable=no,scrollbars=yes');
	win.moveTo(0,0);
	win.resizeTo(width,height);
    win.focus();

	if (closeSelf)
		CloseWin();
}

function CloseWin(){
    var ua=navigator.userAgent
    var ie=navigator.appName=="Microsoft Internet Explorer"?true:false
    if(ie){
        var IEversion=parseFloat(ua.substring(ua.indexOf("MSIE ")+5,ua.indexOf(";",ua.indexOf("MSIE "))))
        if(IEversion< 5.5){
            var str  = '<object id=noTipClose classid="clsid:ADB880A6-D8FF-11CF-9377-00AA003B7A11">'
            str += '<param name="Command" value="Close"></object>';
            document.body.insertAdjacentHTML("beforeEnd", str);
            document.all.noTipClose.Click();
        } else{
            window.opener =null;
            window.close();
        }
    } else {
        window.close();
    }
}


function turnit(name, index, count){
	var currentTree = eval(name+index);
	
	if (currentTree.style.display=="none") {
		for (var i=1; i<=count; i++) {
			try{   
				var tree = eval(name+i);
				tree.style.display="none";
			} catch(e){
			}
		}
		currentTree.style.display="block";
	} else {
		currentTree.style.display="none"; 
	}
}

function MM_changeSearchBarStyle(bar) { 
    if ((obj=MM_findObj(bar))!=null) { 
  		if (obj.style.visibility == 'hidden'){
  		    obj.style.visibility = 'visible';
  		    obj.style.display = 'block';
  		} else {
  		    obj.style.visibility = 'hidden';
  		    obj.style.display = 'none';
  		}
    }
}

function MM_showHideLayers() { //v6.0
  showHiden(MM_showHideLayers.arguments);
}

function showHiden(args)
{
  var i,p,v,obj;
  for (i=0; i<(args.length-2); i+=3){
  	if ((obj=MM_findObj(args[i]))!=null) { 
  		v=args[i+2];
    	if (obj.style) { 
    		obj=obj.style; 
    		v=(v=='show')?'visible':(v=='hide')?'hidden':v; 
    	}
    	obj.visibility=v;     	
    }
  } 
}

function highlightButton(s) {
	if ("INPUT"==event.srcElement.tagName)
		event.srcElement.className=s
}

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; 
  for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_reloadPage(init) {  //reloads the window if Nav4 resized
  if (init==true) with (navigator) {if ((appName=="Netscape")&&(parseInt(appVersion)==4)) {
    document.MM_pgW=innerWidth; document.MM_pgH=innerHeight; onresize=MM_reloadPage; }}
  else if (innerWidth!=document.MM_pgW || innerHeight!=document.MM_pgH) location.reload();
}

MM_reloadPage(true);

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_openBrWindow(theURL,winName,features) { //v2.0
  window.open(theURL,winName,features);
}

function popupCommonWindow(url)
{
	var name = '';
	if (popupCommonWindow.arguments.length > 1){
		name = popupCommonWindow.arguments[1];
	}

    var width = 500;
    var height = 600;
    if (popupCommonWindow.arguments.length > 2)
       width = popupCommonWindow.arguments[2];
    if (popupCommonWindow.arguments.length > 3)
       height = popupCommonWindow.arguments[3];
    
    var win = window.open(url, name, 'scrollbars=yes,width='+width+',height='+height+',status=no,depended=yes');
	win.resizeTo(width, height);
	win.moveTo((screen.width-width)/2, (screen.height-height)/2);
	win.focus();
}

function popupCommonSelector(url){
	var name = '';
	if (popupCommonSelector.arguments.length > 1)
	{
		name = popupCommonSelector.arguments[1];
	}
	popupCommonWindow(url, name);
}

function popupMiniCommonSelector(){
    var args = popupMiniCommonSelector.arguments;
	var url = args[0];
	var top = 0;
	var left = 0;
	if (args.length>1){top=args[1];}
	if (args.length>2){left=args[2];}
	
    var selector = 0;
    if (selector&&(selector.closed)){
        selector.close();
    }
	var selector= window.open(url, 'selector', 'scrollbars=yes,status=yes,width=1,height=1,left=1000,top=1000');
	selector.moveTo(top,left);
	selector.resizeTo(430,735);    
    
}

function disableButton(){
    var args = disableButton.arguments;
    var formObjName = args[0];
    if (args.length >1){
       for (var i=1; i<args.length; i++){
         if (checkObjExist(formObjName, args[i])){
             var objName = args[i];
             document.forms[formObjName].elements[objName].disabled = true;
         }
       }
    }
}
 
function checkObjExist(formObjName, objName){
    var formObj = document.forms[formObjName];
    if (formObj.elements[objName] != "undefined") {
       return true;
    }else{
       return false;
    }
}
 
// TOGGLE OBJECT DISPLAY ==============
function toggleDisplay(objId) {

    if (document.all['mainTable'].height=='100%') {
        document.all['mainTable'].height='83%';
    }else{
        document.all['mainTable'].height='100%';
    }
    
	document.all[objId].style.display = (document.all[objId].style.display == "none") ? "block" : "none";
	if (document.all[objId + "_handle"]) {
		//alert(document.all[objId + "_handle"].src);
		var fullpath = document.all[objId + "_handle"].src;
		var filename = fullpath.substr(fullpath.lastIndexOf("/")+1, fullpath.length);
		
		switch (filename) {
			case "push_top.jpg":
				document.all[objId + "_handle"].src = fullpath.substr(0,fullpath.lastIndexOf("/")+1)+"pull_top.jpg";
				break;
			case "pull_top.jpg":
				document.all[objId + "_handle"].src = fullpath.substr(0,fullpath.lastIndexOf("/")+1)+"push_top.jpg";
				break;
			case "push_left.jpg":
				document.all[objId + "_handle"].src = fullpath.substr(0,fullpath.lastIndexOf("/")+1)+"pull_left.jpg";
				break;
			case "pull_left.jpg":
				document.all[objId + "_handle"].src = fullpath.substr(0,fullpath.lastIndexOf("/")+1)+"push_left.jpg";
				break;	
		}

	}
}

function toggleUnDisplay(objId) {
	document.all[objId].style.display = document.all[objId].style.display = "none" ;
}

//???????????????????????????????????????????????????????????????
var currClass="";
var swapClass="";
//????????????					
function swapOverTR(obj,objClass){
							
    if(objClass!="roll_down"){    	
	    currClass=objClass;
	    obj.className="highlight";
	    swapClass=objClass;
	}
}
//????????????
function swapOutTR(obj){
  if(obj.className!="roll_down"){
	obj.className=swapClass;
  }
}
function onRowChange(obj){
    ele = event.srcElement;
    if(null!=ele&&((ele.type=="checkbox")||(ele.type=="radio")))
         return;    
    while(null!=ele&&ele.tagName!="TD"){
        if(ele.parentNode!=null)
           ele=ele.parentNode;
        else ele=null;
    }
    if(null!=ele){
      ele.parentNode.firstChild.firstChild.checked = !ele.parentNode.firstChild.firstChild.checked;    
    }
}