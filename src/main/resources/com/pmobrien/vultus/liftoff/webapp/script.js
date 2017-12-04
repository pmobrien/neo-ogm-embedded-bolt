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
      function(data) {
        table.fnClearTable();

        for(var i = 0; i < data.length; ++i) {
          // Data always comes back sorted by top score first.
          // Using that to inject rank here.
          data[i].rank = i + 1;
          
          table.oApi._fnAddData(table.fnSettings(), data[i]);
        }

        table.fnDraw();
        table.fnSort([[0, 'asc']]);
      }
  );
}

$(document).ready(function() {
  table = $('#scores-table').dataTable({
    paging: false,
    searching: false,
    info: false,
    language: {
      emptyTable: '',
      zeroRecords: ''
    },
    columns: [
      {
        width: '10%',
        data: 'rank'
      },
      {
        width: '30%',
        data: 'username'
      },
      {
        width: '10%',
        data: 'snatch'
      },
      {
        width: '10%',
        data: 'cleanAndJerk'
      },
      {
        width: '10%',
        data: 'metcon'
      },
      {
        width: '20%',
        data: 'score'
      }
    ]
  });
  
  $('#division-selector').selectpicker();
  
  $('#division-selector').on('changed.bs.select', function(event, index) {
    getScores(divisions[index].gender, divisions[index].ageGroup);
  });
  
  getScores();
});
