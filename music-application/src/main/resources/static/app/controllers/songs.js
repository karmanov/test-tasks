angular.module('SongsApp')
// Creating the Angular Controller
    .controller('SongsController', function ($http, $scope, AuthService) {
        var edit = false;
        $scope.buttonText = 'Create';
        var init = function () {
            $http.get('/song-application/songs').success(function (res) {
                $scope.songs = res;
                $scope.songForm.$setPristine();
                $scope.message = '';
                $scope.currentSong = null;
                $scope.buttonText = 'Create';

            }).error(function (error) {
                $scope.message = error.message;
            });
        };
        $scope.initEdit = function (song) {
            edit = true;
            $scope.currentSong = song;
            $scope.message = '';
            $scope.buttonText = 'Update';
        };
        $scope.initAdd = function () {
            edit = false;
            $scope.currentSong = null;
            $scope.songForm.$setPristine();
            $scope.message = '';
            $scope.buttonText = 'Create';
        };
        $scope.deleteSong = function (song) {
            $http.delete('/song-application/songs/' + song.id).success(function (res) {
                $scope.deleteMessage = "Success!";
                init();
            }).error(function (error) {
                $scope.deleteMessage = error.message;
            });
        };
        var editUser = function () {
            $http.put('/song-application/songs/' + $scope.currentSong.id, $scope.currentSong).success(function (res) {
                $scope.currentSong = null;
                $scope.confirmPassword = null;
                $scope.songForm.$setPristine();
                $scope.message = "Editting Success";
                init();
            }).error(function (error) {
                $scope.message = error.message;
            });
        };
        var addUser = function () {
            $http.post('/song-application/songs', $scope.currentSong).success(function (res) {
                $scope.currentSong = null;
                $scope.songForm.$setPristine();
                $scope.message = "Song Created";
                init();
            }).error(function (error) {
                $scope.message = error.message + "\n" + error.details;
            });
        };
        $scope.submit = function () {
            if (edit) {
                editUser();
            } else {
                addUser();
            }
        };
        init();

    });
