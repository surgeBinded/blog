const baseUrl = "http://localhost:8080/api/v1";
const article = "/article";

// banner preview vars
const bannerUpload = document.querySelector("#bannerUpload");
const output = document.querySelector("#output");

// publish blog vars
const publishButton = document.querySelector("#publishButton");
let base64image = "something";

bannerUpload.onchange = () => {

    // I don't know how this works, but it converts images into base64
    let file = document.querySelector(
        'input[type=file]')['files'][0];
    let reader = new FileReader();
    reader.onload = function () {
        base64image = reader.result.replace("data:", "")
            .replace(/^.+,/, "");
    }
    reader.readAsDataURL(file);

    // show a preview of the banner image
    [file] = bannerUpload.files
    if (file) {
        output.src = URL.createObjectURL(file)
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