document.ondblclick=setScroll
var position = 0; 
var scrollStatus=true;
var interval=50;
var length=10;
function setScroll() {
   scrollStatus=!scrollStatus;
   scroller();
}
function scroller() {
    if (scrollStatus==true){     
		position+=length;
		scroll(0,position); 
		clearTimeout(timer); 
		var timer = setTimeout("scroller()",interval); 
		timer;
    }
	else{
		clearTimeout(timer);
	}
} 
scroller();
