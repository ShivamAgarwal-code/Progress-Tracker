var app = angular.module("mainApp");
app.service('CreateId', function() {


  this.create = function() {
    return '_' + Math.random().toString(36).substr(2, 9);
  }
});
