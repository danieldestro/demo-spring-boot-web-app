
// Casper JQuery Plugin implementation

(function($, window) {
    console.debug('Casper JQuery Plugin Loading...');
    
    /**
     * Casper JQuery Plugin implementation class.
     */
    function CasperPlugin() {
        
        var self = this;
    
        var _global_settings = {
            
            /* this is the application's web context name (i.e. /admin) */
            context: '',
            contextParam: '_casper_context',
            
            pageType: null, // use 'page', 'crud' (or any other future implementation - see stereotypes)
            pageController: null, // Page controller
            
            stereotypes: {
                page: {
                    controller: CasperPageStereotypeController
                },
                crud: {
                    controller: CasperCrudStereotypeController
                }
                // add new stereotypes here to extend the plugin
            },
            
            spinner: {
                effect : 'win8_linear', //win8, ios
                text : 'loading...',
                bg : 'rgba(255,255,255,0.7)',
                color : '#000'
            },
            
            defaults: {
                banner: {
                    type: 'toaster' //alert or toaster
                }
            },
            
            alert: {
                container: '#box-alert-message',
                conf: {
                    method: 'html',
                    hideAfter: 5000, // milliseconds do hide or false
                    fadeOut: 500 // milliseconds that fade out will last for
                },
                templates: {
                    error: 'alert-error-template',
                    warn: 'alert-warn-template',
                    success: 'alert-success-template',
                    info: 'alert-info-template'
                }
            },
            
            toaster: {
                conf: {
                    stack: 5,
                    hideAfter: 5000, // milliseconds do hide or false
                    allowToastClose: true,
                    position: 'top-right',
                    showHideTransition: 'fade'
                },
                templates: {
                    error: {
                        loaderBg: '#ff6849',
                        icon: 'error',
                        heading: 'Error'
                    },
                    warn: {
                        loaderBg: '#ff6849',
                        icon: 'warning',
                        heading: 'Warning'
                    },
                    success: {
                        loaderBg: '#ff6849',
                        icon: 'success',
                        heading: 'Success'
                    },
                    info: {
                        loaderBg: '#ff6849',
                        icon: 'info',
                        heading: 'Info'
                    }
                }
            },
            
            dialogs: {
                confirm: {
                    buttons: {
                        confirm: {
                            label: 'Yes',
                            className: 'btn-success'
                        },
                        cancel: {
                            label: 'No',
                            className: 'btn-danger'
                        }
                    }
                }
            },
            
            pagination: {
                maxPages: 3
            },
            
            htmlElements: {
                'select': {
                    load: 'loadDataIntoSelect'
                }
            }
        };
        
        /**
         * Return the web context configured.
         */
        self.getWebContext = function() {
            return self.settings().context;
        };
        
        /**
         * This method allow to overwrite or change Casper JQuery Plugin settings.
         * All parameters are based on the "_global_settings" structure.
         * If a parameter is not informed it will remain unchanged.
         * 
         * @param options settings to overwrite the default ones.
         * 
         * @returns Casper JQuery Plugin configurations
         */
        self.settings = function(options) {
            var config = $.extend(true, _global_settings, options);
            return config;
        };
        
        /**
         * Do the pagination component for the UI.
         * Spring pagination starts with zero (stupid developers).
         * 
         * @param container container element for the pagination items
         * @param page object (json format) with page details 
         *      (attributes: first, last, number, totalPages, size, numberOfElements, totalElements)
         *      [see Spring Page for more details on the attributes]
         * @param callback callback method called when a click event happens for each of the pagination elements
         */
        self.pagination = function(container, page, callback) {
            $(container).empty().off('*');
            if(!page) {
                $(container).html('<li>pagination settings not found</li>');
                return;
            }

            var pages = [];
            var first = '<a href="#"><i class="fa fa-angle-double-left"></i></a>';
            var previous = '<a href="#"><i class="fa fa-angle-left"></i></a>';
            var next = '<a href="#"><i class="fa fa-angle-right"></i></a>';
            var last = '<a href="#"><i class="fa fa-angle-double-right"></i></a>';

            var maxPages = self.settings().pagination.maxPages;
            var offset = parseInt(maxPages / 2);
            var currentPageIndex = 0;
            var startPageIndex = 0;
            var endPageIndex = 0;

            if(page.number !== undefined) {
                currentPageIndex = page.number;
            }
            
            if(!page.totalPages) {
                maxPages = 1;
                
            } else if(page.totalPages <= maxPages) {
                maxPages = page.totalPages;
                endPageIndex = page.totalPages;
                
            } else {
                var diff = currentPageIndex - offset;
                if(diff < 0) {
                    startPageIndex = 0;
                } else if((currentPageIndex+1) >= page.totalPages) {
                    startPageIndex = currentPageIndex - maxPages + 1;
                } else {
                    startPageIndex = diff;
                }
                
                endPageIndex = startPageIndex + maxPages;
                if(endPageIndex >= page.totalPages) {
                    endPageIndex = page.totalPages;
                }
            }
            
            var isActive = false;
            var isDisabled = false;
            
            isDisabled = !(page.first === undefined || page.first === false);
            first = buildPaginationElem(first, false, isDisabled, 0, callback);
            
            isDisabled = !(page.last === undefined || page.last === false);
            last = buildPaginationElem(last, false, isDisabled, page.totalPages-1, callback);
            
            isDisabled = (currentPageIndex <= 0);
            previous = buildPaginationElem(previous, false, isDisabled, currentPageIndex-1, callback);
            
            isDisabled = (!page.totalPages || (currentPageIndex+1 >= page.totalPages));
            next = buildPaginationElem(next, false, isDisabled, currentPageIndex+1, callback);
            
            for(var i=startPageIndex; i<endPageIndex; i++) {
                isActive = i == currentPageIndex;
                pages.push(buildPaginationElem('<a href="#">'+(i+1)+'</a>', isActive, false, i, callback));
            }
            
            var elems = [first, previous];
            elems = elems.concat(pages);
            elems = elems.concat([next, last]);
            
            $(container).append(elems);
        };
        
        /**
         * Build Pagination element (buttons) for the pagination component.
         * 
         * @param elem html to build the element
         * @param isActive if the element is active
         * @param isDisabled if the element is disabled
         * @param pageNum page number that the element represents
         * @param callback callback method to be called on a Click event
         * 
         * @returns the html element created
         */
        function buildPaginationElem(elem, isActive, isDisabled, pageNum, callback) {
            var cssClass = (isDisabled ? 'disabled ' : '') + (isActive ? 'active ' : '');
            var classAttr = cssClass.length > 0 ? 'class="'+cssClass+'"' : '';
            
            var liveElem = $(elem);
            liveElem.attr('page-num', pageNum);
            
            if(callback && !isDisabled) {
                liveElem.on('click', function() {
                    var pageNum = $(this).attr('page-num');
                    callback(pageNum);
                });
            }
            return $('<li '+classAttr+'></li>').append(liveElem);
        }
        
        /**
         * Handles HTTP errors for API (Ajax) calls and display an alert (error) banner.
         * 
         * @param xhr
         * @param message
         * @param exception
         */
        self.errorHandling = function(xhr, message, exception) {
            var msg = '';
            var status = xhr.status;
            var responseText = xhr.responseText;
            var sendToLoginPage = false;
            
            switch (status) {
            case 0:
                msg = 'Not connecting, verify network';
                break;
            case 401:
                sendToLoginPage = true;
                msg = 'User not authenticated [401]';
                break;
            case 403:
                msg = 'User not authorized to perform this operation [403]';
                break;
            case 404:
                msg = 'Resource not found [404]';
                break;
            case 409:
            case 412:
                var res = $.parseJSON(responseText);
                if(res.length) {
                    res = res[0];
                }
                msg = res.message;
                if(!msg) {
                    msg = res.defaultMessage;
                }
                break;
            case 500:
                msg = 'Internal server error [500]';
                break;
            default:
                if (exception === 'parsererror') {
                    msg = 'Requested JSON parse failed';
                } else if (exception === 'timeout') {
                    msg = 'Time out error';
                } else if (exception === 'abort') {
                    msg = 'Ajax request aborted';
                } else {
                    msg = 'Uncaught error:\n' + responseText;
                }
            }

            if(sendToLoginPage) {
                // unauthenticated users or users with expired session
                var errData = {
                    title: 'User not authenticated',
                    message: 'You will be redirect to the login page'
                };
                self.dialog(errData, function() {
                    window.location.href=self.getWebContext()+'/login';
                });
                
            } else {
                $.Casper.banner('error', {code:status, err:message, message:msg}, { hideAfter: false });
            }
        };
        
        /**
         * configureWebContext
         */
        function configureWebContext() {
            var param = self.settings().contextParam;
            var context = $('meta[name=\"'+param+'\"]').attr('content');
            if(!context || context.length <= 0) {
                throw 'Web context name was not set in the meta tag ['+param+']';
            }
            if(context.endsWith('/')) {
                context = context.substring(0,context.length-1);
            }
            self.settings().context = context;
            console.trace('Web context: '+context);
        }
        
        /**
         * Read CSRF Token from meta headers (which are writen by Spring Security tag <sec:csrfMetaTags />) and 
         * loads it into default JQuery Ajax configurations. So, every time a Ajax call is made with JQuery it
         * will send CSRF token as a HTTP Request Header (according to the name provided by Spring Security).
         * This method has to be called upon page load (init) to take effect.
         */
        function configureCsrfForAjax() {
            var csrf_header = $('meta[name=\"_csrf_header\"]').attr('content');
            var csrf_token = $('meta[name=\"_csrf\"]').attr('content');
            if(!csrf_header) {
                console.warn('CSRF not found on page header');
            } else {
                $(document).ajaxSend(function(e, xhr, options) {
                    xhr.setRequestHeader(csrf_header, csrf_token);
                });
            }
        }
        
        /**
         * Loads all the Mustache JS templates for displaying alert banners.
         * See: "_global_settings.alert.templates" section above.
         */
        function loadTemplatesAlert() {
            self.loadTemplates(self.settings().alert.templates);
        }
        
        /**
         * Loads all the Mustache JS templates. All javascript templates must be declared in the HTML,
         * according to the names configured in the "_global_settings.<xyz>.templates" section above.
         * Mustache JS library is required.
         * 
         * @param templates template configuration object with all template names as attributes
         * @param ignoreMissing if true ignores not found templates, if true it will fail if template not found
         */
        self.loadTemplates = function(templates, ignoreMissing) {
            for( var template in templates ) {
                self.loadTemplate(templates[template], ignoreMissing);
            }
        };
        
        /**
         * Loads a specific Mustache JS template. The Javascript template must be declared in the HTML, 
         * according to the names configured in the "_global_settings.<xyz>.templates" section above.
         * 
         * @param template names
         * @param ignoreMissing if true ignores a not found template, otherwise raises an error.
         */
        self.loadTemplate = function(template, ignoreMissing) {
            if(ignoreMissing) {
                var templateElement = document.getElementById(template);
                if (templateElement === null) {
                    console.trace('Mustache template not found: '+template);
                    return;
                }
            }
            $.Mustache.addFromDom(template);
            console.trace('Mustache template found: '+template);
        };
        
        /**
         * Displays an alert banner with the message passed as argument.
         * 
         * @param type banner type: error, warn, success, info
         * @param message message (or data structure) to be displayed
         * @param options [optional] configurations to change default behavior
         * 
         * @returns a reference to the plugin itself
         */
        self.alert = function(type, message, options) {
            var container = self.settings().alert.container;
            var template = self.settings().alert.templates[type];
            
            var data = message;
            if(typeof message === 'string') {
                data = {
                    message : message
                };
            }
    
            // must clone the object first, otherwise options will override global settings
            var globalSettings = $.extend(true, {}, self.settings().alert.conf);
            var config = $.extend(true, globalSettings, options);
            
            $(container).mustache(template, data, config);
            $(container).show();

            if(config.hideAfter && !(config.hideAfter === false)) {
                $(container).delay(config.hideAfter).fadeOut(config.fadeOut);
            }
            
            return self;
        };
        
        /**
         * Displays an alert toaster with the message passed as argument.
         * 
         * @param type banner type: error, warn, success, info
         * @param message message (or data structure) to be displayed
         * @param title [optional] title (heading) to be displayed
         * @param options [optional] configurations to change default behavior
         */
        self.toaster = function(type, message, title, options) {
            // must clone the object first, otherwise options will override global settings
            var globalSettings = $.extend(true, {}, self.settings().toaster.conf);
            var config = $.extend(true, globalSettings, options);
            
            if(typeof message !== 'string') {
                // just in case the message parameter is a json data structure, not a string
                message = message.message;
            }
            
            var template = self.settings().toaster.templates[type];
            config.showHideTransition = template.showHideTransition;
            config.loaderBg = template.loaderBg;
            config.heading = template.heading;
            config.icon = template.icon;
            
            if(title && title !== '') {
                config.heading = title;
            }
            config.text = message;
            
            $.toast(config);
        };
        
        /**
         * Displays an banner (alert or toaster) with the message passed as argument.
         * This is a convenience method to call one of the banner types (alert or toaster) according
         * to the Casper JQuery Plugin configuration.
         * 
         * @param type banner type: error, warn, success, info
         * @param message message (or data structure) to be displayed
         * @param options [optional] configurations to change default behavior
         */
        self.banner = function(type, message, options) {
            var bannerType = self.settings().defaults.banner.type;
            if(bannerType === 'toaster') {
                $.Casper.toaster(type, message, null, options);
            } else {
                $.Casper.alert(type, message, options);
            }
        };
        
        /**
         * Apply UI template (with Mustache).
         * 
         * @param container element where the template will be applied to
         * @param template mustache template name
         * @param data data to be applied to the template
         */
        self.applyTemplate = function(container, template, data) {
            $(container).mustache(template, data, {
                method : 'html'
            });
        };
        
        /**
         * Display a spinner wheel (loading icon) within a HTML container.
         * WaitMe JQuery Plugin is required.
         * 
         * @param element jquery selector for the element where the spinner will be displayed on
         */
        self.spinner = function(element) {
            $(element).waitMe(self.settings().spinner);
            return self;
        };
        
        /**
         * Hide/Remove the spinner wheel (loading icon) from a HTML container.
         * WaitMe JQuery Plugin is required.
         * 
         * @param element jquery html element where the spinner will be removed from
         * 
         * @returns a reference to the plugin itself
         */
        self.hideSpinner = function(element) {
            $(element).waitMe('hide');
            return self;
        };
        
        /**
         * Shows the user a modal dialog to inform something.
         * 
         * @param message text message to display to the user
         * @param callback callback method to execute after the user acknowledges the dialog (OK or ESC)
         * 
         * @returns a reference to the plugin itself
         */
        self.dialog = function(message, callback) {
            // http://bootboxjs.com/examples.html
            if(typeof message === 'string') {
                bootbox.alert(message, callback);
            } else {
                message.callback = callback;
                bootbox.alert(message);
            }
            return self;
        };
        
        /**
         * Shows the user a confirm modal dialog before proceeding with any action.
         * 
         * @param message text message to display to the user
         * @param callbackConfirm callback method to execute if the user confirms
         * @param callbackCancel callback method to execute if the user does not confirm
         * 
         * @returns a reference to the plugin itself
         */
        self.confirm = function(message, callbackConfirm, callbackCancel) {
            var dialogCallback = function(result) {
                if(result) {
                    if(callbackConfirm) {
                        callbackConfirm();
                    }
                } else if(callbackCancel) {
                    callbackCancel();
                }
            };
            // customized buttons
            var buttons = self.settings().dialogs.confirm.buttons;
            var config = {
                message: message,
                buttons: buttons,
                callback: dialogCallback
            };
            
            bootbox.confirm(config);
            return self;
        };
        
        self.loadDataIntoSelect = function(elementSelector, data, omitBlank) {
            var elem = $(elementSelector);

            var selectedItem = $(elementSelector+' option:selected');
            var selectedIndex = selectedItem ? selectedItem.index() : 0;
            var selectedVal = selectedItem.val();
            
            // clear all existing options
            elem.html('');
            
            if(!omitBlank) {
                // add first element (blank option)
                elem.append(new Option());
            }
            
            // this is a regular select box
            for(var idx in data) {
                var item = data[idx];
                var opt = null;
                if(typeof item === 'string') {
                    var selected = (selectedIndex > 0 && selectedVal == item) ? true : false;
                    opt = new Option(item, item, selected, selected);
                } else {
                    var selected = selectedIndex > 0 && selectedVal == item.value ? true : false;
                    opt = new Option(item.text, item.value, selected, selected);
                }
                elem.append(opt);
            }
            
            if(elem.hasClass('select2')) {
                // this is a select2 component, must notify change
                elem.trigger('change');
            }
        };
        
        /**
         * Makes an Ajax call and set the response (json) into a html component.
         * 
         * @param url Ajax call to be made to this URI (do not need to include the web context - i.e. /api/my/service)
         * @param method HTTP method (GET, POST, PUT, DELETE, etc)
         * @param componentSelector jquery selector to find the element
         */
        self.loadAjaxIntoComponent = function(url, method, componentSelector) {
            $.ajax({
                method: method,
                url: self.getWebContext() + url

            }).done(function(data, status, xhr) {
                
                // dynamically find and execute the method according to the element type
                var elem = $(componentSelector);
                var elemType = elem.get(0).tagName.toLowerCase();
                var elemConfig = self.settings().htmlElements[elemType];
                if(!elemConfig) {
                    throw 'Element type \''+elemType+'\' not supported';
                }
                var methodName = elemConfig.load;
                var method = self[methodName];
                
                // call method to load data into a specific component type
                method(componentSelector, data);
                /*
                if(elem.is('select')) {
                    self.loadDataIntoSelect(componentSelector, data);
                }
                */

            }).fail(function(xhr, message, exception) {
                self.errorHandling(xhr, message, exception);
            });
        };
        
        /**
         * Method to configure standard CRUD page functionalities to the UI.
         * 
         * @param crudSettings settings page controller specific settings
         * 
         * @returns a reference to the plugin itself
         */
        self.crud = function(settings) {
            return self.page('crud', settings);
        };
        
        /**
         * Method to configure standard page functionalities to the UI.
         * 
         * @param stereotype page stereotype (page, crud)
         * @param settings page controller specific settings
         * 
         * @returns a reference to the plugin itself
         */
        self.page = function(stereotype, settings) {
            if(!stereotype || typeof stereotype !== 'string') {
                throw 'stereotype is invalid: '+stereotype;
            }
            var stereotypeConfig = self.settings().stereotypes[stereotype];
            if(!stereotypeConfig) {
                throw 'stereotype is not recognized: '+stereotype;
            }
            console.trace('Configuring page stereotype: '+stereotype);
            // instantiate a new controller object
            var stereotypeController = new stereotypeConfig.controller(self);
            
            console.trace('Page controller: '+stereotypeController.constructor.name);
            self.settings().pageType = stereotype;
            self.settings().pageController = stereotypeController;
            
            if(settings) {
                //$.extend(true, stereotypeController.settings(), settings);
                stereotypeController.settings(settings);
            }
            
            return self;
        };
        
        /**
         * Binds a controller to the page.
         * 
         * @param controller reference to the controller constructor (function) or an instance (object) to it
         * 
         * @returns a reference to the plugin itself
         */
        self.bind = function(controller) {
            if(!controller) {
                return self;
            }
            if(typeof controller === 'function') {
                console.trace('Instanciating new controller: '+controller.name);
                controller = new controller();
            }
            if(typeof controller !== 'object') {
                throw 'Controller is not an object';
            }
            
            var stereotypeController = self.settings().pageController;
            controller = $.extend(true, stereotypeController.settings().controller, controller);
            
            return self;
        };
        
        /**
         * Initialize the plugin
         */
        self.init = function() {
            configureWebContext();
            configureCsrfForAjax();
            
            loadTemplatesAlert();
            
            var stereotypeController = self.settings().pageController;
            stereotypeController.init();
            stereotypeController.onPageLoad();
            
            return self;
        };
        
        /**
         * Called upon page unload
         */
        self.unload = function() {
            var stereotypeController = self.settings().pageController;
            stereotypeController.onPageUnload();
        };
        
        return self;
    };

    // Expose the Casper plugin on jQuery.Casper (or $.Casper for short)
    $.Casper = new CasperPlugin();

    /* Casper plugin's public methods */
    /* settings, page, crud, bind, loadTemplates, init, alert, spinner, hideSpinner, dialog, confirm */

}(jQuery, window));

(function($) {
    $(document).ready(function() {
        //$.Casper.page('page').bind(SomeController).init();
        //$.Casper.crud().bind(SomeController).init();
    });
    
    $(window).unload(function() {
        // page unload (NOT WORKING)
        $.Casper.unload();
    });
}(jQuery));
