# WebQRHunt
Web based QR scavenger hunt

### How to use
1. [Download](https://github.com/maolivas1/WebQRHunt/releases)
2. Port forward and run the server (port 8887)
3. Change the IP address in `QR Hunt Client/client.js` to your [public ip address](https://www.google.com/search?q=what+is+my+ip) or the ip your hosting the server at
4. Host the client on a website
5. [Generate QR codes](https://www.flowcode.com/) that link to the webpage with the ?c tag for [each id](https://github.com/maolivas1/WebQRHunt/blob/d92e37f9cd340f033c8341b9de0ed1c272e8a18e/QR%20Hunt%20Server/src/me/Mark/qr/Thingy.java#L45) for example `http://markstuff.net/qr?c=nMntWa6H`
6. Play

### Notes
* To reset a game or kick someone go to your webpage with the ?c tag `host` for example `http://markstuff.net/qr?c=host`
* To check the score without scanning a code go to your webpage with no ?c tag for example `http://markstuff.net/qr`
* The server only supports one active gave at a time
