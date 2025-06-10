
document.addEventListener("DOMContentLoaded",function(){

new Swiper(".swiper",{
effect:'coverflow',
grabCursor:true,
centeredSlides:true,
slidesPerView: 'auto',
loop:true,
autoplay:{
delay:3500,
disableOnInteraction:false,},
coverflowEffect:{
rotate:50,
stretch:0,
depth:100,
modifier:1,
slideShadows: true,
},
pagination: {
el: '.swiper-pagination',
clickable:true,
},
navigation: {
nextEl:'.swiper-button-next',
prevEl:'.swiper-button-prev',
},
});
});
