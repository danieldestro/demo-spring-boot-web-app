GitHub project: https://github.com/kamranahmedse/jquery-toast-plugin

Demo: http://kamranahmed.info/toast

How to use:
 - in our html include the CSS file and the JavaScript file ("toast.min.js").
 - use either "toast.min.css" default style or "toast.custom.css"


Example:

$.toast({ 
  text : "Let's test some HTML stuff... <a href='#'>github</a>", 
  showHideTransition : 'slide',  // It can be plain, fade or slide
  bgColor : 'blue',              // Background color for toast
  textColor : '#eee',            // text color
  allowToastClose : false,       // Show the close button or not
  hideAfter : 5000,              // `false` to make it sticky or time in miliseconds to hide after
  stack : 5,                     // `fakse` to show one stack at a time count showing the number of toasts that can be shown at once
  textAlign : 'left',            // Alignment of text i.e. left, right, center
  position : 'bottom-left'       // bottom-left or bottom-right or bottom-center or top-left or top-right or top-center or mid-center or an object representing the left, right, top, bottom values to position the toast on page
});

Resetting the toast

var myToast = $.toast('Some toast that needs to be removed.');
myToast.reset(); // remove the toast "Some toast that needs to be removed"

Resetting all toasts

$.toast().reset('all');

Updating a toast

myToast.update({
    text : '<strong>Updated message</strong>',
    bgColor : '#23B65D'
});