
function RoleCrudController() {
	
    var self = this;
    
    var _settings = {
        templates: {
            view: 'template-view-permission',
            edit: 'template-edit-permission',
            editItem: 'template-edit-permission-item'
        }
    };

    self.settings = function() {
        return _settings;
    };

    self.init = function() {
        /*
        $.Casper.loadTemplate('template-view-permission');
        $.Casper.loadTemplate('template-edit-permission');
        $.Casper.loadTemplate('template-edit-permission-item');
    	*/
    }
    
    self.onNew = function() {
    	// clear the permissions table
    	$.Casper.applyTemplate('#box-permissions', 'template-edit-permission', {});
    }

    self.afterNew = function() {
    	initializePermissionAutocomplete();
    }

    self.afterView = function(id, data) {
    	populatePermissions(true, data);
    }
    
    self.afterEdit = function(id, data) {
    	populatePermissions(false, data);
    }
    
    /* used by the autocomplete */
    self.permissionsCache = {}
    
    function populatePermissions(readOnly, data){
    	var permissionsContainer = '#box-permissions'; 
        var template = readOnly === false ? 'template-edit-permission' : 'template-view-permission'; 
        $.Casper.applyTemplate(permissionsContainer, template, data);
        
        if(readOnly === false){
        	initializePermissionAutocomplete();
        	initializePermissionTableBinds();
        }
    }
    
    function initializePermissionAutocomplete() {
		$('#addPermission').autocomplete({
			source : function(request, response) {
				var term = request.term;
				if (term in self.permissionsCache) {
					response(removeSelectedPermissions(self.permissionsCache[term]));
					return;
				}
				$.ajax({
					url : $.Casper.getWebContext() + '/api/security/permissions',
					method : 'GET',
					contentType : 'application/json',
					dataType : 'json',
					data : { page : '', filter : term },
					success : function(data) {
						var results = [];
						$.each(data.content, function(index, value) {
							results.push({ label : value.name, value : value });
						});
						self.permissionsCache[term] = results;
						response(removeSelectedPermissions(results));
					}
				});
			},
			minLength : 3,
			select : permissionSelected
		});
	}
    
    function removeSelectedPermissions(permissions){
    	var filteredArray = [];
    	var selectedPermissionIds = $('#tbl-list-permissions').find('input[name="permissions[][id]"]')
			.map(function() {
			   return parseInt($(this).val());
			}).get(); 
    	
    	$.each(permissions, function(index, permissionWrapper) {
    		if(!selectedPermissionIds.includes(permissionWrapper.value.id)){
    			filteredArray.push(permissionWrapper);
    		}
		});    	
    	return filteredArray;
    }
    
    function initializePermissionTableBinds(){
    	$('*[name=btn-delete-permission]').on('click', function(e) {
    		$(this).closest('tr').remove();      
    	});
    }
    
    function permissionSelected(event, ui) {
		// clear the input
		$(this).val('');
		// adds an entry in the permissions table...
		$('#tbl-list-permissions tr:last').after($.Mustache.render('template-edit-permission-item', ui.item.value));
		initializePermissionTableBinds();
		return false;
	}
    
    return self;
}
