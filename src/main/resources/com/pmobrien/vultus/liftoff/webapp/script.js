function get() {
  $.get("/api/scores", function(data) {
    console.log(data);
  });
}
