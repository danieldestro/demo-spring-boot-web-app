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
// Casper JQuery Plugin Classes

console.debug('Casper JQuery Plugin Classes Loading...');

/**
 * Template of a page Controller with empty methods.
 * Controllers may implement any (or all) of the methods to add functionality to the UI.
 */
function CasperVoidController() {

    this.init = function() {}
    
    /* methods related to page events */
    this.onPageLoad = function() {}
    this.onPageUnload = function() {}
    
    /* methods related to records listing events */
    this.onSearch = function() {}
    this.afterSearch = function() {}
    this.onClear = function() {}
    this.afterClear = function() {}
    this.onRefresh = function() {}
    this.afterRefresh = function() {}
    this.onPaging = function(page) {}
    this.afterPaging = function(page) {}
    
    /* methods related to single record events */
    this.onNew = function() {}
    this.afterNew = function() {}
    this.onView = function(id) {}
    this.afterView = function(id,data) {}
    this.onEdit = function(id) {}
    this.afterEdit = function(id,data) {}
    this.onDelete = function(id) {}
    this.afterDelete = function(id) {}
    this.onCancel = function() {}
    this.afterCancel = function() {}
    this.onSave = function() {}
    this.afterSave = function() {}
}

/**
 * Implementation class to manage aspects of basic stereotype pages.
 * 
 * @param casperPlugin reference to the Casper JQuery Plugin instance
 */
function CasperPageStereotypeController(casperPlugin) {
    
    var self = this;
    var plugin = casperPlugin;
    
    var _settings = {
        
        controller: new CasperVoidController(),
        
        templates: {},
        
        actions: {}
    };
    
    self.settings = function(options) {
        var config = $.extend(true, _settings, options);
        return config;
    };
    
    /**
     * Loads all the Mustache JS templates for displaying page components.
     * See: "_settings.templates" section above.
     */
    function loadTemplates() {
        plugin.loadTemplates(self.settings().templates, true);
    }
    
    /**
     * onPageLoad
     */
    self.onPageLoad = function() {
        self.settings().controller.onPageLoad();
    };
    
    /**
     * onPageUnload
     */
    self.onPageUnload = function() {
        self.settings().controller.onPageUnload();
    };
    
    /**
     * init
     * @returns returns a reference to the own page controller
     */
    self.init = function() {
        loadTemplates();
        return self;
    };
    
    return self;
}

/***********************************************************************************/
/***********************************************************************************/
/***********************************************************************************/

/**
 * Implementation class to manage aspects of CRUD stereotype pages.
 * 
 * @param casperPlugin reference to the Casper JQuery Plugin instance
 */
