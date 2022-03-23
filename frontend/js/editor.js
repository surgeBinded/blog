const baseUrl = "http://localhost:8080/api/v1";
const article = "/article";

// banner preview vars
const bannerUpload = document.querySelector("#bannerUpload");
const output = document.querySelector("#output");

// publish blog vars
const publishButton = document.querySelector("#publishButton");

bannerUpload.onchange = () => {
    // show a preview of the banner image
    const [file] = bannerUpload.files
    console.log(file)
    if (file) {
        output.src = URL.createObjectURL(file)
        console.log(output)
        console.log(output.src)
    }
}

const publishPost = () => {
    const titleValue = document.querySelector("#blogTitle").value;
    const contentValue = document.querySelector("#blogContent").value;

    const data = {
        "title": titleValue,
        "content": contentValue
    };

    fetch(baseUrl + article, {
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