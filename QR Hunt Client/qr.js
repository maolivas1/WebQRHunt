var colors;
function handel(msg) {
	if (msg == "no") {
		document.getElementById("signin").id = "signup";
	} else if (msg.substring(0, 2) == "go"){
	    go(msg);
	} else if (msg.substring(0, 4) == "kick"){
		kick(msg.substring(5));
	} else if (msg.substring(0, 1) == "#"){
		colors = msg;
	}
}
function signin() {
	var nick = document.getElementById("nick").value;
	if (nick == "") document.getElementById("nick").style.border = "thick solid red";
	else {
	send("login " + nick);
	start();
	document.getElementById("signup").id = "signin";
	}
}
function getColor(id) {
	var c = colors.split("#");
	if (id == "A") return c[1];
	else if (id == "B") return c[2];
	else if (id == "C") return c[3];
	else if (id == "D") return c[4];
	else if (id == "E") return c[5];
	else if (id == "F") return c[6];
	else if (id == "G") return c[7];
	else if (id == "H") return c[8];
	else if (id == "I") return c[9];
	else if (id == "J") return c[10];
	return "fff";
}
function kick(name) {
	document.getElementById(name).parentElement.remove();
}
function yes() {
	document.getElementById("kick").className = "hide";
	var name = document.getElementById("kick").getAttribute('name');
	send("kick," + name);
}
function no() {
	document.getElementById("kick").className = "hide";
	document.getElementById("kick").removeAttribute("name");
}
function show(name) {
	if (get() == "host") {
	document.getElementById("kick").className = "show";
	document.getElementById("kicktext").innerHTML = "Kick " + name + "?";
	document.getElementById("kick").setAttribute("name", name);
	}
}
function start() {
	var id = get();
	if (id != false) send("qr," + id);
}
function go(msg) {
	var name = msg.split(",")[1];
	var n;
	if (document.getElementById(name)) {
		document.getElementById(name).innerHTML = name;
		n = document.getElementById(name);
	} else {
		var f = document.createElement('div');
		f.id = "frame";
		f.onclick = function() { show(name); };
		n = document.createElement('span');
		n.id = name;
		n.className = "name";
		n.innerHTML = name;
		f.appendChild(n);
		document.getElementById("body").appendChild(f);
}
		var l = msg.split(",");
        for (i = 2; i < l.length; i++) {
		var s = document.createElement('span');
		s.id = "letters";
		s.style.backgroundColor = getColor(l[i]);
		s.innerHTML = l[i];
		n.appendChild(s);
		}
}
function get() {
       var vars = window.location.search.substring(1).split("&");
       for (var i=0;i<vars.length;i++) {
               var pair = vars[i].split("=");
               if(pair[0] == "c"){return pair[1];}
       }
       return(false);
}