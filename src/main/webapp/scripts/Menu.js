function isMultiId(str)
{
	return str.indexOf(",")>0;
}

function isSingleId(str)
{
	return str != "" && !isMultiId(str);
}

function appendQuery(url, query)
{
	if (url.indexOf("?") >= 0)
		return url + "&" + query;
	else
		return url + "?" + query;
}

function gotoWithLessParam(url, params)
{
	var ids = getIds();
	if (ids == "")
	{
		location = url;
	} else if(isSingleId(ids))
	{
		location = appendQuery(url, params + "=" + ids);
    } else
	{
		alert("请单选或不选操作对象！");
    }
}

function gotoWithSingleParam(url, params)
{   
	var ids = getIds();
	if(isSingleId(ids))
	{
		location = appendQuery(url, params + "=" + ids);
    } else
	{
		alert("请单选操作对象！");
    }
}

function gotoWithMultiParam(url, params)
{
	var ids = getIds();
	if (isMultiId(ids))
	{
		location = appendQuery(url, params + "=" + ids);
    } else
	{
        alert("请多选操作对象！");
    }
}

function gotoWithParam(url, params)
{
	var ids = getIds();
	if(ids == "")
	{
	  alert("请选择操作对象！");
    }else
	{
        location = appendQuery(url, params + "=" + ids);
    }
}

function confirmWithParam(url, params)
{
   if(confirm("您确认该操作吗？")){
       gotoWithParam(url, params);
   }
}

function confirmWithSingleParam(url, params)
{
   if(confirm("您确认该操作吗？")){
       gotoWithSingleParam(url, params);
   }
}

function confirmForm(url, form)
{   
	if(confirm("您确认该操作吗？"))
	{
		submitForm(url, form)
	}
}

function confirmFormWithParam(url, form)
{
	if(getIds() == "")
	{
        alert("请选择操作对象！");
        return;
    }
   
    if(confirm("您确认该操作吗？"))
	{
       submitForm(url, form)
	}
}

function submitForm(url, form)
{
     form.action=url;
     form.submit();
}

function submitFormWithParam(url, form)
{
	if(getIds() == "")
	{
        alert("请选择操作对象！");
        return;
     }
      submitForm(url, form)
}
function submitFormWithSingleParam(url, form)
{
    if(!isSingleId(getIds()))
	{
        alert("请单选操作对象！");
        return;
    }
    submitForm(url, form)
}
