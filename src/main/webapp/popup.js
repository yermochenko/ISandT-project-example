function showMessage(msg) {
	let div = document.createElement('div');
	div.classList.add('popup-message');
	div.classList.add('popup-message-in')
	let closeButton = document.createElement('button');
	closeButton.type = 'button';
	closeButton.appendChild(document.createTextNode('×'));
	const closer = function() {
		div.classList.add('popup-message-out');
		setTimeout(function() {
			document.body.removeChild(div);
		}, 500);
	};
	closeButton.addEventListener('click', closer);
	div.appendChild(closeButton);
	div.appendChild(document.createTextNode(msg));
	document.body.insertBefore(div, document.body.firstChild);
	setTimeout(closer, 5000);
}
