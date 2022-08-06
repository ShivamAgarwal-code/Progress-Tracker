'use strict'
var app = angular.module("mainApp");

app.service("ModuleCrudService", function($http){
  this.saveModule = function saveModule(data){
    return $http({
      method : 'POST',
      url : '/create_object/',
      data : {
    	  	moduleName: data.moduleName,
        attributes: data.attributes
      }
    })
  }
})
