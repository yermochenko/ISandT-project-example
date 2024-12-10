const tabs = [];
let current = 0;

window.addEventListener('load', function() {
	let tabsDiv = document.getElementById('tabs');
	tabsDiv.style.display = 'flex';
	let tabButtons = tabsDiv.getElementsByTagName('button');
	let formTabDivs = document.getElementsByClassName('form-tab');
	for(let i = 1, n = formTabDivs.length; i < n; i++) {
		formTabDivs.item(i).style.display = 'none';
	}
	let size = Math.min(tabButtons.length, formTabDivs.length);
	for(let i = 0; i < size; i++) {
		let tab = {
			button: tabButtons.item(i),
			formDiv: formTabDivs.item(i)
		};
		tab.button.addEventListener('click', function() {
			if(i !== current) {
				tabs[current].button.classList.replace('form-tab-link-active', 'form-tab-link');
				tabs[current].formDiv.style.display = 'none';
				tabs[i].button.classList.replace('form-tab-link', 'form-tab-link-active');
				tabs[i].formDiv.style.display = 'block';
				current = i;
			}
		});
		tabs.push(tab);
	}
});
