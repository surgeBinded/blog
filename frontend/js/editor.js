const baseUrl = "http://localhost:8080/api/v1";
const article = "/article";

// banner preview vars
const bannerUpload = document.querySelector("#bannerUpload");
const output = document.querySelector("#output");

// publish blog vars
const publishButton = document.querySelector("#publishButton");
let bannerImage = null

const toDataURL = url => fetch(url)
    .then(response => response.blob())
    .then(blob => new Promise((resolve, reject) => {
        const reader = new FileReader()
        reader.onloadend = () => resolve(reader.result)
        reader.onerror = reject
        reader.readAsDataURL(blob)
    }))

bannerUpload.onchange = () => {
    // show a preview of the banner image
    const [file] = bannerUpload.files
    console.log(file)

    if (file) {
        output.src = URL.createObjectURL(file)
        toDataURL(output.src)
            .then(dataUrl => {
                bannerImage = dataUrl
                console.log('RESULT:', dataUrl)
            })
    }
}

const publishPost = () => {
    const titleValue = document.querySelector("#blogTitle").value;
    const contentValue = $('#summernote').summernote('code');

    const data = {
        "title": titleValue,
        "content": contentValue,
        "bannerImage": bannerImage
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
    location.href = "/index.html"
}