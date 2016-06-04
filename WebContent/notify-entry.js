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

/** lookup constant name from value */
EventSource.prototype.byIndex = function(i){
  var ii = 0;

  for (var lbl in this){
    if (ii == i)
      return lbl;

    ii += 1;
  }
};

function initEventSource(attempt = 0) {
  var es = new EventSource("http://localhost:4567/stream");
  console.info('Attempt #' + attempt + ' connecting to ' + es.url);

  es.onerror = function(e) {
    if (es.readyState != EventSource.CONNECTING) {
      console.warn('EventSource error; state is ' + es.byIndex(es.readyState));
    } else {
      es.close();
      backoff = Math.pow(2, attempt++);
      console.log('Error establishing EventSource connection to ' + es.url + '; trying again in ' + backoff + ' seconds');
      setTimeout(initEventSource, 1000 * backoff, attempt);
    }
  };

  es.onopen = function(e) {
    attempt = 0;
    console.log('Successfully established EventSource connection to ' + es.url);
  };

  es.onmessage = function(event) {
    console.info(event);

    var doc = JSON.parse(event.data);

    if (!Notify.needsPermission) {
        doNotification(doc);
    } else if (Notify.isSupported()) {
        Notify.requestPermission(onPermissionGranted(doc), onPermissionDenied(doc));
    }
  };
};

initEventSource();
