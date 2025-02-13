(function($) {
    'use strict';

    $(function() {

        var $window = $(window),
            $html = $('html'),
            $footer = $('#footer');

        /* 토글 */
        var $toggle = $('.toggle'),
            $toggleSelector = $toggle.find('[class*="_show"], [class*="_hide"]');

        $toggleSelector.on('click', function (event) {
            var $this = $(this),
                $parent = $this.parents('.toggle'),
                parentClass = $this.closest('.toggle').attr('class').replace(/\s+\active/g,'').split(/\s+/).slice(-2)[0].replace(/_item/,'');

            if($this.is('[class*="_show"]')){
                if ($parent.siblings().hasClass('active')){
                    $parent.siblings().removeClass('active');
                    $html.removeClass(parentClass + '_open');
                }
                $html.toggleClass(parentClass + '_open');
                $parent.toggleClass('active');
            }

            if($this.is('[class*="_hide"]')){
                $html.removeClass(parentClass + '_open');
                $this.closest('.active').removeClass('active');
            }
        });

        /* search */
        var $header = $('#header'),
            $search = $header.find('.search'),
            $searchOpen = $header.find('button.search_open'),
            $searchClose = $search.find('.search_close'),
            $searchPanel = $search.find('.search_panel');

        $searchOpen.on('click', function (){
            $html.addClass('search_open');
            $searchPanel.attr('title', '통합검색 열림');
        });
        $searchClose.on('click', function (){
            $html.removeClass('search_open');
            $searchPanel.attr('title', '닫힘');
        });


        /* 푸터 active 추가 */
        var $footer = $('#footer'),
            $footerSiteButton = $footer.find('.site_button');

        $footerSiteButton.on('click.layout', function(event){
            var $this = $(this),
                $parent = $this.parents('site_item');

            if($this.hasClass('active')){
                $parent.children('.site_button').removeClass('active');
                $this.removeClass('active');
            } else {
                $footerSiteButton.removeClass('active');
                $this.addClass('active');
            }
        });

        //모바일 메인에서 lnb 오픈시 active
        /*setFirstMenu();
        $(window).resize(function(){
            setFirstMenu();
        });
        function setFirstMenu(){
            if($(window).width() < 1001) {
                $('.lnb .depth_list.depth1_list > .depth_item:first-child').addClass('active')
            }
        }*/
    });
})(window.jQuery);
