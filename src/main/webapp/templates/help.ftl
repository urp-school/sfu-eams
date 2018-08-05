<table id="backBar"></table>
<script>
   var bar = new ToolBar('backBar','${labInfo}',null,true,true);
   bar.setMessage('<@getMessage/>');
   bar.addHelp("<@msg.message key="action.help"/>", "${module?if_exists}");
</script>