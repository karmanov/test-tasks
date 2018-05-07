angular.module('CakesApp')
// Creating the Angular Controller
    .controller('CakesController', function ($http, $scope, AuthService) {
        var edit = false;
        $scope.buttonText = 'Create';
        var init = function () {
            $http.get('/cake-application/cakes').success(function (res) {
                $scope.cakes = res;
                $scope.cakeForm.$setPristine();
                $scope.message = '';
                $scope.currentCake = null;
                $scope.buttonText = 'Create';

            }).error(function (error) {
                $scope.message = error.message;
            });
        };
        $scope.initEdit = function (cake) {
            edit = true;
            $scope.currentCake = cake;
            $scope.message = '';
            $scope.buttonText = 'Update';
        };
        $scope.initAdd = function () {
            edit = false;
            $scope.currentCake = null;
            $scope.cakeForm.$setPristine();
            $scope.message = '';
            $scope.buttonText = 'Create';
        };
        $scope.deleteCake = function (cake) {
            $http.delete('/cake-application/cakes/' + cake.id).success(function (res) {
                $scope.deleteMessage = "Success!";
                init();
            }).error(function (error) {
                $scope.deleteMessage = error.message;
            });
        };
        var editCake = function () {
            $http.put('/cake-application/cakes/' + $scope.currentCake.id, $scope.currentCake).success(function (res) {
                $scope.currentCake = null;
                $scope.confirmPassword = null;
                $scope.cakeForm.$setPristine();
                $scope.message = "Editting Success";
                init();
            }).error(function (error) {
                $scope.message = error.message;
            });
        };
        var addCake = function () {
            $http.post('/cake-application/cakes', $scope.currentCake).success(function (res) {
                $scope.currentCake = null;
                $scope.cakeForm.$setPristine();
                $scope.message = "Cake Created";
                init();
            }).error(function (error) {
                $scope.message = error.message + "\n" + error.details;
            });
        };
        $scope.submit = function () {
            if (edit) {
                editCake();
            } else {
                addCake();
            }
        };
        init();

    });
