/********************************************************************* 
* 判断字符串strDate是否为一个正确的日期格式： 
* yyyy-M-d或yyyy-MM-dd 
* *******************************************************************/
function IsDate(strDate) 
{ 
// 先判断格式上是否正确 
var regDate = /^(\d{4})-(\d{1,2})-(\d{1,2})$/; 
if (!regDate.test(strDate)) 
{ 
return false; 
} 
// 将年、月、日的值取到数组arr中，其中arr[0]为整个字符串，arr[1]-arr[3]为年、月、日 
var arr = regDate.exec(strDate); 
// 判断年、月、日的取值范围是否正确 
return IsMonthAndDateCorrect(arr[1], arr[2], arr[3]); 
} 
/********************************************************************* 
* 判断字符串strDateTime是否为一个正确的日期时间格式： 
* yyyy-M-d H:m:s或yyyy-MM-dd HH:mm:ss 
* 时间采用24小时制 
* *******************************************************************/
function IsDateTime(strDateTime) 
{ 
// 先判断格式上是否正确 
var regDateTime = /^(\d{4})-(\d{1,2})-(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/; 
if (!regDateTime.test(strDateTime)) 
return false; 
// 将年、月、日、时、分、秒的值取到数组arr中，其中arr[0]为整个字符串，arr[1]-arr[6]为年、月、日、时、分、秒 
var arr = regDateTime.exec(strDateTime); 
// 判断年、月、日的取值范围是否正确 
if (!IsMonthAndDateCorrect(arr[1], arr[2], arr[3])) 
return false; 
// 判断时、分、秒的取值范围是否正确 
if (arr[4] >= 24) 
return false; 
if (arr[5] >= 60) 
return false; 
if (arr[6] >= 60) 
return false; 
// 正确的返回 
return true; 
} 
// 判断年、月、日的取值范围是否正确 
function IsMonthAndDateCorrect(nYear, nMonth, nDay) 
{ 
// 月份是否在1-12的范围内，注意如果该字符串不是C#语言的，而是JavaScript的，月份范围为0-11 
if (nMonth > 12 || nMonth <= 0) 
return false; 
// 日是否在1-31的范围内，不是则取值不正确 
if (nDay > 31 || nMonth <= 0) 
return false; 
// 根据月份判断每月最多日数 
var bTrue = false; 
switch(nMonth) 
{ 
case 1: 
case 3: 
case 5: 
case 7: 
case 8: 
case 10: 
case 12: 
bTrue = true; // 大月，由于已判断过nDay的范围在1-31内，因此直接返回true 
break; 
case 4: 
case 6: 
case 9: 
case 11: 
bTrue = (nDay <= 30); // 小月，如果小于等于30日返回true 
break; 
} 
if (!bTrue) 
return true; 
// 2月的情况 
// 如果小于等于28天一定正确 
if (nDay <= 28) 
return true; 
// 闰年小于等于29天正确 
if (IsLeapYear(nYear)) 
return (nDay <= 29); 
// 不是闰年，又不小于等于28，返回false 
return false; 
} 
// 是否为闰年，规则：四年一闰，百年不闰，四百年再闰 
function IsLeapYear(nYear) 
{ 
// 如果不是4的倍数，一定不是闰年 
if (nYear % 4 != 0) 
return false; 
// 是4的倍数，但不是100的倍数，一定是闰年 
if (nYear % 100 != 0) 
return true; 
// 是4和100的倍数，如果又是400的倍数才是闰年 
return (nYear % 400 == 0); 
}
