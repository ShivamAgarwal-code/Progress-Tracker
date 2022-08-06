(function() {
  'use strict'
  var app = angular.module("mainApp");
  app.controller("CreateModuleController", [
    '$scope',
    'CreateId',
    'ModuleCrudService',
    'EventCrudService',
    'ObjectService',
    'availableObjects',
    function($scope, CreateId, ModuleCrudService, EventCrudService, ObjectService, availableObjects) {
      console.log('%c Reached Module Controller', 'color:green');
      $(function() {
        $('[data-toggle="tooltip"]').tooltip()
      })
      $scope.availableObjects = [];
      if (availableObjects) {
        $scope.availableObjects = availableObjects;
      }
      $scope.moduleObject = {};
      $scope.moduleObject.attributes = [];
      const dummy = {
        id: "",
        label: "Label"
      }

      let newObj = Object.create(dummy);
      newObj.id = CreateId.create();
      newObj.label = "Label"
      $scope.moduleObject.attributes.push(newObj);

      $scope.moduleType = {
        "name": "Module Type",
        "value": "Object",
        "values": ["Object", "Event"]
      }

      $scope.addAttributes = function() {
        newObj = Object.create(dummy);
        newObj.id = CreateId.create();
        newObj.label = "Label";
        $scope.moduleObject.attributes.push(newObj);
      }

      $scope.removeAttributes = function() {
        if ($scope.moduleObject.length > 1) {
          $scope.moduleObject.attributes.pop();
        }
      }
      
      $scope.saveModule = function() {

        if ($scope.moduleType.value == 'Object') {
          ModuleCrudService.saveModule($scope.moduleObject).then(function success(response) {
            alertify.success('Successfuly created');
          }, function error(err) {
            alertify.error('Something is wrong with the API');
          })
        } else {
          EventCrudService.saveModule($scope.moduleObject).then(function success(response) {
            alertify.success('Successfuly created');
          }, function error(err) {
            alertify.error('Something is wrong with the API');
          })
        }

      }

    }
  ]);
})();
