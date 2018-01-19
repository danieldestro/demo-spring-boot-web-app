
function ConfigurationCrudController() {

    var self = this;
    
    function loadContexts() {
        var url = '/api/admin/configurations/contexts';
        var elementSelector = '#frm-record-context';
        $.Casper.loadAjaxIntoComponent(url, 'GET', elementSelector);
    }
    
    /* methods related to page events */
    
    self.onPageLoad = function() {
        loadContexts();
    };
    
    /* methods related to records listing events */
    
    self.afterClear = function() {
        loadContexts();
    };
    
    self.afterRefresh = function() {
        loadContexts();
    };
    
    self.afterPaging = function(page) {
        loadContexts();
    };
    
    /* methods related to single record events */
    
    self.afterEdit = function(id,data) {
        loadContexts();
    };
    
    self.afterDelete = function(id) {
        loadContexts();
    };
    
    self.afterSave = function() {
        loadContexts();
    };
    
    return self;
}
