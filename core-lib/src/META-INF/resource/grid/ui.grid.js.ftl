<script>

var _before_select_page = 1;

function ${clearFunction}() {
	$("#rowCount").html( '0' );
	$("#sizeShow").html( '1' );
	$("#pageSize").val( '1' );
	$("#showRow").val( '10' );
	$("#select").val( '1' );
	_before_select_page = 1;
	hiddenQueryGridToolBarTable();
	$("#${id}").html( '' );	
}

/**
 * 不顯示換頁TABLE
 */
function hiddenQueryGridToolBarTable() {
	$("#${id}Toolbar").css( "display", "none" );
}

/**
 * 顯示換頁TABLE
 */
function showQueryGridToolBarTable() {
	$("#${id}Toolbar").css( "display", "" );
}

function changeQueryGridPageOfSelect() {
	if ( !( /^\+?(0|[1-9]\d*)$/.test( $("#select").val() ) ) ) { // not a page number
		$("#select").val("1");
	}
	var page = parseInt( $("#select").val() );
	if ( isNaN(page) || page <= 0 ) { // not a page number
		$("#select").val("1");
	}
	if (page>( parseInt( $("#pageSize").val(), 10) || 1 ) ) { // 頁面最小要是1
		page=( parseInt( $("#pageSize").val(), 10) || 1 );
		$("#select").val( page+'' );
	}
	// ----------------------------------------------------------------------------
	
	if ( _before_select_page != page ) {
		${queryFunction}();
	}		
	
}
function changeQueryGridPageOfShowRow() {
	$("#select").val("1");
	${queryFunction}();	
}
function getQueryGridShowRow() {
	return $("#showRow").val();
}
function getQueryGridSelect() {
	return $("#select").val();
}
/**
 * 到第1頁icon click
 */
function changeQueryGridToFirst() {	
	$("#select").val("1");
	${queryFunction}();
}

/**
 * 到最後1頁icon click
 */
function changeQueryGridToLast() {
	$("#select").val( $("#pageSize").val() );
	${queryFunction}();
}

/**
 * 到上1頁icon click
 */
function changeQueryGridToPrev() {
	var page=( parseInt( $("#select").val(), 10 ) || 0 )-1;
	if (page<=0) {
		page=1;
	}
	$("#select").val( page+'' );
	${queryFunction}();
}

/**
 * 到下1頁icon click
 */
function changeQueryGridToNext() {
	var page=( parseInt( $("#select").val(), 10) || 0 )+1;
	if (page>( parseInt( $("#pageSize").val(), 10) || 1 ) ) { // 頁面最小要是1
		page=( parseInt( $("#pageSize").val(), 10) || 1 );
	}
	$("#select").val( page+'' );
	${queryFunction}();
}

function ${queryFunction}() {
	xhrSendParameterForQueryGrid(
			'${xhrUrl}', 
			${xhrParameter}, 
			function(data) {
				if ( _qifu_success_flag != data.success) {
					
					${clearFunction}();
					
					parent.toastrInfo( data.message ); //parent.toastrWarning( data.message );
					return;
				}
				
				var str = '<table class="table">';
				str += '<thead class="thead-inverse">';
				str += '<tr>';
				var girdHead = ${gridFieldStructure};
				for (var i=0; i<girdHead.length; i++) {
					str += '<th>' + girdHead[i].name + '</th>';
				}
				str += '</tr>';
				str += '</thead>';
				str += '<tbody>';
				
				for (var n=0; n<data.value.length; n++) {
					str += '<tr>';
					for (var i=0; i<girdHead.length; i++) {
						var f = girdHead[i].field;
						var val = data.value[n][f];		
						
						
						if ( !(typeof girdHead[i].formatter == 'undefined') && (typeof girdHead[i].formatter === 'function') ) {
							
							str += '<td>' + girdHead[i].formatter(val) + '</td>';
							continue;
						}
						
						
						if($.type(val) === "string") {
							str += '<td>' + val.replace(/</g, "&lt;").replace(/>/g, "&gt;"); + '</td>';
						} else {
							str += '<td>' + val + '</td>';
						}
					}			
					str += '</tr>';
				}
				
				str += '</tbody>';
				str += '</table>';
				
				
				$("#rowCount").html( data.pageOfCountSize );
				$("#sizeShow").html( data.pageOfSize );
				$("#pageSize").val( data.pageOfSize );
				$("#select").val( data.pageOfSelect );
				_before_select_page = data.pageOfSelect;
				
				showQueryGridToolBarTable();
				
				$("#${id}").html( str );
			}, 
			function(){
				${clearFunction}();
			},
			'${selfPleaseWaitShow}'
	);
}    	

</script>
