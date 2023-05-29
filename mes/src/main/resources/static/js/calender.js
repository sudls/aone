document.addEventListener("DOMContentLoaded", function () {


    var calendarEl = document.getElementById("calendar");

    var request = $.ajax({
        url:"/getEvent",
        method:"GET"
    });
    request.done(function(data){
        console.log(JSON.stringify(data,null,2))

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
      eventContent: function(arg) {
        var textColor = (arg.event.extendedProps.status === 'A') ? '#04ba71' : '#3196e2';

          var content = document.createElement('div');
          content.style.backgroundColor = ""; //배경 투명
          content.style.color = textColor;
          content.style.textAlign = "left";
          content.style.border="none";
          content.innerHTML ='<i class="fa-solid fa-circle fa-2xs"></i>　' + arg.event.title;

          return { domNodes: [content] };
        },
      events: data,
    });
    calendar.render();

    });

});