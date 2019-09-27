
// Empty constructor
function DeviceInfo() {}

// The function that passes work along to native shells
// Message is a string, duration may be 'long' or 'short'
DeviceInfo.prototype.show = function(operation, successCallback, errorCallback) {
  var options = {};
    options.operation = operation;
    cordova.exec(successCallback, errorCallback, 'DeviceInfo', 'getDeviceInfo', [options]);
}

// Installation constructor that binds DeviceInfo to window
DeviceInfo.install = function() {
  if (!window.plugins) {
    window.plugins = {};
  }
    window.plugins.deviceInfo = new DeviceInfo();
    return window.plugins.deviceInfo;
};
cordova.addConstructor(DeviceInfo.install);