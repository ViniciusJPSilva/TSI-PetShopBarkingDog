/*
	Script de transição do cabeçalho.
 */
window.addEventListener("scroll", () => {
	let header = document.querySelector(".header");
	header.classList.toggle("sticky", window.scrollY > 0);
});




