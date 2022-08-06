(function() {
    var app = angular.module("mainApp")
    app.controller("SaveDataController", [
        '$scope',
        'availableObjects',
        'ObjectService',
        'Upload',
        function($scope, availableObjects, ObjectService, Upload) {
          console.log("Reached SaveDataController");
          $scope.availableObjects;
          $scope.populateObj;
          $scope.toBeSaved = {};
          $scope.toBeSaved.attributes = [];
          $scope.selected;
          if (availableObjects) {
            $scope.availableObjects = availableObjects;
          }

          $scope.populateForm = function(data) {
            ObjectService.getObjectDetails(data).then(function(response) {
              $scope.toBeSaved = {};
              $scope.toBeSaved.attributes = [];
              $scope.populateObj = Object.values(response.data)[0];
              populateSavedObject($scope.populateObj);
            }, function(err) {
              if (err) throw err;
              alertify.error("Population failed");
            })
          }

          $scope.updateObject = function(label, value) {

            _.forEach($scope.toBeSaved.attributes, function(val, key) {
              if (label == val.label) {
                $scope.toBeSaved.attributes[key].value = value;
              }
            })
          }
          $scope.save = function() {
            let data = {
              moduleName: $scope.selected,
              attributes: $scope.toBeSaved.attributes
            }
            ObjectService.createObject(data).then(function(response) {
              alertify.success("Successfuly created");
            }, function(err) {
              alertify.error("Could not create the data");
            })
            console.log(data);
          }
          $scope.uploadCsv = function($files) {
            Upload.upload({
              url: 'my/upload/url',
              file: $files,
            }).progress(function(e) {}).then(function(data, status, headers, config) {
              // file is uploaded successfully
              console.log(data);
            });
          }



            function populateSavedObject(data) {
              _.forEach(Object.values(data), function(elem) {
                $scope.toBeSaved.attributes.push({
                  "label": elem,
                  "value": ""
                })
              })
            }
          }
]);
    })();
