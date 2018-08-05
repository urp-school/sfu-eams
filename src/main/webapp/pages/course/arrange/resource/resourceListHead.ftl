<BODY>
  <table id="staticBar"></table>
  <script>
    var bar = new ToolBar("staticBar","<@bean.message key="common.list"/>",null,true,true);
    var menu=bar.addMenu("占用详情","self.parent.getOccupyInfo(null,0)");
    menu.addItem("占用详情(合并)","self.parent.getOccupyInfo(null,1)");
    bar.addItem("占用汇总","parent.getStatisInfo(1)");
    bar.addItem("空闲汇总","parent.getStatisInfo(0)");
  </script>