const bannerUpload = document.querySelector("#bannerUpload")
const output = document.querySelector("#output")

bannerUpload.onchange = () => {
    const [file] = bannerUpload.files
    if (file) {
        output.src = URL.createObjectURL(file)
    }
}