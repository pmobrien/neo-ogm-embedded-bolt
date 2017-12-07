// object index in the array matches selection in select picker
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

var rx = [
  undefined,
  true,
  false
];

function getScores(gender, ageGroup, rx) {
  $.getJSON(
      '/api/scores',
      {
        gender: gender,
        ageGroup: ageGroup,
        rx
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

function onKeyDownNumberInput() {
  var e = event || window.event;
  var key = e.keyCode || e.which;
  
  if(key !== 8 && key !== 0 && key !== 9 && key < 48 || key > 57) {
    if(e.preventDefault) {
      e.preventDefault();
    }

    e.returnValue = false;
  }
}

function onKeyDownGender() {
  var e = event || window.event;
  var key = e.keyCode || e.which;
  
  // always allow tab
  if(key === 9) {
    return;
  }
  
  // if there's already a value, only allow backspace
  if($('#gender-input').val()) {
    if(key !== 8) {
      if(e.preventDefault) {
        e.preventDefault();
      }

      e.returnValue = false;
    }
    
    return;
  }
  
  // only allow m/f
  if(key !== 70 && key !== 77) {
    if(e.preventDefault) {
      e.preventDefault();
    }
    
    e.returnValue = false;
  }
}

function onScoreSubmit() {
  if(!$('#first-name-input').val() || !$('#last-name-input').val()) {
    $('#submit-message').addClass('error-message');
    $('#submit-message').html('Error: First and last name are required.');
    
    return;
  }
  
  if(!$('#password-input').val()) {
    $('#submit-message').addClass('error-message');
    $('#submit-message').html('Error: Password is required.');
    
    return;
  }
  
  var data = {
    firstName: $('#first-name-input').val(),
    lastName: $('#last-name-input').val(),
    gender: getGenderFromInput(),
    weight: $('#weight-input').val(),
    age: $('#age-input').val(),
    snatch: $('#snatch-input').val(),
    cleanAndJerk: $('#clean-and-jerk-input').val(),
    metcon: $('#metcon-input').val(),
    rx: $('#rx-checkbox')[0].checked
  };
  
  $.ajax({
    url: '/api/scores?' + $.param({ password: $('#password-input').val() }),
    type: 'POST',
    data: JSON.stringify(data),
    contentType: 'application/json',
    dataType: 'json',
    success: function() {
      $('#submit-message').addClass('success-message');
      $('#submit-message').html('Submission successful.');

      clearSubmissionFields();
      getScores(
          divisions[$('#division-selector').selectpicker()[0].selectedIndex].gender,
          divisions[$('#division-selector').selectpicker()[0].selectedIndex].ageGroup,
          rx[$('#rx-selector').selectpicker()[0].selectedIndex]
      );
  
      setTimeout(function() {
        $('#submit-message').html('');
      }, 5000);
    },
    error: function(error) {
      $('#submit-message').addClass('error-message');
      
      if(error.status === 409) {
        $('#submit-message').html(error.responseJSON.message);
      } else {
        console.log(error);
        $('#submit-message').html('Unknown error.');
      }
    }
  });
}

function getGenderFromInput() {
  if(!$('#gender-input').val()) {
    return null;
  }
  
  return $('#gender-input').val() === 'M'
    ? 'MALE'
    : 'FEMALE';
}

function clearSubmissionFields() {
  $('#first-name-input').val('');
  $('#last-name-input').val('');
  $('#gender-input').val('');
  $('#weight-input').val('');
  $('#age-input').val('');
  $('#snatch-input').val('');
  $('#clean-and-jerk-input').val('');
  $('#metcon-input').val('');
  $("#rx-checkbox").prop("checked", true);
  $('#password-input').val('');
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
        data: 'rank'
      },
      {
        data: 'username'
      },
      {
        data: 'snatch'
      },
      {
        data: 'cleanAndJerk'
      },
      {
        data: 'liftTotal'
      },
      {
        data: 'sinclair'
      },
      {
        data: 'metcon'
      },
      {
        data: 'score'
      }
    ],
    createdRow: function(row, data, index) {
      if(data && data.rx === false) {
        // no need to color if viewing only scaled
        if($('#rx-selector').selectpicker()[0].selectedIndex !== 2) {
          $(row).addClass('scaled-cell');
        }
      }
    }
  });
  
  $('#division-selector').selectpicker();
  $('#rx-selector').selectpicker();
  
  $('#division-selector').on('changed.bs.select', function(event, index) {
    getScores(
        divisions[index].gender,
        divisions[index].ageGroup,
        rx[$('#rx-selector').selectpicker()[0].selectedIndex]
    );
  });
  
  $('#rx-selector').on('changed.bs.select', function(event, index) {
    getScores(
        divisions[$('#division-selector').selectpicker()[0].selectedIndex].gender,
        divisions[$('#division-selector').selectpicker()[0].selectedIndex].ageGroup,
        rx[index]
    );
  });
  
  getScores();
});
