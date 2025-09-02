	const slider = document.getElementById("slider");
	const dotsContainer = document.getElementById("dots");
	let index = 0;
	let sliderCount = 0;
	let dots = [];
	
	function loadImages(i = 1) {
	const img = new Image();
	img.src = `image/slider/${i}.jpg`;
	
	img.onload = () => {
	    slider.appendChild(img);
	    sliderCount++;
	    loadImages(i + 1);
	};
	
	img.onerror = () => {
	    if (sliderCount > 0) {
	    createDots();
	    startSlider();
	    }
	};
	}
	
	function createDots() {
	for (let i = 0; i < sliderCount; i++) {
	    const dot = document.createElement("span");
	    dot.textContent = "•";
	    if (i === 0) dot.classList.add("active");
	    dot.addEventListener("click", () => goToSlide(i));
	    dotsContainer.appendChild(dot);
	    dots.push(dot);
	}
	}
	
	function startSlider() {
	setInterval(() => {
	    nextSlide();
	}, 3000);
	}
	
	function nextSlide() {
	index = (index + 1) % sliderCount;
	updateSlider();
	}
	
	function goToSlide(i) {
	index = i;
	updateSlider();
	}
	
	function updateSlider() {
	const sliderWidth = slider.clientWidth;
	slider.style.transform = `translateX(-${sliderWidth * index}px)`;
	dots.forEach((dot, i) => dot.classList.toggle("active", i === index));
	}
	
	window.addEventListener("resize", () => {
	const sliderWidth = slider.clientWidth;
	slider.style.transform = `translateX(-${sliderWidth * index}px)`;
	});
	
	loadImages();
	
	
