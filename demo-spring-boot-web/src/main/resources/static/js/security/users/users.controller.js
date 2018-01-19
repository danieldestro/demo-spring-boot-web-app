
function UserCrudController() {
    
    var self = this;
    
    var _settings = {
        templates: {
            view: 'template-view-role',
            edit: 'template-edit-role',
            editItem: 'template-edit-role-item'
        }
    };

    self.settings = function() {
        return _settings;
    };

    self.init = function() {
        /*
    	$.Casper.loadTemplate('template-view-role');
    	$.Casper.loadTemplate('template-edit-role');
    	$.Casper.loadTemplate('template-edit-role-item');
    	*/
    }
    
    self.onNew = function() {
    	// clear the roles table
    	$.Casper.applyTemplate('#box-roles', 'template-edit-role', {});
    }
    
    self.afterNew = function() {
    	initializeRoleAutocomplete();
    	initializeEmployeeNumberLoader();
    }
    
    self.afterView = function(id, data) {
    	populateRoles(true, data);
    }
    
    self.afterEdit = function(id, data) {
    	populateRoles(false, data);
    }
    
    /* used by the autocomplete */
    self.rolesCache = {}
    
    function populateRoles(readOnly, data) {
    	var rolesContainer = '#box-roles'; 
        var template = readOnly === false ? 'template-edit-role' : 'template-view-role'; 
        $.Casper.applyTemplate(rolesContainer, template, data);
        
        if(readOnly === false){
        	initializeRoleAutocomplete();
        	initializeRoleTableBinds();
        }
    }
    
    function initializeRoleAutocomplete() {
		$('#addRole').autocomplete({
			source : function(request, response) {
				var term = request.term;
				if (term in self.rolesCache) {
					response(removeSelectedRoles(self.rolesCache[term]));
					return;
				}
				$.ajax({
					url : $.Casper.getWebContext() + '/api/security/roles',
					method : 'GET',
					contentType : 'application/json',
					dataType : 'json',
					data : { page : '', filter : term },
					success : function(data) {
						var results = [];
						$.each(data.content, function(index, value) {
							results.push({ label : value.name, value : value });
						});
						self.rolesCache[term] = results;
						response(removeSelectedRoles(results));
					}
				});
			},
			minLength : 3,
			select : roleSelected
		});
	}
    
    function initializeEmployeeNumberLoader() {
    	$('#emailAddress').on('change', function(e) {
    		var emailAddress = $(this).val();

    		$.ajax({
				url : $.Casper.getWebContext() + '/api/security/users/employeeNumber/' + encodeURIComponent(emailAddress),
				method : 'GET',
				contentType : 'application/json',
				success : function(data) {	
					$('#employeeNumber').val(data);
				},
				statusCode: {
				    404: function() {
				    	$('#employeeNumber').val('');
				    	return false;
				    }
				}
			});
    	});
    }
    
    function removeSelectedRoles(roles) {
    	var filteredArray = [];
    	var selectedRoleIds = $('#tbl-list-roles').find('input[name="roles[][id]"]')
			.map(function() {
			   return parseInt($(this).val());
			}).get(); 
    	
    	$.each(roles, function(index, roleWrapper) {
    		if(!selectedRoleIds.includes(roleWrapper.value.id)) {
    			filteredArray.push(roleWrapper);
    		}
		});    	
    	return filteredArray;
    }
    
    function initializeRoleTableBinds() {
    	$('*[name=btn-delete-role]').on('click', function(e) {
    		$(this).closest('tr').remove();      
    	});
    }
    
    function roleSelected(event, ui) {
		// clear the input
		$(this).val('');
		// adds an entry in the roles table...
		$('#tbl-list-roles tr:last').after($.Mustache.render('template-edit-role-item', ui.item.value));
		initializeRoleTableBinds();
		return false;
	}
    
    return self;
}
