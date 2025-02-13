(function($) {
	'use strict';

	$(function() {

		var $window = $(window),
			$container = $('#container');

		/* 반응형 테이블 */
		var $tableResponsive = $container.find('.table.responsive');

		$tableResponsive.each(function(index, element) {
			var $element = $(element),
				rowdivIs = $element.find('td, th').is('[rowdiv]'),
				theadLength = $element.find('thead').length;

			if(rowdivIs == false && !theadLength == 0){
				$element.find('tbody th, tbody td').each(function(index, element) {
					var $this = $(element),
						thisIndex = $this.index(),
						theadText = $this.parents('tbody').siblings('thead').find('th').eq(thisIndex).text();

					$this.attr('data-content', theadText);
				});

				$element.find('tfoot th, tfoot td').each(function(index, element) {
					var $this = $(element),
						thisIndex = $this.index(),
						theadText = $this.parents('tfoot').siblings('thead').find('th').eq(thisIndex).text();

					$this.attr('data-content', theadText);
				});
			}
		});

		/* 주소복사 */
		var $url = $('.addons').find('.link'),
				$urlBtn = $url.find('.addons_button'),
				url = window.location.href;

		function copyToClipboard(val) {
			const t = document.createElement("textarea");
			document.body.appendChild(t);
			t.value = val;
			t.select();
			document.execCommand('copy');
			document.body.removeChild(t);
		}
		$urlBtn.on("click", function(){
			copyToClipboard(url);
			alert("클립보드에 복사되었습니다. \n원하는 곳에 'ctrl+v'를 사용하여 붙여넣기 하세요.");
		})


		/*컨텐츠 슬릭 8210*/


		var $example = $('.example'),
			$exampleList = $example.find('.example_list'),
			$exampleItem = $example.find('.example_item'),
			$examplePrev = $example.find('.example_prev'),
			$exampleNext = $example.find('.example_next');
		$exampleList.slick({
				infinite: false,
				draggable: false,
				autoplay: false,
				slidesToShow: 4,
				slidesToScroll: 1,
				arrows: true,
				prevArrow: $examplePrev,
				nextArrow: $exampleNext,
				responsive: [
					// 반응형 설정
					{
						breakpoint: 1001,
						settings: {
							slidesToShow: 3,
							draggable: false,
						},
					},
					{
						breakpoint: 801,
						settings: {
							slidesToShow: 2,
							draggable: false,
						},
					},
					{
						breakpoint: 501,
						settings: {
							slidesToShow: 1,
							draggable: false,
						},
					},
				],
			});

			var $tabcontent = $('.tabcontent'),
					$tabItem = $tabcontent.find('.tabitem');


			$window.on('load screen', function () {
				if ($(window).width() < 500) {
					$exampleList.off();
					$exampleList.on("afterChange", function (slick, currentSlide) {
						$exampleItem.removeClass('active').eq(currentSlide.currentSlide).addClass('active');
						$tabItem.siblings().removeClass('active').eq(currentSlide.currentSlide).addClass('active');
					})
				} else {
					$exampleItem.off();
					$exampleItem.on("click", function (event) {
						var index = $exampleItem.index(this);

						$exampleItem.removeClass('active').eq(index).addClass('active');
						$tabItem.siblings().removeClass('active').eq(index).addClass('active');
					});
				}
			});

	});
})(window.jQuery);
