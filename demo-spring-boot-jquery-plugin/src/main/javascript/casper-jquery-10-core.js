// Turns on (if localhost) or off (other hostname) console log methods for: trace, debug, info, log, warn, error 

(function() {
    var __loglevel = 1;
    var __logenabled = window.location.hostname == 'localhost' && __loglevel > 0;
    var __voidMethod = function() {};
    if(!window.console) {
        console = {};
    }
    var __logMethod = console.log || __voidMethod;
    console.trace = (__logenabled && __loglevel<=1) ? __logMethod || __voidMethod : __voidMethod; // level 1
    console.debug = (__logenabled && __loglevel<=2) ? __logMethod || __voidMethod : __voidMethod; // level 2
    console.info =  (__logenabled && __loglevel<=3) ? __logMethod || __voidMethod : __voidMethod; // level 3
    console.log =   (__logenabled && __loglevel<=4) ? __logMethod || __voidMethod : __voidMethod; // level 4
    console.warn =  (__logenabled && __loglevel<=5) ? __logMethod || __voidMethod : __voidMethod; // level 5
    console.error = (__logenabled && __loglevel<=6) ? __logMethod || __voidMethod : __voidMethod; // level 6
})();

// Creates the endsWith method if it is not provided / supported by the browser (IE)
if (typeof String.prototype.endsWith !== 'function') {
    String.prototype.endsWith = function(suffix) {
        return this.indexOf(suffix, this.length - suffix.length) !== -1;
    };
}

//Creates the startsWith method if it is not provided / supported by the browser (IE)
if (typeof String.prototype.startsWith !== 'function') {
    String.prototype.startsWith = function(suffix) {
        return this.indexOf(prefix) === 0;
    };
}
