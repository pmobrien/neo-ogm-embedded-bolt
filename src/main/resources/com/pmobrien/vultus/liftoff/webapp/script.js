function getScores() {
  $.getJSON('/api/scores', null, function(json) {
    table.fnClearTable();

    for(var i = 0; i < json.length; ++i) {
      table.oApi._fnAddData(table.fnSettings(), json[i]);
    }
    
    table.fnDraw();
    table.fnSort([[4, 'desc']]);
  });
}

$(document).ready(function() {
  table = $('#scores_table').dataTable({
    paging: false,
    searching: false,
    info: false,
    language: {
      emptyTable: '',
      zeroRecords: ''
    },
    columns: [
      { data: 'username' },
      { data: 'snatch' },
      { data: 'cleanAndJerk' },
      { data: 'metcon' },
      { data: 'score' }
    ]
  });
  
  getScores();
});
