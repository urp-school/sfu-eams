<#include "/templates/head.ftl"/>
 <script language="JavaScript" type="text/JavaScript" src="scripts/validator.js"></script>
 <BODY LEFTMARGIN="0" TOPMARGIN="0" >
    <p></p>
    <table id="bar"></table>
    <table class="listTable" width="95%" align="center" cellpadding="5">
        <tr class="darkColumn">
            <td width="5%"></td>
            <td width="19%">教室类别</td>
            <td width="19%" id="f_min">最小座位数</td>
            <td width="19%" id="f_max">最大座位数</td>
            <td width="19%" id="f_price">单价(元/小时)</td>
            <td width="19%">操作</td>
        </tr>
        <form method="post" action="roomPriceCatalogue.do?method=saveConfig" name="selectRoomConfigForm" onsubmit="return false;">
            <input name="roomPriceConfig.catalogue.id"  value="${roomPriceCatalogue.id}" type="hidden"/>
            <input name="params" value="&roomPriceId=${roomPriceCatalogue.id}" type="hidden"/>
            <#list roomPriceCatalogue.prices as config>
                <input type="hidden" name="roomConfigTypeId${config.id?if_exists}" value="${config.roomConfigType.id}"/>
                <tr id="tr${config.id?if_exists}">
                    <td align="center" id="state_${config.id?if_exists}"><input type="radio" name="roomConfigId" value="${config.id?if_exists}"/></td>
                    <td id="select_${config.id?if_exists}"><@i18nName config.roomConfigType/></td>
                    <td id="min_${config.id?if_exists}">${config.minSeats?if_exists}<input type="hidden" name="min_${config.id?if_exists}" value="${config.minSeats?if_exists}"/></td>
                    <td id="max_${config.id?if_exists}">${config.maxSeats?if_exists}<input type="hidden" name="max_${config.id?if_exists}" value="${config.maxSeats?if_exists}"/></td>
                    <td id="price_${config.id?if_exists}">${config.price?if_exists}<input type="hidden" name="price_${config.id?if_exists}" value="${config.price?if_exists}"/></td>
                    <td id="action_${config.id?if_exists}"><button onclick="editPrices(${config.id?if_exists})">修改</button>&nbsp;<button onclick="deletePrice(${config.id?if_exists})">删除</button></td>
                </tr>
            </#list>
        </form>
        <form action="roomPriceCatalogue.do?method=saveConfig" method="post" name="oneRecordRoomConfigForm" onsubmit="return false;">
            <input name="roomPriceConfig.catalogue.id"  value="${roomPriceCatalogue.id}" type="hidden"/>
            <input name="params" value="&roomPriceId=${roomPriceCatalogue.id}" type="hidden"/>
            <tr class="grayStyle">
                <td align="center" id="f_select"><font face="Arial" size="4">+</font></td>
                <td id="f_roomType"><@htm.i18nSelect datas=roomConfigTypes selected="" name="roomPriceConfig.roomConfigType.id"/></td>
                <td><input type="text" name="roomPriceConfig.minSeats" size="15" maxlength="5"/></td>
                <td><input type="text" name="roomPriceConfig.maxSeats" size="15" maxlength="5"></td>
                <td><input type="text" name="roomPriceConfig.price" size="11" maxlength="5"></td>
                <td>&nbsp;<button onClick="saveAddPrice()"><@msg.message key="action.save"/></button></td>
            </tr>
        </form>
    </table>
    <script>
        var bar = new ToolBar("bar","教室级差价目表",null,true,true);
        bar.setMessage('<@getMessage/>');
        bar.addItem("打印预览", "printPrice()");
        
        var formOneRecord = document.oneRecordRoomConfigForm;
        var form = document.selectRoomConfigForm;
        
        function editPrices(id) {
            roomConfigId=form["roomConfigTypeId" + id].value;
            document.getElementById("state_"+id).innerHTML = "<font face=\"宋体\" size=\"4\">&gt;</font>";
            document.getElementById("select_"+id).innerHTML = document.getElementById("f_roomType").innerHTML;
            document.getElementById("min_"+id).innerHTML ="<input type=\"text\" name=\"roomPriceConfig.minSeats\" size=\"15\" value=\"" + form["min_" + id].value + "\">";
            document.getElementById("max_"+id).innerHTML ="<input type=\"text\" name=\"roomPriceConfig.maxSeats\" size=\"15\" value=\"" + form["max_" + id].value + "\">";
            document.getElementById("price_"+id).innerHTML ="<input type=\"text\" name=\"roomPriceConfig.price\" size=\"15\" value=\"" + form["price_" + id].value + "\">";
            document.getElementById("action_"+id).innerHTML ="<button onclick=\"saveEditPrice(document.selectRoomConfigForm)\">保存</button>";
            var childs=document.getElementById("select_" + id).childNodes;
            var select=null;
            for(var i=0;i < childs.length;i++){
               if(childs[i].tagName=="SELECT"){
                   select=childs[i];
                   break;
               }
            }
            setSelected(select,roomConfigId);
            addInput(form,"roomPriceConfig.id", id);
        }
        
        function validateData(dataForm){
            var a_fields = {
                'roomPriceConfig.minSeats':{'l':'最小座位数', 'r':true, 't':'f_min', 'f':'unsigned'},
                'roomPriceConfig.maxSeats':{'l':'最大座位数', 'r':true,  't':'f_max','f':'unsigned'},
                'roomPriceConfig.price':{'l':'单价', 'r':true, 't':'f_price','f':'unsignedReal'}
            };
            var v = new validator(dataForm, a_fields, null);
            if (v.exec()) {
               if(parseInt(dataForm['roomPriceConfig.minSeats'].value)>parseInt(dataForm['roomPriceConfig.maxSeats'].value)){
                  alert("最大和最小座位数数字不对"); 
                  return false;
               }
               return true;
            }else{
               return false;
            }
        }
        function saveAddPrice() {
            if(validateData(formOneRecord)) {
	            formOneRecord.target = "_self"
	            formOneRecord.submit();
            }
        }
        
        function saveEditPrice() {
        	if(validateData(form)) {
                form.submit();
            }
        }
        function deletePrice(id) {
            if (confirm("你确定要删除吗？")) {
                addInput(form,"roomPriceConfig.id", id);
                form.action = "roomPriceCatalogue.do?method=deletePrice";
                form.target = "_self"
                form.submit();
            }
        }
        function printPrice() {
            form.target = "_blank";
            form.action = "roomPriceCatalogue.do?method=priceReport";
            form.submit();
        }
    </script>
 </body>
<#include "/templates/foot.ftl"/>