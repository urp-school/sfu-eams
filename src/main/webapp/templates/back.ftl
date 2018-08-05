<table id="backBar" width="100%"></table>
<script>
	var bar = new ToolBar('backBar','${labInfo}',null,true,true);
	bar.setMessage('<@getMessage/>');
	bar.addBackOrClose("<@msg.message key="action.back"/>", "<@msg.message key="action.close"/>");
</script>