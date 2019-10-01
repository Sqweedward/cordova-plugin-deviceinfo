// cordova.define("cordova-plugin-deviceinfo.DeviceInfo", function (require, exports, module) {
var exec = require('cordova/exec');

exports.getDiviceInfo = function (success, error) {
    exec(success, error, 'DeviceInfo', 'getdeviceInfo', []);
};
// });
