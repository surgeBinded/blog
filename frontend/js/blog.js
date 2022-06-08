const baseUrl = "http://localhost:8080/api/v1";
const articles = "/articles";

const para = new URLSearchParams(window.location.search);
const id = para.get("ID");

const fetchArticle = () => {
    fetch(baseUrl + articles + "/" + id)
        .then(response => response.json())
        .then(element => {
                document.getElementById("articleTitle").innerText = element.title;
                document.getElementById("articlePublishDate").innerText = element.dateCreated;
                document.getElementById("articleContent").innerHTML = element.content;
                const image = document.createElement("img")
                image.setAttribute("class", "banner")
                image.setAttribute("id", "output")
                image.setAttribute("src", `${element.bannerImage ? element.bannerImage : 'img/aerial-background-blog-cafe.jpg'}`)

                document.getElementById("articleBanner").append(image);
        })
}

fetchArticle();