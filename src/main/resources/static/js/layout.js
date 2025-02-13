/**
 * @author (주)한신정보기술 퍼블리셔팀 박윤정
 * @since 2019-05-21
 * @version 1.0.0
 */
try {
	this.mode = '';
	(function($) {
		'use strict';

		window.tag = {};

		//제이쿼리가 있을 때
		if(typeof $ === 'function') {
			var $window = tag.$window = $(window),
				$document = tag.$document = $(document),
				$html = tag.$html = $('html'),
				$head = tag.$head = $('head'),
				$inArray = $.inArray,
				$screen = $.screen,
				/**
				 * @name 탭 메뉴 크기 적용
				 * @since 2017-01-16
				 * @param {element || jQuery} element
				 */
				setTabMenuSize = function(element) {
					$(element).each(function(index, element) {
						var $element = $(element),
							unlock = $element.data('tabUnlock'),
							outerHeight = $element.find(' > .tab_wrap > ul > .active > .tab_content').outerHeight() || '',
							state = $screen.settings.state;

						//값이 없을 때
						if(!unlock) {
							unlock = '';
						}

						unlock = unlock.split(',');

						for(var i = 0, unlockLength = unlock.length; i < unlockLength; i++) {
							//해제 상태가 있을 때
							if($inArray(unlock[i], state) > -1) {
								//초기화
								outerHeight = '';

								//반복 중지
								break;
							}
						}

						//크기 적용
						$element.css('padding-bottom', outerHeight);
					});
				};
			//브라우저
			var _browser = navigator.userAgent.toLowerCase();

			//ie7일 때
			if(_browser.indexOf('msie 7.0') > -1) {
				_browser = 'ie7';

			//ie8일 때
			}else if(_browser.indexOf('msie 8.0') > -1) {
				_browser = 'ie8';

			//ie9일 때
			}else if(_browser.indexOf('msie 9.0') > -1) {
				_browser = 'ie9';

			//ie10일 때
			}else if(_browser.indexOf('msie 10.0') > -1) {
				_browser = 'ie10';

			//ie11일 때
			}else if(_browser.indexOf('trident/7.0') > -1) {
				_browser = 'ie11';

			//edge일 때
			}else if(_browser.indexOf('edge') > -1) {
				_browser = 'edge';

			//opera일 때
			}else if(_browser.indexOf('opr') > -1) {
				_browser = 'opera';

			//chrome일 때
			}else if(_browser.indexOf('chrome') > -1) {
				_browser = 'chrome';

			//firefox일 때
			}else if(_browser.indexOf('firefox') > -1) {
				_browser = 'firefox';

			//safari일 때
			}else if(_browser.indexOf('safari') > -1) {
				_browser = 'safari';
			}else{
				_browser = 'unknown';
			}

			/**
			 * @name 브라우저 얻기
			 * @since 2017-12-06
			 * @return {string}
			 */
			window.getBrowser = function() {
				return _browser;
			};

			//브라우저 클래스 추가
			$html.addClass(_browser);

			$(function() {
				var $body = tag.$body = $('body'),
					$htmlAndBody = tag.$htmlAndBody = $html.add($body),
					$wrapper = tag.$wrapper = $('#wrapper'),
					header = tag.header = {},
					$header = header.$element = $('#header'),
					container = tag.container = {},
					$container = container.$element = $('#container'),
					footer = tag.footer = {},
					$footer = footer.$element = $('#footer');

				$window.on('screen:wide.layout screen:web.layout', function(event) {
					window.mode = 'pc';
				});

				$window.on('screen:tablet.layout screen:phone.layout', function(event) {
					window.mode = 'mobile';
				});

				//lnb
				var lnb = header.lnb = {},
					$lnb = lnb.$element = $header.find('.lnb'),
					$lnbTabItem = lnb.$tabItem = $lnb.find('.tab_item'),
					$lnbNavItem = lnb.$navItem = $lnbTabItem.eq(0),
					$lnbTabContent = lnb.$tabContent = $lnb.find('.tab_content'),
					$lnbNavContent = lnb.$navContent = $lnbTabContent.eq(0),
					$lnbShow = lnb.$show = $lnb.find('.menu_show'),
					$lnbShowBtn = lnb.$showBtn = $lnbShow.find('.menu_button'),
					$lnbHide = lnb.$hide = $lnb.find('.menu_hide'),
					$lnbHideBtn = lnb.$hideBtn = $lnbHide.find('.menu_button'),
					$lnbDepthItem = lnb.$depthItem = $lnb.find('.depth_item'),
					$lnbMenu = lnb.$menu = $lnb.find('.menu'),
					$lnbDepth2FirstChild = lnb.$depth2FirstChild = $lnbMenu.find('.depth2 > :first-child'),
					$lnbSpy = lnb.$spy = $lnbMenu.find('.spy:last'),
					lnbHeight;

				$lnbSpy.parents('.depth_item').addClass('actived');

				function refreshLnbHeight() {
					lnbHeight = $lnbMenu.css('transition-property', 'none').outerHeight() || '';
					$lnbMenu.css('transition-property', '');
				}

				$lnbShowBtn.on('click.layout', function(event) {
					//클래스 토글
					$html.toggleClass('lnb_show');
				});

				$lnbHideBtn.on('click.layout', function(event) {
					//클래스 토글
					$html.removeClass('lnb_show');
				});

				$window.on('screen:wide.layout screen:web.layout', function(event) {
					$lnbTabItem.removeClass('active');
					$lnbNavItem.addClass('active');

					$lnbTabContent.removeClass('active')
					$lnbNavContent.addClass('active');
				});

				$lnbDepthItem.on('mouseover.layout focusin.menu', function(event) {
					if(mode === 'pc') {
						var $this = $(this),
							$depth1Item = ($this.hasClass('depth1_item')) ? $this : $this.parents('.depth1_item');

						if($lnbMenu.hasClass('pulldown')) {
							var maxHeight = 0;

							$lnbDepth2FirstChild.each(function(index, element) {
								var $element = $(element),
									outerHeight = $element.outerHeight() || 0;

								//기존 값 보다 얻은 값이 초과일 때
								if(outerHeight > maxHeight) {
									maxHeight = outerHeight;
								}
							});

							$lnbMenu.height(lnbHeight + maxHeight);
						}else if($lnbMenu.hasClass('eachdown')) {
							$lnbMenu.height(lnbHeight + ($depth1Item.find('.depth2 > :first-child').outerHeight() || ''));
						}

						$html.addClass('lnb_open');
						$lnbDepthItem.removeClass('active');
						$this.addClass('active').parents('li').addClass('active');
					}

					event.stopPropagation();
				}).on('click.layout', function(event) {
					if(mode === 'mobile') {
						var $this = $(this),
							$depthText = $this.children('.depth_text'),
							eventTarget = event.target;

						if($depthText.find(eventTarget).length || $depthText[0] === eventTarget) {
							if($this.hasClass('depth1_item')) {
								if($this.hasClass('active')) {
									$html.removeClass('lnb_open');
								}else{
									$html.addClass('lnb_open');
								}
							}

							if($this.children('.depth').length) {
								$this.toggleClass('active').siblings('.depth_item').removeClass('active');
								event.preventDefault();
							}
						}
					}

					event.stopPropagation();
				}).each(function(index, element) {
					var $element = $(element);

					if($element.children('.depth').length) {
						$element.addClass('has');
					}else{
						$element.addClass('solo');
					}
				});

				 // 20240123 p20
		        $lnbMenu.on('focusout.menu', function(event) {
					if(mode === 'pc') {
						$lnbMenu.height('');
						$html.removeClass('lnb_open');
						$lnbDepthItem.removeClass('active');
					}
				});

				$lnbMenu.on('mouseleave.layout', function(event) {
					if(mode === 'pc') {
						$lnbMenu.height('');
						$html.removeClass('lnb_open');
						$lnbDepthItem.removeClass('active');
					}
				});

				$window.on('screen:wide.layout screen:web.layout', function(event) {
					refreshLnbHeight();

					if($lnbSpy.length) {
						$html.removeClass('lnb_open');
						$lnbSpy.parents('.depth_item').removeClass('active');
					}
				});

				$window.on('screen:tablet.layout screen:phone.layout', function(event) {
					refreshLnbHeight();

					if($lnbSpy.length) {
						$html.addClass('lnb_open');
						$lnbSpy.parents('.depth_item').addClass('active');
					}
				});

				//탭메뉴
				var tabMenu = container.tabMenu = {},
					$tabMenu = tabMenu.$element = $('.tab_menu'),
					$tabWrap = tabMenu.$wrap = $tabMenu.children('.tab_wrap'),
					$tabList = tabMenu.$list = $tabWrap.children('ul'),
					$tabItem = tabMenu.$item = $tabList.children('li'),
					$tabButton = tabMenu.$button = $tabMenu.find('.tab_button');

					$tabButton.on('mouseover.layout focusin.layout', function(event) {
					var hash = this.hash,
						tagName = this.tagName.toLowerCase();

					//해쉬가 있거나 버튼일때
					if(hash || tagName === 'button') {
						var $this = $(this),
							isContent = $this.parents('#sub');

						if(isContent) {
							var targetIndex = $this.parent('.tab_item').index(),
								$tabContent = $tabMenu.find('.tab_content');

							$tabContent.eq(targetIndex).addClass('active').siblings($tabContent).removeClass('active');
						}
						$this.parent('li').addClass('active').siblings('li').removeClass('active');

						//영역 처리
						setTabMenuSize($this.parents('.tab_menu'));

						event.preventDefault();
					}
				});

				//언어
				var $language = $('.language'),
					$languageButton = $language.children('button'),
					$languageLastItemAnchor = $language.find('li').last().children('a');

				$languageButton.on('click.common', function(){
					$language.toggleClass('active');
				});
				$languageLastItemAnchor.on('mouseleave.common focusout.common', function(){
					$language.removeClass('active');
				});

				//커스텀폼 플러그인 시작
				if(typeof $.fn.customForm === 'function') {
					var $customForm = $('.custom_form');

					$customForm.customForm(); //등록

					$(':reset').on('click.customForm', function(event) {
						$(this).closest('form')[0].reset();

						$customForm.customForm('refresh');

						event.preventDefault();
					});
				}

				//검색
				var $search = $header.find('.search'),
					$searchOpenButton = $search.find('.search_open'),
					$searchContent = $search.find('.search_content'),
					$searchCloseButton = $searchContent.find('.search_close');

				$searchOpenButton.on('click.layout', function(event){
					if($search.hasClass('active')) {
						$search.removeClass('active');
					} else {
						$search.addClass('active');
					}
				});
				$searchCloseButton.on('click.layout', function(event){
					$search.removeClass('active');
				});

				//관련사이트
				var $footerSite = $footer.find('.footer_site'),
					$footerSiteItem = $footerSite.find('.site_item'),
					$footerSiteItemButton = $footerSiteItem.children('button'),
					$footerSiteItemList = $footerSiteItem.children('.site_list'),
					$footerSiteItemListLastItemAnchor = $footerSiteItemList.find('li').last().children('a');

				$footerSiteItemButton.on('click.layout', function(event){
					var $this = $(this),
						$targetSiteItem = $this.parent('.site_item');

					if($targetSiteItem.children('.site_list').hasClass('active')) {
						$targetSiteItem.children('.site_list').removeClass('active');
						$targetSiteItem.children('button').removeAttr('title');
					} else {
						$footerSiteItemList.removeClass('active');
						$targetSiteItem.children('.site_list').addClass('active');
						$this.attr('title', '활성');
					}
				});

				/*$footerSiteItemListLastItemAnchor.on('mouseleave.layout focusout.layout', function(){
					$footerSiteItemList.removeClass('active');
				});*/
			});

			$document.on('ready.layout', function(event) {
				/**
				 * @link {https://github.com/JungHyunKwon/screen}
				 */
				$screen({
					state : [{
						name : 'wide',
						horizontal : {
							from : 9999,
							to : 1223
						}
					}, {
						name : 'web',
						horizontal : {
							from : 1222,
							to : 1001
						}
					}, {
						name : 'tablet',
						horizontal : {
							from : 1000,
							to : 641
						}
					}, {
						name : 'phone',
						horizontal : {
							from : 640,
							to : 0
						}
					}]
				});
			});

			$window.on('load.layout', function(event) {
				var $tabItem = tag.container.tabMenu.$item;

				$window.on('screen:resize.layout', function(event) {
					//활성화된 탭 아이템을 역순으로 반복
					$($tabItem.filter('.active').get().reverse()).each(function(index, element) {
						setTabMenuSize($(element).parents('.tab_menu'));
					});
				}).triggerHandler('screen:resize.layout');
			});
		}else{
			throw '제이쿼리가 없습니다.';
		}
	})(window.jQuery);
}catch(e) {
	console.error(e);
}