function CasperCrudStereotypeController(casperPlugin) {
    
    var self = this;
    var plugin = casperPlugin;
    
    var _settings = {
        
        controller: new CasperVoidController(),
        
        templates: {
            table: 'template-crud-tbl',
            form: 'template-crud-frm'
        },
        
        containers: {
            list: 'box-page-list',
            table: 'box-page-table',
            form: 'box-page-record',
            pagination: 'box-page-pagination'
        },
        
        forms: {
            search: 'frm-page-search',
            record: 'frm-page-record'
        },
        
        fields: {
            id: {
                name: 'id',
                type: 'int'
            },
            lockVersion: {
                name: 'lockVersion',
                type: 'int'
            },
            audit: {
                name: 'audit[id]',
                type: 'int'
            }
        },
        
        apis: {
            list: {
                url: null,
                method: 'GET'
            },
            save: {
                url: null,
                method: 'POST'
            },
            update: {
                url: null,
                method: 'PUT'
            },
            'delete': {
                url: null,
                method: 'DELETE'
            },
            load: {
                url: null,
                method: 'GET'
            }
        },
        
        actions: {
            btnSearch: {
                id: 'btn-page-search',
                click: function() {
                    self.onSearch();
                }
            },
            btnClear: {
                id: 'btn-page-clear',
                click: function() {
                    self.onClear();
                }
            },
            btnRefresh: {
                id: 'btn-page-refresh',
                click: function() {
                    self.onRefresh();
                }
            },
            btnNew: {
                id: 'btn-page-new',
                click: function() {
                    self.onNew();
                }
            },
            btnView: {
                name: 'btn-page-view',
                perRecord: true,
                click: function() {
                    self.onView($(this).attr('data-id'));
                }
            },
            btnEdit: {
                name: 'btn-page-edit',
                perRecord: true,
                click: function() {
                    self.onEdit($(this).attr('data-id'));
                }
            },
            btnDelete: {
                name: 'btn-page-delete',
                perRecord: true,
                confirm: true,
                click: function() {
                    var button = self.settings().actions.btnDelete;
                    var message = 'Do you want to delete the record?';
                    var id = $(this).attr('data-id');
                    self.checkConfirmDialog(button, message, function() {
                        self.onDelete(id);
                    });
                }
            },
            btnCancel: {
                id: 'btn-page-cancel',
                click: function() {
                    self.onCancel();
                }
            },
            btnSave: {
                id: 'btn-page-save',
                click: function() {
                    self.onSave();
                }
            }
        },
        
        validation: {}
    };
    
    /**
     * Opens up a confirmation dialog to the user and call the correspondent callback method according to the
     * user action (confirm / cancel). If the actionElement does not require confirmation, it will not show
     * the dialog and will call the callbackConfirm method and ignore the callbackCancel method.
     * 
     * @param actionElement reference to the action element configuration (i.e. self.settings().actions.btnDelete) 
     * @param message message to be displayed in the dialog (can use some html tags)
     * @param callbackConfirm callback method called when dialog is confirmed
     * @param callbackCancel callback method called when dialog is canceled
     */
    self.checkConfirmDialog = function(actionElement, message, callbackConfirm, callbackCancel) {
        var confirm = actionElement.confirm;
        
        if(confirm) {
            // user confirmation required
            plugin.confirm(message, callbackConfirm, callbackCancel);
            
        } else {
            // user confirmation not needed
            if(callbackConfirm) {
                callbackConfirm();
            }
        }
    };
    
    /**
     * This method allow to overwrite or change Casper CRUD Page settings.
     * All parameters are based on the "_settings" structure.
     * If a parameter is not informed it will remain unchanged.
     * 
     * @param options settings to overwrite the default ones.
     * 
     * @returns returns the page stereotype configurations
     */
    self.settings = function(options) {
        if(options) {
            setRestfulApi(options);
        }
        var config = $.extend(true, _settings, options);
        return config;
    };
    
    /**
     * If restful API URL is set, then sets the same URL to all API settings options.
     * 
     * @options settings configuration according to '_settings' structure.
     */
    function setRestfulApi(options) {
        if(options.apis && options.apis.restful) {
            var apis = self.settings().apis;
            for( var api in apis ) {
                apis[api].url = options.apis.restful.url;
            }
        }
    }
    
    /**
     * Load a list of records via API call and display them in the table.
     * 
     * @param callbackWhenFinished method callback when this method finishes
     */
    function listRecords(callbackWhenFinished) {
        var conf = self.settings();
        var spinnerContainer = '#'+conf.containers.list;

        $.ajax({
            method: conf.apis.list.method,
            url: conf.apis.list.url,
            //url : $.Casper.getWebContext() + '/api/manager/application',
            data: $('#'+conf.forms.search).serialize(),

            beforeSend : function() {
                plugin.spinner(spinnerContainer);
            }

        }).done(function(data, status, xhr) {

            var payload = {
                data: data
            };
            
            displayRecords(payload);

        }).fail(function(xhr, message, exception) {
            plugin.errorHandling(xhr, message, exception);

        }).always(function(dataOrXhr, status, xhrOrException) {
            
            registerRecordsActionButtonEvents();
            plugin.hideSpinner(spinnerContainer);
            
            if(callbackWhenFinished) {
                callbackWhenFinished();
            }
        });
    }
    
    /**
     * Display data (json format) according to the template provided.
     * 
     * @param data the data structure itself
     */
    function displayRecords(data) {
        var container = '#'+self.settings().containers.table;
        var template = self.settings().templates.table;
        $.Casper.applyTemplate(container, template, data);

        buildPagination(data);
    }
    
    /**
     * Build the pagination component in the UI.
     * 
     * @param data the data structure itself
     */
    function buildPagination(data) {
        var paginationContainer = '#'+self.settings().containers.pagination;
        $.Casper.pagination(paginationContainer, data.data, function(page) {
            self.onPaging(page);
        });
    }
    
    /**
     * Delete a record.
     * 
     * @param id id of the record to be deleted
     * @param callbackWhenFinished 
     */
    function deleteRecord(id, callbackWhenFinished) {
        
        var conf = self.settings();
        var spinnerContainer = '#'+conf.containers.list;

        $.ajax({
            method : conf.apis['delete'].method,
            url: conf.apis['delete'].url + id,

            beforeSend : function() {
                plugin.spinner(spinnerContainer);
            }

        }).done(function(data, status, xhr) {
            
            var info = {
                code : 0,
                message : 'Record deleted sucessfully'
            };

            //plugin.alert('warn', info);
            //plugin.toaster('warn', info);
            plugin.banner('warn', info);
            
            listRecords();
            
            closeEditPanelWhenNecessary(id);

        }).fail(function(xhr, message, exception) {
            plugin.errorHandling(xhr, message, exception);

        }).always(function(dataOrXhr, status, xhrOrException) {
            plugin.hideSpinner(spinnerContainer);
            
            if(callbackWhenFinished) {
                callbackWhenFinished();
            }
        });
    }
    
    /**
     * View record details in the form (not editable).
     * 
     * @param id id of the record for lookup and display
     * @param callbackWhenFinished
     */
    function viewRecord(id, callbackWhenFinished) {
        editRecord(id, true, callbackWhenFinished);
    }
    
    /**
     * Edit record details in the form.
     * 
     * @param id id of the record for lookup and display for edition
     * @param readOnly indicates if it is read only
     * @param callbackWhenFinished
     */
    function editRecord(id, readOnly, callbackWhenFinished) {

        var conf = self.settings();
        var form = conf.containers.form;
        var container = '#'+form;
        
        clearRecordForm();
        $(container).show();
        
        $.ajax({
            method : conf.apis.load.method,
            url: conf.apis.load.url + id,

            beforeSend : function() {
                plugin.spinner(container);
            }

        }).done(function(data, status, xhr) {
            
            loadRecordToForm(data, readOnly);

        }).fail(function(xhr, message, exception) {
            plugin.errorHandling(xhr, message, exception);

        }).always(function(dataOrXhr, status, xhrOrException) {
            plugin.hideSpinner(container);
            
            if(callbackWhenFinished) {
                callbackWhenFinished(id, dataOrXhr);
            }
        });
    }
    
    /**
     * Serialize a form (its fields) to a JSON string.
     * 
     * @param frmSelector jquery selector for the form
     * @returns a json string
     */
    function serializeFormToJSon(frmSelector) {
        //var jsonData = JSON.stringify($(frmSelector).serialize()); // jquery api
        //var jsonData = JSON.stringify($(frmSelector).jsonify()); // jsonify plugin
        var jsonData = $(frmSelector).serializeJSON(); // serializeJSON plugin
        //var jsonData = JSON.stringify($(frmSelector).jsForm("get"), null, " "); // jsForm plugin
        //var jsonData = JSON.stringify(transForm.serialize(frmSelector));
        return jsonData;
    }
    
    /**
     * Deserialize a javascript object (json notation) to a form (its fields).
     * 
     * @param frmSelector jquery selector for the form
     * @param jsonData object containing the data to be deserialized
     */
    function deserializeJsonToForm(formSelector, jsonData) {
        //$(formSelector).dejsonify(jsonData); // jsonify plugin
        //$(formSelector).jsForm({ data: jsonData }); // jsForm plugin
        transForm.deserialize(formSelector, jsonData);
    }
    
    /**
     * Save the record details from the form.
     */
    function saveRecord(callbackWhenCompleted) {
        var conf = self.settings();
        var spinnerContainer = '#'+conf.containers.form;
        var frm = '#'+conf.forms.record;
        
        var idFieldName = self.settings().fields.id.name;
        var idField = $(frm).find('[name='+idFieldName+']');
        
        var which = null;
        if(idField && idField.val() != '') {
            which = 'update';
        } else {
            which = 'save';
        }
        
        $(frm).validate({
            rules: conf.validation
        });
        
        if($(frm).valid()) {
            var jsonData = serializeFormToJSon(frm);
            
            $.ajax({
                method : conf.apis[which].method,
                url: conf.apis[which].url,
                data: jsonData,
                contentType: 'application/json',
                dataType: 'json',

                beforeSend : function() {
                    plugin.spinner(spinnerContainer);
                }

            }).done(function(data, status, xhr) {
                
                var info = {
                    code : 0,
                    message : 'Record saved sucessfully'
                };

                //plugin.alert('success', info);
                //plugin.toaster('success', info);
                plugin.banner('success', info);
                
                var idElemName = '#'+self.settings().fields.id.name;
                $(frm+' '+idElemName).val(data.id);

                // without the lockVersion, the update doesn't work
                if(data.hasOwnProperty('lockVersion')){
                    var lockVersionElemName = '#'+self.settings().fields.lockVersion.name;
                    $(frm+' '+lockVersionElemName).val(data.lockVersion);
                }
                
                // without the audit, the update doesn't work
                if(data.hasOwnProperty('audit') && data.audit.hasOwnProperty('id')){
                    var auditElemName = '#'+self.settings().fields.audit.name;
                    $(frm+' '+auditElemName).val(data.audit.id);
                }
                
                listRecords();

            }).fail(function(xhr, message, exception) {
                plugin.errorHandling(xhr, message, exception);

            }).always(function(dataOrXhr, status, xhrOrException) {
                plugin.hideSpinner(spinnerContainer);
                
                if(callbackWhenCompleted) {
                    callbackWhenCompleted();
                }
            });
        }
    }
    
    /**
     * Loads all the Mustache JS templates for displaying CRUD page components.
     * See: "_settings.templates" section above.
     */
    function loadTemplatesCrud() {
        plugin.loadTemplates(self.settings().templates, true);
        // more templates can be provided in the page controller settings
        var settingsMethod = self.settings().controller.settings;
        if(settingsMethod) {
            plugin.loadTemplates(settingsMethod().templates, true);
        }
    }
    
    /**
     * Register events for action buttons related to page itself.
     */
    function registerPageActionButtonEvents() {
        var actions = self.settings().actions;
        for( var action in actions ) {
            var button = actions[action];
            if(button.perRecord) {
                continue;
            }
            registerActionButtonEvent(button, action);
        }
    }
    
    /**
     * Register events for action buttons related to individual records displayed.
     */
    function registerRecordsActionButtonEvents() {
        var actions = self.settings().actions;
        for( var action in actions ) {
            var button = actions[action];
            if(!button.perRecord) {
                continue;
            }
            registerActionButtonEvent(button, action);
        }
    }
    
    /**
     * Register an action/event for a specific action button.
     * 
     * @param button the button configuration element
     * @param action the action configuration element
     * @throws exception case button id or name attribute is not provided
     */
    function registerActionButtonEvent(button, action) {
        if(button.id) {
            $('#'+button.id).click(button.click);
        } else if(button.name) {
            $('[name="'+button.name+'"]').click(button.click);
        } else {
            throw 'Action button does not define id or name attributes: '+action;
        }
    }
    
    /**
     * Clear form fields.
     */
    function clearRecordForm() {
        loadRecordToForm(null);
    }
    
    /**
     * Load record details into the form fields.
     * 
     * @param data data object to be loaded into the field
     * @param readOnly flag to determine whether the form is read-only or not
     */
    function loadRecordToForm(data, readOnly) {
        if (!data) {
            data = {};
        }
        
        // set record to form fields
        var frm = self.settings().forms.record;
        
        // enable all form fields so they can be cleared
        $('#'+frm+' :input').attr('disabled', false);
        
        deserializeJsonToForm('#'+frm, data);
        
        // enable or disable all form fields according to readOnly value
        $('#'+frm+' :input').attr('disabled', readOnly ? true : false);
        
        // enable cancel button always
        $('#'+self.settings().actions.btnCancel.id).attr('disabled', false);
    }
    
    /**
     * Clear all form fields values.
     * 
     * @param formSelector jquery selector to identify the form component
     */
    function clearFormFields(formSelector) {
        // clear all form fields
        deserializeJsonToForm(formSelector, {});
        
        // select2 components need special treatment
        $(formSelector+' .select2').each(function() {
            $(this).trigger('change');
            //$(this).val(null).trigger('change');
        });
    }
    
    /**
     * Closes the edit form if the deleted entity was the one under edition.
     * 
     * @param id of the entity that was deleted.
     */
    function closeEditPanelWhenNecessary(id){
        
        var container = self.settings().containers.form;
        var idFieldName = self.settings().fields.id.name;
        var currentEntityId = $('#' + container + ' ' + 'input[name=\"' + idFieldName + '\"]').val();
        if(currentEntityId === id){
            $('#'+container).hide();
        }
    }
    
    /**
     * onPageLoad
     */
    self.onPageLoad = function() {
        self.settings().controller.onPageLoad();
        registerPageActionButtonEvents();
        listRecords();
    };
    
    /**
     * onPageUnload
     */
    self.onPageUnload = function() {
        self.settings().controller.onPageUnload();
    };
    
    /**
     * onSearch
     */
    self.onSearch = function() {
        self.settings().controller.onSearch();

        var callbackAfter = self.settings().controller.afterSearch;
        listRecords(callbackAfter);
    };
    
    /**
     * onClear
     */
    self.onClear = function() {
        self.settings().controller.onClear();
        
        // clear form fields
        var frm = self.settings().forms.search;
        clearFormFields('#'+frm);

        var callbackAfter = self.settings().controller.afterClear;
        listRecords(callbackAfter);
    };
    
    /**
     * onRefresh
     */
    self.onRefresh = function() {
        self.settings().controller.onRefresh();

        var callbackAfter = self.settings().controller.afterRefresh;
        listRecords(callbackAfter);
    };
    
    /**
     * onPaging
     * @param pageNumber page number to browse to
     */
    self.onPaging = function(pageNumber) {
        self.settings().controller.onPaging(pageNumber);
        
        var frm = self.settings().forms.search;
        $('#'+frm+' #frm-page-number').val(pageNumber);

        var callbackAfter = self.settings().controller.afterPaging;
        listRecords(callbackAfter);
    };
    
    /**
     * onNew
     */
    self.onNew = function() {
        self.settings().controller.onNew();
        
        var container = self.settings().containers.form;
        removeValidationErrors('#'+container);
        $('#'+container).show(100, function() {
            loadRecordToForm(null);
        });

        var callbackAfter = self.settings().controller.afterNew;
        callbackAfter();
    };
    
    /**
     * Removes all validation errors from the provided form.
     * 
     */
    function removeValidationErrors(formElement){
        try{
            //Internal $.validator is exposed through $(form).validate()
            var validator = $(formElement).validate();
            //Iterate through named elements inside of the form, and mark them as error free
            $('[name]',formElement).each(function(){
                validator.successList.push(this);//mark as error free
                validator.showErrors();//remove error messages if present
            });
            validator.resetForm();//remove error class on name elements and clear history
            validator.reset();//remove all error and success data
        }catch(err){
            // if any error happens here, this should not break the UI. 
        }
    }
    
    /**
     * onView
     * 
     * @param id id of the record to be viewed
     */
    self.onView = function(id) {
        self.settings().controller.onView(id);
        
        var container = self.settings().containers.form;
        removeValidationErrors('#'+container);

        var callbackAfter = self.settings().controller.afterView;
        viewRecord(id, callbackAfter);
    };
    
    /**
     * onEdit
     * 
     * @param id id of the record to be edited
     */
    self.onEdit = function(id) {
        self.settings().controller.onEdit(id);
        
        var container = self.settings().containers.form;
        removeValidationErrors('#'+container);

        var callbackAfter = self.settings().controller.afterEdit;
        editRecord(id, false, callbackAfter);
    };
    
    /**
     * onDelete
     * 
     * @param id id of the record to be deleted
     */
    self.onDelete = function(id) {
        self.settings().controller.onDelete(id);
               
        var callbackAfter = self.settings().controller.afterDelete;
        deleteRecord(id, callbackAfter);
    };
    
    /**
     * onCancel
     */
    self.onCancel = function() {
        self.settings().controller.onCancel();
        
        var container = self.settings().containers.form;
        removeValidationErrors('#'+container);
        $('#'+container).hide();

        var callbackAfter = self.settings().controller.afterCancel;
        callbackAfter();
    };
    
    /**
     * onSave
     */
    self.onSave = function() {
        self.settings().controller.onSave();

        var callbackAfter = self.settings().controller.afterSave;
        saveRecord(callbackAfter);
    };
    
    /**
     * init
     */
    self.init = function() {
        loadTemplatesCrud();
        self.settings().controller.init();
        return self;
    };
    
    return self;
}

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
