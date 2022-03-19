const baseUrl = "http://localhost:8080/v1";
const posts = "/posts"

// banner preview vars
const bannerUpload = document.querySelector("#bannerUpload")
const output = document.querySelector("#output")

// publish blog vars
const publishButton = document.querySelector("#publishButton")
const titleValue = document.getElementById("blogTitle").value;
const contentValue = document.getElementById("blogContent").value;

bannerUpload.onchange = () => {
    const [file] = bannerUpload.files
    if (file) {
        output.src = URL.createObjectURL(file)
    }
}

const publishPost = () => {
    const data = {
        "password": titleValue,
        "username": contentValue
    };

    fetch(baseUrl + posts, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
    })
        .then(response => response.json())
        .then(d => {
            console.log('Success:', d);
        })
        .catch((error) => {
            console.error('Error:', error);
        });
}

publishButton.onclick = () => {
    publishPost();
}