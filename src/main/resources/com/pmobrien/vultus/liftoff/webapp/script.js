var divisions = [
  {},
  { gender: 'MALE' },
  { gender: 'MALE', ageGroup: 'GROUP_0_39' },
  { gender: 'MALE', ageGroup: 'GROUP_40_54' },
  { gender: 'MALE', ageGroup: 'GROUP_55_PLUS' },
  { gender: 'FEMALE' },
  { gender: 'FEMALE', ageGroup: 'GROUP_0_39' },
  { gender: 'FEMALE', ageGroup: 'GROUP_40_54' },
  { gender: 'FEMALE', ageGroup: 'GROUP_55_PLUS' }
];

function getScores(gender, ageGroup) {
  $.getJSON(
      '/api/scores',
      {
        gender: gender,
        ageGroup: ageGroup
      },
      function(json) {
        table.fnClearTable();

        for(var i = 0; i < json.length; ++i) {
          table.oApi._fnAddData(table.fnSettings(), json[i]);
        }

        table.fnDraw();
        table.fnSort([[4, 'desc']]);
      }
  );
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
      {
        width: '25%',
        data: 'username'
      },
      {
        width: '16%',
        data: 'snatch'
      },
      {
        width: '17%',
        data: 'cleanAndJerk'
      },
      {
        width: '16%',
        data: 'metcon'
      },
      {
        width: '25%',
        data: 'score'
      }
    ]
  });
  
  $('#division_selector').selectpicker();
  
  $('#division_selector').on('changed.bs.select', function(event, index) {
    getScores(divisions[index].gender, divisions[index].ageGroup);
  });
  
  getScores();
});
