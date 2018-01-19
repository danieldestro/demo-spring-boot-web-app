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
