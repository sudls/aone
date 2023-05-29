document.addEventListener("DOMContentLoaded", function () {


    var calendarEl = document.getElementById("calendar");

    var request = $.ajax({
        url:"/getEvent",
        method:"GET"
    });
    request.done(function(data){

    var calendar = new FullCalendar.Calendar(calendarEl, {
      initialView: "dayGridMonth",
      eventClick: function(info) {
          info.jsEvent.preventDefault(); // 기본 동작 중지

        },
       // Add the onSelect event handler
      dateClick: function(info) {
          document.getElementById('selectedDate').textContent = info.dateStr;
          var selectDate = document.getElementById('selectedDate').textContent;
          // AJAX 요청을 보냅니다.
          $.ajax({
              url: "/check",
              method: "GET",
              data: { selectDate: selectDate },
              dataType:"html",
              success: function(response) {
                  // 서버로부터 받은 결과를 처리합니다.
                  let resultDataHtml = $(response).find("#resultData").html();
                  $("#resultData").html(resultDataHtml);

              },
              error: function(error) {
                  console.log(error);
              }
          });
      },
      events: data
    });
    calendar.render();

    });

});