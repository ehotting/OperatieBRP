angular.module('DatumFormats', [])
.directive('lo3Datumtijd', function ($window) {
    return {
        restrict: 'A',
        require: 'ngModel',
        link: function (scope, element, attrs, ngModel) {
            var moment = $window.moment;
            moment.locale('nl', {
                longDateFormat: {
                    LT: "HH:mm",
                    ll: "YYYY-MM-DD",
                    lll: "YYYY-MM-DD LT"
                }
            });

            ngModel.$formatters.push(toView);
            ngModel.$parsers.push(toModel);

            element.on('change', function (e) {
                var element = e.target;
                element.value = formatter(ngModel.$modelValue);
            });

            function toView(value) {
                var m = moment(value, "YYYY-MM-DD HH:mm:ss", true);
                var valid = m.isValid();
                if (valid) {
                    return m.valueOf();
                }  else {
                    return null;
                }
            }

            function toModel(value) {
                if(value) {
                    var m = moment(value);
                    var valid = m.isValid();
                    ngModel.$setValidity('datumtijd', valid);
                    if (valid) {
                        return m.format("YYYY-MM-DD HH:mm:ss");
                    } else {
                        return null;
                    }
                } else {
                    return null;
                }
            }
        } //link
    };
})//appDatumtijd
.directive('lo3Datum', function ($window) {
    return {
        restrict: 'A',
        require: 'ngModel',
        link: function (scope, element, attrs, ngModel) {
            var moment = $window.moment;

            ngModel.$formatters.push(toView);
            ngModel.$parsers.push(toModel);

            element.on('change', function (e) {
                var element = e.target;
                element.value = formatter(ngModel.$modelValue);
            });

            function toView(value) {
                var m = moment(value, "YYYYMMDD", true);
                var valid = m.isValid();
                if (valid) {
                    return m.valueOf();
                }  else {
                    return null;
                }
            }

            function toModel(value) {
                if(value) {
                    var m = moment(value);
                    var valid = m.isValid();
                    ngModel.$setValidity('datumtijd', valid);
                    if (valid) {
                        return m.format("YYYYMMDD");
                    } else {
                        return null;
                    }
                } else {
                    return null;
                }
            }
        } //link
    };
})//appDatumtijd
; // DatumFormats