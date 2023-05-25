// 페이지가 로드될 때 실행되는 함수
window.addEventListener("DOMContentLoaded", function () {
  // 현재 URL 가져오기
  var currentURL = window.location.href;

  // 모든 sub-link 요소를 가져와서 반복 처리
  var subLinks = document.getElementsByClassName("sub-link");
  for (var i = 0; i < subLinks.length; i++) {
    var subLink = subLinks[i];
    var href = subLink.querySelector("a").getAttribute("href");

    // 현재 URL과 sub-link의 href 속성이 일치하는지 확인
    if (currentURL.includes(href)) {
      // sub-link에 active 클래스 추가
      subLink.classList.add("active");

      // 부모 link 요소를 찾아 active 클래스 추가
      var link = subLink.closest(".link");
      if (link) {
        link.classList.add("active");
      }
    }
  }
});

// 모든 link 요소를 가져와서 반복 처리
var links = document.querySelectorAll(".link");
links.forEach(function (link) {
  // link 요소를 클릭했을 때의 동작을 정의
  link.addEventListener("click", function (event) {
    var isActive = link.classList.contains("active");

    // 현재 클릭한 link 요소가 active 상태이고 클릭한 요소가 link 자신이라면 active 클래스 유지
    if (isActive && event.target === link) {
      event.preventDefault();
      return;
    }

    // 모든 link 요소에서 active 클래스 제거
    links.forEach(function (lnk) {
      lnk.classList.remove("active");
    });

    // 현재 클릭한 link 요소의 active 클래스 토글
    if (!isActive) {
      link.classList.add("active");
    }

    // 현재 클릭한 link 요소의 하위 sub-link 요소들의 active 클래스 제거
    var subLinks = link.querySelectorAll(".sub-link");
    subLinks.forEach(function (subLink) {
      subLink.classList.remove("active");
    });

    // 현재 클릭한 link 요소와 연관된 sub-link 중 현재 URL과 일치하는 sub-link를 찾아 active 클래스 추가
    var currentURL = window.location.href;
    subLinks.forEach(function (subLink) {
      var href = subLink.querySelector("a").getAttribute("href");
      if (currentURL.endsWith(href)) {
        subLink.classList.add("active");

        // 현재 클릭한 sub-link가 속한 link에도 active 클래스 추가
        link.classList.add("active");
      }
    });
  });
});
