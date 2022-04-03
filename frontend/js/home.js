const baseUrl = "http://localhost:8080/api/v1";
const articles = "/articles";

const articleCard = () => {
    fetch(baseUrl + articles)
        .then(response => response.json())
        .then(data => {
            data.forEach(element => {
                const card = document.createElement("div");
                card.setAttribute("class", "blog-card");
                card.innerHTML = `
                <img src=${element.bannerImage ? element.bannerImage : "img/aerial-background-blog-cafe.jpg"} class="blog-image" alt="" />
                <h1 class="blog-title">${element.title}</h1>
                <p class="blog-overview">${element.content}</p>
                <a href="/" class="btn dark">read</a>
            `;
                document.querySelector("section").append(card);
            })
        });
}

articleCard()