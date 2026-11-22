// Fade cards in as they scroll into view.
const items = document.querySelectorAll('.reveal');

if ('IntersectionObserver' in globalThis) {
  const observer = new IntersectionObserver(
    (entries) => {
      for (const entry of entries) {
        if (entry.isIntersecting) {
          entry.target.classList.add('visible');
          observer.unobserve(entry.target);
        }
      }
    },
    { threshold: 0.15 }
  );
  for (const el of items) {
    observer.observe(el);
  }
} else {
  for (const el of items) {
    el.classList.add('visible');
  }
}
