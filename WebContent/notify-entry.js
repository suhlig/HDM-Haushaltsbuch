var es = new EventSource('http://localhost:4567/stream');
var Notify = window.Notify.default;
  
function onShowNotification () {
  // console.log('notification is shown!');
}

function onCloseNotification () {
  // console.log('notification is closed!');
}

function onClickNotification () {
  // console.log('notification was clicked!');
}

function onErrorNotification () {
  console.error('Error showing notification. You may need to request permission.');
}

function onPermissionGranted (data) {
  console.log('Permission has been granted by the user');
  doNotification(data);
}

function onPermissionDenied() {
  console.warn('Permission has been denied by the user');
}

function doNotification(event) {
  var myNotification = new Notify("Neue Buchung", {
    body: event.payment_type + ": " + event.value + "€ von/nach " + event.src_dest + ": " + event.description,
    tag: event.id,
    notifyShow: onShowNotification,
    notifyClose: onCloseNotification,
    notifyClick: onClickNotification,
    notifyError: onErrorNotification,
    timeout: 10
  });
  
  myNotification.show();
}

es.onmessage = function(e) { 
  var event = JSON.parse(e.data);
  
  console.log(event);
  
  if (!Notify.needsPermission) {
      doNotification(event);
  } else if (Notify.isSupported()) {
      Notify.requestPermission(onPermissionGranted(event), onPermissionDenied(event));
  }
};
