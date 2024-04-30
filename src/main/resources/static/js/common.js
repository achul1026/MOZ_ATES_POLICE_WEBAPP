window.onload = () => {
    MozAtesPoliceApp.setValidateMsg(document);
}

// element.hasClass('className');
HTMLElement.prototype.hasClass = function(className) {
    return (' ' + this.className + ' ').indexOf(' ' + className + ' ') > -1;
};

/**
 	node.empty();
 */
Node.prototype.empty = function(){
    this.innerHTML = "";
}

// 숫자 3자리수 마다 comma
function numberComma(number) {
	if(number == null) return 0;
    return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

// 현재 날짜와 시간을 가져오는 함수
function getCurrentDateTime(date , formatType) {
    // YYYY-MM-DDTHH:MM 형식으로 포맷팅
    const formattedDate = date.getFullYear() + '-' + ('0' + (date.getMonth() + 1)).slice(-2) + '-' + ('0' + date.getDate()).slice(-2);
    const formattedTime = ('0' + date.getHours()).slice(-2) + ':' + ('0' + date.getMinutes()).slice(-2);
    
    switch(formatType){
		case 'datetime-local':
			return formattedDate + 'T' + formattedTime;
		default:
			return formattedDate
	}
}

//스크롤 푸터픽스메뉴 이벤트
window.onload = function(){
	let fixMenu = document.getElementById('fixed-menu');
	let lastTouchY = 0;
	let isScrolling = false;

	window.addEventListener('touchstart', function(event) {
		lastTouchY = event.touches[0].clientY;
		isScrolling = false;
	});

	window.addEventListener('touchmove', function(event) {
		let currentY = event.touches[0].clientY;
		let deltaY = currentY - lastTouchY;
		lastTouchY = currentY;

		if (deltaY > 0) {
			fixMenu.style.bottom="0px";
			fixMenu.style.transition="0.5s";
		} else {
			fixMenu.style.bottom="-70px";
			fixMenu.style.transition="0.5s";
		}

		isScrolling = true;
	});
}
	
