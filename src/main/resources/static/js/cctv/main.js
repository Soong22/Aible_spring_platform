(function($) {
	'use strict';

	$(function() {
		/* 비주얼 */
		var $visual = $('.visual'),
			$visualList = $visual.find('.visual_list'),
			$visualItem = $visual.find('.visual_item'),
			$visualCurrent = $visual.find('.visual_current'),
			$visualTotal =  $visual.find('.visual_total'),
			$visualProgress =  $('.progress');

		$visualList.slick({
			fade: true,
			autoplay : true,
			dots:false,
			slidesToShow: 1,
			slidesToScroll: 1,
			autoplaySpeed : 5000,
			total: $visualTotal,
			current: $visualCurrent,
			infinite: true,
			prevArrow: $('.visual_prev'),
			nextArrow: $('.visual_next'),
			customState : function(state) {
				var slidestoshow = $visualList.slick('getSlick').options.slidesToShow,
					current = Math.ceil(state.current / slidestoshow),
					total = Math.ceil(state.total / slidestoshow);

				if(current < 10) {
					state.current = '0' + current;
				}
				if(total < 10) {
					state.total = '0' + total;
				}
				return state;
			},
			swipe:false,
			draggable:false,
		}).on("beforeChange", function() {
			$visualProgress.removeClass('on');
		}).on('afterChange', function(event, slick, currentSlide){
			$visualProgress.addClass('on');
			$visualItem.removeClass('active');
			$(this).find('.visual_item').eq(currentSlide).addClass('active');
		});
		$visualItem.eq(0).addClass('active');



		/* info */
		var $infoList = $('.info_list');

		$infoList.slick({
			draggable: true,
			infinite: false,
			slidesToShow:3,
			slidesToScroll: 3,
			arrows: false,
			variableWidth: true,
			responsive: [
				{
					breakpoint: 801,
					settings: {
						slidesToShow: 1,
						slidesToScroll: 1,
						variableWidth: true,
					}
				}
				]


		});


		/* 갤러리 */
		var $galleryList = $('.gallery_list');

		$galleryList.slick({
			draggable: true,
			infinite: true,
			slidesToShow:1,
			slidesToScroll: 1,
			arrows: true,
			variableWidth: true,
			prevArrow: $('.gallery_prev'),
			nextArrow: $('.gallery_next'),

			responsive: [
				{
					breakpoint: 1201,
					settings: {
						slidesToShow: 3,
						variableWidth: true,
					}
				},
				{
					breakpoint: 801,
					settings: {
						slidesToShow:2
					}
				},
				{
					breakpoint: 640,
					settings: {
						slidesToShow:1
					}
				}
			]
		});

		/* fade */
		var $fade = $('#container').find('.fade');
		function fade(){
			$fade.each( function(i){
				var bottom_of_object = $(this).offset().top + $(this).outerHeight();
				var bottom_of_window = $(window).scrollTop() + $(window).height();
				if(bottom_of_window > bottom_of_object/1.3){
					$(this).addClass('show');
				}else{
					$(this).removeClass('show');
				}
			});
		}

		fade();
		$(window).scroll(function(){
			fade();
		});

	});

})(window.jQuery);



