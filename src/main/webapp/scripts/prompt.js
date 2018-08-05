/*
Made by PuterJam
*/

var rT=true;
var bT=true;
var tw=200;
var endaction=false;

var ns4 = document.layers;
var ns6 = document.getElementById && !document.all;
var ie4 = document.all;
offsetX = 0;
offsetY = 20;
var toolTipSTYLE="";

function initToolTips(){
  if(ns4||ns6||ie4)  {
    if(ns4) toolTipSTYLE = document.toolTipLayer;
    else if(ns6) toolTipSTYLE = document.getElementById("toolTipLayer").style;
    else if(ie4) toolTipSTYLE = document.all.toolTipLayer.style;
    if(ns4) document.captureEvents(Event.MOUSEMOVE);
    else
    {
      toolTipSTYLE.visibility = "visible";
      toolTipSTYLE.display = "none";
    }
    document.onmousemove = moveToMouseLoc;
  }
}
/**
 * 鼠标提示
 * @msg 要显示的HTML代码 
 * @fg 字体颜色
 * @bg 背景颜色
 * @width 提示框的宽度
 */
function toolTip(msg, fg, bg,width){
  if(null==width) width=tw;
  if(toolTip.arguments.length < 1) // hide
  {
    if(ns6)// modified by chaostone 2006-12-13
    {
    toolTipSTYLE.visibility = "hidden";
    }
    else 
    {
      //--?????????--
      if (!endaction) {toolTipSTYLE.display = "none";}
      if (rT) document.all("msg1").filters[1].Apply();
      if (bT) document.all("msg1").filters[2].Apply();
      document.all("msg1").filters[0].opacity=0;
      if (rT) document.all("msg1").filters[1].Play();
      if (bT) document.all("msg1").filters[2].Play();
      if (rT){ 
      if (document.all("msg1").filters[1].status==1 || document.all("msg1").filters[1].status==0){  
      toolTipSTYLE.display = "none";}
      }
      if (bT){
      if (document.all("msg1").filters[2].status==1 || document.all("msg1").filters[2].status==0){  
      toolTipSTYLE.display = "none";}
      }
      if (!rT && !bT) toolTipSTYLE.display = "none";
      //----------------------
    }
  }
  else // show
  {
    if(!fg) fg = "#777777";
    if(!bg) bg = "#eeeeee";
    var content =
    '<table id="msg1" name="msg1" border="0" cellspacing="0" cellpadding="1" bgcolor="' + fg + '" class="trans_msg"><td>' +
    '<table border="0" cellspacing="0" cellpadding="3" bgcolor="' + bg + 
    '"><td width=' + width + '><font face="Arial" color="' + fg +
    '" size="2">' + msg +
    '&nbsp\;</font></td></table></td></table>';

    if(ns4)
    {
      toolTipSTYLE.document.write(content);
      toolTipSTYLE.document.close();
      toolTipSTYLE.visibility = "visible";
    }
    if(ns6)
    {
      document.getElementById("toolTipLayer").innerHTML = content;
      toolTipSTYLE.display='block';
      toolTipSTYLE.visibility = "visible";
    }
    if(ie4)
    {
      document.all("toolTipLayer").innerHTML=content;
      toolTipSTYLE.display='block'
      //--?????????--
      var cssopaction=document.all("msg1").filters[0].opacity
      document.all("msg1").filters[0].opacity=0;
      if (rT) document.all("msg1").filters[1].Apply();
      if (bT) document.all("msg1").filters[2].Apply();
      document.all("msg1").filters[0].opacity=cssopaction;
      if (rT) document.all("msg1").filters[1].Play();
      if (bT) document.all("msg1").filters[2].Play();
      //----------------------
    }
  }
}
function moveToMouseLoc(e){
  if(ns4||ns6)  {
    x = e.pageX;
    y = e.pageY;
  }
  else  {
    x = event.x + document.body.scrollLeft;
    y = event.y + document.body.scrollTop;
  }
  toolTipSTYLE.left = x + offsetX;
  toolTipSTYLE.top = y + offsetY;
  return true;
}
